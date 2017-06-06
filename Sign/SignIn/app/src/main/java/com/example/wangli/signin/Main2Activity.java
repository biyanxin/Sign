package com.example.wangli.signin;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends Activity {
    private Button ketangguanli;
    private Button button_back;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ketangguanli = (Button) findViewById(R.id.button_kechengguanli);
        ketangguanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, KechengguanliActivity.class);
                startActivity(intent);
            }
        });
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void Woyaoqiandao1(View view){
        //获取本地蓝牙适配器
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //判断是否硬件支持蓝牙
        if (mBluetoothAdapter == null) {
            Toast.makeText(Main2Activity.this, "本地蓝牙不可用", Toast.LENGTH_SHORT).show();
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
            Intent intent=new Intent(Main2Activity.this,WoyaoqiandaoActivity.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onActivityResult(int requestCode,int resultcode,Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE:
                if (resultcode == RESULT_OK) {
                    Intent intent=new Intent(Main2Activity.this,WoyaoqiandaoActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Main2Activity.this,"蓝牙打开失败，无法进行签到", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    public void qiTacaozuo(View view){
        Toast.makeText(Main2Activity.this,"暂时还没有实现功能哦！", Toast.LENGTH_SHORT).show();
    }
}
