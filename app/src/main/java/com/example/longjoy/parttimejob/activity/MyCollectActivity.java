package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.Collect;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.widget.pullToRefresh.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2016/3/22  13:36
 * 方法描述: 我的收藏
 */
public class MyCollectActivity extends AppCompatActivity implements View.OnClickListener, XListView.IXListViewListener {

    private Activity activity;
    private Context context;
    private XListView mList;
    private JobInfoAdapter mAdapter;
    private List<JobInfo> jobList;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
        getData();
    }

    private void getData() {
        FunctionUtils.showLoadingDialog(this);
        BmobQuery<JobInfo> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        query.addWhereRelatedTo("likes", new BmobPointer(myUser));
        query.findObjects(context, new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> list) {
                jobList = list;
                mAdapter = new JobInfoAdapter(context, list);
                mList.setAdapter(mAdapter);
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                Logger.getInstance().v("chenbin", "查询错误为 == " + s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });

    }

    private void initView() {
        mHandler = new Handler();
        mList = (XListView) findViewById(R.id.activity_my_collect_list);
        mList.setPullRefreshEnable(true);
        mList.setPullLoadEnable(true);
        mList.setAutoLoadEnable(false);
        mList.setXListViewListener(this);
        mList.setRefreshTime(getTime());
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra("type", "collect");
                intent.putExtra("data", jobList.get(position-1));
                startActivityForResult(intent, 10086);
            }
        });
    }

    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("我的收藏");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.GONE);
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

    private void onLoad() {
        mList.stopRefresh();
        mList.stopLoadMore();
        mList.setRefreshTime(getTime());
    }


    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10086) {
            if (!data.getBooleanExtra("isCollect", true)) {//判定是否做过改动
                getData();
            }
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2500);
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                mAdapter.notifyDataSetChanged();
                onLoad();
            }
        }, 2500);
    }
}
