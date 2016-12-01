package com.lxkj.pos.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lxkj.pos.bean.LYGBean;
import com.lxkj.pos.utils.MySqliteHelper;
import com.lxkj.pos.utils.ParameterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/2.
 */
public class LYGBeanService {
    MySqliteHelper mySqliteHelper;
    SQLiteDatabase database;
    ContentValues values;
    public static final String TAG = LYGBeanService.class.getSimpleName();

    public LYGBeanService(MySqliteHelper mySqliteHelper) {
        this.mySqliteHelper = mySqliteHelper;
        values = new ContentValues();
    }

    /**
     * 插入数据用到的方法
     *
     * @return
     */
    public ContentValues putContentValues(LYGBean lygBean) {
        values.put(ParameterManager.ID, lygBean.getID());
        values.put(ParameterManager.DATE, lygBean.getDate());
        values.put(ParameterManager.FLAG, lygBean.getFlag());
        values.put(ParameterManager.LIST, lygBean.getList());
        return values;
    }

    /**
     * 插入数据
     *
     * @param values
     * @param tableName 表名
     */
    public void insert(String tableName, ContentValues values) {
        database = mySqliteHelper.getReadableDatabase();
        database.insert(tableName, null, values);
        values.clear();
        if (query(tableName, null, null, null) != null)
            Log.e(TAG, "insert success !!!" + query(tableName, null, null, null));
    }

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
        Log.e(TAG, "delect success !!!");
    }

    /**
     * 查询数据
     *
     * @param tableName     表名
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public List<LYGBean> query(String tableName, String[] columns, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        Cursor cursor = database.query(tableName, columns, selection, selectionArgs, null, null, null);
        LYGBean lygBean = null;
        List<LYGBean> listBeans = new ArrayList<>();
        if (cursor.moveToFirst()) {//间数据的指针移到第一行
            do {//遍历所有的Cursor对象
                String Id = cursor.getString(cursor.getColumnIndex(ParameterManager.ID));
                String date = cursor.getString(cursor.getColumnIndex(ParameterManager.DATE));
                String flag = cursor.getString(cursor.getColumnIndex(ParameterManager.FLAG));
                String list = cursor.getString(cursor.getColumnIndex(ParameterManager.LIST));
                lygBean = new LYGBean(Id, date, flag, list);
                listBeans.add(lygBean);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listBeans;
    }
}
