package com.yedi.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.yedi.app.qqmusic.R;
import com.yedi.app.tools.DisplayTools;

import java.util.logging.LogRecord;

public class CircleImageView extends View {
    private int radio=0;

    private Bitmap mBitmap;

    private Paint mPaint;

    private static final long rotate_delay = 1;//旋转动作时间

    private int mrotatedegrees;//旋转的角度

    private boolean isRotate;


    private Handler mRotate=new Handler();
    private final Runnable rotateRunnable=new Runnable() {
        @Override
        public void run() {
            updateRotate();
            mRotate.postDelayed(this,rotate_delay);
        }
    };

    private void updateRotate(){
      mrotatedegrees+=1;
      mrotatedegrees=mrotatedegrees%360;
      Log.i("gaojie",mrotatedegrees+"");
      postInvalidate();
    }

    private void start(){
      isRotate=true;
      mRotate.removeCallbacksAndMessages(null);
      mRotate.postDelayed(rotateRunnable,rotate_delay);
      postInvalidate();
    }

    private void stop(){
     isRotate=false;
     postInvalidate();
    }

    public void setBitmap(Bitmap mBitmap){
        if(mBitmap==null){
            this.mBitmap= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.default_cover);
        }else{
            int width=mBitmap.getWidth();
            int height=mBitmap.getHeight();
            Matrix m=new Matrix();
            float width_scale=(float) radio/width;
            float height_scale=(float)radio/height;
            m.postScale(1/width_scale,1/height_scale);
            this.mBitmap=Bitmap.createBitmap(mBitmap,0,0,width,height,m,false);
        }
        postInvalidate();
    }
    public CircleImageView(Context context) {
        super(context);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true);
        Bitmap drawBitmap;
        if(mBitmap==null){
            mBitmap= BitmapFactory.decodeResource(getContext().getResources(),R.drawable.background_player_bar);
        }
        drawBitmap=getCropBitmap(mBitmap,radio);
        canvas.rotate(mrotatedegrees,radio/2,radio/2);
        canvas.drawBitmap(drawBitmap, 0,0,mPaint);
        if(mrotatedegrees==0) {
            start();
        }
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=MeasureSpec.getSize(heightMeasureSpec);
        int width_mode=MeasureSpec.getMode(widthMeasureSpec);
        int pre_radio=Math.max(width,height);
         radio=Math.max(pre_radio,radio);
        int m=MeasureSpec.makeMeasureSpec(radio,MeasureSpec.EXACTLY);
        super.onMeasure(m, m);
    }

    private Bitmap getCropBitmap(Bitmap cirBitmap,int radio){
        Bitmap output = Bitmap.createBitmap(radio, radio,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radio, radio);

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawCircle(radio / 2 + 0.7f,
                radio / 2 + 0.7f, radio / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(cirBitmap, rect, rect, paint);

        return output;
    }

    private void init(Context context,AttributeSet attrs){
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.CircleImageViewStyle);
        radio=array.getInteger(R.styleable.CircleImageViewStyle_radio,40);
        mPaint=new Paint();
        array.recycle();
    }
}
