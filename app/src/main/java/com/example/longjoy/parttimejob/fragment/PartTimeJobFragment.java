package com.example.longjoy.parttimejob.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.activity.JobDetailActivity;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.tools.ToastDiy;
import com.example.longjoy.parttimejob.widget.swipemenuListview.SwipeMenu;
import com.example.longjoy.parttimejob.widget.swipemenuListview.SwipeMenuCreator;
import com.example.longjoy.parttimejob.widget.swipemenuListview.SwipeMenuItem;
import com.example.longjoy.parttimejob.widget.swipemenuListview.SwipeMenuListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2015/12/30  16:41
 * 方法描述: 发布兼职
 */
public class PartTimeJobFragment extends Fragment implements View.OnClickListener, SwipeMenuListView.IXListViewListener {

    private SwipeMenuListView mList;
    private JobInfoAdapter mAdapter;
    private List<JobInfo> jobList;
    private Handler mHandler;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parttimejob_fragment, container, false);
        context = getContext();
        initView(view);
        getData();
        return view;
    }

    /**
     * Created by 陈彬 on 2016/4/1  15:17
     * 方法描述: 获取数据
     */
    private void getData() {
        BmobQuery<JobInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("creatPerson", AppConfig.prefs.getString("id", ""));
        query.findObjects(getContext(), new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> list) {
                jobList = list;
                mAdapter = new JobInfoAdapter(getContext(), list);
                mList.setAdapter(mAdapter);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    /**
     * Created by 陈彬 on 2016/4/1  14:12
     * 方法描述: 初始化视图
     */
    private void initView(View view) {
        mHandler = new Handler();
        mList = (SwipeMenuListView) view.findViewById(R.id.parttime_fragment_lv_listView);
        setListView();
        mList.setPullRefreshEnable(true);
        mList.setPullLoadEnable(true);
        mList.setAutoLoadEnable(false);
        mList.setXListViewListener(this);
        mList.setRefreshTime(getTime());
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), JobDetailActivity.class);
                intent.putExtra("type", "home");
                intent.putExtra("data", jobList.get(position - 1));
                startActivity(intent);
            }
        });
    }

    private void setListView() {
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        context);
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                deleteItem.setWidth(dp2px(90));
                deleteItem.setIcon(R.mipmap.ic_delete);
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        mList.setMenuCreator(creator);
        mList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        JobInfo jobInfo = jobList.get(position);
                        jobList.remove(position);
                        mAdapter.notifyDataSetChanged();
                        //删除数据
                        deleteJobInfo(jobInfo);
                        break;
                }
            }
        });

        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra("type", "home");
                intent.putExtra("data", jobList.get(position));
                startActivity(intent);
            }
        });
    }

    /**
     * Created by 陈彬 on 2016/4/13  16:56
     * 方法描述: 删除服务端的兼职信息
     */
    private void deleteJobInfo(JobInfo jobInfo) {
        FunctionUtils.showLoadingDialog(getActivity());
        jobInfo.delete(context, new DeleteListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"删除成功!");
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context,s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }


    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
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

    private void onLoad() {
        mList.stopRefresh();
        mList.stopLoadMore();
        mList.setRefreshTime(getTime());
    }


    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getResources().getDisplayMetrics());
    }
}
