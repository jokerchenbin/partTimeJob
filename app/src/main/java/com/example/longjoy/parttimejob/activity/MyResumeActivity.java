package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;

/**
 * Created by 陈彬 on 2016/1/4  16:16
 * 方法描述: 我的简历
 */
public class MyResumeActivity extends AppCompatActivity implements View.OnClickListener{

    private Activity activity;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
    }


    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    /**
     * Created by 陈彬 on 2015/12/30  9:59
     * 方法描述: 改变头部状态
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("我的简历");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setOnClickListener(this);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim://返回键
                activity.finish();
                break;
            case R.id.top_bar_common_tv_save://保存按钮
                Animation anim = AnimationUtils.loadAnimation(context,R.anim.text_anim);
                tv_save.startAnimation(anim);
                break;
        }
    }
}
