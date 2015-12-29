package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.Configs;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.UserInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.tools.FileTools;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by 陈彬 on 2015/12/29  14:31
 * 方法描述: 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "LoginActivity";
    private static final int RESULT_ERROR = 0;
    private static final int RESULT_OK = 1;
    private static final int RESULT_NODATE = -1;

    private TextView forgetPass;
    private Intent intent;
    private EditText et_username,et_password;
    private Button btn_login,btn_registe,btn_header;
    private String telephone,password;
    private ImageView iv_logo;

    private Activity activity;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        context = this;
        AppConfig.prefs.getString("telephone","1235");
        AppConfig.prefs.getString("password", "1235");
        Log.v(TAG, AppConfig.prefs.getString("telephone", "1235"));
        Log.v(TAG, AppConfig.prefs.getString("password", "1235"));
        ((AppApplication) getApplication()).addActivity(activity);
        initViewID();
    }

    private void initViewID() {
        forgetPass = (TextView)findViewById(R.id.activity_login_tv_forgetPass);
        forgetPass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetPass.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.activity_login_et_username);
        et_password = (EditText) findViewById(R.id.activity_login_et_password);
        btn_login = (Button) findViewById(R.id.activity_login_btn_login);
        btn_login.setOnClickListener(this);
        btn_registe = (Button) findViewById(R.id.activity_login_btn_registe);
        btn_registe.setOnClickListener(this);
        //得到相应的值
        telephone = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_login_btn_login:
                //登录按钮
                FunctionUtils.showLoadingDialog(activity);
                loginToUserSystem();
                break;
            case R.id.activity_login_btn_registe:
                //注册按钮
                Intent intentRegister = new Intent(activity,RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.activity_login_tv_forgetPass:
                //忘记密码
                Intent intentForget = new Intent(activity,ForgotPasswordActivity.class);
                startActivity(intentForget);
                break;

        }
    }



    /**
     * Created by 陈彬 on 2015/12/29  14:30
     * 方法描述: 登录到用户系统
     */
    private void loginToUserSystem() {
        BmobQuery<UserInfo> query = new BmobQuery<>();
        query.addWhereEqualTo("telephone", telephone);
        query.findObjects(context, new FindListener<UserInfo>() {
            @Override
            public void onSuccess(List<UserInfo> list) {
                Log.v(TAG, list.size() + "长度");

                for (UserInfo userInfo : list) {
                    Log.v(TAG, userInfo.toString());
                    if (userInfo.getPassword().equals(password)) {
                        //查询到相应的数据  返回登录成功
                        AppConfig.prefs.edit().putString("telephone", telephone)
                                .putString("password", password).commit();
                        handler.sendEmptyMessage(RESULT_OK);
                        return;
                    }
                }
                //没有找到相应的数据
                handler.sendEmptyMessage(RESULT_NODATE);
            }

            @Override
            public void onError(int i, String s) {
                Log.v(TAG, i + "  --> " + s);
                handler.sendEmptyMessage(RESULT_ERROR);
            }
        });
    }


    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
                switch (msg.what){
                    case RESULT_OK:
                        Toast.makeText(context,"登录成功",Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_NODATE:
                        Toast.makeText(context,"电话号码或密码输入错误，请重试",Toast.LENGTH_SHORT).show();
                        break;
                    case RESULT_ERROR:
                        Toast.makeText(context,"登录失败,请检查网络状态",Toast.LENGTH_SHORT).show();
                        break;
                }
            FunctionUtils.dissmisLoadingDialog();
        }
    };








}
