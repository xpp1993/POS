package com.lxkj.xpp.jtm_techfrontier.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.lxkj.xpp.jtm_techfrontier.OnItemClickListener;
import com.lxkj.xpp.jtm_techfrontier.R;
import com.lxkj.xpp.jtm_techfrontier.bean.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */

public class ArticleAdapter extends Adapter<ArticleAdapter.ArticleViewHolder> {
    List<Article> mDataSet = new ArrayList<Article>();
    OnItemClickListener<Article> mItemClickListener;

    public ArticleAdapter(List<Article> dataSet) {
        mDataSet = dataSet;
    }

    protected Article getItem(int position) {
        return mDataSet.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    @Override
    public final void onBindViewHolder(ArticleViewHolder viewHolder, int position) {
        final Article item = getItem(position);
        bindArticleToItemView(viewHolder, item);
        setupItemViewClickListener(viewHolder, item);
    }

    public void setOnItemClickListener(OnItemClickListener<Article> mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /**
     * ItemView的点击事件
     *
     * @param viewHolder
     * @param item
     */
    protected void setupItemViewClickListener(ArticleViewHolder viewHolder, final Article item) {
        viewHolder.itemView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onClick(item);
                }
            }
        });
    }

    protected View inflateItemView(ViewGroup viewGroup, int layoutId, boolean attach) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, attach);
    }

    protected void bindArticleToItemView(ArticleViewHolder viewHolder, Article item) {
        viewHolder.titleTv.setText(item.title);
        viewHolder.publishTimeTv.setText(item.publishTime);
        viewHolder.authorTv.setText(item.author);
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return createArticleViewHolder(viewGroup);
    }

    protected ArticleViewHolder createArticleViewHolder(ViewGroup viewGroup) {
        return new ArticleViewHolder(inflateItemView(viewGroup, R.layout.recyclerview_article_item,
                false));
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
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
