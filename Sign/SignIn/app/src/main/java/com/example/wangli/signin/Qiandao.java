package com.example.wangli.signin;

import android.view.View;

import cn.bmob.v3.BmobObject;

/**
 * Created by Wangli on 2016/10/28.
 */
public class Qiandao extends BmobObject {
    private String name;
    private String Kname;
    private String Shiyou;
    private String Xuehao;
    private String Xingming;
    private String Lanyadizhi;

    public String getLanyadizhi() {
        return Lanyadizhi;
    }

    public void setLanyadizhi(String lanyadizhi) {
        Lanyadizhi = lanyadizhi;
    }

    public String getXingming() {
        return Xingming;
    }

    public void setXingming(String xingming) {
        Xingming = xingming;
    }

    public void setXuehao(String xuehao) {
        Xuehao = xuehao;
    }

    public void setShiyou(String shiyou) {
        Shiyou = shiyou;
    }

    public void setKname(String kname) {
        Kname = kname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getShiyou() {
        return Shiyou;
    }

    public String getKname() {
        return Kname;
    }

    public String getXuehao() {
        return Xuehao;
    }
}
