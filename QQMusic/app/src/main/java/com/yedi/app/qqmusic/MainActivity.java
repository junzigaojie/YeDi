package com.yedi.app.qqmusic;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yedi.app.adapter.SearchAdapter;
import com.yedi.app.bean.SearchEntity;
import com.yedi.app.fragment.BandListFragment;
import com.yedi.app.fragment.BaseFragment;
import com.yedi.app.fragment.FavorFragment;
import com.yedi.app.fragment.LocalFragment;
import com.yedi.app.fragment.SettingFragment;
import com.yedi.app.service.ReadMediaService;
import com.yedi.app.view.CustomSearchView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomSearchView.SearchViewListener{


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_person:
                    if(navigation_select_id!=R.id.navigation_person) {
                        replaceFragment(mLocalFragment);
                        navigation_select_id=R.id.navigation_person;
                    }
                    return true;
                case R.id.navigation_favor:
                    if(navigation_select_id!=R.id.navigation_favor){
                        replaceFragment(mFavorFragment);
                        navigation_select_id=R.id.navigation_favor;
                    }
                    return true;
                case R.id.navigation_band:
                    if(navigation_select_id!=R.id.navigation_band){
                        replaceFragment(mBandListFragment);
                        navigation_select_id=R.id.navigation_band;
                    }
                    return true;
                case R.id.navigation_setting:
                    if(navigation_select_id!=R.id.navigation_setting){
                        replaceFragment(mSettingFragment);
                        navigation_select_id=R.id.navigation_setting;
                    }
                    return true;
            }
            return false;
        }
    };

    /**
     * 搜索结果列表view
     */
    private ListView lvResults;

    /**
     * 搜索view
     */
    private CustomSearchView searchView;


    /**
     * 热搜框列表adapter
     */
    private ArrayAdapter<String> hintAdapter;

    /**
     * 自动补全列表adapter
     */
    private ArrayAdapter<String> autoCompleteAdapter;

    /**
     * 搜索结果列表adapter
     */
    private SearchAdapter resultAdapter;

    private List<SearchEntity> dbData;

    /**
     * 热搜版数据
     */
    private List<String> hintData;

    /**
     * 搜索过程中自动补全数据
     */
    private List<String> autoCompleteData;

    /**
     * 搜索结果的数据
     */
    private List<SearchEntity> resultData;

    /**
     * 默认提示框显示项的个数
     */
    private static int DEFAULT_HINT_SIZE = 4;

    /**
     * 提示框显示项的个数
     */
    private static int hintSize = DEFAULT_HINT_SIZE;

    /**
     * 设置提示框显示项的个数
     *
     * @param hintSize 提示框显示个数
     */
    public static void setHintSize(int hintSize) {
        MainActivity.hintSize = hintSize;
    }


    private Fragment mFragment;

    private long navigation_select_id=0;

    private LocalFragment mLocalFragment;

    private FavorFragment mFavorFragment;

    private BandListFragment mBandListFragment;

    private SettingFragment mSettingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mLocalFragment=new LocalFragment();
        mFavorFragment=new FavorFragment();
        mBandListFragment=new BandListFragment();
        mSettingFragment=new SettingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_layout, mFavorFragment).commit();
        navigation.setSelectedItemId(R.id.navigation_favor);
        initSharePreferences();
    }

    private void initSharePreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("app_info", Context.MODE_PRIVATE); //私有数据
        int times=sharedPreferences.getInt("times",0);
        if(times<1) {
            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
            editor.putInt("times", times++);
            editor.commit();//提交修改
            }
        applypermission();
    }

    /**
     * 初始化视图
     */
    private void initSearchViews() {
        lvResults = (ListView) findViewById(R.id.main_lv_search_results);
        searchView = (CustomSearchView) findViewById(R.id.main_search_layout);
        //设置监听
        searchView.setSearchViewListener(this);
        //设置adapter
        searchView.setTipsHintAdapter(hintAdapter);
        searchView.setAutoCompleteAdapter(autoCompleteAdapter);

        lvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this, position + "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //从数据库获取数据
        getDbData();
        //初始化热搜版数据
        getHintData();
        //初始化自动补全数据
        getAutoCompleteData(null);
        //初始化搜索结果数据
        getResultData(null);
    }


    /**
     * 获取db 数据
     */
    private void getDbData() {
        int size = 100;
        dbData = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            dbData.add(new SearchEntity(R.drawable.icon, "android开发必备技能" + (i + 1), "Android自定义view——自定义搜索view", i * 20 + 2 + ""));
        }
    }


    /**
     * 获取热搜版data 和adapter
     */
    private void getHintData() {
        hintData = new ArrayList<>(hintSize);
        for (int i = 1; i <= hintSize; i++) {
            hintData.add("热搜版" + i + "：Android自定义View");
        }
        hintAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, hintData);
    }


    /**
     * 获取自动补全data 和adapter
     */
    private void getAutoCompleteData(String text) {
        if (autoCompleteData == null) {
            //初始化
            autoCompleteData = new ArrayList<>(hintSize);
        } else {
            // 根据text 获取auto data
            autoCompleteData.clear();
            for (int i = 0, count = 0; i < dbData.size()
                    && count < hintSize; i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    autoCompleteData.add(dbData.get(i).getTitle());
                    count++;
                }
            }
        }
        if (autoCompleteAdapter == null) {
            autoCompleteAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, autoCompleteData);
        } else {
            autoCompleteAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取搜索结果data和adapter
     */
    private void getResultData(String text) {
        if (resultData == null) {
            // 初始化
            resultData = new ArrayList<>();
        } else {
            resultData.clear();
            for (int i = 0; i < dbData.size(); i++) {
                if (dbData.get(i).getTitle().contains(text.trim())) {
                    resultData.add(dbData.get(i));
                }
            }
        }
        if (resultAdapter == null) {
            resultAdapter = new SearchAdapter(this, resultData, R.layout.search_item_layout);
        } else {
            resultAdapter.notifyDataSetChanged();
        }
    }



    /**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
    //更新数据
        getAutoCompleteData(text);
    }


    @Override
    public void onSearch(String text) {
        //更新result数据
        getResultData(text);
        lvResults.setVisibility(View.VISIBLE);
        //第一次获取结果 还未配置适配器
        if (lvResults.getAdapter() == null) {
            //获取搜索数据 设置适配器
            lvResults.setAdapter(resultAdapter);
        } else {
            //更新搜索数据
            resultAdapter.notifyDataSetChanged();
        }
        Toast.makeText(this, "完成搜素", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void replaceFragment(BaseFragment mFragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, mFragment).commit();
    }

    public void applypermission(){
        if(Build.VERSION.SDK_INT>=23){
            //检查是否已经给了权限
            int checkpermission= ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkpermission!= PackageManager.PERMISSION_GRANTED){//没有给权限
                Log.e("permission","动态申请");
                //参数分别是当前活动，权限字符串数组，requestcode
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(MainActivity.this,R.string.apply_ok,Toast.LENGTH_SHORT).show();
            ReadMediaService.startReadMediaFile(this);
        }else{
            Toast.makeText(MainActivity.this,R.string.apply_fail,Toast.LENGTH_SHORT).show();
        }
    }
}
