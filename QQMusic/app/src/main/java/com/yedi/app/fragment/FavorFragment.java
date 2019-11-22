package com.yedi.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.yedi.app.adapter.BandListAdapter;
import com.yedi.app.adapter.NoScrollBandAdapter;
import com.yedi.app.adapter.RecommendYediAdapter;
import com.yedi.app.qqmusic.R;
import com.yedi.app.view.IconTableView;
import com.yedi.app.view.NoScrollListView;

public class FavorFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IconTableView re_table;
    private IconTableView re_nostalgic_table;
    private NoScrollListView re_pregnancy_table;



    public FavorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavorFragment newInstance(String param1, String param2) {
        FavorFragment fragment = new FavorFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected View getRootView(LayoutInflater inflater, ViewGroup container) {
        rootView=inflater.inflate(R.layout.fragment_favor, container, false);
        return rootView;
    }

    @Override
    protected void initView() {
        Log.i("gaojie","1111111");
        re_table=(IconTableView)rootView.findViewById(R.id.re_yedi_table);
        re_table.setAdapter(new RecommendYediAdapter(getActivity()));
        re_nostalgic_table=(IconTableView)rootView.findViewById(R.id.re_nostalgic_table);
        re_nostalgic_table.setAdapter(new BandListAdapter(getActivity()));
        re_pregnancy_table=(NoScrollListView)rootView.findViewById(R.id.re_pregnancy_table);
        re_pregnancy_table.setAdapter(new NoScrollBandAdapter(getActivity()));
        re_table.setOnItemClickListener(this);
        re_pregnancy_table.setOnItemClickListener(this);
        re_nostalgic_table.setOnItemClickListener(this);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.i("gaojie","222");
        Toast.makeText(getActivity(),"aaaaa",Toast.LENGTH_LONG).show();
    }
}
