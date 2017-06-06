package com.example.wangli.signin;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class KchengmingActivity extends Activity {
    private TextView textview_kchenming;
    private String coursename;
    private ListView listView_dianmingliebiao;
    private List<String> dianminglist=new ArrayList<String>();
    KechengAdapter adapter;
    private String name;
    private Button button_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kchengming);
        button_back=(Button)findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent_coursename=getIntent();
        coursename=intent_coursename.getStringExtra("coursename");
        textview_kchenming=(TextView)findViewById(R.id.textView_kchenming);
        textview_kchenming.setText(coursename);
        ((MyUserNameApplication)getApplication()).setCourseName(coursename);
        name=((MyUserNameApplication)getApplication()).getName();
        listView_dianmingliebiao=(ListView)findViewById(R.id.listView_dianmingliebiao);
        adapter=new KechengAdapter(KchengmingActivity.this,R.layout.kecheng_item,dianminglist);
        listView_dianmingliebiao.setAdapter(adapter);
        BmobQuery<Dianming> query=new BmobQuery<Dianming>();
        query.addWhereEqualTo("name",name);
        query.addWhereEqualTo("Kname",coursename);
        query.findObjects(new FindListener<Dianming>(){
            @Override
            public void done(List<Dianming> list, BmobException e) {
                if(list!=null&&list.size()>0){
                    for(Dianming dianming:list){
                        String data=dianming.getShiyou();
                        dianminglist.add(data);
                    }
                    adapter.notifyDataSetChanged();
                }else {
                }
            }
        });
        listView_dianmingliebiao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str=dianminglist.get(position);
                Intent intent=new Intent(KchengmingActivity.this,DianmingshiyouActivity.class);
                intent.putExtra("shiyou",str);
                startActivity(intent);
            }
        });
    }
    public void Faqidianming(View view){
        Intent intent=new Intent(KchengmingActivity.this,FaqidianmingActivity.class);
        intent.putExtra("coursename",coursename);
        startActivity(intent);
        finish();
    }
}
