package com.lxkj.administrator.pos.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lxkj.administrator.pos.bean.DrugButtonBean;
import com.lxkj.administrator.pos.utils.MySqliteHelper;
import com.lxkj.administrator.pos.utils.ParameterManager;

/**
 * Created by Administrator on 2016/9/22.
 * 增删查改DrugButtonBean表类
 */
public class DrugButtonBeanService {
    MySqliteHelper mySqliteHelper;
    SQLiteDatabase database;
    ContentValues values;
    public static final String TAG = DrugButtonBeanService.class.getSimpleName();

    public DrugButtonBeanService(MySqliteHelper mySqliteHelper) {
        this.mySqliteHelper = mySqliteHelper;
        values = new ContentValues();
    }

    /**
     * 插入数据用到的方法
     *
     * @param drugButtonBean
     * @return
     */
    public ContentValues putContentValues(DrugButtonBean drugButtonBean) {
        values.put(ParameterManager.BUTTONNAME, drugButtonBean.getBUTTONNAME());
        values.put(ParameterManager.BUTTONVALU, drugButtonBean.getBUTTONVALU());
        values.put(ParameterManager.DRUGCODING, drugButtonBean.getDRUGCODING());
        values.put(ParameterManager.DRUGNAME, drugButtonBean.getDRUGNAME());
        values.put(ParameterManager.DRUGSTYLE, drugButtonBean.getDRUGSTYLE());
        values.put(ParameterManager.USESTATUS, drugButtonBean.getUSESTATUS());
        values.put(ParameterManager.CURRENTAMO, drugButtonBean.getCURRENTAMO());
        values.put(ParameterManager.MAXAMOUNT, drugButtonBean.getMAXAMOUNT());
        values.put(ParameterManager.VALIDDATE, drugButtonBean.getVALIDDATE());
        values.put(ParameterManager.BATCH, drugButtonBean.getBATCH());
        values.put(ParameterManager.ROOTJIAO, drugButtonBean.getROOTJIAO());
        return values;
    }

    /**
     * 添加数据
     *
     * @param values ContentValues values
     */
    public void insert(ContentValues values) {
        database = mySqliteHelper.getReadableDatabase();
        database.insert(ParameterManager.TABLENAME_DRUGBUTTONBEAN, null, values);
        values.clear();
        Log.v(TAG, "insert success !!!");
    }

    /**
     * 更新数据
     *
     * @param selectionParams values.put(selectionParams,value)
     * @param value
     * @param selection       指定where的约束条件
     * @param selectionArgs   为where中的占位符 提供具体的值
     */
    public void updata(String selectionParams, String value, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        values.put(selectionParams, value);
        database.update(ParameterManager.TABLENAME_DRUGBUTTONBEAN, values, selection, selectionArgs);
        values.clear();
        Log.v(TAG, "updata success !!!");
    }

    /**
     * 删除数据
     *
     * @param selection     指定where的约束条件
     * @param selectionArgs 为where中的占位符 提供具体的值
     */
    public void delect(String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        database.delete(ParameterManager.TABLENAME_DRUGBUTTONBEAN, selection, selectionArgs);
        Log.v(TAG, "delect success !!!");
    }

    /**
     * 查询数据
     *
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public DrugButtonBean query(String[] columns, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        Cursor cursor = database.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, columns, selection, selectionArgs, null, null, null);
        DrugButtonBean drugButtonBean = null;
        if (cursor.moveToFirst()) {//间数据的指针移到第一行
            do {//遍历所有的Cursor对象
                String buttonName = cursor.getString(cursor.getColumnIndex(ParameterManager.BUTTONNAME));
                String buttonValue = cursor.getString(cursor.getColumnIndex(ParameterManager.BUTTONVALU));
                String drugcoding = cursor.getString(cursor.getColumnIndex(ParameterManager.DRUGCODING));
                String drugName = cursor.getString(cursor.getColumnIndex(ParameterManager.DRUGNAME));
                String drugStyle = cursor.getString(cursor.getColumnIndex(ParameterManager.DRUGSTYLE));
                String useStatus = cursor.getString(cursor.getColumnIndex(ParameterManager.USESTATUS));
                String currentAmo = cursor.getString(cursor.getColumnIndex(ParameterManager.CURRENTAMO));
                String maxAmount = cursor.getString(cursor.getColumnIndex(ParameterManager.MAXAMOUNT));
                String valueDate = cursor.getString(cursor.getColumnIndex(ParameterManager.VALIDDATE));
                String batch = cursor.getString(cursor.getColumnIndex(ParameterManager.BATCH));
                String rootJiao = cursor.getString(cursor.getColumnIndex(ParameterManager.ROOTJIAO));
                drugButtonBean = new DrugButtonBean( buttonName, buttonValue, drugcoding, drugName, drugStyle, useStatus, currentAmo, maxAmount, valueDate, batch, rootJiao);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return drugButtonBean;
    }
}
