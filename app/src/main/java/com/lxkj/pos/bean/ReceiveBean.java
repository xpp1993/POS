package com.lxkj.pos.bean;

/**
 * Created by Administrator on 2016/11/22.
 */
public class ReceiveBean {
    private String IDENTITYNU;//身份证号
    private String AMOUNT;//数量
    private String CODING;//药具
    private String STYLE;//药具类型
    private String TIME;//领用时间
    private String POSNUM;//机号
    private String ONECODE;
    private String TWOCODE;
    private String THREECODE;
    private String PRICE;//价格
    private String AREACODE;//区号
    private String USERNAM;//姓名
    private String USERSEX;//性别
    private String USERNATION;//民族
    private String BORNDATE;//生日
    private String PAPERWORKD;//身份证有效期
    private String ADDRESS;//住址

    public ReceiveBean() {
    }

    public ReceiveBean(String IDENTITYNU, String AMOUNT, String CODING, String STYLE, String TIME, String POSNUM, String ONECODE, String TWOCODE, String THREECODE, String PRICE, String AREACODE, String USERNAM, String USERSEX, String USERNATION, String BORNDATE, String PAPERWORKD, String ADDRESS) {
        this.IDENTITYNU = IDENTITYNU;
        this.AMOUNT = AMOUNT;
        this.CODING = CODING;
        this.STYLE = STYLE;
        this.TIME = TIME;
        this.POSNUM = POSNUM;
        this.ONECODE = ONECODE;
        this.TWOCODE = TWOCODE;
        this.THREECODE = THREECODE;
        this.PRICE = PRICE;
        this.AREACODE = AREACODE;
        this.USERNAM = USERNAM;
        this.USERSEX = USERSEX;
        this.USERNATION = USERNATION;
        this.BORNDATE = BORNDATE;
        this.PAPERWORKD = PAPERWORKD;
        this.ADDRESS = ADDRESS;
    }

    public String getIDENTITYNU() {
        return IDENTITYNU;
    }

    public void setIDENTITYNU(String IDENTITYNU) {
        this.IDENTITYNU = IDENTITYNU;
    }

    public String getAMOUNT() {
        return AMOUNT;
    }

    public void setAMOUNT(String AMOUNT) {
        this.AMOUNT = AMOUNT;
    }

    public String getCODING() {
        return CODING;
    }

    public void setCODING(String CODING) {
        this.CODING = CODING;
    }

    public String getSTYLE() {
        return STYLE;
    }

    public void setSTYLE(String STYLE) {
        this.STYLE = STYLE;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
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

    public String getPRICE() {
        return PRICE;
    }

    public void setPRICE(String PRICE) {
        this.PRICE = PRICE;
    }

    public String getAREACODE() {
        return AREACODE;
    }

    public void setAREACODE(String AREACODE) {
        this.AREACODE = AREACODE;
    }

    public String getUSERNAM() {
        return USERNAM;
    }

    public void setUSERNAM(String USERNAM) {
        this.USERNAM = USERNAM;
    }

    public String getUSERSEX() {
        return USERSEX;
    }

    public void setUSERSEX(String USERSEX) {
        this.USERSEX = USERSEX;
    }

    public String getUSERNATION() {
        return USERNATION;
    }

    public void setUSERNATION(String USERNATION) {
        this.USERNATION = USERNATION;
    }

    public String getBORNDATE() {
        return BORNDATE;
    }

    public void setBORNDATE(String BORNDATE) {
        this.BORNDATE = BORNDATE;
    }

    public String getPAPERWORKD() {
        return PAPERWORKD;
    }

    public void setPAPERWORKD(String PAPERWORKD) {
        this.PAPERWORKD = PAPERWORKD;
    }

    public String getADDRESS() {
        return ADDRESS;
    }

    public void setADDRESS(String ADDRESS) {
        this.ADDRESS = ADDRESS;
    }

    @Override
    public String toString() {
        return "ReceiveBean{" +
                "IDENTITYNU='" + IDENTITYNU + '\'' +
                ", AMOUNT='" + AMOUNT + '\'' +
                ", CODING='" + CODING + '\'' +
                ", STYLE='" + STYLE + '\'' +
                ", TIME='" + TIME + '\'' +
                ", POSNUM='" + POSNUM + '\'' +
                ", ONECODE='" + ONECODE + '\'' +
                ", TWOCODE='" + TWOCODE + '\'' +
                ", THREECODE='" + THREECODE + '\'' +
                ", PRICE='" + PRICE + '\'' +
                ", AREACODE='" + AREACODE + '\'' +
                ", USERNAM='" + USERNAM + '\'' +
                ", USERSEX='" + USERSEX + '\'' +
                ", USERNATION='" + USERNATION + '\'' +
                ", BORNDATE='" + BORNDATE + '\'' +
                ", PAPERWORKD='" + PAPERWORKD + '\'' +
                ", ADDRESS='" + ADDRESS + '\'' +
                '}';
    }
}
