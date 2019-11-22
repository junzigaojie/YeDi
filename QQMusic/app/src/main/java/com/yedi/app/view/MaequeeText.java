package com.yedi.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.TextView;

public class MaequeeText extends TextView {

    /** 是否停止滚动 */
    private boolean mStopMarquee;
    private String mText;
    private float mCoordinateX;
    private float mTextWidth;

    public MaequeeText(Context context) {
        super(context);
    }

    public MaequeeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MaequeeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * a.当前控件的焦点,第一次xml加载 的情况
     */
    @Override
    public boolean isFocused() {
        return true;//告诉我们的系统 ,我这里是一直有焦点的
    }



    //b.在更改焦点时,有别的控件申请焦点的情况下
    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        if (focused) {//有焦点
            super.onFocusChanged(focused, direction, previouslyFocusedRect);
        }
    }

    //c.弹出对话框的情况下
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (hasWindowFocus) {
            super.onWindowFocusChanged(hasWindowFocus);
        }
    }


}
