package com.lxkj.xpp.jtm_techfrontier.weigets;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by Administrator on 2016/11/29.
 */
public class AutoLoadRecyclerView extends RecyclerView {
    OnLoadListener mLoadListener;

    boolean isLoading = false;

    public AutoLoadRecyclerView(Context context) {
        this(context, null);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoLoadRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if ( isInEditMode() ) {
            return ;
        }
        init();
    }

    private void init() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                checkLoadMore(dx, dy);
            }
        });
    }

    public void setOnLoadListener(OnLoadListener listener) {
        mLoadListener = listener;
    }

    private void checkLoadMore(int dx, int dy) {
        if (isBottom(dx, dy) && !isLoading
                && isValidDelay
                && mLoadListener != null) {
            isValidDelay = false;
            mLoadListener.onLoad();
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    isValidDelay = true;
                }
            }, 1000);
        }
    }

    private boolean isBottom(int dx, int dy) {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
        int totalItemCount = layoutManager.getItemCount();
        return lastVisibleItem >= totalItemCount - 4 && dy > 0;
    }

    boolean isValidDelay = true;

    public void setLoading(boolean loading) {
        this.isLoading = loading;
    }

    public static interface OnLoadListener {
        public void onLoad();
    }

}
