package com.lxkj.xpp.mvpdemo.app;

import android.app.Application;
import android.util.Log;

import com.lxkj.xpp.mvpdemo.db.DatabaseHelper;

/**
 * Created by Administrator on 2016/12/2.
 */

public class DemoApplication extends Application {
    private String TAG = this.getClass().getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
        DatabaseHelper.init(this);
    }
}
