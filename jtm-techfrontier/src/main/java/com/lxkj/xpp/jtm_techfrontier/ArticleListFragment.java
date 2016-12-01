package com.lxkj.xpp.jtm_techfrontier;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.xpp.jtm_techfrontier.adapter.ArticleAdapter;
import com.lxkj.xpp.jtm_techfrontier.bean.Article;
import com.lxkj.xpp.jtm_techfrontier.db.DatabaseHelper;
import com.lxkj.xpp.jtm_techfrontier.utils.ArticleParser;
import com.lxkj.xpp.jtm_techfrontier.utils.DataListener;
import com.lxkj.xpp.jtm_techfrontier.utils.Httpflinger;
import com.lxkj.xpp.jtm_techfrontier.weigets.AutoLoadRecyclerView;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 * 文章列表界面，包含自动滚动广告栏、文章列表
 */

public class ArticleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AutoLoadRecyclerView.OnLoadListener {

    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected AutoLoadRecyclerView mRecyclerView;
    protected ArticleAdapter mAdapter;
    private int mPageIndex = 1;
    ArticleParser mArticleParser = new ArticleParser();


    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycleview, container, false);
        initRefreshView(rootView);
        initAdapter();
        mSwipeRefreshLayout.setRefreshing(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter.addItems(DatabaseHelper.getInstance().loadArticles());
    }

    protected void initRefreshView(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (AutoLoadRecyclerView) rootView.findViewById(R.id.articles_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()
                .getApplicationContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setVisibility(View.VISIBLE);
        mRecyclerView.setOnLoadListener(this);
    }

    protected void initAdapter() {
        //mAdapter = new ArticleAdapter(mDataSet);
        mAdapter = new ArticleAdapter();
        // mAdapter.addItems(mDataSet);
        mAdapter.setOnItemClickListener(new OnItemClickListener<Article>() {

            @Override
            public void onClick(Article article) {
                if (article != null) {
                    jumpToDetailActivity(article);
                }
            }
        });
        // 设置Adapter
        mRecyclerView.setAdapter(mAdapter);
        fetchArticle(1);
    }

    private void fetchArticle(final int page) {
        mSwipeRefreshLayout.setRefreshing(true);
       Httpflinger.get(prepareRequestUrl(), mArticleParser, new DataListener<List<Article>>() {
            @Override
            public void onComplete(List<Article> result) {
                if (result==null)
                    return;
                // 添加心数据
                mAdapter.addItems(result);
                mSwipeRefreshLayout.setRefreshing(false);
                // 存储文章列表
                DatabaseHelper.getInstance().saveArticles(result);
                if (result.size() > 0) {
                    mPageIndex++;
                }
            }
        });
    }

    private String prepareRequestUrl() {
        String getUrl =
                "http://www.devtf.cn/api/v1/?type=articles&page=" + mPageIndex
                        + "&count=20&category=1";
        return getUrl;
    }

    protected void jumpToDetailActivity(Article article) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("post_id", article.post_id);
        intent.putExtra("title", article.title);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        fetchArticle(1);
    }

    @Override
    public void onLoad() {
        mSwipeRefreshLayout.setRefreshing(true);
        fetchArticle(mPageIndex);
    }
}
