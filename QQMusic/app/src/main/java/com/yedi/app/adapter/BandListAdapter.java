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

public class BandListAdapter extends BaseAdapter {

    private ArrayList<String> urls=new ArrayList<>();
    private LayoutInflater mInflater;//布局装载器对象

    public BandListAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return 3;
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
        ViewHolder viewHolder;
        //如果view未被实例化过，缓存池中没有对应的缓存
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // 由于我们只需要将XML转化为View，并不涉及到具体的布局，所以第二个参数通常设置为null
            convertView = mInflater.inflate(R.layout.grid_list_item, null);

            //对viewHolder的属性进行赋值
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_gridView_item);
            viewHolder.title = (TextView) convertView.findViewById(R.id.tv_gridView_item);

            //通过setTag将convertView与viewHolder关联
            convertView.setTag(viewHolder);
        }else{//如果缓存池中有对应的view缓存，则直接通过getTag取出viewHolder
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 取出bean对象

        // 设置控件的数据
        viewHolder.imageView.setImageResource(R.drawable.pager_test);
        viewHolder.title.setText("季姬击鸡记昆明妈妈");
        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public TextView title;
    }
}
