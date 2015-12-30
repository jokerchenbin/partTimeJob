package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.fragment.HomePageFragment;
import com.example.longjoy.parttimejob.fragment.MyFragment;
import com.example.longjoy.parttimejob.fragment.PartTimeJobFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.activity_main_framelayout, new HomePageFragment()).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_rbtn_firstPage:
                HomePageFragment firstPage = new HomePageFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, firstPage).commit();
                break;
            case R.id.activity_main_rbtn_partTimeJob:
                PartTimeJobFragment job = new PartTimeJobFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, job).commit();
                break;
            case R.id.activity_main_rbtn_my:
                MyFragment myFragment = new MyFragment();
                fm.beginTransaction().replace(R.id.activity_main_framelayout, myFragment).commit();
                break;
            case R.id.top_button_tim: //选择城市
                Intent cityChooseIntent = new Intent(context,CityChooseActivity.class);
                startActivity(cityChooseIntent);
                break;
        }
    }
}
