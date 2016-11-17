package com.lxkj.administrator.pos.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lxkj.administrator.pos.bean.SystemBean;
import com.lxkj.administrator.pos.utils.MySqliteHelper;
import com.lxkj.administrator.pos.utils.ParameterManager;

/**
 * Created by Administrator on 2016/9/22.
 * 提供增删查改SystemBean表方法类
 */
public class SystemBeanService {
    MySqliteHelper mySqliteHelper;
    SQLiteDatabase database;
    public static final String TAG = SystemBeanService.class.getSimpleName();

    public SystemBeanService(MySqliteHelper mySqliteHelper) {
        this.mySqliteHelper = mySqliteHelper;
    }

    /**
     * 插入数据
     *
     * @param systemBean
     */
    public void insert(String tablename, SystemBean systemBean) {
        database = mySqliteHelper.getReadableDatabase();
        database.execSQL("insert into " + tablename +
                        "(POSNUM, ONECODE, TWOCODE, THREECODE, ADDRESS,IP,PORT,DATE,Min,Max, AREACODE) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{systemBean.getPOSNUM(), systemBean.getONECODE(),
                        systemBean.getTWOCODE(), systemBean.getTHREECODE(),
                        systemBean.getADDRESS(), systemBean.getIP(),
                        systemBean.getPORT(), systemBean.getDATE(),
                        systemBean.getMin(), systemBean.getMax(),
                        systemBean.getTHREECODE()});
        Log.e(TAG, "insert success !!!");
    }

//    /**
//     * 删除数据
//     */
//    public void delete(int PKID) {
//        database = mySqliteHelper.getReadableDatabase();
//        database.execSQL("delete from _SystemBean where PKID = ?", new String[]{String.valueOf(PKID)});
//        Log.v(TAG, "delete success !!!");
//    }
//

    /**
     * 删除数据
     *
     * @param tableName
     * @param selection
     * @param selectionArgs
     */
    public void delect(String tableName, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        database.delete(tableName, selection, selectionArgs);
        Log.v(TAG, "delect success !!!");
    }

    /**
     * 查询所有的数据
     */
    public SystemBean findAll(String tableName) {
        database = mySqliteHelper.getReadableDatabase();
        SystemBean systemBean;
//        Cursor cursor = database.rawQuery("select * from " + ParameterManager.TABLENAME_SYSTEMBEAN, null);
        Cursor cursor = database.rawQuery("select * from " + tableName, null);
        while (cursor.moveToNext()) {
            String posNum = cursor.getString(cursor.getColumnIndex("POSNUM"));
            if (posNum == null || posNum.equals(""))
                return null;
            String onecode = cursor.getString(cursor.getColumnIndex("ONECODE"));
            if (onecode == null || onecode.equals(""))
                return null;
            String twocode = cursor.getString(cursor.getColumnIndex("TWOCODE"));
            if (twocode == null || twocode.equals(""))
                return null;
            String threecode = cursor.getString(cursor.getColumnIndex("THREECODE"));
            if (threecode == null || threecode.equals(""))
                return null;
            String address = cursor.getString(cursor.getColumnIndex("ADDRESS"));
            if (address == null || address.equals(""))
                return null;
            String ip = cursor.getString(cursor.getColumnIndex("IP"));
            if (ip == null || ip.equals(""))
                return null;
            String port = cursor.getString(cursor.getColumnIndex("PORT"));
            if (port == null || port.equals(""))
                return null;
            String date = cursor.getString(cursor.getColumnIndex("DATE"));
            if (date == null || date.equals(""))
                return null;
            String min = cursor.getString(cursor.getColumnIndex("Min"));
            if (min == null || min.equals(""))
                return null;
            String max = cursor.getString(cursor.getColumnIndex("Max"));
            if (max == null || max.equals(""))
                return null;
            String areacode = cursor.getString(cursor.getColumnIndex("AREACODE"));
            if (areacode == null || areacode.equals(""))
                return null;
            systemBean = new SystemBean(posNum, onecode, twocode, threecode, address, ip, port, date, min, max, areacode);
            Log.e(TAG, systemBean.toString());
            return systemBean;
        }
        return null;
    }
//
//    /**
//     * 修改数据
//     */
//    public void modify(SystemBean systemBean) {
//        database = mySqliteHelper.getReadableDatabase();
//        database.execSQL("update _SystemBean set POSNUM = ?, ONECODE = ?, TWOCODE = ?, THREECODE = ?, ADDRESS = ?, IP = ?,PORT = ?, DATE = ?, Min = ?, Max = ?, AREACODE = ?",
//                new Object[]{systemBean.getPOSNUM(), systemBean.getONECODE(),
//                        systemBean.getTWOCODE(), systemBean.getTHREECODE(), systemBean.getADDRESS(),
//                        systemBean.getIP(), systemBean.getPORT(), systemBean.getDATE(), systemBean.getMin(),
//                        systemBean.getMax(), systemBean.getTHREECODE()});
//        Log.v(TAG, "modify success !!!");
//    }
}
