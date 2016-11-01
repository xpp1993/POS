package com.lxkj.administrator.pos.utils;

import android.content.Context;

import com.lxkj.administrator.pos.base.BaseApplication;

/**
 * Created by Administrator on 2016/11/1.
 */
public class AppUtils {
    public static final String FIRSTInstall="firstInstall";
    public static Context getContext(){
        return BaseApplication.context;
    }

    public static boolean isFirstInstall(){
        return !PreferencesUtils.getInstance().getBoolean(getContext(),FIRSTInstall);
    }

    public static void firstInstall(){
        PreferencesUtils.getInstance().putBoolean(AppUtils.getContext(),FIRSTInstall,true);
    }
}
