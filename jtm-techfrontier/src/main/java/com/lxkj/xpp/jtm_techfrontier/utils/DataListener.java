package com.lxkj.xpp.jtm_techfrontier.utils;

/**
 * Created by Administrator on 2016/11/30.
 * 联网下载数据解析数据后的回调
 */

public interface DataListener<T> {
    void onComplete(T result);
}
