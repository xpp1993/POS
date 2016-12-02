package com.lxkj.xpp.mvpdemo.net.parser;

import org.json.JSONException;

/**
 * Created by Administrator on 2016/12/2.
 */

public class DefaultParser implements RespParser<String> {
    @Override
    public String parseResponse(String result) throws JSONException {
        return result;
    }
}
