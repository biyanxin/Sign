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

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class TianjiakechengActivity extends Activity {
   private EditText editText_kecheng;
    private String name;
    private Button button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        name=((MyUserNameApplication)getApplication()).getName();
        setContentView(R.layout.activity_tianjiakecheng);
        editText_kecheng=(EditText)findViewById(R.id.editText_shiyou);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void Quedingtianjia (View view){
        String kname=editText_kecheng.getText().toString();
        if (kname.equals("")) {
            Toast.makeText(TianjiakechengActivity.this, "课程名称不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        Kecheng kecheng = new Kecheng() ;
        kecheng.setKname(kname);
        kecheng.setName(name);
        kecheng.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(TianjiakechengActivity.this);
                    dialog.setTitle("添加成功！");
                    dialog.setMessage("返回课程管理界面");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(TianjiakechengActivity.this, KechengguanliActivity.class);
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
                    Toast.makeText(TianjiakechengActivity.this, "该课程已存在", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
