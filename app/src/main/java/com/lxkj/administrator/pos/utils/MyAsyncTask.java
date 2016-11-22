package com.lxkj.administrator.pos.utils;

import android.os.AsyncTask;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/22.
 */
public class MyAsyncTask extends AsyncTask<Void, Void, String> {
    Map<String, Object> requestParamsMap;

    public MyAsyncTask(Map<String, Object> requestParamsMap) {
        this.requestParamsMap = requestParamsMap;
    }

    @Override
    protected String doInBackground(Void... params) {
        String data = CommonTools.postDown(ParameterManager.HOST + ParameterManager.WEBSERVICE, requestParamsMap);
        System.out.println(data);
        return data;
    }
}

