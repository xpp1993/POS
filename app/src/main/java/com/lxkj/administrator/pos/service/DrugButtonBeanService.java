package com.lxkj.administrator.pos.service;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.lxkj.administrator.pos.bean.DrugButtonBean;
import com.lxkj.administrator.pos.bean.ReceiveBean;
import com.lxkj.administrator.pos.utils.MySqliteHelper;
import com.lxkj.administrator.pos.utils.ParameterManager;

import java.util.ArrayList;
import java.util.List;

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
     * 插入数据用到的方法
     */
    public ContentValues putContentValuesforReceive(ReceiveBean receiveBean) {
        values.put("IDENTITYNU", receiveBean.getIDENTITYNU());
        values.put("AMOUNT", receiveBean.getAMOUNT());
        values.put("CODING", receiveBean.getCODING());
        values.put("STYLE", receiveBean.getSTYLE());
        values.put("TIME", receiveBean.getTIME());
        values.put("POSNUM", receiveBean.getPOSNUM());
        values.put("ONECODE", receiveBean.getONECODE());
        values.put("TWOCODE", receiveBean.getTWOCODE());
        values.put("THREECODE", receiveBean.getTHREECODE());
        values.put("PRICE", receiveBean.getPRICE());
        values.put("AREACODE", receiveBean.getAREACODE());
        values.put("USERNAME", receiveBean.getUSERNAM());
        values.put("USERSEX", receiveBean.getUSERSEX());
        values.put("USERNATION", receiveBean.getUSERNATION());
        values.put("BORNDATE", receiveBean.getBORNDATE());
        values.put("PAPERWORKD", receiveBean.getPAPERWORKD());
        values.put("ADDRESS", receiveBean.getADDRESS());
        return values;
    }

    /**
     * 添加数据
     *
     * @param values ContentValues values
     */
    public void insert(String tableName, ContentValues values) {
        database = mySqliteHelper.getReadableDatabase();
        database.insert(tableName, null, values);
        values.clear();
        if (ParameterManager.TABLENAME_DRUGBUTTONBEAN.equals(tableName)) {
            if (query(tableName, null, null, null).toString() != null)
            Log.e(TAG, "insert success !!!" + query(tableName, null, null, null).toString());
        }else if (ParameterManager.TABLENAME_RECEIVEBEAN.equals(tableName)){
            if (queryReceiveBeans(tableName, null, null, null) != null){
                Log.e(TAG, "insert success" + queryReceiveBeans(tableName, null, null, null).toString());
            }
        }
    }

    /**
     * 更新数据
     *
     * @param selectionParams values.put(selectionParams,value)
     * @param value
     * @param selection       指定where的约束条件
     * @param selectionArgs   为where中的占位符 提供具体的值
     */
    public void updata(String tableName, String selectionParams, String value, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        values.put(selectionParams, value);
//        database.update(ParameterManager.TABLENAME_DRUGBUTTONBEAN, values, selection, selectionArgs);
        database.update(tableName, values, selection, selectionArgs);
        values.clear();
        if (query(tableName, null, selection, selectionArgs).toString() != null) {
            if (ParameterManager.TABLENAME_DRUGBUTTONBEAN.equals(tableName))
                Log.e(TAG, "updata success !!!" + query(tableName, null, selection, selectionArgs).toString());
            else if (ParameterManager.TABLENAME_RECEIVEBEAN.equals(tableName)) {
                Log.e(TAG, "updata success !!!" + queryReceiveBeans(tableName, null, selection, selectionArgs).toString());
            }
        }

    }

    /**
     * 删除数据
     *
     * @param selection     指定where的约束条件
     * @param selectionArgs 为where中的占位符 提供具体的值
     */
    public void delect(String tableName, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
//        database.delete(ParameterManager.TABLENAME_DRUGBUTTONBEAN, selection, selectionArgs);
        database.delete(tableName, selection, selectionArgs);
        Log.e(TAG, "delect success !!!");
    }

    /**
     * 查询数据
     *
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public List<DrugButtonBean> query(String tableName, String[] columns, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
//        Cursor cursor = database.query(ParameterManager.TABLENAME_DRUGBUTTONBEAN, columns, selection, selectionArgs, null, null, null);
        Cursor cursor = database.query(tableName, columns, selection, selectionArgs, null, null, null);
        DrugButtonBean drugButtonBean = null;
        List<DrugButtonBean> drugButtonBeans = new ArrayList<>();
        if (cursor.moveToFirst()) {//间数据的指针移到第一行
            do {//遍历所有的Cursor对象
                int PKID=cursor.getInt(cursor.getColumnIndex("PKID"));
                String buttonName = cursor.getString(cursor.getColumnIndex(ParameterManager.BUTTONNAME));
                if (buttonName == null || buttonName.equals(""))
                    return null;
                String buttonValue = cursor.getString(cursor.getColumnIndex(ParameterManager.BUTTONVALU));
                if (buttonValue == null || buttonValue.equals(""))
                    return null;
                String drugcoding = cursor.getString(cursor.getColumnIndex(ParameterManager.DRUGCODING));
                if (drugcoding == null || drugcoding.equals(""))
                    return null;
                String drugName = cursor.getString(cursor.getColumnIndex(ParameterManager.DRUGNAME));
                if (drugName == null || drugName.equals(""))
                    return null;
                String drugStyle = cursor.getString(cursor.getColumnIndex(ParameterManager.DRUGSTYLE));
                if (drugStyle == null || drugStyle.equals(""))
                    return null;
                String useStatus = cursor.getString(cursor.getColumnIndex(ParameterManager.USESTATUS));
                if (useStatus == null || useStatus.equals(""))
                    return null;
                String currentAmo = cursor.getString(cursor.getColumnIndex(ParameterManager.CURRENTAMO));
                if (currentAmo == null || currentAmo.equals(""))
                    return null;
                String maxAmount = cursor.getString(cursor.getColumnIndex(ParameterManager.MAXAMOUNT));
                if (maxAmount == null || maxAmount.equals(""))
                    return null;
                String valueDate = cursor.getString(cursor.getColumnIndex(ParameterManager.VALIDDATE));
                if (valueDate == null || valueDate.equals(""))
                    return null;
                String batch = cursor.getString(cursor.getColumnIndex(ParameterManager.BATCH));
                if (batch == null || batch.equals(""))
                    return null;
                String rootJiao = cursor.getString(cursor.getColumnIndex(ParameterManager.ROOTJIAO));
                if (rootJiao == null || rootJiao.equals(""))
                    return null;
                drugButtonBean = new DrugButtonBean(buttonName, buttonValue, drugcoding, drugName, drugStyle, useStatus, currentAmo, maxAmount, valueDate, batch, rootJiao,PKID);
                drugButtonBeans.add(drugButtonBean);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return drugButtonBeans;
    }

    /**
     * 查询数据
     *
     * @param columns
     * @param selection
     * @param selectionArgs
     * @return
     */
    public List<ReceiveBean> queryReceiveBeans(String tableName, String[] columns, String selection, String[] selectionArgs) {
        database = mySqliteHelper.getReadableDatabase();
        Cursor cursor = database.query(tableName, columns, selection, selectionArgs, null, null, null);
        ReceiveBean receiveBean = null;
        List<ReceiveBean> receiveBeans = new ArrayList<>();
        if (cursor.moveToFirst()) {//间数据的指针移到第一行
            do {//遍历所有的Cursor对象
                String IDENTITYNU = cursor.getString(cursor.getColumnIndex("IDENTITYNU"));
                String AMOUNT = cursor.getString(cursor.getColumnIndex("AMOUNT"));
                String CODING = cursor.getString(cursor.getColumnIndex("CODING"));
                String STYLE = cursor.getString(cursor.getColumnIndex("STYLE"));
                String TIME = cursor.getString(cursor.getColumnIndex("TIME"));
                String POSNUM = cursor.getString(cursor.getColumnIndex("POSNUM"));
                String ONECODE = cursor.getString(cursor.getColumnIndex("ONECODE"));
                String TWOCODE = cursor.getString(cursor.getColumnIndex("TWOCODE"));
                String THREECODE = cursor.getString(cursor.getColumnIndex("THREECODE"));
                String PRICE = cursor.getString(cursor.getColumnIndex("PRICE"));
                String AREACODE = cursor.getString(cursor.getColumnIndex("AREACODE"));
                String USERNAME = cursor.getString(cursor.getColumnIndex("USERNAME"));
                String USERSEX = cursor.getString(cursor.getColumnIndex("USERSEX"));
                String USERNATION = cursor.getString(cursor.getColumnIndex("USERNATION"));
                String BORNDATE = cursor.getString(cursor.getColumnIndex("BORNDATE"));
                String PAPERWORKD = cursor.getString(cursor.getColumnIndex("PAPERWORKD"));
                String ADDRESS = cursor.getString(cursor.getColumnIndex("ADDRESS"));
                receiveBean = new ReceiveBean(IDENTITYNU, AMOUNT, CODING, STYLE, TIME, POSNUM, ONECODE, TWOCODE, THREECODE, PRICE, AREACODE, USERNAME, USERSEX, USERNATION, BORNDATE, PAPERWORKD, ADDRESS);
                receiveBeans.add(receiveBean);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return receiveBeans;
    }
}
