package com.lxkj.administrator.pos.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lxkj.administrator.pos.bean.SystemBean;
import com.lxkj.administrator.pos.utils.MySqliteHelper;

/**
 * Created by Administrator on 2016/9/22.
 * 提供增删查改SystemBean表方法类
 */
public class SystemBeanService {
    MySqliteHelper mySqliteHelper;
    SQLiteDatabase database;
    private static final String DATANAME = "dbo.db";

    public SystemBeanService(Context context, int version) {
        mySqliteHelper = new MySqliteHelper(context, DATANAME, null, version);
    }

    /**
     * 插入数据
     *
     * @param systemBean
     */
    public void insert(SystemBean systemBean) {
        database = mySqliteHelper.getReadableDatabase();
        database.execSQL("insert into _SystemBean(PKID, POSNUM, ONECODE, TWOCODE, THREECODE, ADDRESS,IP,PORT,DATE,Min,Max, AREACODE) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{systemBean.getPKID(), systemBean.getPOSNUM(), systemBean.getONECODE(),
                        systemBean.getTWOCODE(), systemBean.getTHREECODE(), systemBean.getADDRESS(),
                        systemBean.getIP(), systemBean.getPORT(), systemBean.getDATE(), systemBean.getMin(),
                        systemBean.getMax(), systemBean.getTHREECODE()});
        Log.v("insert", "insert success !!!");
    }

    /**
     * 删除数据
     */
    public void delete(int PKID) {
        database = mySqliteHelper.getReadableDatabase();
        database.execSQL("delete from _SystemBean where PKID = ?", new String[]{String.valueOf(PKID)});
        Log.v("delete", "delete success !!!");
    }

    /**
     * 查询数据
     */
    public SystemBean find(int PKID) {
        database = mySqliteHelper.getReadableDatabase();
        SystemBean systemBean;
        Cursor cursor = database.rawQuery("select *from student where PKID = ?", new String[]{String.valueOf(PKID)});
        while (cursor.moveToNext()) {
            systemBean = new SystemBean();
            systemBean.setPKID(cursor.getInt(cursor.getColumnIndex("PKID")));
            systemBean.setPOSNUM(cursor.getString(cursor.getColumnIndex("POSNUM")));
            systemBean.setONECODE(cursor.getString(cursor.getColumnIndex("ONECODE")));
            systemBean.setTWOCODE(cursor.getString(cursor.getColumnIndex("TWOCODE")));
            systemBean.setTHREECODE(cursor.getString(cursor.getColumnIndex("THREECODE")));
            systemBean.setADDRESS(cursor.getString(cursor.getColumnIndex("ADDRESS")));
            systemBean.setIP(cursor.getString(cursor.getColumnIndex("IP")));
            systemBean.setPORT(cursor.getString(cursor.getColumnIndex("PORT")));
            systemBean.setDATE(cursor.getString(cursor.getColumnIndex("DATE")));
            systemBean.setMin(cursor.getString(cursor.getColumnIndex("Min")));
            systemBean.setMax(cursor.getString(cursor.getColumnIndex("Max")));
            systemBean.setAREACODE(cursor.getString(cursor.getColumnIndex("AREACODE")));
            Log.v("systemBean", systemBean.toString());
            return systemBean;
        }
        return null;
    }

    /**
     * 修改数据
     */
    public void modify(int PKID, SystemBean systemBean) {
        database = mySqliteHelper.getReadableDatabase();
        database.execSQL("update _SystemBean set POSNUM = ?, ONECODE = ?, TWOCODE = ?, THREECODE = ?, ADDRESS = ?, IP = ?,PORT = ?, DATE = ?, Min = ?, Max = ?, AREACODE = ?",
                new Object[]{systemBean.getPKID(), systemBean.getPOSNUM(), systemBean.getONECODE(),
                        systemBean.getTWOCODE(), systemBean.getTHREECODE(), systemBean.getADDRESS(),
                        systemBean.getIP(), systemBean.getPORT(), systemBean.getDATE(), systemBean.getMin(),
                        systemBean.getMax(), systemBean.getTHREECODE()});
        Log.v("modify", "modify success !!!");
    }
}
