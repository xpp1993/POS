package com.lxkj.xpp.mvpdemo.presenter;

import android.text.TextUtils;

import com.lxkj.xpp.mvpdemo.beans.ArticleDetail;
import com.lxkj.xpp.mvpdemo.db.DatabaseHelper;
import com.lxkj.xpp.mvpdemo.listener.DataListener;
import com.lxkj.xpp.mvpdemo.mvpview.ArticleDertailView;
import com.lxkj.xpp.mvpdemo.net.HtmlUtls;
import com.lxkj.xpp.mvpdemo.net.parser.HttpFlinger;

/**
 * Created by Administrator on 2016/12/2.
 * 文章详情页面Presenter，负责加载文章内容。如果数据库中有缓存，那么使用缓存，否则从网络上下载内容到本地，并存储
 */

public class ArticleDetailPresenter extends BasePresenter<ArticleDertailView> {
    /**
     * 加载文章的内容，先从数据库中加载，如果数据库中有，那么则不会从网上获取
     */
    public void fetchArticleContent(final String postId, String title) {
        //从数据库获取文章内容缓存
        String articleContent = loadArticleContentFromDB(postId);
        if (!TextUtils.isEmpty(articleContent)) {
            String htmlContent = HtmlUtls.wrapArticleContent(title, articleContent);
            mView.onFetcheedArticleContent(htmlContent);
        } else {
            fetchContentFromServer(postId, title);
        }

    }

    public String loadArticleContentFromDB(String postId) {
        return DatabaseHelper.getInstance().loadArticleDetail(postId).content;
    }

    /**
     * 网络获取文章详情
     *
     * @param postId
     * @param title
     */
    protected void fetchContentFromServer(final String postId, final String title) {
        mView.onShowLoading();
        final String reqUrl = "http://www.devtf.cn/api/v1/?type=article&post_id=" + postId;
        HttpFlinger.get(reqUrl, new DataListener<String>() {
            @Override
            public void onComplete(String result) {
                mView.onHideLoding();
                if (TextUtils.isEmpty(result)) {
                    result = "未获取到文章内容";
                }
                mView.onFetcheedArticleContent(HtmlUtls.wrapArticleContent(title, result));
                DatabaseHelper.getInstance().saveArricleDetail(new ArticleDetail(postId, reqUrl));
            }
        });
    }
}
