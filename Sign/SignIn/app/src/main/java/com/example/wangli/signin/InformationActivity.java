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

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class InformationActivity extends Activity {
    private EditText editText_xuehao;
    private EditText editText_xingming;
    private String kouling;
    String shiyou;
    String name;
    String kname;
    private Button button_back;
    private String studentlanya;
    private String xingming;
    private String xuehao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        kouling=intent.getStringExtra("kouling");
        studentlanya=intent.getStringExtra("studentlanya");
        editText_xingming=(EditText)findViewById(R.id.editText_xingming);
        editText_xuehao=(EditText)findViewById(R.id.editText_xuehao);
    }
    public void Submit(View view){
         xingming=editText_xingming.getText().toString();
         xuehao=editText_xuehao.getText().toString();
        if("".equals(xingming)||"".equals(xuehao)){
            Toast.makeText(InformationActivity.this, "学号或姓名不能为空！", Toast.LENGTH_LONG).show();
            return;
        }
        BmobQuery<Dianming> query = new BmobQuery<Dianming>();
        query.addWhereEqualTo("Kouling",kouling);
        query.findObjects(new FindListener<Dianming>(){
            @Override
            public void done(List<Dianming> list, BmobException e) {
                if(list!=null&&list.size()>0){
                    for(Dianming dianming:list){
                         shiyou=dianming.getShiyou();
                         name=dianming.getName();
                        kname=dianming.getKname();
                    }
                    BmobQuery<Qiandao> querylan = new BmobQuery<Qiandao>();
                    querylan.addWhereEqualTo("name",name);
                    querylan.addWhereEqualTo("Kname",kname);
                    querylan.addWhereEqualTo("Shiyou",shiyou);
                    querylan.findObjects(new FindListener<Qiandao>() {
                        @Override
                        public void done(List<Qiandao> list, BmobException e) {
                            if (list != null && list.size() > 0) {
                                for (Qiandao qiandaolan : list) {
                                    if (qiandaolan.getLanyadizhi().equals(studentlanya)) {
                                        Toast.makeText(InformationActivity.this, "该设备已签到！", Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                }
                                Qiandao qiandao = new Qiandao() ;
                                qiandao.setName(name);
                                qiandao.setShiyou(shiyou);
                                qiandao.setKname(kname);
                                qiandao.setXuehao(xuehao);
                                qiandao.setXingming(xingming);
                                qiandao.setLanyadizhi(studentlanya);
                                qiandao.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(InformationActivity.this);
                                            dialog.setTitle("签到成功！");
                                            dialog.setMessage("返回主界面");
                                            dialog.setCancelable(false);
                                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(InformationActivity.this, Main2Activity.class);
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
                                        }else{
                                            Toast.makeText(InformationActivity.this, "该学号已签到！", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                            else{
                                Qiandao qiandao = new Qiandao() ;
                                qiandao.setName(name);
                                qiandao.setShiyou(shiyou);
                                qiandao.setKname(kname);
                                qiandao.setXuehao(xuehao);
                                qiandao.setXingming(xingming);
                                qiandao.setLanyadizhi(studentlanya);
                                qiandao.save(new SaveListener<String>() {
                                    @Override
                                    public void done(String s, BmobException e) {
                                        if (e == null) {
                                            AlertDialog.Builder dialog = new AlertDialog.Builder(InformationActivity.this);
                                            dialog.setTitle("签到成功！");
                                            dialog.setMessage("返回主界面");
                                            dialog.setCancelable(false);
                                            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(InformationActivity.this, Main2Activity.class);
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
                                        }else{
                                            Toast.makeText(InformationActivity.this, "该学号已签到！", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }else {
                    Toast.makeText(InformationActivity.this, "口令已超时", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
