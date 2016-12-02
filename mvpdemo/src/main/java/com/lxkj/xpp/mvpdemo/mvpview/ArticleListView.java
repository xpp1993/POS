package com.lxkj.xpp.mvpdemo.mvpview;

import com.lxkj.xpp.mvpdemo.beans.Article;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public interface ArticleListView extends MvpView {
    public void onFetchedArticles(List<Article> result);

    public void clearCacheArticles();
}
