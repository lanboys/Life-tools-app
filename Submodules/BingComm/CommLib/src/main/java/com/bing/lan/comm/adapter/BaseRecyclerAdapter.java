package com.bing.lan.comm.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class BaseRecyclerAdapter<T> extends
        RecyclerView.Adapter< BaseViewHolder> {

    public static final int ITEM_TYPE_NORMAL = 1000;
    /*数据集合*/
    protected List<T> data;
    /*单击事件*/
    private OnItemClickListener mListener;

    /**
     * 返回item的布局资源id
     */
    public abstract int getItemLayoutResId(int viewType);

    /**
     * 返回BaseViewHolder的子类
     */
    public abstract  BaseViewHolder createViewHolder(View itemView, int type);

    /**
     * 设置事件单击监听器
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Deprecated
    public void setItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public  BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View itemView = inflater.inflate(getItemLayoutResId(viewType), parent, false);
        final  BaseViewHolder holder = createViewHolder(itemView, viewType);
        if (mListener != null) {
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onRecyclerViewItemClick(itemView, holder.getLayoutPosition());
                        }
                    });
        }

        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_TYPE_NORMAL;
    }

    @Override
    public void onBindViewHolder( BaseViewHolder holder, int position) {
        holder.fillData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public void setDataAndRefresh(List<T> data1) {
        if (data != null) {
            data.clear();
            data.addAll(data1);
        } else {
            data = data1;
        }
        notifyDataSetChanged();
        notifyItemRangeChanged(0, getItemCount() - 1);
    }

    public void addDataAndRefresh(List<T> data1) {
        if (data != null) {
            data.addAll(data1);
        } else {
            data = data1;
        }
        notifyDataSetChanged();
        notifyItemRangeChanged(0, getItemCount() - 1);
    }

    public T getItem(int position) {
        return data.get(position);
    }

    /*事件监听接口*/
    public interface OnItemClickListener {

        void onRecyclerViewItemClick(View v, int position);
    }

    //protected abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    //
    //    public View itemView;
    //
    //    public View getItemView() {
    //        return itemView;
    //    }
    //
    //    public BaseViewHolder(View itemView) {
    //        super(itemView);
    //        this.itemView = itemView;
    //        ButterKnife.bind(this, itemView);
    //    }
    //
    //    public abstract void fillData(T data, int position);
    //}
}

