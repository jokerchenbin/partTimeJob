package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.UserInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_telephone, et_username, et_password, et_nextpassword;
    private String telephone, username, password, nextpassword;
    private Button btn_Register;
    private Activity activity;
    private String errMessage;

    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //加入Activity 中的 list 中
        activity = this;
        ((AppApplication) getApplication()).addActivity(activity);
        initViewIDs();
    }

    /**
     * 初始化View IDs
     */
    private void initViewIDs() {
        et_telephone = (EditText) findViewById(R.id.activity_register_telephone);
        et_username = (EditText) findViewById(R.id.activity_register_username);
        et_password = (EditText) findViewById(R.id.activity_register_password);
        et_nextpassword = (EditText) findViewById(R.id.activity_register_nextpassword);
        btn_Register = (Button) findViewById(R.id.activity_register_btn_ok);
        btn_Register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_register_btn_ok://注册按钮执行相应动作
                registerUser("");
                break;
        }
    }


    /**
     * Created by 陈彬 on 2015/12/25  14:29
     * 方法描述: 注册
     */
    private void registerUser(String name) {
        /* 得到相应edittext中的值 */
        telephone = et_telephone.getText().toString().trim();
        username = et_username.getText().toString().trim();
        password = et_password.getText().toString().trim();
        nextpassword = et_nextpassword.getText().toString().trim();
        //检查手机号码是否合理
        if (!isMobile(telephone)) {
            Toast.makeText(activity, "手机号码有误，请检查！", Toast.LENGTH_SHORT).show();
            return;
        }
        //检查必须填的信息是否填完整
        if (!isFillCompletion(username, password, nextpassword)) {
            Toast.makeText(activity, errMessage, Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(activity, "正在注册，请稍后...", Toast.LENGTH_SHORT).show();
        //调用注册的方法
        userInfo = new UserInfo();
        userInfo.setTelephone(telephone);
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userInfo.save(activity, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(activity, "恭喜您，注册成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(activity,LoginActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(activity, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 校验电话号码
     *
     * @param str
     * @return 是否合法的电话号码
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     * 判断输入信息是否完整
     *
     * @param username     用户名
     * @param password     密码
     * @param nextpassword 确认密码
     * @return
     */
    private boolean isFillCompletion(String username, String password, String nextpassword) {
        if ("".equals(username)) {
            errMessage = "用户名不能为空。";
            return false;
        }
        if (!password.equals(nextpassword)) {
            errMessage = "两次密码不一样，请重新输入！";
            return false;
        }
        if ("".equals(password) || "".equals(nextpassword)) {
            errMessage = "密码不能为空";
            return false;
        }
        return true;
    }
}
