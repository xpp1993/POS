package com.lxkj.pos.utils;

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
            "(PKID integer primary key autoincrement," +
            "POSNUM varchar(50) NULL," +
            "ONECODE varchar(50) NULL," +
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
            "(PKID integer primary key autoincrement," +
            "BUTTONNAME varchar(50) ," +
            "BUTTONVALU varchar(50) ," +
            "DRUGCODING varchar(50) ," +
            "DRUGNAME varchar(50) ," +
            "DRUGSTYLE varchar(50) ," +
            "USESTATUS varchar(50) ," +
            "CURRENTAMO varchar(50) ," +
            "MAXAMOUNT varchar(50) ," +
            "VALIDDATE varchar(50) ," +
            "BATCH varchar(50)," +
            "ROOTJIAO varchar(50) );";

    /**
     * 创建系统设置备份表
     * 机号				地址	二级域名	端口		最小年龄	最大年龄	区编码
     */
    final static String SYSTEMBEAN_TABLE_DUPLICATEFILE = "create table " + ParameterManager.TABLENAME_SYSTEMBEAN_DUPLICATEFILE +
            "(POSNUM varchar(50) NULL," +
            "ONECODE varchar(50) NULL," +
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
     * 创建药具和通道对应关系备份表
     * 键名	键号	药具代码	药具名称	药具类型	使用标志	当前数量	最大数量	有效期	批号	对应的控制角
     */
    final static String DRUGBUTTONCEAN_TABLE_DUPLICATEFILE = "create table " + ParameterManager.TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE +
            "(BUTTONNAME varchar(50) ," +
            "BUTTONVALU varchar(50) ," +
            "DRUGCODING varchar(50) ," +
            "DRUGNAME varchar(50) ," +
            "DRUGSTYLE varchar(50) ," +
            "USESTATUS varchar(50) ," +
            "CURRENTAMO varchar(50) ," +
            "MAXAMOUNT varchar(50) ," +
            "VALIDDATE varchar(50) ," +
            "BATCH varchar(50)," +
            "ROOTJIAO varchar(50) );";
    /**
     * 创建黑名单
     */
    final static String BLACKBEAN_TABLE = "create table " + ParameterManager.TABLENAME_BLACKBEAN +
            "(ID varchar(50) ," +
            "DATE varchar(50) ," +
            "FLAG varchar(50) ," +
            "LIST varchar(50) );";
    /**
     * 创建本月本机器上领用情况表
     */
    final static String LYGBEAN_TABLE = "create table " + ParameterManager.TABLENAME_LYGBEAN +
            "(ID varchar(50) ," +
            "DATE varchar(50) ," +
            "FLAG varchar(50) ," +
            "LIST varchar(50) );";
    /**
     * 创建本月所有机器上领用情况表
     */
    final static String MLYGBEAN_TABLE = "create table " + ParameterManager.TABLENAME_MLYGBEAN +
            "(ID varchar(50) ," +
            "DATE varchar(50) ," +
            "FLAG varchar(50) ," +
            "LIST varchar(50) );";
    /**
     * 创建领用记录表
     */
    final static String RECEIVEBEAN_TABLE = "create table " + ParameterManager.TABLENAME_RECEIVEBEAN +
            "(IDENTITYNU varchar(50) ," +
            "AMOUNT varchar(50) ," +
            "CODING varchar(50) ," +
            "STYLE varchar(50) ," +
            "TIME varchar(50) ," +
            "POSNUM varchar(50) ," +
            "ONECODE varchar(50) ," +
            "TWOCODE varchar(50) ," +
            "THREECODE varchar(50) ," +
            "PRICE varchar(50) ," +
            "AREACODE varchar(50) ," +
            "USERNAME varchar(50) ," +
            "USERSEX varchar(50) ," +
            "USERNATION varchar(50) ," +
            "BORNDATE varchar(50) ," +
            "PAPERWORKD varchar(50) ," +
            "ADDRESS varchar(50) );";
}
