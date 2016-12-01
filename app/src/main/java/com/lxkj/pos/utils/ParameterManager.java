package com.lxkj.pos.utils;

/**
 * Created by Administrator on 2016/11/1.
 */
public class ParameterManager {
    public static final String DATABASENAME = "dbo.db";//数据库名称
    public static final String HOST = "http://120.24.86.194:8081";//WebServiceip
    public static final String WEBSERVICE = "/WS/GrantService.asmx";
    public static final String KEY= "GZJSMIS";//握手密码，初定为：GZJSMIS
    public static final String TABLENAME_DRUGBUTTONBEAN = "_DrugButtonBean";//药具通道和药具关系对应表名
    public static final String TABLENAME_DRUGBUTTONBEAN_DUPLICATEFILE = "_DrugButtonBean_duplicate_file";//药具通道和药具关系对应备份表名
    public static final String TABLENAME_SYSTEMBEAN = "_SystemBean";//系统设置表名
    public static final String TABLENAME_SYSTEMBEAN_DUPLICATEFILE = "_SystemBean_duplicate_file";//系统设置备份表名
    public static final String TABLENAME_BLACKBEAN = "_BlackBean";//黑名单应表名
    public static final String TABLENAME_LYGBEAN = "_LYGBean";//本月本机器上领用情况表名
    public static final String TABLENAME_RECEIVEBEAN = "_ReceiveBean";//本月所有机器上领用情况表名
    public static final String TABLENAME_MLYGBEAN = "_MLYGBean";//本月所有机器上领用情况表名
    public static final String BUTTONNAME = "BUTTONNAME";//键名
    public static final String BUTTONVALU = "BUTTONVALU";//键号
    public static final String DRUGCODING = "DRUGCODING";//药具代码
    public static final String DRUGNAME = "DRUGNAME";//药具名称
    public static final String DRUGSTYLE = "DRUGSTYLE";//药具类型
    public static final String USESTATUS = "USESTATUS";//使用标志
    public static final String CURRENTAMO = "CURRENTAMO";//当前数量
    public static final String MAXAMOUNT = "MAXAMOUNT";//最大数量
    public static final String VALIDDATE = "VALIDDATE";//有效期
    public static final String BATCH = "BATCH";//批号
    public static final String ROOTJIAO = "ROOTJIAO";//对应的控制角
    public static final String SYSTEMBEAN_POSNUM_VALUE = "1";//机号
    public static final String SYSTEMBEAN_ONECODE_VALUE = "0";//
    public static final String SYSTEMBEAN_TWOCODE_VALUE = "0";//
    public static final String SYSTEMBEAN_THREECODE_VALUE = "01";//
    public static final String SYSTEMBEAN_ADDRESS_VALUE = "4";//地址
    public static final String SYSTEMBEAN_IP_VALUE = "120.24.86.194";//二级域名
    public static final String SYSTEMBEAN_PORT_VALUE = "5009";//端口
    public static final String SYSTEMBEAN_DATE_VALUE = "20151012";//日期
    public static final String SYSTEMBEAN_Min_VALUE = "15";//最小年龄
    public static final String SYSTEMBEAN_Max_VALUE = "65";//最大年龄
    public static final String SYSTEMBEAN_AREACODE_VALUE = "1";//区编码
    public static final String ID = "ID";//身份证号
    public static final String DATE = "DATE";//日期
    public static final String FLAG = "FLAG";//
    public static final String LIST = "LIST";//
}
