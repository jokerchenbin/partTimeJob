package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.JobListAdapter;

public class ChooseJobActivity extends AppCompatActivity implements View.OnClickListener{

    private Activity activity;
    private Context context;
    private ListView list;
    private JobListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_job);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initViewIDs();
    }

    /**
     * Created by 陈彬 on 2016/1/5  13:46
     * 方法描述: 初始化 View
     */
    private void initViewIDs() {
        list = (ListView) findViewById(R.id.activity_choose_job_lv_list);
        adapter = new JobListAdapter(context,jobList);
        list.setAdapter(adapter);
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
        tv_topBar.setText("选择职位类型");
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
                Intent saveIntent = new Intent();
                saveIntent.putExtra("word",adapter.getResultStr());
                setResult(AppConfig.DEFAULT_RESULT,saveIntent);
                activity.finish();
                break;
        }
    }


    private String[] jobList = new String[]{"客服","演出","家教","实习","模特","拍单","文员",
            "设计","其它","校内","临时工","服务员","销售","安保","礼仪","促销","翻译"};
}
