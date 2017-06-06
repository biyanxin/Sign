package com.example.wangli.signin;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class Chakanlichang extends AppCompatActivity {
    private Button button_back;
    private ListView listView_watch;
    KechengAdapter adapter;
    private String name;
    private String kname;
    private String shiyou;
    private BluetoothAdapter mBluetoothAdapter;
    private BroadcastReceiver mReceiver;
    private int i=0;
    private SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String systemstartdate;
    private String systemrundate;
    private Date beginTime;
    private Date runTime;
    private String[] ab=new String[40];
    private String data;
    private ProgressDialog progressDialog;
    private List<Studentshuaxin> watchlist=new ArrayList<Studentshuaxin>();
    private List<String> lichanglist;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chakanlichang);
        Intent intent1=getIntent();
        shiyou=intent1.getStringExtra("shiyou");
        listView_watch=(ListView)findViewById(R.id.listView_watch);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name=((MyUserNameApplication)getApplication()).getName();
        kname=((MyUserNameApplication)getApplication()).getCourseName();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // TODO Auto-generated method stub
                String action = intent.getAction();
                // 获得已经搜索到的蓝牙设备
                if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // 搜索到的不是已经绑定的蓝牙设备
                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                        data=device.getAddress();
                        ab[i++] =data;
                    }
                    systemrundate = CurrentTime.format(new java.util.Date());
                    try{
                        beginTime=CurrentTime.parse(systemstartdate);
                        runTime=CurrentTime.parse(systemrundate);
                        if(((runTime.getTime() - beginTime.getTime())/(10*1000))>=1) {
                            //大于10秒
                            progressDialog.dismiss();
                            setTitle("检测时间到");
                        }
                    }catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    // 搜索完成
                } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                    //setProgressBarIndeterminateVisibility(false);
                    progressDialog.dismiss();
                    setTitle("检测完成");
                }
            }
        };
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //setProgressBarIndeterminateVisibility(true);
        progressDialog=new ProgressDialog(Chakanlichang.this);
        progressDialog.setTitle("正在检测周围环境,以确定学生是否在教室内,请耐心等待...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        systemstartdate = CurrentTime.format(new java.util.Date());
        setTitle("正在扫描....");
        // 如果正在搜索，就先取消搜索
        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        // 开始搜索蓝牙设备,搜索到的蓝牙设备通过广播返回
        mBluetoothAdapter.startDiscovery();

        IntentFilter mFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, mFilter);
        // 注册搜索完时的receiver
        mFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(mReceiver, mFilter);

    }
    public void chaKanqingdan(View view){
        initdatas();
    }


    private void initdatas(){
        lichanglist=new ArrayList<String>();
        BmobQuery<Qiandao> query=new BmobQuery<Qiandao>();
        query.addWhereEqualTo("name",name);
        query.addWhereEqualTo("Kname",kname);
        query.addWhereEqualTo("Shiyou",shiyou);
        query.findObjects(new FindListener<Qiandao>(){
            @Override
            public void done(List<Qiandao> list, BmobException e) {
                if(list!=null&&list.size()>0){
                    for(Qiandao qiandao:list){
                        Studentshuaxin studentshuaxin=new Studentshuaxin();
                        studentshuaxin.setXuejiaxing(qiandao.getXuehao()+qiandao.getXingming());
                        studentshuaxin.setStulanya(qiandao.getLanyadizhi());
                        watchlist.add(studentshuaxin);
                    }

                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            check();

                            adapter=new KechengAdapter(Chakanlichang.this,R.layout.kecheng_item,lichanglist);
                            listView_watch.setAdapter(adapter);
                            //adapter.notifyDataSetChanged();

                            watchlist.removeAll(watchlist);
                        }
                    });
                }else {
                    Toast.makeText(Chakanlichang.this, "没有人签到哦！", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });


    }

    private void check() {
        for(int k=0;k<watchlist.size();k++){
            int j=0;
            for(;j<i;j++){
                if(watchlist.get(k).getStulanya().equals(ab[j])){
                    lichanglist.add(watchlist.get(k).getXuejiaxing());
                    Toast.makeText(Chakanlichang.this, "未离场", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
            if(j==i){
                lichanglist.add(watchlist.get(k).getXuejiaxing()+"     （已离场）");
                Toast.makeText(Chakanlichang.this, "已离场！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //解除注册
        unregisterReceiver(mReceiver);
    }
}
