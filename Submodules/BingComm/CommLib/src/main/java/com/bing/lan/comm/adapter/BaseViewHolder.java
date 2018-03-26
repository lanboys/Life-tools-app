package com.bing.lan.comm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by 蓝兵 on 2018/3/24.
 */

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    public View itemView;

    public View getItemView() {
        return itemView;
    }

    public BaseViewHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        ButterKnife.bind(this, itemView);
    }

    public abstract void fillData(T data, int position);
}