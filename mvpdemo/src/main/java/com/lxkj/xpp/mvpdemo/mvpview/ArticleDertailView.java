package com.lxkj.xpp.mvpdemo.mvpview;

/**
 * Created by Administrator on 2016/12/2.
 */

public interface ArticleDertailView extends MvpView {
    void onFetcheedArticleContent(String html);
}
