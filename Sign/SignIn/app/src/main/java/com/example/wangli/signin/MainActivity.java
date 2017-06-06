package com.example.wangli.signin;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity  {
    private Button button_denglu;
    private Button button_zhuce;
    private EditText mName,mPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Bmob.initialize(this,"87258b4d59e381e2c21d18f8ff590423");
        button_zhuce=(Button) findViewById(R.id.button_zhuce);
        button_denglu=(Button) findViewById(R.id.button_denglu);
        mName=(EditText)findViewById(R.id.editText_name);
        mPwd =(EditText) findViewById(R.id.editText_pwd);
        button_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,ZhuCeActivity.class);
                startActivity(intent);
            }
        });
    }
    public void denglu(View view) {
        final String name = mName.getText().toString();
        final String pwd = mPwd.getText().toString();
        if (name.equals("") || pwd.equals("")) {
            Toast.makeText(MainActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        BmobQuery<YongHu> query = new BmobQuery<YongHu>();
        query.addWhereEqualTo("name",name);
        query.addWhereEqualTo("pwd",pwd);
        query.findObjects(new FindListener<YongHu>(){
            @Override
            public void done(List<YongHu> list, BmobException e) {
                if(list!=null&&list.size()>0){
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(MainActivity.this,Main2Activity.class);
                    //name1.setName(name);
                    ((MyUserNameApplication)getApplication()).setName(name);
                    //intent.putExtra("name",name);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
