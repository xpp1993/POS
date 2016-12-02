package com.lxkj.xpp.mvpdemo.net.parser;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/12/2.
 */

public interface RespParser<T> {
    public T parseResponse(String result) throws JSONException;
}
