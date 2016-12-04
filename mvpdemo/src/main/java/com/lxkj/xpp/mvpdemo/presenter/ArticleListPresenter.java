package com.lxkj.xpp.mvpdemo.presenter;

import com.lxkj.xpp.mvpdemo.beans.Article;
import com.lxkj.xpp.mvpdemo.db.DatabaseHelper;
import com.lxkj.xpp.mvpdemo.listener.DataListener;
import com.lxkj.xpp.mvpdemo.mvpview.ArticleListView;
import com.lxkj.xpp.mvpdemo.net.parser.ArticleParser;
import com.lxkj.xpp.mvpdemo.net.parser.HttpFlinger;

import java.util.List;

/**
 * 文章列表的Presenter,负责从网络上加载最新的文章列表。第一次加载最新文章列表时先从数据库中加载缓存，然后再从网络上加载最新的数据。
 *
 */
public class ArticleListPresenter extends BasePresenter<ArticleListView> {

    public static final int FIRST_PAGE = 1;
    private int mPageIndex = FIRST_PAGE;
    ArticleParser mArticleParser = new ArticleParser();
    private boolean isCacheLoaded = false;

    /**
     * 第一次先从数据库中加载缓存,然后再从网络上获取数据
     */
    public void fetchLastestArticles() {
        if (!isCacheLoaded) {
            mView.onFetchedArticles(DatabaseHelper.getInstance().loadArticles());
        }
        // 从网络上获取最新的数据
        fetchArticlesAsync(FIRST_PAGE);
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
                // 存储文章列表
                DatabaseHelper.getInstance().saveArticles(result);
                updatePageIndex(page, result);
            }
        });
    }

    /**
     * 更新下一页的索引,当请求成功且不是第一次请求最新数据时更新索引值。
     *
     * @param result
     */
    public void updatePageIndex(int curPage, List<Article> result) {
        if (result.size() > 0
                && shouldUpdatePageIndex(curPage)) {
            mPageIndex++;
        }
    }

    /**
     * 是否应该更新Page索引。更新索引值的时机有两个，一个是首次成功加载最新数据时mPageIndex需要更新;另一个是每次加载更多数据时需要更新.
     *
     * @param curPage
     * @return
     */
    private boolean shouldUpdatePageIndex(int curPage) {
        return (mPageIndex > 1 && curPage > 1)
                || (curPage == 1 && mPageIndex == 1);
    }

    public int getPageIndex() {
        return mPageIndex;
    }

    public void loadNextPageArticles() {
        fetchArticlesAsync(mPageIndex);
    }

    private String prepareRequestUrl(int page) {
        return "http://www.devtf.cn/api/v1/?type=articles&page=" + page
                + "&count=20&category=1";
    }
}