package com.lxkj.xpp.mvpdemo.presenter;

import com.lxkj.xpp.mvpdemo.beans.Article;
import com.lxkj.xpp.mvpdemo.db.DatabaseHelper;
import com.lxkj.xpp.mvpdemo.listener.DataListener;
import com.lxkj.xpp.mvpdemo.mvpview.ArticleListView;
import com.lxkj.xpp.mvpdemo.net.parser.ArticleParser;
import com.lxkj.xpp.mvpdemo.net.parser.HttpFlinger;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 * 文章列表的Presenter，负责从网络上加载最新的文章列表。第一次加载最新的文章列表先从数据库中加载缓存，然后再从网络上加载最新的数据
 */

public class ArticleListPresenter extends BasePresenter<ArticleListView> {
    public static final int FIRST_PAGE = 1;
    private int mPageIndex = FIRST_PAGE;
    ArticleParser mArticleParser = new ArticleParser();
    private boolean isCacheLoaded = false;

    /**
     * 第一次先从数据库中加载缓存，然后再从网络上获取数据
     */
    public void fetchLastestArticles() {
        if (!isCacheLoaded) {
            mView.onFetchedArticles(DatabaseHelper.getInstance().loadArticles());
        }
        //从网络获取最新的数据

    }

    private void fetchArticlesAsync(final int page) {
        mView.onShowLoading();
        HttpFlinger.get(prepareRequestUrl(page), mArticleParser, new DataListener<List<Article>>() {
            @Override
            public void onComplete(List<Article> result) {
                mView.onHideLoding();
                if (!isCacheLoaded && result != null) {
                    mView.clearCacheArticles();
                    isCacheLoaded = true;
                }
                if (result == null) {
                    return;
                }
                mView.onFetchedArticles(result);
                //存储文章列表
                DatabaseHelper.getInstance().saveArticles(result);
            }
        });
    }

    public void loadNextPageArticles() {
        fetchArticlesAsync(mPageIndex);
    }
    /**
     * 更新下一页的
     *
     * @param page
     * @return
     */
    private String prepareRequestUrl(int page) {
        return "http://www.devtf.cn/api/v1/?type=articles&page=" + page
                + "&count=20&category=1";
    }
}
