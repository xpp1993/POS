package com.lxkj.administrator.pos.bean;

/**
 * Created by Administrator on 2016/9/22.
 * 系统设置类
 */
public class SystemBean {
    private int PKID;
    private String POSNUM;//机号
    private String ONECODE;
    private String TWOCODE;
    private String THREECODE;
    private String ADDRESS;
    private String IP;//二级域名
    private String PORT;//端口
    private String DATE;//日期
    private String Min;//最小年龄
    private String Max;//最大年龄
    private String AREACODE;//区域编码

    public SystemBean() {
    }

    public SystemBean(int PKID, String POSNUM, String ONECODE, String TWOCODE, String THREECODE, String ADDRESS, String IP, String PORT, String DATE, String min, String max, String AREACODE) {
        this.PKID = PKID;
        this.POSNUM = POSNUM;
        this.ONECODE = ONECODE;
        this.TWOCODE = TWOCODE;
        this.THREECODE = THREECODE;
        this.ADDRESS = ADDRESS;
        this.IP = IP;
        this.PORT = PORT;
        this.DATE = DATE;
        Min = min;
        Max = max;
        this.AREACODE = AREACODE;
    }

    public int getPKID() {
        return PKID;
    }

    public void setPKID(int PKID) {
        this.PKID = PKID;
    }

    public String getPOSNUM() {
        return POSNUM;
    }

    public void setPOSNUM(String POSNUM) {
        this.POSNUM = POSNUM;
    }

    public String getONECODE() {
        return ONECODE;
    }

    public void setONECODE(String ONECODE) {
        this.ONECODE = ONECODE;
    }

    public String getTWOCODE() {
        return TWOCODE;
    }

    public void setTWOCODE(String TWOCODE) {
        this.TWOCODE = TWOCODE;
    }

    public String getTHREECODE() {
        return THREECODE;
    }

    public void setTHREECODE(String THREECODE) {
        this.THREECODE = THREECODE;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    public String getIP() {
        return IP;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public String getPORT() {
        return PORT;
    }

    public void setPORT(String PORT) {
        this.PORT = PORT;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMin() {
        return Min;
    }

    public void setMin(String min) {
        Min = min;
    }

    public String getMax() {
        return Max;
    }

    public void setMax(String max) {
        Max = max;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    @Override
    public String toString() {
        return "SystemBean{" +
                "PKID=" + PKID +
                ", POSNUM='" + POSNUM + '\'' +
                ", ONECODE='" + ONECODE + '\'' +
                ", TWOCODE='" + TWOCODE + '\'' +
                ", THREECODE='" + THREECODE + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                ", IP='" + IP + '\'' +
                ", PORT='" + PORT + '\'' +
                ", DATE='" + DATE + '\'' +
                ", Min='" + Min + '\'' +
                ", Max='" + Max + '\'' +
                ", AREACODE='" + AREACODE + '\'' +
                '}';
    }
}
