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
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.bean.UserInfo;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.tools.FileTools;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by 陈彬 on 2015/12/29  14:31
 * 方法描述: 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private static final int RESULT_ERROR = 0;
    private static final int RESULT_OK = 1;
    private static final int RESULT_NODATE = -1;

    private TextView forgetPass;
    private Intent intent;
    private EditText et_username, et_password;
    private Button btn_login, btn_registe, btn_header;
    private String telephone, password;
    private ImageView iv_logo;

    private Activity activity;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        initViewID();
    }

    private void initViewID() {
        forgetPass = (TextView) findViewById(R.id.activity_login_tv_forgetPass);
        forgetPass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        forgetPass.setOnClickListener(this);
        et_username = (EditText) findViewById(R.id.activity_login_et_username);
        et_password = (EditText) findViewById(R.id.activity_login_et_password);
        btn_login = (Button) findViewById(R.id.activity_login_btn_login);
        btn_login.setOnClickListener(this);
        btn_registe = (Button) findViewById(R.id.activity_login_btn_registe);
        btn_registe.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_login_btn_login:
                //登录按钮
                FunctionUtils.showLoadingDialog(activity);
                loginToUserSystem();
                break;
            case R.id.activity_login_btn_registe:
                //注册按钮
                Intent intentRegister = new Intent(activity, RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.activity_login_tv_forgetPass:
                //忘记密码
                Intent intentForget = new Intent(activity, ForgotPasswordActivity.class);
                startActivity(intentForget);
                break;

        }
    }


    /**
     * Created by 陈彬 on 2015/12/29  14:30
     * 方法描述: 登录到用户系统
     */
    private void loginToUserSystem() {
        //得到相应的值
        telephone = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        //两种登录方式    电话号码 + 密码     用户名 + 密码
        BmobUser.loginByAccount(context, telephone, password, new LogInListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (myUser != null) {
                    Toast.makeText(context, "登录成功", Toast.LENGTH_SHORT).show();
                    //将个人信息写入本地
                    writeUserInfoToLocal(myUser.getObjectId(),myUser.getUsername(),myUser.getMobilePhoneNumber(),
                            myUser.getImageUrl(),myUser.getAge(),myUser.getSex());
                    //跳转到主页面
                    Intent intent = new Intent(activity,MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "登录失败", Toast.LENGTH_SHORT).show();
                }
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    /**
     * Created by 陈彬 on 2016/1/4  15:29
     * 方法描述: 将获取到用户基本信息写入本地
     */
    private void writeUserInfoToLocal(String objectId, String username
            ,String mobilePhoneNumber, String imageUrl, Integer age, String sex) {
        AppConfig.prefs.edit().putString("objectId",objectId)
                .putString("username",username)
                .putString("mobilePhoneNumber",mobilePhoneNumber)
                .putString("imageUrl",imageUrl)
                .putInt("age",age)
                .putString("sex",sex).commit();
    }


}
