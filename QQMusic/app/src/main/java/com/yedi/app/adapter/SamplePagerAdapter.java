package com.yedi.app.adapter;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yedi.app.qqmusic.R;

import java.util.Random;

public class SamplePagerAdapter extends PagerAdapter {

    private final Random random = new Random();
    private int mSize;

    public SamplePagerAdapter() {
        mSize = 5;
    }

    public SamplePagerAdapter(int count) {
        mSize = count;
    }

    @Override
    public int getCount() {
        return mSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        /*
        TextView textView = new TextView(view.getContext());

        textView.setText(position + 1 + "");
        textView.setBackgroundColor(0xff000000 | random.nextInt(0x00ffffff));
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(50);
        view.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        */

        ImageView image=new ImageView(view.getContext());
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        image.setImageResource(R.drawable.pager_test);
        view.addView(image,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        return image;
    }

    // 增加item
    public void addItem() {
        mSize++;
        notifyDataSetChanged();
    }

    // 删除item
    public void removeItem() {
        mSize--;
        mSize = mSize < 0 ? 0 : mSize;

        notifyDataSetChanged();
    }

}
