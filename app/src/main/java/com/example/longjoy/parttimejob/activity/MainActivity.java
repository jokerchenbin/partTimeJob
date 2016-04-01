package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.Configs;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.FragmentAdapter;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.fragment.HomePageFragment;
import com.example.longjoy.parttimejob.fragment.MyFragment;
import com.example.longjoy.parttimejob.fragment.PartTimeJobFragment;
import com.example.longjoy.parttimejob.tools.FileTools;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;
import com.example.longjoy.parttimejob.widget.CustomViewPager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private RadioButton rbtn_FirstPage, rbtn_PartTimeJob, rbtn_My;
    private TextView tv_chooseCity;
    private Context context;
    private Activity activity;
    private LocationClient mLocationClient;
    private MyLocationListener mMyLocationListener = new MyLocationListener();
    private CustomViewPager viewPager;
    private FragmentManager bg;
    private HomePageFragment homeFragment;
    private PartTimeJobFragment partJobFragment;
    private MyFragment myFragment;
    private List<Fragment> listfraFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        context = this;
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(mMyLocationListener);    //注册监听函数
        InitLocation();
        mLocationClient.start();
        ((AppApplication) getApplication()).addActivity(activity);
        initViewIds();
    }


    /**
     * 初始化View上的布局ID
     */
    private void initViewIds() {
        viewPager = (CustomViewPager) findViewById(R.id.activity_main_framelayout);
        /* 单选按钮 */
        rbtn_FirstPage = (RadioButton) findViewById(R.id.activity_main_rbtn_firstPage);
        rbtn_FirstPage.setOnClickListener(this);
        rbtn_FirstPage.setChecked(true);
        rbtn_PartTimeJob = (RadioButton) findViewById(R.id.activity_main_rbtn_partTimeJob);
        rbtn_PartTimeJob.setOnClickListener(this);
        rbtn_My = (RadioButton) findViewById(R.id.activity_main_rbtn_my);
        rbtn_My.setOnClickListener(this);
        tv_chooseCity = (TextView) findViewById(R.id.top_button_tim);
        tv_chooseCity.setText("重庆市");
        tv_chooseCity.setOnClickListener(this);
        bg = getSupportFragmentManager();
        homeFragment = new HomePageFragment();
        partJobFragment = new PartTimeJobFragment();
        myFragment = new MyFragment();
        listfraFragments = new ArrayList<>();
        listfraFragments.add(homeFragment);
        listfraFragments.add(partJobFragment);
        listfraFragments.add(myFragment);
        FragmentAdapter fadpter = new FragmentAdapter(bg, listfraFragments);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(fadpter);
        viewPager.setCurrentItem(0);
        viewPager.setPagingEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_rbtn_firstPage://首页
                viewPager.setCurrentItem(0);
                changeTopBarState("蜂鸟兼职");
                break;
            case R.id.activity_main_rbtn_partTimeJob: // 兼职工作
                viewPager.setCurrentItem(1);
                changeTopBarState("我的发布");
                break;
            case R.id.activity_main_rbtn_my: // 我的
                viewPager.setCurrentItem(2);
                changeTopBarState("我的");
                break;
            case R.id.top_button_tim: //选择城市
                Intent cityChooseIntent = new Intent(context, CityChooseActivity.class);
                startActivityForResult(cityChooseIntent, AppConfig.DEFAULT_RESULT);
                break;
            case R.id.top_bar_tv_right: //设置界面
                String temp = tv_right.getText().toString();
                if ("设置".equals(temp)) {
                    startActivity(new Intent(context, SettingActivity.class));
                } else {//提交兼职信息
                    startActivity(new Intent(context,PublishJobActivity.class));
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case AppConfig.DEFAULT_REQUEST: // 城市选择的返回数据
                if (!"".equals(data.getStringExtra("cityName"))) {
                    tv_chooseCity.setText(data.getStringExtra("cityName"));
                }
                break;
        }
    }

    /* 头部的相关视图  标题 */
    private TextView tv_topBar, tv_right;

    /**
     * Created by 陈彬 on 2015/12/30  9:59
     * 方法描述: 改变头部状态
     */
    private void changeTopBarState(String title) {
        tv_topBar = (TextView) findViewById(R.id.top_bar_tv_title);
        tv_right = (TextView) findViewById(R.id.top_bar_tv_right);
        tv_right.setOnClickListener(this);
        tv_topBar.setText(title);
        if ("我的".equals(title)) {
            findViewById(R.id.top_button_tim).setVisibility(View.GONE);
            tv_right.setText("设置");
            tv_right.setVisibility(View.VISIBLE);
        } else if ("我的发布".equals(title)) {
            findViewById(R.id.top_button_tim).setVisibility(View.GONE);
            tv_right.setText("发布");
            tv_right.setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.top_button_tim).setVisibility(View.VISIBLE);
            tv_right.setVisibility(View.GONE);
        }
    }

    /**
     * 实现实位回调监听
     */
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location.getCity().length() > 1) {
                tv_chooseCity.setText(location.getCity());
                mLocationClient.stop();
            }
        }

    }


    private void InitLocation() {
        // 设置定位参数
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(10000); // 10分钟扫描1次
        // 需要地址信息，设置为其他任何值（string类型，且不能为null）时，都表示无地址信息。
        option.setAddrType("all");
        // 设置是否返回POI的电话和地址等详细信息。默认值为false，即不返回POI的电话和地址信息。
        //option.setPoiExtraInfo(true);
        // 设置产品线名称。强烈建议您使用自定义的产品线名称，方便我们以后为您提供更高效准确的定位服务。
        option.setProdName("通过GPS定位我当前的位置");
        // 禁用启用缓存定位数据
        option.disableCache(true);
        // 设置最多可返回的POI个数，默认值为3。由于POI查询比较耗费流量，设置最多返回的POI个数，以便节省流量。
        //option.setPoiNumber(3);
        // 设置定位方式的优先级。
        // 当gps可用，而且获取了定位结果时，不再发起网络请求，直接返回给用户坐标。这个选项适合希望得到准确坐标位置的用户。如果gps不可用，再发起网络请求，进行定位。
        option.setPriority(LocationClientOption.GpsFirst);
        mLocationClient.setLocOption(option);
    }

}
