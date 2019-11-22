package com.yedi.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yedi.app.bean.MusicInfo;
import com.yedi.app.qqmusic.QuickMusicActivity;
import com.yedi.app.qqmusic.R;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseFragment extends Fragment {


    protected View rootView;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView=getRootView(inflater,container);
        initView();
        return rootView;
    }

    protected abstract View getRootView(LayoutInflater inflater, ViewGroup container);

    protected abstract void initView();

    protected void onTableClickListerer(Activity context, ArrayList<String> urls, int position)
    {
       Intent intent  =new Intent(context, QuickMusicActivity.class);
       Log.i("gaojie","ssssssss");
       context.startActivity(intent);
    }

}
