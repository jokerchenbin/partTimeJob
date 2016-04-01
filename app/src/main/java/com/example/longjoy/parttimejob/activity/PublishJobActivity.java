package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.common.FunctionUtils;

/**
 * Created by 陈彬 on 2016/4/1  14:44
 * 方法描述: 发布兼职
 */
public class PublishJobActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_eat, tv_live, tv_money;
    private boolean isEat = false, isLive = false, isMoney = false;
    private TextView tv_sex, tv_name;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_job);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
        initData();
    }

    /**
     * Created by 陈彬 on 2016/4/1  14:12
     * 方法描述: 初始化视图
     */
    private void initView() {
        tv_eat = (TextView) findViewById(R.id.parttime_fragment_tv_eat);
        tv_live = (TextView) findViewById(R.id.parttime_fragment_tv_live);
        tv_money = (TextView) findViewById(R.id.parttime_fragment_tv_money);
        tv_sex = (TextView) findViewById(R.id.parttime_fragment_tv_sex);
        tv_name = (TextView) findViewById(R.id.parttime_fragment_tv_name);
        tv_sex.setOnClickListener(this);
        tv_eat.setOnClickListener(this);
        tv_live.setOnClickListener(this);
        tv_money.setOnClickListener(this);
    }

    /**
     * Created by 陈彬 on 2016/4/1  14:33
     * 方法描述: 初始化数据
     */
    private void initData() {
        tv_name.setText(AppConfig.prefs.getString("username", ""));
    }

    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("发布兼职");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setText("提交");
        tv_save.setOnClickListener(this);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setText("");
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim:
                finish();
                break;
            case R.id.parttime_fragment_tv_eat:
                changeState(tv_eat, isEat);
                isEat = !isEat;
                break;
            case R.id.parttime_fragment_tv_live:
                changeState(tv_live, isLive);
                isLive = !isLive;
                break;
            case R.id.parttime_fragment_tv_money:
                changeState(tv_money, isMoney);
                isMoney = !isMoney;
                break;
            case R.id.parttime_fragment_tv_sex://选择性别
                showPop();//选择性别
                break;
        }
    }



    /**
     * Created by 陈彬 on 2016/4/1  14:18
     * 方法描述: 判断显示状态
     */
    private void changeState(TextView view, boolean is) {
        if (!is) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_click_bg));
        } else {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_bg));
        }
    }


    /**
     * 显示性别pop
     */
    private void showPop() {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.sex_pop);
        // 设置大小
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = FunctionUtils.dip2px(context, 267);
        // layoutParams.height = FunctionUtils.dip2px(getActivity(), 463);
        window.setAttributes(layoutParams);
        TextView all = (TextView) window.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_sex.setText("不限");
            }
        });
        TextView girl = (TextView) window.findViewById(R.id.girl);
        girl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_sex.setText("女");
                //ChangeInfo("sex", sexnume + "");
            }
        });
        TextView boy = (TextView) window.findViewById(R.id.boy);
        boy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_sex.setText("男");
                //ChangeInfo("sex", sexnume + "");
            }
        });
    }
}
