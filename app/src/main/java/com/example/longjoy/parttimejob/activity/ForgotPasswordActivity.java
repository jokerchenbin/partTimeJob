package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.longjoy.parttimejob.R;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private TextView tv_back, tv_topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        activity = this;
        context = this;
        initViewIDs();
        changeTopBarState();
    }

    /**
     * Created by 陈彬 on 2015/12/29  16:34
     * 方法描述: 初始化View ID
     */
    private void initViewIDs() {
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim://返回键
                activity.finish();
                break;
        }
    }

    /**
     * Created by 陈彬 on 2015/12/30  9:59
     * 方法描述: 改变头部状态
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("找回密码");
    }
}
