package com.lxkj.xpp.mvpdemo.listener;

/**
 * Created by Administrator on 2016/12/2.
 */

public interface DataListener<T> {
    void onComplete(T result);
}
