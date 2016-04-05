package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;
import com.example.longjoy.parttimejob.tools.ToastDiy;
import com.example.longjoy.parttimejob.widget.catloading.CatLoadingView;

/**
 * Created by 陈彬 on 2016/3/22  17:28
 * 方法描述: 设置界面
 */
public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    private Activity activity;
    private Context context;
    private RelativeLayout mpdLayout,suggestLayout,messLayout;
    private Button btn_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
    }

    /**
     * Created by 陈彬 on 2016/3/29  17:47
     * 方法描述: 初始化视图
     */
    private void initView() {
        mpdLayout = (RelativeLayout) findViewById(R.id.activity_setting_layout_modify);
        messLayout = (RelativeLayout) findViewById(R.id.activity_setting_layout_mess);
        suggestLayout = (RelativeLayout) findViewById(R.id.activity_setting_layout_suggest);
        btn_out = (Button) findViewById(R.id.activity_setting_layout_out);
        btn_out.setOnClickListener(this);
        mpdLayout.setOnClickListener(this);
        messLayout.setOnClickListener(this);
        suggestLayout.setOnClickListener(this);
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
        tv_topBar.setText("设置");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.GONE);
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
            case R.id.activity_setting_layout_modify://修改密码
                startActivity(new Intent(context, ModifyPwdActivity.class));
                /*CatLoadingView view = new CatLoadingView();
                view.show(getSupportFragmentManager(),"");*/
                break;
            case R.id.activity_setting_layout_mess://消息提醒
                startActivity(new Intent(context, MessageActivity.class));
                break;
            case R.id.activity_setting_layout_suggest://给点意见
                startActivity(new Intent(context, CommentActivity.class));
                break;
            case R.id.activity_setting_layout_out://退出按钮
                SelectHeadTools.openDialogOut(context);
                break;
        }
    }
}
