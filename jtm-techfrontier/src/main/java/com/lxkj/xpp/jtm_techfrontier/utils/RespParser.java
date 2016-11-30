package com.lxkj.xpp.jtm_techfrontier.utils;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/11/30.
 * 联网获取数据后解析的接口
 */

public interface RespParser<T> {
    public T parseResponse(String result) throws JSONException;
}
