package com.lxkj.xpp.jtm_techfrontier.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.lxkj.xpp.jtm_techfrontier.adapter.MenuAdapter.MenuViewHolder;
import com.lxkj.xpp.jtm_techfrontier.R;
import com.lxkj.xpp.jtm_techfrontier.bean.MenuItem;

/**
 * Created by Administrator on 2016/11/29.
 */

public class MenuAdapter extends RecyclerBaseAdapter<MenuItem, MenuViewHolder> {


    @Override
    protected void bindDataToItemView(MenuViewHolder viewHolder, MenuItem item) {
        viewHolder.nameTextView.setText(item.text);
        viewHolder.userImageView.setImageResource(item.iconResId);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MenuViewHolder(inflateItemView(parent, R.layout.menu_item));
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
