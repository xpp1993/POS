package com.lxkj.administrator.pos.bean;

/**
 * Created by Administrator on 2016/11/2.
 */
public class LYGBean {
    private String ID;
    private String date;
    private String flag;
    private String list;

    public LYGBean() {
    }

    public LYGBean(String ID, String date, String flag, String list) {
        this.ID = ID;
        this.date = date;
        this.flag = flag;
        this.list = list;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getList() {
        return list;
    }

    public void setList(String list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "LYGBean{" +
                "ID='" + ID + '\'' +
                ", date='" + date + '\'' +
                ", flag='" + flag + '\'' +
                ", list='" + list + '\'' +
                '}';
    }
}
