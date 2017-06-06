package com.example.wangli.signin;

import cn.bmob.v3.BmobObject;

/**
 * Created by Wangli on 2016/10/28.
 */
public class Dianming extends BmobObject {
    private String name;
    private String Kname;
    private String Shiyou;
    private String Kouling;
    private String Lanyadizhi;

    public String getLanyadizhi() {
        return Lanyadizhi;
    }

    public void setLanyadizhi(String lanyadizhi) {
        Lanyadizhi = lanyadizhi;
    }

    public void setKouling(String kouling) {
        Kouling = kouling;
    }

    public String getKouling() {
        return Kouling;
    }

    public String getShiyou() {
        return Shiyou;
    }

    public void setShiyou(String shiyou) {
        Shiyou = shiyou;
    }

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
