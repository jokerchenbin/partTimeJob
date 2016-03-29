package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 陈彬 on 2016/3/29  17:40
 * 方法描述: 修改密码
 */
public class ModifyPwdActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private EditText et_old, et_new1, et_new2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
    }

    /**
     * Created by 陈彬 on 2016/3/29  17:43
     * 方法描述: 初始化视图
     */
    private void initView() {
        et_old = (EditText) findViewById(R.id.activity_modify_et_old);
        et_new1 = (EditText) findViewById(R.id.activity_modify_et_new1);
        et_new2 = (EditText) findViewById(R.id.activity_modify_et_new2);
    }


    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("修改密码");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setText("完成");
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
            case R.id.top_bar_common_tv_save://完成
                String old = et_old.getText().toString().trim();
                String new1 = et_new1.getText().toString().trim();
                String new2 = et_new2.getText().toString().trim();
                if (isOk(old, new1, new2)) {
                    modifyPwd(old, new1);
                } else {
                    ToastDiy.showShort(context, err);
                }

                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/3/29  22:16
     * 方法描述: 修改密码
     */
    private void modifyPwd(String old, String new1) {
        FunctionUtils.showLoadingDialog(activity);
        BmobUser.updateCurrentUserPassword(context, old, new1, new UpdateListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"密码修改成功");
                FunctionUtils.dissmisLoadingDialog();
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context,s);
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    /**
     * Created by 陈彬 on 2016/3/29  22:10
     * 方法描述: 检查密码输入
     */
    private String err;//错误信息

    private boolean isOk(String old, String new1, String new2) {

        if (old.length() < 6 || new1.length() < 6 || new2.length() < 6) {
            err = "密码长度输入错误，请检查";
            return false;
        }
        if (!new1.equals(new2)) {
            err = "两次密码输入不一致";
            return false;
        }

        return true;
    }
}
