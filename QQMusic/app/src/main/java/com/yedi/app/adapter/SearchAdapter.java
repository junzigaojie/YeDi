package com.yedi.app.adapter;

import android.content.Context;

import com.yedi.app.bean.SearchEntity;
import com.yedi.app.qqmusic.R;
import com.yedi.app.tools.SearchViewHolder;

import java.util.List;

public class SearchAdapter extends DefaultSearchAdapter<SearchEntity>{

    public SearchAdapter(Context context, List<SearchEntity> data, int layoutId) {
        super(context, data, layoutId);
    }

    @Override
    public void convert(SearchViewHolder holder, int position) {
        holder.setImageResource(R.id.item_search_iv_icon,mData.get(position).getIconId())
                .setText(R.id.item_search_tv_title,mData.get(position).getTitle())
                .setText(R.id.item_search_tv_content,mData.get(position).getContent())
                .setText(R.id.item_search_tv_comments,mData.get(position).getComments());
    }
}
