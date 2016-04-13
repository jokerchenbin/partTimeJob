package com.example.longjoy.parttimejob.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.activity.JobDetailActivity;
import com.example.longjoy.parttimejob.activity.MainActivity;
import com.example.longjoy.parttimejob.adapter.ImageAdapter;
import com.example.longjoy.parttimejob.bean.AD;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.widget.AutoScrollViewPager;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.tools.ToastDiy;
import com.example.longjoy.parttimejob.widget.pullToRefresh.XListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2015/12/30  16:40
 * 方法描述:  首页
 */
public class HomePageFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, XListView.IXListViewListener {

    private Button btn_add;
    private XListView list;
    private JobInfoAdapter mAdapter;
    private List<JobInfo> jobList;
    private View view;
    private Handler mHandler;
    private SliderLayout mSlider;
    private List<AD> adList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.firstpage_fragment, container, false);
        initView(view);
        getAd(getContext());//获取广告
        getData(getContext());//获取数据

        return view;
    }

    /**
     * Created by 陈彬 on 2016/3/31  15:02
     * 方法描述: 获取广告
     */
    private void getAd(Context context) {
        BmobQuery<AD> ad = new BmobQuery<>();
        ad.findObjects(context, new FindListener<AD>() {
            @Override
            public void onSuccess(List<AD> list) {
                adList = list;
                putIntoMap(adList);//将返回数据放进map
            }

            @Override
            public void onError(int i, String s) {
                Logger.getInstance().v("chenbin", s);
            }
        });
    }

    private void putIntoMap(List<AD> adList) {
        HashMap<String, String> url_maps = new HashMap<>();
        for (int i = 0; i < adList.size(); i++) {
            AD ad = adList.get(i);
            url_maps.put(ad.getContent(), ad.getFile().getFileUrl(getContext()));
        }
        showViewPager(url_maps);
    }

    private void showViewPager(HashMap<String, String> map) {
        for (String name : map.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView
                    .description(name)
                    .image(map.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra", name);

            mSlider.addSlider(textSliderView);
        }
        mSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }

    /**
     * Created by 陈彬 on 2016/3/18  16:03
     * 方法描述: 获取兼职信息数据
     */
    private void getData(final Context context) {
        FunctionUtils.showLoadingDialog(getActivity());
        BmobQuery<JobInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("isChecked", true);
        query.findObjects(context, new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> jobInfolist) {
                jobList = jobInfolist;
                mAdapter = new JobInfoAdapter(context, jobInfolist);
                list.setAdapter(mAdapter);
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(context, s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    private void initView(View view) {
        mHandler = new Handler();
        list = (XListView) view.findViewById(R.id.firstpage_list);
        list.setPullRefreshEnable(true);
        list.setPullLoadEnable(true);
        list.setAutoLoadEnable(false);
        list.setXListViewListener(this);
        list.setRefreshTime(getTime());
        View v = View.inflate(getContext(), R.layout.head, null);
        mSlider = (SliderLayout) v.findViewById(R.id.slider);
        list.addHeaderView(v);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAd(getContext());//获取广告
                getData(getContext());//获取数据
                onLoad();
            }
        }, 2500);
    }

    private void onLoad() {
        list.stopRefresh();
        list.stopLoadMore();
        list.setRefreshTime(getTime());
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getAd(getContext());//获取广告
                getData(getContext());//获取数据
                onLoad();
            }
        }, 2500);
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}
