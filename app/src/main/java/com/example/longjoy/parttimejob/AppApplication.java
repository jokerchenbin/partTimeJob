package com.example.longjoy.parttimejob;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

import com.example.longjoy.parttimejob.common.FunctionUtils;


public class AppApplication extends Application {

	private String updateConfName = "android_update_conf.json";
	/** 所以的Activity */
	public List<Activity> activities = new ArrayList<Activity>();

	@Override
	public void onCreate() {
		super.onCreate();
		initUpdateJsonData();
		initCommonData();
		AppConfig.context = this;

	}

	/**
	 * 初始化asset的配置文件
	 */
	public void initUpdateJsonData() {

		if (AppConfig.prefs == null) {
			AppConfig.prfsName = "jjConfig.xml";
			AppConfig.prefs = getSharedPreferences(AppConfig.prfsName, 0);
		}
		if (AppConfig.jPushfs == null) {
			AppConfig.jPushfsName = "jjConfig1.xml";
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
	 * 
	 * @param activity
	 *            add single activity in the activities
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
