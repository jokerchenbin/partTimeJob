package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
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
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.fragment.MyFragment;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener{

    private Activity activity;
    private Context context;
    private TextView tv_sex;
    private EditText et_name,et_age,et_height,et_school;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        initView();
        initData();
        changeTopBarState();
    }

    private void initData() {
        et_name.setText(AppConfig.prefs.getString("username",""));
        et_age.setText(AppConfig.prefs.getInt("age", -1)+"");
        et_school.setText(AppConfig.prefs.getString("school", ""));
        et_height.setText(AppConfig.prefs.getString("height", ""));
        tv_sex.setText(AppConfig.prefs.getString("sex",""));
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
        tv_topBar.setText("编辑个人信息");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setOnClickListener(this);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setOnClickListener(this);
    }

    private void initView() {
        tv_sex = (TextView) findViewById(R.id.activity_user_info_tv_sex);
        tv_sex.setOnClickListener(this);
        et_name = (EditText) findViewById(R.id.user_info_et_name);
        et_age = (EditText) findViewById(R.id.user_info_et_age);
        et_school = (EditText) findViewById(R.id.user_info_et_school);
        et_height = (EditText) findViewById(R.id.user_info_et_height);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_user_info_tv_sex:
                showPop();//选择性别
                break;
            case R.id.top_button_tim://返回键
                activity.finish();
                break;
            case R.id.top_bar_common_tv_save://保存按钮
                FunctionUtils.showLoadingDialog(activity);
                updataUserInfo();
                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/3/18  10:59
     * 方法描述: 更新用户数据
     */
    private void updataUserInfo() {
        final MyUser user = new MyUser();
        user.setUsername(et_name.getText().toString());
        user.setAge(Integer.parseInt(et_age.getText().toString()));
        user.setHeight(et_height.getText().toString());
        user.setSchool(et_school.getText().toString());
        user.setSex(tv_sex.getText().toString());
        BmobUser b = BmobUser.getCurrentUser(this);
        user.update(this, b.getObjectId(), new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"更新成功");
                //将个人信息写入本地
                FunctionUtils.writeUserInfoToLocal(user);
                MyFragment.handler.sendEmptyMessage(1);//通知页面更改姓名
                finish();
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context,s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }


    /**
     * 显示性别pop
     */
    private void showPop() {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.sex_pop);
        // 设置大小
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = FunctionUtils.dip2px(this, 267);
        // layoutParams.height = FunctionUtils.dip2px(getActivity(), 463);
        window.setAttributes(layoutParams);
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
}
