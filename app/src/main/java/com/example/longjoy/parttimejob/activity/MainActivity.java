package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.Configs;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.fragment.HomePageFragment;
import com.example.longjoy.parttimejob.fragment.MyFragment;
import com.example.longjoy.parttimejob.fragment.PartTimeJobFragment;
import com.example.longjoy.parttimejob.tools.FileTools;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
//    This is a Text!!!!!!第二次
    private static final String TAG = "MainActivity";
    private LinearLayout main_fragment;
    private FragmentManager fm;
    private RadioButton rbtn_FirstPage, rbtn_PartTimeJob, rbtn_My;
    private TextView tv_chooseCity;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        initViewIds();
    }


    /**
     * 初始化View上的布局ID
     */
    private void initViewIds() {
        main_fragment = (LinearLayout) findViewById(R.id.activity_main_framelayout);
        /* 单选按钮 */
        rbtn_FirstPage = (RadioButton) findViewById(R.id.activity_main_rbtn_firstPage);
        rbtn_FirstPage.setOnClickListener(this);
        rbtn_FirstPage.setChecked(true);
        rbtn_PartTimeJob = (RadioButton) findViewById(R.id.activity_main_rbtn_partTimeJob);
        rbtn_PartTimeJob.setOnClickListener(this);
        rbtn_My = (RadioButton) findViewById(R.id.activity_main_rbtn_my);
        rbtn_My.setOnClickListener(this);
        tv_chooseCity = (TextView) findViewById(R.id.top_button_tim);
        tv_chooseCity.setOnClickListener(this);
		//陈彬 22222
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.activity_main_framelayout, new HomePageFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_rbtn_firstPage:
                HomePageFragment firstPage = new HomePageFragment(); //首页
                fm.beginTransaction().replace(R.id.activity_main_framelayout, firstPage).commit();
                break;
            case R.id.activity_main_rbtn_partTimeJob: // 兼职工作
                PartTimeJobFragment job = new PartTimeJobFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, job).commit();
                break;
            case R.id.activity_main_rbtn_my: // 我的
                MyFragment myFragment = new MyFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, myFragment).commit();
                break;
            case R.id.top_button_tim: //选择城市
                Intent cityChooseIntent = new Intent(context,CityChooseActivity.class);
                startActivityForResult(cityChooseIntent, AppConfig.DEFAULT_RESULT);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case AppConfig.DEFAULT_REQUEST: // 城市选择的返回数据
                tv_chooseCity.setText(data.getStringExtra("cityName"));
                break;
        }
    }
}
