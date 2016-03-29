package com.example.longjoy.parttimejob.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bmob.BTPFileResponse;
import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.Configs;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.activity.MyCollectActivity;
import com.example.longjoy.parttimejob.activity.MyResumeActivity;
import com.example.longjoy.parttimejob.activity.UserInfoActivity;
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.tools.FileTools;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;
import com.example.longjoy.parttimejob.widget.ActionSheetDialog;
import com.example.longjoy.parttimejob.widget.CircleImageView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.UpdateListener;

/**
 * 我的  界面
 * Created by longjoy on 2015/12/24.
 * 2015/12/24
 */
public class MyFragment extends Fragment implements View.OnClickListener {

    private static CircleImageView iv_header;
    private Button btn_logout;
    private Context context;
    private Activity activity;
    private Fragment fragment;
    private Uri photoUri = null;
    private static TextView tv_logotext;

    /* 布局按钮 */
    private LinearLayout ly_resume, ly_myCollect, ly_signUp, ly_myInfo, ly_checkUpdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        context = getContext();
        activity = getActivity();
        fragment = this;
        initViewIDs(view);
        return view;
    }

    /**
     * Created by 陈彬 on 2015/12/30  17:21
     * 方法描述: 初始化 View  ID
     */
    private void initViewIDs(View view) {
        iv_header = (CircleImageView) view.findViewById(R.id.my_fragment_iv_head);
        iv_header.setOnClickListener(this);
        ImageLoader.getInstance().displayImage(AppConfig.prefs.getString("imageUrl", ""),
                iv_header, AppConfig.options);



        ly_resume = (LinearLayout) view.findViewById(R.id.my_fragment_ly_myResume);
        ly_myCollect = (LinearLayout) view.findViewById(R.id.my_fragment_ly_myCollect);
        ly_signUp = (LinearLayout) view.findViewById(R.id.my_fragment_ly_signUp);
        ly_myInfo = (LinearLayout) view.findViewById(R.id.my_fragment_ly_myInfo);
        ly_checkUpdate = (LinearLayout) view.findViewById(R.id.my_fragment_ly_checkUpdate);
        ly_resume.setOnClickListener(this);
        ly_myCollect.setOnClickListener(this);
        ly_signUp.setOnClickListener(this);
        ly_myInfo.setOnClickListener(this);
        ly_checkUpdate.setOnClickListener(this);
        tv_logotext = (TextView) view.findViewById(R.id.my_fragment_tv_logontext);
        tv_logotext.setText(AppConfig.prefs.getString("username", ""));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_fragment_iv_head: //点击头像,选择加载头像
                showPicAnim();
                break;
            case R.id.my_fragment_ly_myResume: //我的简历
                Intent myResumeIntent = new Intent(context, MyResumeActivity.class);
                startActivity(myResumeIntent);
                break;
            case R.id.my_fragment_ly_myCollect: //我的收藏
                startActivity(new Intent(context, MyCollectActivity.class));
                break;
            case R.id.my_fragment_ly_signUp: //我的报名
                break;
            case R.id.my_fragment_ly_myInfo: //我的信息
                Intent myInfo = new Intent(context, UserInfoActivity.class);
                startActivity(myInfo);
                break;
            case R.id.my_fragment_ly_checkUpdate: //检查更新
                Toast.makeText(context, "已经是最新版本", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * Created by 陈彬 on 2015/12/30  17:56
     * 方法描述:  图片点击的动画加载
     */
    private void showPicAnim() {
        //加载 点击动画
        Animation alpha = AnimationUtils.loadAnimation(context, R.anim.image_anim);
        iv_header.startAnimation(alpha);

        //弹出头像选择
        if (!FileTools.hasSdcard()) {
            Toast.makeText(context, "没有找到SD卡，请检查SD卡是否存在", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = FileTools.getUriByFileDirAndFileName(Configs.SystemPicture.SAVE_DIRECTORY, Configs.SystemPicture.SAVE_PIC_NAME);
        } catch (IOException e) {
            Toast.makeText(context, "创建文件失败", Toast.LENGTH_SHORT).show();
            return;
        }
        SelectHeadTools.openDialog(fragment, photoUri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectHeadTools.startPhotoZoom(fragment, photoUri, 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data == null) {
                    return;
                }
                SelectHeadTools.startPhotoZoom(fragment, data.getData(), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data == null) {
                    return;
                }
                Bitmap bit = data.getExtras().getParcelable("data");
                iv_header.setImageBitmap(bit);
                saveBitmaptoLocal(bit);
                break;
        }
    }


    /**
     * Created by 陈彬 on 2016/1/4  11:38
     * 方法描述: 保存头像图片 Bitmap  到本地
     */
    private void saveBitmaptoLocal(Bitmap bitmap) {
        long l2 = System.currentTimeMillis();
        String fileName = l2 + ".jpg";
        String tempImgPath = Environment.getExternalStorageDirectory() + Configs.SystemPicture.SAVE_DIRECTORY + fileName;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        final File file = new File(tempImgPath);
        //上传头像文件  开起线程上传头像
        new Thread() {
            @Override
            public void run() {
                uploadFile(file);
            }
        }.start();
    }

    /**
     * Created by 陈彬 on 2016/1/4  15:40
     * 方法描述: 上传头像 并且更新到云端服务器
     */
    private void uploadFile(File file) {
        BTPFileResponse response = BmobProFile.getInstance(context).upload(file.getAbsolutePath(), new UploadListener() {

            @Override
            public void onSuccess(String fileName, String url, BmobFile file) {
                AppConfig.prefs.edit().putString("imageUrl", file.getUrl()).commit();
                handler.sendEmptyMessage(2);
                MyUser myUser = new MyUser();
                myUser.setImageUrl(file.getUrl());
                BmobUser usr = BmobUser.getCurrentUser(context);
                myUser.update(context, usr.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(context, "头像更新成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(int i, String s) {
                    }
                });
            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onError(int statuscode, String errormsg) {
            }
        });
    }

    public static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case 1://更改页面的名字
                    tv_logotext.setText(AppConfig.prefs.getString("username", ""));
                    break;
                case 2://重新加载头像
                    ImageLoader.getInstance().displayImage(AppConfig.prefs.getString("imageUrl", ""),
                            iv_header, AppConfig.options);
                    break;
            }
        }
    };

}
