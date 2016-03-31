package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.common.Logger;

/**
 * Created by 陈彬 on 2016/3/31  22:12
 * 方法描述: 消息提醒
 */
public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private ImageView iv_sound, iv_shock;
    private int soundType, shockType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        context = this;
        //加入Activity 中的 list 中
        activity = this;
        ((AppApplication) getApplication()).addActivity(activity);
        initViewIDs();
        changeTopBarState();
    }

    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("消息提醒");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.GONE);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setText("");
        tv_back.setOnClickListener(this);
    }

    /**
     * Created by 陈彬 on 2016/3/31  22:14
     * 方法描述: 初始化视图
     */
    private void initViewIDs() {

        iv_sound = (ImageView) findViewById(R.id.activity_message_sound);
        iv_shock = (ImageView) findViewById(R.id.activity_message_shock);
        iv_sound.setOnClickListener(this);
        iv_shock.setOnClickListener(this);
        soundType = AppConfig.prefs.getInt("sound", 0);
        shockType = AppConfig.prefs.getInt("shock", 0);
        if (soundType == 0) {
            iv_sound.setBackgroundDrawable(getResources().getDrawable(R.mipmap.set_button_yes));
        } else {
            iv_sound.setBackgroundDrawable(getResources().getDrawable(R.mipmap.set_button_no));
        }
        if (shockType == 0) {
            iv_shock.setBackgroundDrawable(getResources().getDrawable(R.mipmap.set_button_yes));
        } else {
            iv_shock.setBackgroundDrawable(getResources().getDrawable(R.mipmap.set_button_no));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim:
                finish();
                break;
            case R.id.activity_message_sound://声音
                soundType = setBackGound(soundType,iv_sound);
                AppConfig.prefs.edit().putInt("sound",soundType).commit();
                break;
            case R.id.activity_message_shock://震动
                shockType = setBackGound(shockType,iv_shock);
                AppConfig.prefs.edit().putInt("shock",shockType).commit();
                break;
        }
    }


    private int setBackGound(int type,ImageView view){
        type = (type+1)%2;
        if (type == 0) {
            view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.set_button_yes));
        } else {
            view.setBackgroundDrawable(getResources().getDrawable(R.mipmap.set_button_no));
        }
        return  type;
    }


}
