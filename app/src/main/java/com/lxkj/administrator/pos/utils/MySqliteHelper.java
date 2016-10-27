package com.lxkj.administrator.pos.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/9/19.
 * 数据库帮助类
 */
public class MySqliteHelper extends SQLiteOpenHelper {
   // private static final String DATANAME = "dbo.db";
    private static final int VERSION = 1;
    private SQLiteDatabase db;
    /*
       * 参数说明 1：上下文。 2：数据库的名字。 3:是否需要自己创建Cursor的工厂，一般的情况都不自己创建，所以就写null, 4:数据库的版本，
       */
    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
//    public MySqliteHelper(Context context) {
//        super(context, DATANAME, null, VERSION);
//        db = this.getReadableDatabase();
//    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreatTableSql.SYSTEMBEAN_TABLE);//创建系统设置表
        db.execSQL(CreatTableSql.DRUGBUTTONCEAN_TABLE);//药具和通道对应关系表
        //本月本机器上领用情况
        //本月所有机器上领用情况
        //消费记录表（上传成功后清除）
        //系统设置表
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("drop table if exists _SystemBean");
            db.execSQL("drop table if exists _DrugButtonBean");
            onCreate(db);
        }
    }
}
