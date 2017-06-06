package com.example.wangli.signin;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class WoyaoqiandaoActivity extends AppCompatActivity {
    private EditText editText_kouling;
    private String kouling;
    private String date;
    private Button button_back;
    private ProgressDialog progressDialog;
    private BroadcastReceiver mReceiver;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothAdapter smBluetoothAdapter;
    private String data;
    private String laoshilanya;
    private String[] ab=new String[20];
    private int i=0;
    private SimpleDateFormat CurrentTime= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private String systemstartdate;
    private String systemrundate;
    private Date beginTime;
    private Date runTime;
    private String studentlanya;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_woyaoqiandao);
        editText_kouling = (EditText) findViewById(R.id.editText_kouling);
        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        smBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //studentlanya=smBluetoothAdapter.getAddress();
        studentlanya=GetAdress.getBtAddressViaReflection();

        progressDialog=new ProgressDialog(WoyaoqiandaoActivity.this);
        progressDialog.setTitle("正在检测周围环境,以确定您是否在教室内,请耐心等待...");
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
        //setProgressBarIndeterminateVisibility(true);
    }
    public void onClickQianDao(View view) {
        kouling = editText_kouling.getText().toString();
        if (kouling.equals("")) {
            Toast.makeText(WoyaoqiandaoActivity.this, "口令不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        BmobQuery<Dianming> query = new BmobQuery<Dianming>();
        query.addWhereEqualTo("Kouling", kouling);
        query.findObjects(new FindListener<Dianming>() {
            @Override
            public void done(List<Dianming> list, BmobException e) {
                if (list != null && list.size() > 0) {
                    for (Dianming dianming : list) {
                        final SimpleDateFormat CurrentTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = dianming.getCreatedAt();
                        laoshilanya=dianming.getLanyadizhi();
                        String systemdate = CurrentTime.format(new java.util.Date());
                        try {
                            final Date beginTime = CurrentTime.parse(date);
                            Date endTime = CurrentTime.parse(systemdate);
                            if (((endTime.getTime() - beginTime.getTime()) / (10 * 60 * 1000)) >= 1) {
                                //大于10分钟
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
                                Toast.makeText(WoyaoqiandaoActivity.this, "签到已关闭，口令已删除", Toast.LENGTH_LONG).show();
                                return;
                            } else {
                                boolean haha=false;
                                for(int j=0;j<i;j++)
                                {
                                    if(ab[j].equals(laoshilanya))
                                    {
                                        Toast.makeText(WoyaoqiandaoActivity.this, "搜索成功", Toast.LENGTH_LONG).show();
                                        haha=true;
                                        Long left = 10 * 60 * 1000 - (endTime.getTime() - beginTime.getTime());
                                        long minute;
                                        minute = left / (60 * 1000);
                                        AlertDialog.Builder dialog = new AlertDialog.Builder(WoyaoqiandaoActivity.this);
                                        dialog.setTitle("口令正确！");
                                        dialog.setMessage("请填写信息");
                                        dialog.setCancelable(false);
                                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent(WoyaoqiandaoActivity.this, InformationActivity.class);
                                                intent.putExtra("kouling", kouling);
                                                intent.putExtra("studentlanya",studentlanya);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                                        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        });
                                        dialog.show();
                                        break;
                                    }
                                }
                                if(haha==false){
                                    Toast.makeText(WoyaoqiandaoActivity.this, "搜索失败，请确认您在教室内！", Toast.LENGTH_LONG).show();
                                    return;
                                }
                            }
                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(WoyaoqiandaoActivity.this, "口令不存在", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //解除注册
        unregisterReceiver(mReceiver);
    }
}

