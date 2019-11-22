package com.yedi.app.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.yedi.app.bean.LrcEntity;
import com.yedi.app.qqmusic.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.LogRecord;

public class LrcView extends View {
    private int width,height;//歌词视图宽高度
    private String lrc_str="";
    private String lrc_str_temp="";
    private List<LrcEntity> mList;
    private List<String> lrc_nostr_list,lrc_str_list;
    private List<Long> lrc_time_list;
    private long druaing=0;
    private long start=0;
    private int current=0;
    private Paint mPaint;
    private Paint bandPaint;//粗体字
    private int no_times=0;

    private int little_size=45;
    private int big_size=50;
    private int bank_size=55;


    private int standard=0;

    private float pre_y=0;



    public LrcView(Context context) {
        super(context);
    }

    public LrcView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LrcView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        lrc_str_temp=getContext().getResources().getString(R.string.title_default);
        setFocusable(true);     //设置可对焦
        mPaint=new Paint();
        bandPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#FF6B8E23"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(little_size);

        bandPaint.setAntiAlias(true);
        bandPaint.setColor(Color.parseColor("#FF138E23"));
        bandPaint.setTextSize(big_size);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();

        if(action==MotionEvent.ACTION_DOWN){
            Log.i("gaojie","ACTION_DOWN");
            pre_y=event.getY();
            return true;
        }

        if(action==MotionEvent.ACTION_MOVE){
            Log.i("gaojie","ACTION_MOVE");
            float current=event.getY()-pre_y;
            float abs_y=Math.abs(current);
            return true;
        }

        if(action==MotionEvent.ACTION_UP){
            Log.i("gaojie","ACTION_UP");
            return true;
        }

        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(lrc_str.length()==0){
            canvas.drawText(lrc_str_temp,(width-lrc_str_temp.length()*little_size)/2,standard,mPaint);
        }else{
            LrcEntity c_entity=mList.get(current);
            String lrc_str=c_entity.getText();
            canvas.drawText(lrc_str,(width-lrc_str.length()*big_size)/2,standard-little_size-bank_size/2,bandPaint);

            //打印上半部分
            int up_start=current-1;
            for(int i=up_start;i>0;i--){
             LrcEntity up_entity=mList.get(i);
             String up_Str=up_entity.getText();
             canvas.drawText(up_Str,(width-up_Str.length()*little_size)/2,standard-(current-i)*(little_size+bank_size)-bank_size,mPaint);
            }

            int down_start=current+1;
            for(int i=down_start;i<mList.size()-1;i++){
                LrcEntity down_entity=mList.get(i);
                String down_Str=down_entity.getText();
                canvas.drawText(down_Str,(width-down_Str.length()*little_size)/2,standard+(i-current-1)*(little_size+bank_size)+bank_size/2,mPaint);
            }
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        width=MeasureSpec.getSize(widthMeasureSpec);
        height=MeasureSpec.getSize(heightMeasureSpec);
        standard=height/2;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    // lrc字符串拼接
    private String getFromLrcFile(String lrcPath) {
        // TODO Auto-generated method stub

        try {
            AssetManager.AssetInputStream a= (AssetManager.AssetInputStream) getContext().getAssets().open("mp3.lrc");
            /*InputStreamReader inputReader = new InputStreamReader(
                    new FileInputStream(lrcPath));
                    */
            InputStreamReader inputReader=new InputStreamReader(a);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            String Result = "";
            while ((line = bufReader.readLine()) != null) {
                if (line.trim().equals(""))
                    continue;
                Result += line + "\r\n";
            }
            lrc_str=Result;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public void updateLirView(int progress){
        long time= (long) (((float)progress/100)*druaing);
        if(time>start) {
            obtainCurrentText(time);
        }
        obtainNoTime(time);
        current=current+no_times;
        postInvalidate();
    }

    private void obtainNoTime(long time){
        int size=lrc_nostr_list.size();
        if(size>0){
            long pre_time=start/lrc_nostr_list.size();
            for(int i=0;i<lrc_nostr_list.size();i++){
                if(pre_time*i>time){
                    no_times=i;
                    break;
                }

            }
        }



    }



    private void obtainCurrentText(long time){
        for(int i=0;i<mList.size();i++){
            if(lrc_time_list.get(i)>=time){
                current=i;
                break;
            }
        }
    }

    public void setLrcPath(String path){
        current=0;
        lrc_str="";
       new ParseThread(path) .start();
    }

    class ParseThread extends Thread{
        private String path;
        @Override
        public void run() {
            super.run();
            String lrc_str=getFromLrcFile(path);
            mList=LrcEntity.parseLrc(lrc_str);
            lrc_nostr_list=new ArrayList<>();
            lrc_str_list=new ArrayList<>();
            lrc_time_list=new ArrayList<>();
            druaing=mList.get(mList.size()-1).getTimeLong();
            start=mList.get(0).getTimeLong();
            for(int i=0;i<mList.size();i++){
                if(mList.get(i).time.length()==0){
                    lrc_nostr_list.add(mList.get(i).getText());
                }else{
                    lrc_str_list.add(mList.get(i).getText());
                    lrc_time_list.add(mList.get(i).getTimeLong());
                }
            }
            Message msg=new Message();
            msg.what=1;
            handler.sendMessage(msg);
        }

        public ParseThread(String path){
           this.path=path;

        }
    }

    private Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            invalidate();
        }
    };
}


