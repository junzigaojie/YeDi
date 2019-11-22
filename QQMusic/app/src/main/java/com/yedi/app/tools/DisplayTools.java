package com.yedi.app.tools;

import android.content.Context;
import android.view.WindowManager;

public class DisplayTools {

    public static final int getScreenWidth(Context context){
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width=manager.getDefaultDisplay().getWidth();
        return width;
    }

    public static final int getScreenHeight(Context context){
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height=manager.getDefaultDisplay().getWidth();
        return height;
    }
}
