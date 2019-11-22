package com.yedi.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yedi.app.qqmusic.R;

import java.util.ArrayList;

public class NoScrollBandAdapter extends BaseAdapter {

    private ArrayList<String> urls=new ArrayList<>();
    private LayoutInflater mInflater;//布局装载器对象

    public NoScrollBandAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return R.drawable.pager_test;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoScrollBandAdapter.ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHolder = new NoScrollBandAdapter.ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布局，所以第二个参数通常设置为null
            convertView = mInflater.inflate(R.layout.band_list_item, null);

            //对viewHolder的属性进行赋值
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_band_item_img);
            viewHolder.band_first = (TextView) convertView.findViewById(R.id.tv_band_item_1);
            viewHolder.band_second = (TextView) convertView.findViewById(R.id.tv_band_item_2);
            viewHolder.band_thrid = (TextView) convertView.findViewById(R.id.tv_band_item_3);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }else{//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (NoScrollBandAdapter.ViewHolder) convertView.getTag();
        }
        // 取出bean对象

        // 设置控件的数据
        viewHolder.imageView.setImageResource(R.drawable.pager_test);
        viewHolder.band_first.setText("季姬击鸡记昆明妈妈");
        viewHolder.band_second.setText("季姬击鸡记昆明妈妈");
        viewHolder.band_thrid.setText("季姬击鸡记昆明妈妈");
        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView band_first;
        public TextView band_second;
        public TextView band_thrid;
    }
}
