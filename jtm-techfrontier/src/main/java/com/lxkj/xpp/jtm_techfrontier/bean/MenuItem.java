package com.lxkj.xpp.jtm_techfrontier.bean;

/**
 * Created by Administrator on 2016/11/29.
 */

public class MenuItem {
    public int iconResId;
    public String text;

    public MenuItem() {
    }

    public MenuItem(String text, int resId) {
        this.text = text;
        iconResId = resId;
    }
}
