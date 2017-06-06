package com.example.wangli.signin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class KechengguanliActivity extends Activity {
   private Button button_tianjiakecheng;
    private ListView listView_kechengliebiao;
    private List<String> courselist=new ArrayList<String>();
    private String name;
    private int i=0;
    private Button button_back;
    KechengAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kechengguanli);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        name=((MyUserNameApplication)getApplication()).getName();
        listView_kechengliebiao=(ListView)findViewById(R.id.listView_kechengliebiao);
        adapter=new KechengAdapter(KechengguanliActivity.this,R.layout.kecheng_item,courselist);
        listView_kechengliebiao.setAdapter(adapter);
        BmobQuery<Kecheng> query=new BmobQuery<Kecheng>();
        query.addWhereEqualTo("name",name);
        query.findObjects(new FindListener<Kecheng>(){
            @Override
            public void done(List<Kecheng> list, BmobException e) {
                if(list!=null&&list.size()>0){
                    for(Kecheng kecheng:list){
                        String data=kecheng.getKname();
                        courselist.add(data);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                }
            }
        });
        listView_kechengliebiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=courselist.get(position);
                Intent intent=new Intent(KechengguanliActivity.this,KchengmingActivity.class);
                intent.putExtra("coursename",str);
                startActivity(intent);
            }
        });
    }
    public void Tianjiakecheng(View view){
        Intent intent=new Intent(KechengguanliActivity.this,TianjiakechengActivity.class);
        startActivity(intent);
        finish();
    }
}
