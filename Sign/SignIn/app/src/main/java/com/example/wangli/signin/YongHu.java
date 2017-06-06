package com.example.wangli.signin;

import cn.bmob.v3.BmobObject;

/**
 * Created by Wangli on 2016/10/27.
 */
public class YongHu extends BmobObject {
    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
