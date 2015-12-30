package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;

import cn.bmob.v3.BmobUser;

public class WelcomeActivity extends AppCompatActivity {

    private Context context;
    private Activity activity;
    private Intent intent;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);

        BmobUser user = BmobUser.getCurrentUser(context);
        //用来区分用户是否已经登录  判定跳转的Activity
        if (user != null){
            intent = new Intent(context, MainActivity.class);
        }else {
            intent = new Intent(context,LoginActivity.class);
        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
            }
        },2000);
    }
}
