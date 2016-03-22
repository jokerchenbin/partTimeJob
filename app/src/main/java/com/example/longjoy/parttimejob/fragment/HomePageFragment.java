package com.example.longjoy.parttimejob.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.activity.JobDetailActivity;
import com.example.longjoy.parttimejob.adapter.ImageAdapter;
import com.example.longjoy.parttimejob.widget.AutoScrollViewPager;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2015/12/30  16:40
 * 方法描述:  首页
 */
public class HomePageFragment extends Fragment {

    private Button btn_add;
    private ListView list;
    private JobInfoAdapter mAdapter;
    private List<JobInfo> jobList;
    private View view;
    private AutoScrollViewPager viewPager;
    private ImageAdapter adapter;
    private Drawable[] drawables;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.firstpage_fragment, container, false);
            initView(view);
            getData(getContext());
        }
        return view;
    }

    /**
     * Created by 陈彬 on 2016/3/18  16:03
     * 方法描述: 获取兼职信息数据
     */
    private void getData(final Context context) {

        BmobQuery<JobInfo> query = new BmobQuery<>();
        query.findObjects(context, new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> jobInfolist) {
                jobList = jobInfolist;
                mAdapter = new JobInfoAdapter(context, jobInfolist);
                list.setAdapter(mAdapter);
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(context, s);
            }
        });
    }

    private void initView(View view) {
        Drawable able = getResources().getDrawable(R.mipmap.banner_jiazaizhong);
        drawables = new Drawable[]{able, getResources().getDrawable(R.mipmap.wodeneiyebeijing), able, getResources().getDrawable(R.mipmap.wodeneiyebeijing)};
        list = (ListView) view.findViewById(R.id.firstpage_list);
        View v = View.inflate(getContext(), R.layout.head, null);
        viewPager = (AutoScrollViewPager) v.findViewById(R.id.view_pager);
        adapter = new ImageAdapter(getContext(), drawables);
        viewPager.setAdapter(adapter);
        viewPager.startAutoScroll();
        list.addHeaderView(v);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), JobDetailActivity.class);
                intent.putExtra("type","home");
                intent.putExtra("data", jobList.get(position - 1));
                startActivity(intent);
            }
        });
    }


}
