package com.lxkj.administrator.pos.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2016/11/1.
 */
public class BaseApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
    }
}
