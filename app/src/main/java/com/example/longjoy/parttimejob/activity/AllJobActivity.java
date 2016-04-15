package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.inter.OnCloseLisener;
import com.example.longjoy.parttimejob.tools.ToastDiy;
import com.example.longjoy.parttimejob.widget.dropdownmenu.ConstellationAdapter;
import com.example.longjoy.parttimejob.widget.dropdownmenu.DropDownMenu;
import com.example.longjoy.parttimejob.widget.dropdownmenu.GirdDropDownAdapter;
import com.example.longjoy.parttimejob.widget.dropdownmenu.ListDropDownAdapter;
import com.example.longjoy.parttimejob.widget.pullToRefresh.XListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2016/4/14  18:12
 * 方法描述: 所有工作界面
 */
public class AllJobActivity extends AppCompatActivity implements View.OnClickListener, XListView.IXListViewListener {

    private Activity activity;
    private Context context;
    private List<JobInfo> jobList;
    private String cityName;
    private String type;
    private int typeId;
    private String headers[];
    private List<View> popupViews = new ArrayList<>();

    private GirdDropDownAdapter cityAdapter;
    private ListDropDownAdapter ageAdapter;
    private ListDropDownAdapter sexAdapter;
    private JobInfoAdapter mAdapter;
    private ConstellationAdapter constellationAdapter;
    private DropDownMenu mDropDownMenu;

    private String citys[] = {"不限", "北京", "上海", "成都", "重庆", "杭州", "烟台"};
    private String ages[] = {"不限", "18岁以下", "18-22岁", "23-26岁", "27-35岁", "35岁以上"};
    private String sexs[] = {"不限", "男", "女"};
    private String constellations[] = {"不限", "促销", "服务员", "话务员", "礼仪", "家教", "派单", "营业员", "销售", "实习", "其他"};

    private int constellationPosition = 0;
    private XListView mListView;
    private String setTag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_job);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
        getData(cityName, "", typeId);
    }

    /**
     * Created by 陈彬 on 2016/4/14  18:14
     * 方法描述: 初始化视图
     */
    private void initView() {
        mHandler = new Handler();
        cityName = getIntent().getStringExtra("cityName");
        type = getIntent().getStringExtra("type");
        typeId = getIntent().getIntExtra("typeId", -1);
        headers = new String[]{cityName, "年龄", "性别", type};
        mDropDownMenu = (DropDownMenu) findViewById(R.id.dropDownMenu);
        //init city menu
        final ListView cityView = new ListView(this);
        cityAdapter = new GirdDropDownAdapter(this, Arrays.asList(citys));
        cityView.setDividerHeight(0);
        cityView.setAdapter(cityAdapter);

        //init age menu
        final ListView ageView = new ListView(this);
        ageView.setDividerHeight(0);
        ageAdapter = new ListDropDownAdapter(this, Arrays.asList(ages));
        ageView.setAdapter(ageAdapter);

        //init sex menu
        final ListView sexView = new ListView(this);
        sexView.setDividerHeight(0);
        sexAdapter = new ListDropDownAdapter(this, Arrays.asList(sexs));
        sexView.setAdapter(sexAdapter);

        //init constellation
        final View constellationView = getLayoutInflater().inflate(R.layout.custom_layout, null);
        GridView constellation = (GridView) constellationView.findViewById(R.id.constellation);
        constellationAdapter = new ConstellationAdapter(this, Arrays.asList(constellations));
        constellation.setAdapter(constellationAdapter);
        TextView ok = (TextView) constellationView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenu.setTabText(constellations[constellationPosition]);
                mDropDownMenu.closeMenu();
            }
        });

        //init popupViews
        popupViews.add(cityView);
        popupViews.add(ageView);
        popupViews.add(sexView);
        popupViews.add(constellationView);

        //add item click event
        cityView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cityAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[0] : citys[position]);
                cityName = (position == 0 ? headers[0] : citys[position]);
                mDropDownMenu.closeMenu();
            }
        });

        ageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ageAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[1] : ages[position]);
                mDropDownMenu.closeMenu();
            }
        });

        sexView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sexAdapter.setCheckItem(position);
                mDropDownMenu.setTabText(position == 0 ? headers[2] : sexs[position]);
                setSex(sexs[position]);
                mDropDownMenu.closeMenu();
            }
        });

        constellation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constellationAdapter.setCheckItem(position);
                constellationPosition = position;
                if (position == 0) {
                    typeId = -1;
                } else {
                    typeId = position;
                }

            }
        });

        //init context view
        mListView = (XListView) new XListView(this);
        mListView.setPullRefreshEnable(true);
        mListView.setPullLoadEnable(true);
        mListView.setAutoLoadEnable(false);
        mListView.setXListViewListener(this);
        mListView.setRefreshTime(getTime());
        mListView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra("type", "home");
                intent.putExtra("data", jobList.get(position - 1));
                startActivity(intent);
            }
        });
        //init dropdownview
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, mListView);
        mDropDownMenu.setOnCloseLisener(new OnCloseLisener() {
            @Override
            public void close() {
                getData(cityName, setTag, typeId);
            }
        });
    }

    /**
     * Created by 陈彬 on 2016/4/15  15:08
     * 方法描述: 设置性别
     */
    private void setSex(String tag) {
        if (tag.equals("不限")) {
            setTag = "";
        }
        if (tag.equals("男")) {
            setTag = "2";
        }
        if (tag.equals("女")) {
            setTag = "3";
        }
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }

    /**
     * Created by 陈彬 on 2016/4/15  14:21
     * 方法描述: 获取数据
     */
    private void getData(String cityName, String sexTag, int typeId) {
        FunctionUtils.showLoadingDialog(this);
        BmobQuery<JobInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("isChecked", true);
        if (!cityName.equals("不限")) {
            //添加分城市查询
        }
        if (typeId != -1) {
            query.addWhereEqualTo("type", typeId);
        }
        if (!sexTag.isEmpty()) {
            query.addWhereContains("tag", sexTag);
        }
        query.findObjects(context, new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> jobInfolist) {
                jobList = jobInfolist;
                mAdapter = new JobInfoAdapter(context, jobInfolist);
                mListView.setAdapter(mAdapter);
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                ToastDiy.showShort(context, s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }


    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("全部岗位");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.GONE);
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

    @Override
    public void onBackPressed() {
        //退出activity前关闭菜单
        if (mDropDownMenu.isShowing()) {
            mDropDownMenu.closeMenu();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData(cityName, setTag, typeId);//获取数据
                onLoad();
            }
        }, 2500);
    }


    private Handler mHandler;

    private void onLoad() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    @Override
    public void onLoadMore() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //getData(getContext());//获取数据
                onLoad();
            }
        }, 2500);
    }
}
