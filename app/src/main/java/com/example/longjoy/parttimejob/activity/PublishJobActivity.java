package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.tools.ToastDiy;
import com.example.longjoy.parttimejob.widget.AppDateDialog;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 陈彬 on 2016/4/1  14:44
 * 方法描述: 发布兼职
 */
public class PublishJobActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_eat, tv_live, tv_money;
    private boolean isEat = false, isLive = false, isMoney = false;
    private TextView tv_sex, tv_name;
    private Context context;
    private Activity activity;
    private TextView tv_type;
    private TextView tv_startTime, tv_endTime;
    private int type;//类别
    private String startDate, endDate;
    private SimpleDateFormat format;
    private EditText et_telephone;
    private EditText et_name, et_place, et_addr, et_number, et_money, et_desc, et_company;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_job);
        activity = this;
        context = this;
        format = new SimpleDateFormat("yyyy-MM-dd");
        startDate = format.format(new Date());
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
        initData();
    }

    /**
     * Created by 陈彬 on 2016/4/1  14:12
     * 方法描述: 初始化视图
     */
    private void initView() {
        tv_eat = (TextView) findViewById(R.id.parttime_fragment_tv_eat);
        tv_live = (TextView) findViewById(R.id.parttime_fragment_tv_live);
        tv_money = (TextView) findViewById(R.id.activity_publish_et_money);
        tv_sex = (TextView) findViewById(R.id.parttime_fragment_tv_sex);
        tv_name = (TextView) findViewById(R.id.parttime_fragment_tv_name);
        tv_type = (TextView) findViewById(R.id.activity_publish_tv_type);
        tv_startTime = (TextView) findViewById(R.id.activity_publish_tv_startTime);
        tv_endTime = (TextView) findViewById(R.id.activity_publish_tv_endTime);
        et_telephone = (EditText) findViewById(R.id.activity_publish_et_telephono);
        et_name = (EditText) findViewById(R.id.activity_publish_et_name);
        et_place = (EditText) findViewById(R.id.activity_publish_et_place);
        et_addr = (EditText) findViewById(R.id.activity_publish_et_addr);
        et_money = (EditText) findViewById(R.id.activity_publish_et_money);
        et_desc = (EditText) findViewById(R.id.activity_publish_et_desc);
        et_number = (EditText) findViewById(R.id.activity_publish_et_number);
        et_company = (EditText) findViewById(R.id.activity_publish_et_company);
        tv_startTime.setOnClickListener(this);
        tv_endTime.setOnClickListener(this);
        tv_type.setOnClickListener(this);
        tv_sex.setOnClickListener(this);
        tv_eat.setOnClickListener(this);
        tv_live.setOnClickListener(this);
        tv_money.setOnClickListener(this);
    }

    /**
     * Created by 陈彬 on 2016/4/1  14:33
     * 方法描述: 初始化数据
     */
    private void initData() {
        tv_name.setText(AppConfig.prefs.getString("username", ""));
    }

    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("发布兼职");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setText("提交");
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
            case R.id.parttime_fragment_tv_eat:
                changeState(tv_eat, isEat);
                isEat = !isEat;
                break;
            case R.id.parttime_fragment_tv_live:
                changeState(tv_live, isLive);
                isLive = !isLive;
                break;
            case R.id.parttime_fragment_tv_money:
                changeState(tv_money, isMoney);
                isMoney = !isMoney;
                break;
            case R.id.parttime_fragment_tv_sex://选择性别
                showPop();//选择性别
                break;
            case R.id.activity_publish_tv_type://选择类别
                Intent intent = new Intent(context, JobTypeActivity.class);
                startActivityForResult(intent, 10086);
                break;
            case R.id.activity_publish_tv_startTime://开始时间
                showDataPop(true);
                break;
            case R.id.activity_publish_tv_endTime://结束时间
                showDataPop(false);
                break;
            case R.id.top_bar_common_tv_save://提交
                if (checkInfo()) {
                    publicJob();
                } else {
                    ToastDiy.showShort(context,errMess);
                }
                break;
        }
    }

    private String errMess;//错误信息

    /**
     * Created by 陈彬 on 2016/4/12  18:03
     * 方法描述: 核对消息
     */
    private boolean checkInfo() {
        if (et_name.getText().toString().isEmpty()){
            errMess = "请填写正确的岗位名称";
            return false;
        }if (et_company.getText().toString().isEmpty()){
            errMess = "请填写正确的发布方名称";
            return false;
        }if (tv_type.getText().toString().isEmpty()){
            errMess = "请选择岗位类型";
            return false;
        }if (tv_startTime.getText().toString().isEmpty()){
            errMess = "请选择工作开始时间";
            return false;
        }if (tv_endTime.getText().toString().isEmpty()){
            errMess = "请选择工作结束时间";
            return false;
        }if (et_place.getText().toString().isEmpty()){
            errMess = "请输入工作区域";
            return false;
        }if (et_addr.getText().toString().isEmpty()){
            errMess = "请输入工作地点";
            return false;
        }if (et_number.getText().toString().isEmpty()){
            errMess = "请输入招聘人数";
            return false;
        }if (et_desc.getText().toString().isEmpty()){
            errMess = "请输入职位秒速";
            return false;
        }if (et_telephone.getText().toString().isEmpty()){
            errMess = "请输入联系电话";
            return false;
        }
        return true;
    }


    /**
     * Created by 陈彬 on 2016/4/12  17:25
     * 方法描述: 发布兼职
     */
    private void publicJob() {
        FunctionUtils.showLoadingDialog(this);
        JobInfo info = new JobInfo();
        info.setType(type);
        info.setWorkDate(startDate + "至" + endDate);
        info.setDate(format.format(new Date()));
        info.setMoney(tv_money.getText().toString());
        info.setTelephone(et_telephone.getText().toString());
        info.setName(et_name.getText().toString());
        info.setNumber(Integer.parseInt(et_number.getText().toString()));
        info.setPalce(et_place.getText().toString());
        info.setTag("1,3");
        info.setCompany(et_company.getText().toString());
        info.setDesc(et_desc.getText().toString());
        info.setLinkman(AppConfig.prefs.getString("username", ""));
        info.setAddr(et_addr.getText().toString());
        info.setIsChecked(false);
        info.setCreatPerson(AppConfig.prefs.getString("id", ""));
        info.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                FunctionUtils.dissmisLoadingDialog();
                ToastDiy.showShort(context, "您的兼职信息已提交，正在等待审核管理员审核.");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
                ToastDiy.showShort(context, s);
            }
        });
    }

    /**
     * Created by 陈彬 on 2016/4/12  17:10
     * 方法描述: 时间选择器
     */
    private void showDataPop(final boolean isStart) {
        String title = "";
        if (isStart) {
            title = getResources().getString(R.string.start_date);
        } else {
            title = getResources().getString(R.string.end_date);
        }
        AppDateDialog dateDialog = new AppDateDialog(activity, title, null, null, R.style.alert_dialog, isStart,
                startDate, new AppDateDialog.TimeSelectListenner() {

            @Override
            public void callBack(String date) {

                if (isStart) {
                    startDate = date;
                    tv_startTime.setText(date);
                } else {
                    endDate = date;
                    tv_endTime.setText(date);
                }
            }
        });
        dateDialog.show();
    }


    /**
     * Created by 陈彬 on 2016/4/1  14:18
     * 方法描述: 判断显示状态
     */
    private void changeState(TextView view, boolean is) {
        if (!is) {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_click_bg));
        } else {
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_bg));
        }
    }


    /**
     * 显示性别pop
     */
    private void showPop() {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.sex_pop);
        // 设置大小
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = FunctionUtils.dip2px(context, 267);
        // layoutParams.height = FunctionUtils.dip2px(getActivity(), 463);
        window.setAttributes(layoutParams);
        TextView all = (TextView) window.findViewById(R.id.all);
        all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_sex.setText("不限");
            }
        });
        TextView girl = (TextView) window.findViewById(R.id.girl);
        girl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_sex.setText("女");
                //ChangeInfo("sex", sexnume + "");
            }
        });
        TextView boy = (TextView) window.findViewById(R.id.boy);
        boy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                tv_sex.setText("男");
                //ChangeInfo("sex", sexnume + "");
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 10086) {
            tv_type.setText(data.getStringExtra("type"));
            type = data.getIntExtra("id", 1);
        }
    }
}
