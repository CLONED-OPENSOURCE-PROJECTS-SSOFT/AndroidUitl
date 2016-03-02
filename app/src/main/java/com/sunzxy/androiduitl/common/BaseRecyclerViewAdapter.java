package com.sunzxy.androiduitl.common;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zhengxiaoyong on 16/3/2.
 */
public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseRecyclerViewAdapter.RecyclerViewHolder> {
    private List<T> mDatas;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;

    public BaseRecyclerViewAdapter(List<T> datas) {
        this.mDatas = datas;
    }

    public List<T> getmDatas() {
        return mDatas;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(OnItemLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;
    }

    public void add(T data, int position) {
        mDatas.add(position, data);
        this.notifyItemInserted(position);
    }

    public void addAll(List<T> datas) {
        mDatas.addAll(datas);
        this.notifyDataSetChanged();
    }

    public void remove(int position) {
        mDatas.remove(position);
        this.notifyItemRemoved(position);
    }

    public void clearAll() {
        mDatas.clear();
        this.notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = bindItemLayoutId();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new RecyclerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {

        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mItemLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }

        bindItemData(holder, position, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @LayoutRes
    public abstract int bindItemLayoutId();

    public abstract void bindItemData(RecyclerViewHolder holder, int position, T data);

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private SparseArray<View> mItemViews;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mItemViews = new SparseArray<>();
        }

        public <T extends View> T findView(int viewId) {
            View view = mItemViews.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                mItemViews.put(viewId, view);
            }
            return (T) view;
        }

        public TextView getTextView(int viewId) {
            return findView(viewId);
        }

        public Button getButton(int viewId) {
            return findView(viewId);
        }

        public ImageView getImageView(int viewId) {
            return findView(viewId);
        }
    }
}