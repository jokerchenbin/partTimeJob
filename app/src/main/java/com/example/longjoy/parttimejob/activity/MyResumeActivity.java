package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.Resume;
import com.example.longjoy.parttimejob.common.FunctionUtils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 陈彬 on 2016/1/4  16:16
 * 方法描述: 我的简历
 */
public class MyResumeActivity extends AppCompatActivity implements View.OnClickListener{

    private Activity activity;
    private Context context;
    private RadioGroup rg_sex;
    private EditText et_wantedjob,et_telephone,et_name,et_age;
    private EditText et_school,et_hight,et_selfInfo,et_email,et_QQ;
    private String name,age,sex,school,hight,wanted,selfInfo,email,QQ,telephone;
    private RadioButton rb_man,rb_woman;
    private boolean isExitResume = true; //判断云端是否存在简历
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_resume);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initViewIDs();
        getBmobInfo();
    }


    private  void  initViewIDs(){
        et_wantedjob = (EditText) findViewById(R.id.activity_my_resume_et_wantjob);
        et_telephone = (EditText) findViewById(R.id.activity_my_resume_et_telephone);
        et_telephone.setText(AppConfig.prefs.getString("mobilePhoneNumber",""));
        et_name = (EditText) findViewById(R.id.activity_my_resume_et_name);
        et_age = (EditText) findViewById(R.id.activity_my_resume_et_age);
        rg_sex = (RadioGroup) findViewById(R.id.activity_my_resume_rg_group);
        et_school = (EditText) findViewById(R.id.activity_my_resume_et_school);
        et_hight = (EditText) findViewById(R.id.activity_my_resume_et_hight);
        et_selfInfo = (EditText) findViewById(R.id.activity_my_resume_et_selfInfo);
        et_email = (EditText) findViewById(R.id.activity_my_resume_et_email);
        et_QQ = (EditText) findViewById(R.id.activity_my_resume_et_QQ);
        rb_man = (RadioButton) findViewById(R.id.activity_my_resume_rb_man);
        rb_woman = (RadioButton) findViewById(R.id.activity_my_resume_rb_woman);
    }

    /**
     * Created by 陈彬 on 2016/1/5  14:58
     * 方法描述: 查询云端服务器中的数据
     */
    private void getBmobInfo(){
        FunctionUtils.showLoadingDialog(activity);
        BmobQuery<Resume> query = new BmobQuery<>();
        query.addWhereEqualTo("userId",AppConfig.prefs.getString("objectId",""));
        query.findObjects(context, new FindListener<Resume>() {
            @Override
            public void onSuccess(List<Resume> list) {
                setEditTextString(list.get(0));
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                if (i == 9015){
                    Toast.makeText(context,"您还没有创建简历，赶紧创建吧^-^",Toast.LENGTH_SHORT).show();
                    isExitResume = false;
                }else {
                    Toast.makeText(context,"^-^ "+s,Toast.LENGTH_SHORT).show();
                }
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }

    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    /**
     * Created by 陈彬 on 2015/12/30  9:59
     * 方法描述: 改变头部状态
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("我的简历");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.VISIBLE);
        tv_save.setOnClickListener(this);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim://返回键
                activity.finish();
                break;
            case R.id.top_bar_common_tv_save://保存按钮
                Animation anim = AnimationUtils.loadAnimation(context,R.anim.text_anim);
                tv_save.startAnimation(anim);
                getEditTextString(); //得到输入框中的值
                Resume resume = new Resume();
                resume.setName(name);
                resume.setAge(age);
                resume.setSex(sex);
                resume.setSchool(school);
                resume.setHight(hight);
                resume.setWantJob(wanted);
                resume.setSelfIntroduction(selfInfo);
                resume.setEmailAddr(email);
                resume.setQQ(QQ);
                resume.setTelephone(telephone);
                resume.setUserId(AppConfig.prefs.getString("objectId", ""));
                FunctionUtils.showLoadingDialog(activity);
                if (!isExitResume){ //云端不存在简历信息，则保存数据
                    resume.save(context, new SaveListener() {
                        @Override
                        public void onSuccess() {
                            FunctionUtils.dissmisLoadingDialog();
                            Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            FunctionUtils.dissmisLoadingDialog();
                            Toast.makeText(context,"保存失败"+s,Toast.LENGTH_SHORT).show();
                        }
                    });
                }else { //云端存在简历信息，则更新
                    resume.update(context, resumeObjectId, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(context,"更新成功",Toast.LENGTH_SHORT).show();
                            FunctionUtils.dissmisLoadingDialog();
                        }

                        @Override
                        public void onFailure(int i, String s) {
                            Toast.makeText(context,"更新失败"+s,Toast.LENGTH_SHORT).show();
                            FunctionUtils.dissmisLoadingDialog();
                        }
                    });
                }
                finish();
                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/1/5  12:03
     * 方法描述: 点击选择求职意向
     */
    public void onChooseClick(View view){
       //跳转到选择页面
        Intent chooseIntent = new Intent(context,ChooseJobActivity.class);
        startActivityForResult(chooseIntent, AppConfig.DEFAULT_RESULT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode){
            case AppConfig.DEFAULT_RESULT: //得到返回的求职意向数据
                et_wantedjob.setText(data.getStringExtra("word"));
                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/1/5  14:39
     * 方法描述: 得到输入框里边的值
     */
    private void getEditTextString(){
        name = et_name.getText().toString().trim();
        age = et_age.getText().toString().trim();
        sex = ((RadioButton)findViewById(rg_sex.getCheckedRadioButtonId())).getText().toString().trim();
        school = et_school.getText().toString().trim();
        hight = et_hight.getText().toString().trim();
        wanted = et_wantedjob.getText().toString().trim();
        selfInfo = et_selfInfo.getText().toString().trim();
        email = et_email.getText().toString().trim();
        QQ = et_QQ.getText().toString().trim();
        telephone = et_telephone.getText().toString().trim();
    }

    private String resumeObjectId;
    /**
     * Created by 陈彬 on 2016/1/5  15:03
     * 方法描述: 给相应的edittext 赋值
     */
    private void setEditTextString(Resume resume){
        resumeObjectId = resume.getObjectId();
        et_name.setText(resume.getName());
        et_age.setText(resume.getAge());
        if (resume.getSex().equals("男")){
            rb_man.setChecked(true);
        }else {
            rb_woman.setChecked(true);
        }
        et_school.setText(resume.getSchool());
        et_hight.setText(resume.getHight());
        et_wantedjob.setText(resume.getWantJob());
        et_selfInfo.setText(resume.getSelfIntroduction());
        et_email.setText(resume.getEmailAddr());
        et_QQ.setText(resume.getQQ());
    }
}
