package com.lxkj.administrator.pos.utils;

import android.content.Context;

import com.lxkj.administrator.pos.base.BaseApplication;

import java.util.Collection;
import java.util.Map;

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
    /**
     * 空判断
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(Object obj) {
        if (null == obj)
            return true;
        else if (obj == "")
            return true;
        else if (obj instanceof Integer || obj instanceof Long
                || obj instanceof Double) {
            // if((Integer.parseInt(obj.toString()))==0) return bool;
            try {
                Double.parseDouble(obj + "");
            } catch (Exception e) {
                return true;
            }
        } else if (obj instanceof String) {
            if (((String) obj).length() <= 0)
                return true;
            if ("null".equals(obj))
                return true;
        } else if (obj instanceof Map) {
            if (((Map) obj).size() == 0)
                return true;
        } else if (obj instanceof Collection) {
            if (((Collection) obj).size() == 0)
                return true;
        }
        return false;
    }
}
