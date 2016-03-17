package com.example.longjoy.parttimejob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.example.longjoy.parttimejob.bean.Province;
import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.tools.ProvinceInit;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;


public class AppApplication extends Application {

    private String updateConfName = "android_update_conf.json";
    /**
     * 所以的Activity
     */
    public List<Activity> activities = new ArrayList<>();
    public static ArrayList<Province> list;

    @Override
    public void onCreate() {
        super.onCreate();
        initUpdateJsonData();
        initCommonData();
        AppConfig.context = this;

        // 使用时请将第二个参数Application ID替换成你在Bmob服务器端创建的Application ID
        Bmob.initialize(this, "fd7d827e175eaee39ab299f944c564dc");
        BmobInstallation.getCurrentInstallation(this).save();
        //得到一个全局的数据
        list = ProvinceInit.initPrivince();
        initImageLoader();
    }

    /**
     * Created by 陈彬 on 2016/1/5  16:43
     * 方法描述: 初始化ImageLoader的信息
     */
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .memoryCacheSizePercentage(30)
                .build();
        ImageLoader.getInstance().init(config);

        AppConfig.options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .cacheOnDisk(true)
                .showImageOnFail(R.mipmap.ic_launcher)
                .build();
    }

    /**
     * 初始化asset的配置文件
     */
    public void initUpdateJsonData() {

        if (AppConfig.prefs == null) {
            AppConfig.prfsName = "parttime";
            AppConfig.prefs = getSharedPreferences(AppConfig.prfsName, 0);
        }
        if (AppConfig.jPushfs == null) {
            AppConfig.jPushfsName = "parttime_jpush";
            AppConfig.jPushfs = getSharedPreferences(AppConfig.jPushfsName, 0);
        }
    }

    /**
     * get all the activity
     *
     * @return List<Activity>
     */
    public List<Activity> getActivities() {
        return activities;
    }

    /**
     * @param activity add single activity in the activities
     */
    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     * 获取设备信息
     */
    public void initCommonData() {
        AppConfig.udId = FunctionUtils.getUdId(this);
        AppConfig.version = FunctionUtils.getAppVersion(this);
        AppConfig.apiVersion = FunctionUtils.getapiVersion(this);
        AppConfig.device = FunctionUtils.getDevice();
        AppConfig.os = FunctionUtils.getOs();
    }


}
