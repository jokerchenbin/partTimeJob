package com.example.longjoy.parttimejob.activity;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.longjoy.parttimejob.R;

public class LoginActivity extends AppCompatActivity {

    private TextView forgetPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViewID();
    }

    private void initViewID() {
        forgetPass = (TextView)findViewById(R.id.activity_login_tv_forgetPass);
        forgetPass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
