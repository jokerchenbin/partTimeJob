package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.RequestSMSCodeListener;
import cn.bmob.v3.listener.SaveListener;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private TextView tv_back, tv_topBar;
    private EditText et_tele, et_code, et_newPwd;
    private Button btn_verify, btn_modify;
    private String TAG = "chenbin";
    private String telephone;

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
        et_tele = (EditText) findViewById(R.id.activity_forgot_password_tele);
        et_code = (EditText) findViewById(R.id.activity_forgot_password_code);
        et_newPwd = (EditText) findViewById(R.id.activity_forgot_password_newPwd);
        btn_verify = (Button) findViewById(R.id.activity_forgot_password_btn_verification);
        btn_modify = (Button) findViewById(R.id.activity_forgot_password_btn_modify);
        btn_verify.setOnClickListener(this);
        btn_modify.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim://返回键
                activity.finish();
                break;
            case R.id.activity_forgot_password_btn_verification://获取验证码
                telephone = et_tele.getText().toString();
                //检查手机号码是否合理
                if (!isMobile(telephone)) {
                    ToastDiy.showShort(context, "手机号码有误，请检查！");
                    return;
                }
                //获取验证码
                sendCode(telephone);
                break;
            case R.id.activity_forgot_password_btn_modify://修改密码
                String code = et_code.getText().toString();
                String newPwd = et_newPwd.getText().toString();
                if (isFillCompletion(code,newPwd)){
                    loginLyTele(telephone,code,newPwd);
                }else {
                    ToastDiy.showShort(context,errMessage);
                }
                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/4/17  9:16
     * 方法描述: 登录重置密码
     */
    private void loginLyTele(String tele,String code, String newPwd) {
        MyUser user = new MyUser();
        user.setMobilePhoneNumber(tele);
        user.setPassword(newPwd);
        user.signOrLogin(context, code, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"密码重置成功。");
                finish();
            }

            @Override
            public void onFailure(int i, String s) {
                ToastDiy.showShort(context,s);
            }
        });
    }

    private String errMessage;
    /**
     * 判断输入信息是否完整
     */
    private boolean isFillCompletion(String code, String password) {
        if ("".equals(code)) {
            errMessage = "请输入验证码。";
            return false;
        }
        if (!password.equals(password)) {
            errMessage = "请输入新密码！";
            return false;
        }
        return true;
    }


    private void sendCode(String telephone) {
        timeHandler.sendEmptyMessageDelayed(1, 100);
        BmobSMS.requestSMSCode(context, telephone, "注册短信验证", new RequestSMSCodeListener() {
            @Override
            public void done(Integer integer, BmobException e) {
                Toast.makeText(context, "短信已发送，请注意查收.", Toast.LENGTH_SHORT).show();
                if (e == null) {
                    Log.v(TAG, integer + "  收到的数据");
                } else {
                    Log.v(TAG, e.toString());
                }
            }
        });
    }

    //短信验证计时器
    private int time = 60;
    /**
     * Created by 陈彬 on 2015/12/30  10:46
     * 方法描述:  短信计时的 Handler
     */
    private Handler timeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (time == 0) {
                btn_verify.setText("获取验证码");
                btn_verify.setFocusable(true);
                btn_verify.setFocusableInTouchMode(true);
                btn_verify.setClickable(true);
                btn_verify.setBackgroundDrawable(getResources().getDrawable(R.drawable.edittext_bg));
                btn_verify.setTextColor(getResources().getColor(R.color.text_defulat));
                time = 60;
            } else {
                btn_verify.setBackgroundDrawable(getResources().getDrawable(R.drawable.btn_bg));
                btn_verify.setTextColor(getResources().getColor(R.color.white));
                btn_verify.setClickable(false);
                btn_verify.setText(time-- + "秒重发");
                timeHandler.sendEmptyMessageDelayed(1, 1000);
            }
        }
    };


    /**
     * Created by 陈彬 on 2015/12/30  9:59
     * 方法描述: 改变头部状态
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("找回密码");
    }

    /**
     * 校验电话号码
     *
     * @param str
     * @return 是否合法的电话号码
     */
    public boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
}
