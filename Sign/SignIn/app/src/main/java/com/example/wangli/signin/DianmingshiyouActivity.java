package com.example.wangli.signin;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.ListViewAutoScrollHelper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SQLQueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class DianmingshiyouActivity extends Activity {
    private Button button_back;
    private TextView textview_shiyou;
    private TextView textview_zhuangtai;
    private String shiyou;
    private ListView listView_qiandao;
    private List<String> qiandaolist=new ArrayList<String>();
    KechengAdapter adapter;
    private String name;
    private String date;
    private String kname;
    private long bian;
    private TextView textView_renshu;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianmingshiyou);
        Intent intent=getIntent();
        shiyou=intent.getStringExtra("shiyou");
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textview_shiyou=(TextView)findViewById(R.id.textView_shiyou);
        textView_renshu=(TextView)findViewById(R.id.textView_renshu) ;
        textview_zhuangtai=(TextView)findViewById(R.id.textView_zhuangtai) ;
        textview_shiyou.setText(shiyou);
        name=((MyUserNameApplication)getApplication()).getName();
        kname=((MyUserNameApplication)getApplication()).getCourseName();
        listView_qiandao=(ListView)findViewById(R.id.listView_qiandao);
        adapter=new KechengAdapter(DianmingshiyouActivity.this,R.layout.kecheng_item,qiandaolist);
        listView_qiandao.setAdapter(adapter);
        BmobQuery<Qiandao> query=new BmobQuery<Qiandao>();
        query.addWhereEqualTo("name",name);
        query.addWhereEqualTo("Kname",kname);
        query.addWhereEqualTo("Shiyou",shiyou);
        query.findObjects(new FindListener<Qiandao>(){
            @Override
            public void done(List<Qiandao> list, BmobException e) {
                if(list!=null&&list.size()>0){
                    int renshu=list.size();
                    textView_renshu.setText("人数："+renshu);
                    for(Qiandao qiandao:list){
                        String xuehao=qiandao.getXuehao();
                        String xingming=qiandao.getXingming();
                        String data=xuehao+xingming;
                        qiandaolist.add(data);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                }
            }
        });
        BmobQuery<Dianming> querytime=new BmobQuery<Dianming>();
        querytime.addWhereEqualTo("name",name);
        querytime.addWhereEqualTo("Kname",kname);
        querytime.addWhereEqualTo("Shiyou",shiyou);
        querytime.findObjects(new FindListener<Dianming>(){
            @Override
            public void done(List<Dianming> listtime, BmobException e) {
                if(listtime!=null&&listtime.size()>0){
                    for(final Dianming dianming:listtime){
                        if(dianming.getKouling()==""){
                            //口令为空说明已经结束；
                        }else{
                            SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            date=dianming.getCreatedAt();
                            String systemdate = CurrentTime.format(new java.util.Date());
                            try {
                                final Date beginTime=CurrentTime.parse(date);
                                final Date endTime=CurrentTime.parse(systemdate);
                                if(((endTime.getTime() - beginTime.getTime())/(10*60*1000))>=1) {
                                    //大于10分钟
                                    Dianming p2 = new Dianming();
                                    p2.setKouling("");
                                    p2.update(dianming.getObjectId(), new UpdateListener() {
                                        @Override
                                        public void done(BmobException e) {
                                            if(e==null){
                                            }else{
                                            }
                                        }
                                    });
                                }else {
                                    final long sum = (10 * 60 * 1000 - (endTime.getTime() - beginTime.getTime())) / 1000;
                                    final Handler handler = new Handler();
                                    Runnable runnable = new Runnable() {
                                        @Override
                                        public void run() {
                                            bian++;
                                            long left = sum - bian;
                                            long minute, second;
                                            minute = left / 60;
                                            second = left % 60;
                                            if (left == 0) {
                                                textview_zhuangtai.setText("状态:已结束");
                                                Dianming p2 = new Dianming();
                                                p2.setKouling("");
                                                p2.update(dianming.getObjectId(), new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                        if (e == null) {
                                                        } else {
                                                        }
                                                    }
                                                });
                                                return;
                                            }
                                            textview_zhuangtai.setText("状态:正在进行中   口令:" + dianming.getKouling() + "    还剩" + minute + "分" + second + "秒");
                                            handler.postDelayed(this, 1000);
                                        }
                                    };
                                    handler.postDelayed(runnable, 1000);
                                }
                            } catch (ParseException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }else {
                }
            }
        });
    }
    public void Suijidianming(View view){
        int n=qiandaolist.size();
        if(n>0){
            int str=(int)(Math.random()*100000%(n));
            String data=qiandaolist.get(str);
            AlertDialog.Builder builder = new AlertDialog.Builder(DianmingshiyouActivity.this);
            builder.setMessage(data);
            builder.create().show();
        }else {
            Toast.makeText(DianmingshiyouActivity.this, "没有人签到哦！", Toast.LENGTH_LONG).show();
        }
    }
    public void chaKanlichang(View view){
        //获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断是否硬件支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(DianmingshiyouActivity.this, "本地蓝牙不可用", Toast.LENGTH_SHORT).show();
            //退出应用
            return;
        }
        //判断是否打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            //弹出对话框提示用户是后打开
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE);
            //不做提示，强行打开
            //mBluetoothAdapter.enable();
        }else{
            Intent intent=new Intent(DianmingshiyouActivity.this,Chakanlichang.class);
            intent.putExtra("shiyou",shiyou);
            startActivity(intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode,int resultcode,Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE:
                if (resultcode == RESULT_OK) {
                    Intent intent=new Intent(DianmingshiyouActivity.this,Chakanlichang.class);
                    intent.putExtra("shiyou",shiyou);
                    startActivity(intent);
                }else{
                    Toast.makeText(DianmingshiyouActivity.this,"蓝牙打开失败，无法查看离场！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
