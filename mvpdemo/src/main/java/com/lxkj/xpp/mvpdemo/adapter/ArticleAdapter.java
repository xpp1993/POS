package com.lxkj.xpp.mvpdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.xpp.mvpdemo.R;
import com.lxkj.xpp.mvpdemo.beans.Article;

/**
 * Created by Administrator on 2016/12/2.
 */

public class ArticleAdapter extends RecyclerBaseAdapter<Article, ArticleAdapter.ArticleViewHolder> {
    @Override
    protected void bindDataToItemView(ArticleViewHolder viewHolder, Article item) {
        viewHolder.authorTv.setText(item.author);
        viewHolder.publishTimeTv.setText(item.publishTime);
        viewHolder.titleTv.setText(item.title);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ArticleViewHolder(inflateItemView(parent, R.layout.recyclerview_article_item));
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTv;
        public TextView publishTimeTv;
        public TextView authorTv;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            titleTv = (TextView) itemView.findViewById(R.id.article_title_tv);
            publishTimeTv = (TextView) itemView.findViewById(R.id.article_time_tv);
            authorTv = (TextView) itemView.findViewById(R.id.article_author_tv);
        }
    }
}
