package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.JobInfo;

/**
 * Created by 陈彬 on 2016/3/21  10:13
 * 方法描述: 兼职详细情况展示界面
 */
public class JobDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private JobInfo jobInfo;
    private String jobId;
    private TextView tv_name, tv_date, tv_company, tv_number, tv_money, tv_place,
            tv_workdate, tv_desc, tv_linkman, tv_telephone, tv_addr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
        initData();

    }

    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    /**
     * Created by 陈彬 on 2016/3/18  10:53
     * 方法描述: 初始化头部
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("详情");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.GONE);
        tv_save.setOnClickListener(this);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setText("");
        tv_back.setOnClickListener(this);
    }

    /**
     * Created by 陈彬 on 2016/3/21  10:17
     * 方法描述: 初始化数据
     */
    private void initData() {
        jobInfo = (JobInfo) getIntent().getSerializableExtra("data");
        jobId = jobInfo.getObjectId();
        tv_name.setText(jobInfo.getName());
        tv_date.setText(jobInfo.getDate());
        tv_company.setText(jobInfo.getCompany());
        tv_number.setText(jobInfo.getNumber() + "");
        tv_money.setText(jobInfo.getMoney());
        tv_place.setText(jobInfo.getPalce());
        tv_workdate.setText(jobInfo.getWorkDate());
        tv_desc.setText(jobInfo.getDesc());
        tv_linkman.setText(jobInfo.getLinkman());
        tv_telephone.setText(jobInfo.getTelephone());
        tv_addr.setText(jobInfo.getAddr());
    }


    /**
     * Created by 陈彬 on 2016/3/21  10:17
     * 方法描述: 初始化组件
     */
    private void initView() {
        tv_addr = (TextView) findViewById(R.id.activity_job_detail_addr);
        tv_name = (TextView) findViewById(R.id.activity_job_detail_name);
        tv_date = (TextView) findViewById(R.id.activity_job_detail_date);
        tv_company = (TextView) findViewById(R.id.activity_job_detail_company);
        tv_number = (TextView) findViewById(R.id.activity_job_detail_number);
        tv_money = (TextView) findViewById(R.id.activity_job_detail_money);
        tv_place = (TextView) findViewById(R.id.activity_job_detail_place);
        tv_workdate = (TextView) findViewById(R.id.activity_job_detail_workDate);
        tv_desc = (TextView) findViewById(R.id.activity_job_detail_desc);
        tv_linkman = (TextView) findViewById(R.id.activity_job_detail_linkman);
        tv_telephone = (TextView) findViewById(R.id.activity_job_detail_telephone);
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
