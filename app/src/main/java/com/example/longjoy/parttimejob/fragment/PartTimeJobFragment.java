package com.example.longjoy.parttimejob.fragment;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.activity.JobDetailActivity;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2015/12/30  16:41
 * 方法描述: 发布兼职
 */
public class PartTimeJobFragment extends Fragment implements View.OnClickListener {

    private ListView mList;
    private JobInfoAdapter mAdapter;
    private List<JobInfo> jobList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.parttimejob_fragment, container, false);
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
        query.addWhereEqualTo("creatPerson",AppConfig.prefs.getString("id",""));
        query.findObjects(getContext(), new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> list) {
                jobList = list;
                mAdapter = new JobInfoAdapter(getContext(),list);
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
        mList = (ListView) view.findViewById(R.id.parttime_fragment_lv_list);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }



}
