package com.lxkj.xpp.mvpdemo.app;

import android.app.Application;

import com.lxkj.xpp.mvpdemo.db.DatabaseHelper;

/**
 * Created by Administrator on 2016/12/2.
 */

public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.init(this);
    }
}
