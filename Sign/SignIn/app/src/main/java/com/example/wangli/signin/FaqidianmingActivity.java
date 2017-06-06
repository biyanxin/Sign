package com.example.wangli.signin;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class FaqidianmingActivity extends Activity {
    private EditText editText_shiyou ;
    private String name;
    private String kname;
    private String coursename;
    private Button button_back;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE = 1;
    String shiyou;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqidianming);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent_course=getIntent();
        coursename=intent_course.getStringExtra("coursename");
        editText_shiyou=(EditText)findViewById(R.id.editText_shiyou);
        name=((MyUserNameApplication)getApplication()).getName();
        kname=((MyUserNameApplication)getApplication()).getCourseName();
    }
    public void Quedingfaqi (View view){
        shiyou=editText_shiyou.getText().toString();
        if (shiyou.equals("")) {
            Toast.makeText(FaqidianmingActivity.this, "事由不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        //获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断是否硬件支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(FaqidianmingActivity.this, "本地蓝牙不可用", Toast.LENGTH_SHORT).show();
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
        }
       else {
            //获取本地蓝牙名称
            // String name = mBluetoothAdapter.getName();
            //获取本地蓝牙地址
            //String address = mBluetoothAdapter.getAddress();
            String address=GetAdress.getBtAddressViaReflection();
            //打印相关信息
            //Toast.makeText(FaqidianmingActivity.this,address, Toast.LENGTH_SHORT).show();
            final Dianming dianming = new Dianming();
            int numcode = (int) ((Math.random() * 9 + 1) * 100000);
            final String strings = "口令码为：" + numcode + "，限时10分钟";
            dianming.setKname(kname);
            Toast.makeText(FaqidianmingActivity.this, "老师"+name, Toast.LENGTH_LONG).show();
            dianming.setName(name);
            dianming.setShiyou(shiyou);
            dianming.setKouling(numcode + "");
            dianming.setLanyadizhi(address);
            dianming.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(FaqidianmingActivity.this);
                        dialog.setTitle("发起点名成功！");
                        dialog.setMessage(strings);
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(FaqidianmingActivity.this, KchengmingActivity.class);
                                intent.putExtra("coursename", coursename);
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
                    } else {
                        Toast.makeText(FaqidianmingActivity.this, "该事由已存在", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
     }
    @Override
    protected void onActivityResult(int requestCode,int resultcode,Intent data){
        switch (requestCode){
            case REQUEST_ENABLE:
                if(resultcode==RESULT_OK){
                    String address = mBluetoothAdapter.getAddress();
                    //打印相关信息
                    //Toast.makeText(FaqidianmingActivity.this,address, Toast.LENGTH_SHORT).show();
                    final Dianming dianming = new Dianming();
                    int numcode = (int) ((Math.random() * 9 + 1) * 100000);
                    final String strings = "口令码为：" + numcode + "，限时10分钟";
                    dianming.setKname(kname);
                    dianming.setName(name);
                    dianming.setShiyou(shiyou);
                    dianming.setKouling(numcode + "");
                    dianming.setLanyadizhi(address);
                    dianming.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(FaqidianmingActivity.this);
                                dialog.setTitle("发起点名成功！");
                                dialog.setMessage(strings);
                                dialog.setCancelable(false);
                                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent = new Intent(FaqidianmingActivity.this, KchengmingActivity.class);
                                        intent.putExtra("coursename", coursename);
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
                            } else {
                                Toast.makeText(FaqidianmingActivity.this, "该事由已存在", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(FaqidianmingActivity.this,"蓝牙打开失败，无法发起点名", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            default:
                break;
        }
    }
}
