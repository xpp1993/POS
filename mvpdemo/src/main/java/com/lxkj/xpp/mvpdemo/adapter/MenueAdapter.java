package com.lxkj.xpp.mvpdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxkj.xpp.mvpdemo.R;
import com.lxkj.xpp.mvpdemo.beans.MenuItem;

/**
 * Created by Administrator on 2016/12/2.
 * 菜单列表Adapter
 */

public class MenueAdapter extends RecyclerBaseAdapter<MenuItem, MenueAdapter.MenuViewHolder> {

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(inflateItemView(parent, R.layout.menu_item));
    }

    @Override
    protected void bindDataToItemView(MenuViewHolder viewHolder, MenuItem item) {
        viewHolder.nameTextView.setText(item.text);
        viewHolder.userImageView.setImageResource(item.iconResId);
    }

    static class MenuViewHolder extends RecyclerView.ViewHolder {
        public ImageView userImageView;
        public TextView nameTextView;

        public MenuViewHolder(View itemView) {
            super(itemView);
            userImageView = (ImageView) itemView.findViewById(R.id.menu_icon_imageview);
            nameTextView = (TextView) itemView.findViewById(R.id.menu_text_tv);
        }
    }
}
