package com.example.wangli.signin;

import android.app.Application;

import cn.bmob.v3.Bmob;

/**
 * Created by Wangli on 2016/10/28.
 */
public class MyUserNameApplication extends Application {
    String name;
    String courseName;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"87258b4d59e381e2c21d18f8ff590423");
    }
}
