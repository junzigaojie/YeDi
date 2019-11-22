package com.yedi.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.yedi.app.adapter.BandListAdapter;
import com.yedi.app.adapter.NoScrollBandAdapter;
import com.yedi.app.adapter.SamplePagerAdapter;
import com.yedi.app.qqmusic.R;
import com.yedi.app.view.IconTableView;
import com.yedi.app.view.LoopViewPager;
import com.yedi.app.view.NoScrollListView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BandListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BandListFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean auto_scroll=true;

    private LoopViewPager viewpager;

    private int current_id;

    private IconTableView band_first;

    private NoScrollListView band_list;

    private IconTableView re_artist;

    public BandListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BandListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BandListFragment newInstance(String param1, String param2) {
        BandListFragment fragment = new BandListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container) {
        rootView=inflater.inflate(R.layout.fragment_band_list, container, false);
        return rootView;
    }

    @Override
    protected void initView() {
        viewpager = (LoopViewPager) rootView.findViewById(R.id.viewpager);
        viewpager.setAdapter(new SamplePagerAdapter());
        auto_scroll=true;
        current_id=viewpager.getCurrentItem()+1;
        startScroll();
        band_first=(IconTableView)rootView.findViewById(R.id.band_first);
        band_first.setAdapter(new BandListAdapter(getActivity()));
        band_list=(NoScrollListView)rootView.findViewById(R.id.band_list);
        band_list.setAdapter(new NoScrollBandAdapter(getActivity()));
        re_artist=(IconTableView)rootView.findViewById(R.id.re_artist);
        re_artist.setAdapter(new BandListAdapter(getActivity()));
    }

    private void startScroll(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(auto_scroll){
                    try {
                        Thread.sleep(1800);
                        Message msg=new Message();
                        msg.what=1;
                        mHandler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                viewpager.setCurrentItem(current_id++,true);
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        auto_scroll=false;
    }
}

