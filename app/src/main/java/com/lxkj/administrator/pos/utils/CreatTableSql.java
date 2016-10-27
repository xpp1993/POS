package com.lxkj.administrator.pos.utils;

/**
 * Created by Administrator on 2016/9/22.
 * 创建表
 */
public class CreatTableSql {
    /**
     * 创建系统设置表
     */
    final static String SYSTEMBEAN_TABLE = "create table _SystemBean(" +
            "PKID int IDENTITY(1,1) NOT NULL," +
            "POSNUM varchar(50) NULL," +
            "ONECODE varchar(50) NULL," +
            "TWOCODE varchar(50) NULL," +
            "TWOCODE varchar(50) NULL," +
            "THREECODE varchar(50) NULL," +
            "ADDRESS varchar(50) NULL," +
            "IP varchar(50) NULL," +
            "PORT varchar(50) NULL," +
            "DATE varchar(50) NULL," +
            "Min varchar(50) NULL," +
            "Max varchar(50) NULL," +
            "AREACODE varchar(50) NULL);";
    /**
     * 创建药具和通道对应关系表
     */
    final static String DRUGBUTTONCEAN_TABLE = "create table _DrugButtonBean(" +
            "PKID int IDENTITY(1,1) NOT NULL," +
            "POSNUM varchar(50) NULL," +
            "BUTTONNAME varchar(50) NULL," +
            "BUTTONVALU varchar(50) NULL," +
            "DRUGNAME varchar(50) NULL," +
            "DRUGSTYLE varchar(50) NULL," +
            "USESTATUS varchar(50) NULL," +
            "CURRENTAMO varchar(50) NULL," +
            "MAXAMOUNT varchar(50) NULL," +
            "VALIDDATE varchar(50) NULL," +
            "BATCH varchar(50) NULL," +
            "ROOTJIAO varchar(50) NULL);";
    //   final static String BLACKBEAN_TABLE = "";//创建黑名单
//   final static String LYGBEAN_TABLE = "";//创建本月本机器上领用情况表
//   final static String MLYGBEAN_TABLE = "";//创建本月所有机器上领用情况表
}
