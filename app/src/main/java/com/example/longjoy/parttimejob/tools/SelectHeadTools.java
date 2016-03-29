package com.example.longjoy.parttimejob.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.Configs;
import com.example.longjoy.parttimejob.activity.LoginActivity;
import com.example.longjoy.parttimejob.widget.ActionSheetDialog;

import cn.bmob.v3.BmobUser;


/**
 * Created by ZYMAppOne on 2015/12/16.
 */
public class SelectHeadTools {

    /*****
     * 打开选择框
     *
     * @param fragment Context  Activity上下文对象
     * @param uri      Uri
     */
    public static void openDialog(final Fragment fragment, final Uri uri) {
        final Context context = fragment.getContext();
        new ActionSheetDialog(context)
                .builder()
                .setTitle("选择图片")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        startCamearPicCut(fragment, uri);
                    }
                })
                .addSheetItem("从手机相册选择", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        startImageCaptrue(fragment);
                    }
                })
                .show();
    }

    /**
     * Created by 陈彬 on 2016/1/5  16:15
     * 方法描述: 打开注销框
     */
    public static void openDialogOut(final Context context) {
        new ActionSheetDialog(context)
                .builder()
                .setTitle("您确定要注销登录？")
                .setCancelable(true)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("确定", ActionSheetDialog.SheetItemColor.BLACK, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        //退出系统
                        AppConfig.prefs.edit().clear().commit();//清空本地数据
                        BmobUser.logOut(context);   //清除缓存用户对象
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                })
                .show();
    }

    /****
     * 调用系统的拍照功能
     *
     * @param context Activity上下文对象
     * @param uri     Uri
     */
    private static void startCamearPicCut(Fragment context, Uri uri) {
        // 调用系统的拍照功能
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra("camerasensortype", 2);// 调用前置摄像头
        intent.putExtra("autofocus", true);// 自动对焦
        intent.putExtra("fullScreen", true);// 全屏
        intent.putExtra("showActionIcons", false);
        // 指定调用相机拍照后照片的储存路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_TAKEPHOTO);
    }

    /***
     * 调用系统的图库
     *
     * @param context Activity上下文对象
     */
    private static void startImageCaptrue(Fragment context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_GALLERY);
    }


    /*****
     * 进行截图
     *
     * @param context Activity上下文对象
     * @param uri     Uri
     * @param size    大小
     */
    public static void startPhotoZoom(Fragment context, Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);

        context.startActivityForResult(intent, Configs.SystemPicture.PHOTO_REQUEST_CUT);
    }
}
