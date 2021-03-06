package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.JobTypeAdapter;
import com.example.longjoy.parttimejob.tools.ToastDiy;

/**
 * Created by 陈彬 on 2016/4/1  17:34
 * 方法描述: 工作类型界面
 */
public class JobTypeActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private ListView mList;
    private String[] typeArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_type);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initData();
        initView();

    }

    /**
     * Created by 陈彬 on 2016/4/6  9:54
     * 方法描述: 初始化数据
     */
    private void initData() {
        typeArr = getResources().getStringArray(R.array.job_type);
    }

    /**
     * Created by 陈彬 on 2016/4/1  17:36
     * 方法描述: 初始化视图
     */
    private void initView() {
        mList = (ListView) findViewById(R.id.activity_job_list);
        mList.setAdapter(new JobTypeAdapter(context, typeArr));
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("type", typeArr[position]);
                intent.putExtra("id",position+1);
                setResult(10086, intent);
                finish();
            }
        });
    }


    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("岗位类型");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setText("");
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
        }
    }
}
