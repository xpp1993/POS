package com.lxkj.administrator.pos.bean;

/**
 * Created by Administrator on 2016/9/22.
 * 药具通道和药具关系对应类
 */
public class DrugButtonBean {
    private String BUTTONNAME;//键名
    private String BUTTONVALU;//键号
    private String DRUGCODING;//药具代码
    private String DRUGNAME;//药具名称
    private String DRUGSTYLE;//药具类型
    private String USESTATUS;//使用标志
    private String CURRENTAMO;//当前数量
    private String MAXAMOUNT;//最大数量
    private String VALIDDATE;//有效期
    private String BATCH;//批号
    private String ROOTJIAO;//对应的控制角
    private int PKID;
    public DrugButtonBean() {
    }

    public DrugButtonBean(String BUTTONNAME, String BUTTONVALU, String DRUGCODING,
                          String DRUGNAME, String DRUGSTYLE, String USESTATUS, String CURRENTAMO,
                          String MAXAMOUNT, String VALIDDATE, String BATCH, String ROOTJIAO) {
        this.BUTTONNAME = BUTTONNAME;
        this.BUTTONVALU = BUTTONVALU;
        this.DRUGCODING = DRUGCODING;
        this.DRUGNAME = DRUGNAME;
        this.DRUGSTYLE = DRUGSTYLE;
        this.USESTATUS = USESTATUS;
        this.CURRENTAMO = CURRENTAMO;
        this.MAXAMOUNT = MAXAMOUNT;
        this.VALIDDATE = VALIDDATE;
        this.BATCH = BATCH;
        this.ROOTJIAO = ROOTJIAO;
    }

    public DrugButtonBean(String BUTTONNAME, String BUTTONVALU, String DRUGCODING,
                          String DRUGNAME, String DRUGSTYLE, String USESTATUS, String CURRENTAMO,
                          String MAXAMOUNT, String VALIDDATE, String BATCH, String ROOTJIAO, int PKID) {
        this.BUTTONNAME = BUTTONNAME;
        this.BUTTONVALU = BUTTONVALU;
        this.DRUGCODING = DRUGCODING;
        this.DRUGNAME = DRUGNAME;
        this.DRUGSTYLE = DRUGSTYLE;
        this.USESTATUS = USESTATUS;
        this.CURRENTAMO = CURRENTAMO;
        this.MAXAMOUNT = MAXAMOUNT;
        this.VALIDDATE = VALIDDATE;
        this.BATCH = BATCH;
        this.ROOTJIAO = ROOTJIAO;
        this.PKID = PKID;
    }

    public int getPKID() {
        return PKID;
    }

    public void setPKID(int PKID) {
        this.PKID = PKID;
    }

    public String getBUTTONNAME() {
        return BUTTONNAME;
    }

    public void setBUTTONNAME(String BUTTONNAME) {
        this.BUTTONNAME = BUTTONNAME;
    }

    public String getBUTTONVALU() {
        return BUTTONVALU;
    }

    public void setBUTTONVALU(String BUTTONVALU) {
        this.BUTTONVALU = BUTTONVALU;
    }

    public String getDRUGCODING() {
        return DRUGCODING;
    }

    public void setDRUGCODING(String DRUGCODING) {
        this.DRUGCODING = DRUGCODING;
    }

    public String getDRUGNAME() {
        return DRUGNAME;
    }

    public void setDRUGNAME(String DRUGNAME) {
        this.DRUGNAME = DRUGNAME;
    }

    public String getDRUGSTYLE() {
        return DRUGSTYLE;
    }

    public void setDRUGSTYLE(String DRUGSTYLE) {
        this.DRUGSTYLE = DRUGSTYLE;
    }

    public String getUSESTATUS() {
        return USESTATUS;
    }

    public void setUSESTATUS(String USESTATUS) {
        this.USESTATUS = USESTATUS;
    }

    public String getCURRENTAMO() {
        return CURRENTAMO;
    }

    public void setCURRENTAMO(String CURRENTAMO) {
        this.CURRENTAMO = CURRENTAMO;
    }

    public String getMAXAMOUNT() {
        return MAXAMOUNT;
    }

    public void setMAXAMOUNT(String MAXAMOUNT) {
        this.MAXAMOUNT = MAXAMOUNT;
    }

    public String getVALIDDATE() {
        return VALIDDATE;
    }

    public void setVALIDDATE(String VALIDDATE) {
        this.VALIDDATE = VALIDDATE;
    }

    public String getBATCH() {
        return BATCH;
    }

    public void setBATCH(String BATCH) {
        this.BATCH = BATCH;
    }

    public String getROOTJIAO() {
        return ROOTJIAO;
    }

    public void setROOTJIAO(String ROOTJIAO) {
        this.ROOTJIAO = ROOTJIAO;
    }

    @Override
    public String toString() {
        return "DrugButtonBean{" +
                "BUTTONNAME='" + BUTTONNAME + '\'' +
                ", BUTTONVALU='" + BUTTONVALU + '\'' +
                ", DRUGCODING='" + DRUGCODING + '\'' +
                ", DRUGNAME='" + DRUGNAME + '\'' +
                ", DRUGSTYLE='" + DRUGSTYLE + '\'' +
                ", USESTATUS='" + USESTATUS + '\'' +
                ", CURRENTAMO='" + CURRENTAMO + '\'' +
                ", MAXAMOUNT='" + MAXAMOUNT + '\'' +
                ", VALIDDATE='" + VALIDDATE + '\'' +
                ", BATCH='" + BATCH + '\'' +
                ", ROOTJIAO='" + ROOTJIAO + '\'' +
                ", PKID=" + PKID +
                '}';
    }
}
