package com.example.wangli.signin;

import cn.bmob.v3.BmobObject;

/**
 * Created by Wangli on 2016/10/28.
 */
public class Kecheng extends BmobObject{
    private String name;
    private String Kname;

    public String getName() {
        return name;
    }

    public String getKname() {
        return Kname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKname(String kname) {
        Kname = kname;
    }
}
