package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.bean.Suggest;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by 陈彬 on 2016/3/29  22:30
 * 方法描述: 意见
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private EditText et_content;
    private TextView tv_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
    }

    /**
     * Created by 陈彬 on 2016/3/29  22:33
     * 方法描述: 初始化组件
     */
    private void initView() {
        et_content = (EditText) findViewById(R.id.activity_comment_et_content);
        tv_num = (TextView) findViewById(R.id.activity_comment_tv_num);
        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tv_num.setText((140 - s.length()) + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("意见反馈");
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
            case R.id.top_bar_common_tv_save://保存
                String  comment= et_content.getText().toString().trim();
                if (comment.length()>0){
                    saveComment(comment);
                }else {
                    ToastDiy.showShort(context,"亲，请留下您宝贵的意见");
                }

                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/3/29  22:49
     * 方法描述: 意见保存
     */
    private void saveComment(String comment) {
        FunctionUtils.showLoadingDialog(activity);
        Suggest suggest = new Suggest();
        suggest.setComment(comment);
        suggest.setUser(BmobUser.getCurrentUser(context, MyUser.class));
        suggest.save(context, new SaveListener() {
            @Override
            public void onSuccess() {
                ToastDiy.showShort(context,"我们将会认真思考您的意见..");
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
}
