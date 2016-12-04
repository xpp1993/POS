package com.lxkj.xpp.mvpdemo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.xpp.mvpdemo.adapter.ArticleAdapter;
import com.lxkj.xpp.mvpdemo.beans.Article;
import com.lxkj.xpp.mvpdemo.listener.OnItemClickListener;
import com.lxkj.xpp.mvpdemo.mvpview.ArticleListView;
import com.lxkj.xpp.mvpdemo.presenter.ArticleListPresenter;
import com.lxkj.xpp.mvpdemo.widgets.AutoLoadRecyclerView;

import java.util.List;

/**
 * Created by XPP on 2016/12/4/004.
 * 文章列表界面，包含自动滚动广告栏，文章列表
 */

public class ArticleListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, AutoLoadRecyclerView.OnLoadListener, ArticleListView {
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    protected AutoLoadRecyclerView mRecycleView;
    protected ArticleAdapter mAdapter;
    private ArticleListPresenter mPresenter = new ArticleListPresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recyclerview, container, false);
        initRefreshView(rootView);
        initAdapter();
        mSwipeRefreshLayout.setRefreshing(true);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.attach(this);
        mPresenter.fetchLastestArticles();
    }

    protected void initRefreshView(View rootView) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecycleView = (AutoLoadRecyclerView) rootView.findViewById(R.id.articles_recycler_view);
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setVisibility(View.VISIBLE);
        mRecycleView.setOnLoadListener(this);
    }

    protected void initAdapter() {
        mAdapter = new ArticleAdapter();
        mAdapter.setOnItemClickListener(new OnItemClickListener<Article>() {
            @Override
            public void onClick(Article item) {
                if (item != null) {
                    jumpToDetailActivity(item);
                }
            }
        });
        //设置adapter
        mRecycleView.setAdapter(mAdapter);
    }

    protected void jumpToDetailActivity(Article article) {
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("post_id", article.post_id);
        intent.putExtra("title", article.title);
        startActivity(intent);
    }

    @Override
    public void onRefresh() {
        mPresenter.fetchLastestArticles();
    }

    @Override
    public void onLoad() {
        mPresenter.loadNextPageArticles();
    }

    @Override
    public void onFetchedArticles(List<Article> result) {
        mAdapter.addItems(result);
    }

    @Override
    public void clearCacheArticles() {
        mAdapter.clear();
    }

    @Override
    public void onShowLoading() {
        mSwipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideLoding() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
