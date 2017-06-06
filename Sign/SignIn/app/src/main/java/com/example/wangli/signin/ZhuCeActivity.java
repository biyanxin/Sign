package com.example.wangli.signin;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class ZhuCeActivity extends Activity {
    private Button button_back;
    private EditText mName,mPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhu_ce);
        Bmob.initialize(this,"87258b4d59e381e2c21d18f8ff590423");
        mName=(EditText)findViewById(R.id.editText_name);
        mPwd =(EditText) findViewById(R.id.editText_pwd);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void Quedingzhuce(View view){
        String name = mName.getText().toString();
        String pwd = mPwd.getText().toString();
        if (name.equals("") || pwd.equals("")) {
            Toast.makeText(ZhuCeActivity.this, "用户名或密码不能为空", Toast.LENGTH_LONG).show();
            return;
        }
        YongHu yonghu = new YongHu() ;
        yonghu.setName(name);
        yonghu.setPwd(pwd);
        yonghu.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(ZhuCeActivity.this);
                    dialog.setTitle("注册成功！");
                    dialog.setMessage("返回登录界面");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog.show();
                }else{
                    Toast.makeText(ZhuCeActivity.this, "该用户已存在", Toast.LENGTH_LONG).show();
                }
            }

        });
    }
}
