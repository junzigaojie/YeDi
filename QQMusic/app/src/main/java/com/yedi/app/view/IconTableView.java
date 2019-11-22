package com.yedi.app.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class IconTableView extends GridView {
    private int startY;
    public IconTableView(Context context) {
        super(context);
    }

    public IconTableView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public IconTableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /*
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE)
        {
            return true;// 禁止Gridview进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }*/


       @Override
       protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
          int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,MeasureSpec.AT_MOST);
          super.onMeasure(widthMeasureSpec, expandSpec);
        }


}
