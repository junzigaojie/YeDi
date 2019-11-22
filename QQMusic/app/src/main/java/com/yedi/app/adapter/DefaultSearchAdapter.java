package com.yedi.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yedi.app.tools.SearchViewHolder;

import java.util.List;

public abstract  class DefaultSearchAdapter <T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mData;
    protected int mLayoutId;

    public DefaultSearchAdapter(Context context, List<T> data, int layoutId){
        mContext = context;
        mData = data;
        mLayoutId = layoutId;

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SearchViewHolder holder = SearchViewHolder.getHolder(mContext,convertView,mLayoutId,parent,position);
        convert(holder,position);
        return holder.getConvertView();
    }

    /**
     * get holder convert
     */
    public abstract void convert(SearchViewHolder holder,int position);
}
