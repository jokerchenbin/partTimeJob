package com.example.longjoy.parttimejob.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.adapter.JobInfoAdapter;
import com.example.longjoy.parttimejob.bean.Collect;
import com.example.longjoy.parttimejob.bean.JobInfo;
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;
import com.example.longjoy.parttimejob.tools.ToastDiy;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.listener.DeleteListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 陈彬 on 2016/3/21  10:13
 * 方法描述: 兼职详细情况展示界面
 */
public class JobDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private JobInfo jobInfo;
    private String jobId;
    private TextView tv_name, tv_date, tv_company, tv_number, tv_money, tv_place,
            tv_workdate, tv_desc, tv_linkman, tv_telephone, tv_addr;
    private LinearLayout layout;
    private boolean isCollect = false;//判断该用户是否收藏了该工作
    private TextView tv_collect;
    private ImageView iv_image, iv_phone;
    private String colletId;
    private String type;
    private Button btn_call;
    private TextView tv_tag6,tv_tag2,tv_tag3,tv_tag4,tv_tag5;
    private TextView tv_suggest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        activity = this;
        context = this;
        ((AppApplication) getApplication()).addActivity(activity);
        changeTopBarState();
        initView();
        initData();
        getData();
    }


    /* 头部的相关视图   标题，返回，保存*/
    private TextView tv_topBar;
    private TextView tv_back;
    private TextView tv_save;

    /**
     * Created by 陈彬 on 2016/3/18  10:53
     * 方法描述: 初始化头部
     */
    private void changeTopBarState() {
        tv_topBar = (TextView) findViewById(R.id.top_bar_common_title);
        tv_topBar.setText("详情");
        tv_save = (TextView) findViewById(R.id.top_bar_common_tv_save);
        tv_save.setVisibility(View.GONE);
        tv_save.setOnClickListener(this);
        tv_back = (TextView) findViewById(R.id.top_button_tim);
        tv_back.setText("");
        tv_back.setOnClickListener(this);
    }

    /**
     * Created by 陈彬 on 2016/3/21  10:17
     * 方法描述: 初始化数据
     */
    private void initData() {
        jobInfo = (JobInfo) getIntent().getSerializableExtra("data");
        type = getIntent().getStringExtra("type");
        jobId = jobInfo.getObjectId();
        tv_name.setText(jobInfo.getName());
        tv_date.setText(jobInfo.getDate());
        tv_company.setText(jobInfo.getCompany());
        tv_number.setText(jobInfo.getNumber() + "");
        tv_money.setText(jobInfo.getMoney());
        tv_place.setText(jobInfo.getPalce());
        tv_workdate.setText(jobInfo.getWorkDate());
        tv_desc.setText(jobInfo.getDesc());
        tv_linkman.setText(jobInfo.getLinkman());
        tv_telephone.setText(jobInfo.getTelephone());
        tv_addr.setText(jobInfo.getAddr());
        FunctionUtils.setImage(context, iv_phone, jobInfo.getType() + "");
        String[] tagArr = jobInfo.getTag().split(",");
        for (int i = 0; i < tagArr.length; i++) {
            int num = Integer.parseInt(tagArr[i]);
            switch (num){
                case 2:
                    tv_tag2.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    tv_tag3.setVisibility(View.VISIBLE);
                    break;
                case 4:
                    tv_tag4.setVisibility(View.VISIBLE);
                    break;
                case 5:
                    tv_tag5.setVisibility(View.VISIBLE);
                    break;
                case 6:
                    tv_tag6.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


    /**
     * Created by 陈彬 on 2016/3/21  10:17
     * 方法描述: 初始化组件
     */
    private void initView() {
        tv_suggest = (TextView) findViewById(R.id.activity_job_detail_tv_suggest);
        tv_suggest.setOnClickListener(this);
        btn_call = (Button) findViewById(R.id.activity_job_detail_btn_call);
        btn_call.setOnClickListener(this);
        tv_tag6 = (TextView) findViewById(R.id.job_info_item_tag6);
        tv_tag2 = (TextView) findViewById(R.id.job_info_item_tag2);
        tv_tag3 = (TextView) findViewById(R.id.job_info_item_tag3);
        tv_tag4 = (TextView) findViewById(R.id.job_info_item_tag4);
        tv_tag5 = (TextView) findViewById(R.id.job_info_item_tag5);
        iv_image = (ImageView) findViewById(R.id.activity_job_detail_iv_image);
        iv_phone = (ImageView) findViewById(R.id.activity_job_detail_iv_phono);
        tv_collect = (TextView) findViewById(R.id.activity_job_detail_tv_collect);
        tv_addr = (TextView) findViewById(R.id.activity_job_detail_addr);
        tv_name = (TextView) findViewById(R.id.activity_job_detail_name);
        tv_date = (TextView) findViewById(R.id.activity_job_detail_date);
        tv_company = (TextView) findViewById(R.id.activity_job_detail_company);
        tv_number = (TextView) findViewById(R.id.activity_job_detail_number);
        tv_money = (TextView) findViewById(R.id.activity_job_detail_money);
        tv_place = (TextView) findViewById(R.id.activity_job_detail_place);
        tv_workdate = (TextView) findViewById(R.id.activity_job_detail_workDate);
        tv_desc = (TextView) findViewById(R.id.activity_job_detail_desc);
        tv_linkman = (TextView) findViewById(R.id.activity_job_detail_linkman);
        tv_telephone = (TextView) findViewById(R.id.activity_job_detail_telephone);
        layout = (LinearLayout) findViewById(R.id.activity_job_detail_collect);
        layout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_button_tim:
                if (type.equals("collect")) {//从收藏页面进来的
                    Intent intent = new Intent();
                    intent.putExtra("isCollect", isCollect);
                    setResult(10086, intent);
                }
                finish();
                break;
            case R.id.activity_job_detail_collect://收藏，取消收藏按钮
                if (!isCollect) {
                    colletJob();//收藏
                } else {
                    cancelCollet();//取消收藏
                }
                break;
            case R.id.activity_job_detail_btn_call://联系商家
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+jobInfo.getTelephone()));
                startActivity(intent);
                break;
            case R.id.activity_job_detail_tv_suggest://投诉
                SelectHeadTools.openDialogSuggest(context);
                break;
        }
    }

    /**
     * Created by 陈彬 on 2016/3/22  13:23
     * 方法描述: 取消收藏
     */
    private void cancelCollet() {
        FunctionUtils.showLoadingDialog(this);
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        MyUser user = new MyUser();
        user.setObjectId(myUser.getObjectId());
        JobInfo jobInfo = new JobInfo();
        jobInfo.setObjectId(jobId);
        BmobRelation relation = new BmobRelation();
        relation.remove(jobInfo);
        user.setLikes(relation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                isCollect = false;
                FunctionUtils.dissmisLoadingDialog();
                handler.sendEmptyMessage(0);
                ToastDiy.showShort(context, "取消收藏~~");
            }

            @Override
            public void onFailure(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
                ToastDiy.showShort(context, s);
            }
        });
    }

    /**
     * Created by 陈彬 on 2016/3/22  11:06
     * 方法描述: 收藏工作信息
     */
    private void colletJob() {
        FunctionUtils.showLoadingDialog(this);
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        MyUser user = new MyUser();
        user.setObjectId(myUser.getObjectId());
        JobInfo jobInfo = new JobInfo();
        jobInfo.setObjectId(jobId);
        BmobRelation relation = new BmobRelation();
        //将当前用户添加到多对多关联中
        relation.add(jobInfo);
        //多对多关联指向`post`的`likes`字段
        user.setLikes(relation);
        user.update(context, new UpdateListener() {
            @Override
            public void onSuccess() {
                isCollect = true;
                FunctionUtils.dissmisLoadingDialog();
                handler.sendEmptyMessage(1);
                ToastDiy.showShort(context, "收藏成功");
            }

            @Override
            public void onFailure(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
                Logger.getInstance().v("chenbin", "错误码" + i + "  " + s);
                ToastDiy.showShort(context, s);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 1://收藏了该工作
                    iv_image.setBackgroundDrawable(getResources().getDrawable(R.mipmap.xingxingtianchong));
                    tv_collect.setText("已收藏");
                    break;
                case 0://没有收藏该工作
                    iv_image.setBackgroundDrawable(getResources().getDrawable(R.mipmap.xingxingxian));
                    tv_collect.setText("收藏");
                    break;
            }
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (type.equals("collect")) {//从收藏页面进来的
            Intent intent = new Intent();
            intent.putExtra("isCollect", isCollect);
            setResult(10086, intent);
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 查询数据
     */
    private void getData() {
        FunctionUtils.showLoadingDialog(this);
        BmobQuery<JobInfo> query = new BmobQuery<>();
        MyUser myUser = BmobUser.getCurrentUser(context, MyUser.class);
        query.addWhereRelatedTo("likes", new BmobPointer(myUser));
        query.findObjects(context, new FindListener<JobInfo>() {
            @Override
            public void onSuccess(List<JobInfo> list) {
                for (JobInfo info : list) {
                    if (info.getObjectId().equals(jobId)) {
                        isCollect = true;
                        FunctionUtils.dissmisLoadingDialog();
                        handler.sendEmptyMessage(1);
                        return;
                    }
                }
                FunctionUtils.dissmisLoadingDialog();
            }

            @Override
            public void onError(int i, String s) {
                FunctionUtils.dissmisLoadingDialog();
            }
        });
    }
}
