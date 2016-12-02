package com.lxkj.xpp.mvpdemo.adapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lxkj.xpp.mvpdemo.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 */

public abstract class RecyclerBaseAdapter<D, V extends ViewHolder> extends Adapter<V> {
    /**
     * RecyclerView中的数据集
     */
    protected final List<D> mDataSet = new ArrayList<>();
    /**
     * 点击事件处理回调
     */
    private OnItemClickListener<D> mItemClickListener;

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    protected D getItem(int position) {
        return mDataSet.get(position);
    }

    public void addItems(List<D> items) {
        //移除已经存在的数据，避免数据重复
        items.removeAll(mDataSet);
        //添加新的数据
        mDataSet.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        mDataSet.clear();
        notifyDataSetChanged();
    }

    /**
     * 绑定数据，主要分为两步，绑定数据与设置每项的点击事件
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(V holder, int position) {
        final D item = getItem(position);
        bindDataToItemView(holder, item);
        setupItemViewClickListener(holder, item);
    }

    protected View inflateItemView(ViewGroup viewGroup, int layoutId) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
    }

    public void setOnItemClickListener(OnItemClickListener<D> mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /**
     * ItemView 的点击事件
     */
    protected void setupItemViewClickListener(V viewHolder, final D item) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onClick(item);
                }
            }
        });
    }

    /**
     * 将数据绑定到ItemView上
     */
    protected abstract void bindDataToItemView(V viewHolder, D item);

}
