package com.lxkj.administrator.pos.utils;

/**
 * Created by Administrator on 2016/9/22.
 * 创建表
 */
public class CreatTableSql {
    /**
     * 创建系统设置表
     * 机号				地址	二级域名	端口		最小年龄	最大年龄	区编码
     */
    final static String SYSTEMBEAN_TABLE = "create table " + ParameterManager.TABLENAME_SYSTEMBEAN +
            "(PKID int IDENTITY(1,1) NOT NULL," +
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
     * 键名	键号	药具代码	药具名称	药具类型	使用标志	当前数量	最大数量	有效期	批号	对应的控制角
     */
    final static String DRUGBUTTONCEAN_TABLE = "create table " + ParameterManager.TABLENAME_DRUGBUTTONBEAN +
            "(PKID int IDENTITY(1,1) NOT NULL," +
            "BUTTONNAME varchar(50) NULL," +
            "BUTTONVALU varchar(50) NULL," +
            "DRUGCODING varchar(50) NULL," +
            "DRUGNAME varchar(50) NULL," +
            "DRUGSTYLE varchar(50) NULL," +
            "USESTATUS varchar(50) NULL," +
            "CURRENTAMO varchar(50) NULL," +
            "MAXAMOUNT varchar(50) NULL," +
            "VALIDDATE varchar(50) NULL," +
            "BATCH varchar(50) NULL," +
            "ROOTJIAO varchar(50) NULL);";
    /**
     * 创建黑名单
     */
    final static String BLACKBEAN_TABLE = "create table " + ParameterManager.TABLENAME_BLACKBEAN +
            "(ID varchar(50) NOT NULL," +
            "DATE varchar(50) NOT NULL," +
            "FLAG varchar(50) NOT NULL," +
            "LIST varchar(50) NOT NULL);";
    /**
     * 创建本月本机器上领用情况表
     */
    final static String LYGBEAN_TABLE = "create table " + ParameterManager.TABLENAME_LYGBEAN +
            "(ID varchar(50) NOT NULL," +
            "DATE varchar(50) NOT NULL," +
            "FLAG varchar(50) NOT NULL," +
            "LIST varchar(50) NOT NULL);";
    /**
     * 创建本月所有机器上领用情况表
     */
    final static String MLYGBEAN_TABLE = "create table " + ParameterManager.TABLENAME_MLYGBEAN +
            "(ID varchar(50) NOT NULL," +
            "DATE varchar(50) NOT NULL," +
            "FLAG varchar(50) NOT NULL," +
            "LIST varchar(50) NOT NULL);";
}
