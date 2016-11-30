package com.lxkj.xpp.jtm_techfrontier.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxkj.xpp.jtm_techfrontier.R;
import com.lxkj.xpp.jtm_techfrontier.bean.Article;

/**
 * Created by Administrator on 2016/11/29.
 */

public class ArticleAdapter extends RecyclerBaseAdapter<Article, ArticleAdapter.ArticleViewHolder> {
    @Override
    protected void bindDataToItemView(ArticleViewHolder viewHolder, Article item) {
        viewHolder.titleTv.setText(item.title);
        viewHolder.publishTimeTv.setText(item.publishTime);
        viewHolder.authorTv.setText(item.author);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = inflateItemView(viewGroup, R.layout.recyclerview_article_item);
        return new ArticleViewHolder(itemView);
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
