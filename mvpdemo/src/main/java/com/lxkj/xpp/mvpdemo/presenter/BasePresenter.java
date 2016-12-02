package com.lxkj.xpp.mvpdemo.presenter;

import com.lxkj.xpp.mvpdemo.mvpview.MvpView;

/**
 * Created by Administrator on 2016/12/2.
 */

public abstract class BasePresenter<T extends MvpView> {
    protected T mView;

    public void attach(T mView) {
        this.mView = mView;
    }

    public void detach() {
        mView = null;
    }
}
