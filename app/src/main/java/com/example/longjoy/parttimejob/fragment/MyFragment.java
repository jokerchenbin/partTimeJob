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
import android.widget.Toast;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.Configs;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.common.Logger;
import com.example.longjoy.parttimejob.tools.FileTools;
import com.example.longjoy.parttimejob.tools.SelectHeadTools;
import com.example.longjoy.parttimejob.widget.ActionSheetDialog;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 我的  界面
 * Created by longjoy on 2015/12/24.
 * 2015/12/24
 */
public class MyFragment extends Fragment implements View.OnClickListener{

    private ImageView iv_header;
    private Button btn_logout;
    private Context context;
    private Activity activity;
    private Fragment fragment;
    private Uri photoUri = null;

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
        iv_header = (ImageView) view.findViewById(R.id.my_fragment_iv_head);
        iv_header.setOnClickListener(this);
        String uriStr = AppConfig.prefs.getString("uri","kong");
        if (!"kong".equals(uriStr)){
            photoUri = Uri.parse(uriStr);
            iv_header.setImageURI(photoUri);
        }
        btn_logout = (Button) view.findViewById(R.id.my_fragment_btn_logout);
        btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.my_fragment_iv_head: //点击头像,选择加载头像
                showPicAnim();
                break;
        }
    }

    /**
     * Created by 陈彬 on 2015/12/30  17:56
     * 方法描述:  图片点击的动画加载
     */
    private void showPicAnim() {
        //加载 点击动画
        Animation alpha = AnimationUtils.loadAnimation(context,R.anim.image_anim);
        iv_header.startAnimation(alpha);

        //弹出头像选择
        if(!FileTools.hasSdcard()){
            Toast.makeText(context,"没有找到SD卡，请检查SD卡是否存在",Toast.LENGTH_SHORT).show();
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
        switch (requestCode){
            case Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO: // 拍照
                SelectHeadTools.startPhotoZoom(fragment, photoUri, 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_GALLERY://相册获取
                if (data==null) {
                    return;
                }
                SelectHeadTools.startPhotoZoom(fragment, data.getData(), 600);
                break;
            case Configs.SystemPicture.PHOTO_REQUEST_CUT:  //接收处理返回的图片结果
                if (data==null) {
                    return;
                }
                Bitmap bit = data.getExtras().getParcelable("data");
                iv_header.setImageBitmap(bit);
                saveBitmaptoLocal(bit);
                //File file = FileTools.getFileByUri(activity,photoUri);
                break;
        }
    }


    /**
     * Created by 陈彬 on 2016/1/4  11:38
     * 方法描述: 保存Bitmap  到本地
     */
    private void saveBitmaptoLocal(Bitmap bitmap){
        long l2 = System.currentTimeMillis();
        String fileName = l2 + ".jpg";
        String tempImgPath = Environment.getExternalStorageDirectory() + Configs.SystemPicture.SAVE_DIRECTORY +fileName;
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(tempImgPath));
            bitmap.compress(Bitmap.CompressFormat.JPEG,75,bos);
            AppConfig.prefs.edit().putString("uri",tempImgPath).commit();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
