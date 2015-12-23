package com.example.longjoy.parttimejob;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;

import android.R.string;
import android.content.Context;
import android.content.SharedPreferences;

public class AppConfig {
	public static boolean isDebug = true;

	public static Context context;
	public static int pageSize = 20;
	public static int gridPageSize = 15;
	public static int AllPageSize = 50;
	public static int homeGridSize = 9;
	public static int colorId = 0;
	public static String longitude = "1.0";// 经度
	public static String latitude = "1.0"; // 纬度
	public static String appId;
	public static String udId;
	public static String version;
	public static String apiVersion;
	public static int versionCode = 0;
	public static String device;
	public static String os;
	public static int screenWidth;
	public static int screenHeight;
	public static boolean hasNewVersion = false;
	public static String name;
	public static String url;
	public static String updateNote;
	public static String limitNum = "140";
	public static String hearNum = "20";
	public static String PIC = "jinjin";
	public static String datestring = "";

	public static final int DEFAULT_TAG = 2;
	public static final int DEFAULT_TAG2 = 10;
	public static final int DEFAULT_RESULT = 10001;
	public static final int DEFAULT_CAMERA_RESULT = 20001;
	public static final int DEFAULT_CROP_RESULT = 20002;
	public static final int DEFAULT_MAP_RESULT = 10002;
	public static final int DEFAULT_PIC_RESULT = 10003;
	public static final int DEFAULT_CITY_RESULT = 10004;
	public static final int DEFAULT_EDIT_RESULT = 10005;
	public static final int DEFAULT_REQUEST = 10001;
	public static final String DEFAULT_TAG_NAME = "tag";
	public static final String DEFAULT_LOG_NAME = "myLog";

	public static SharedPreferences prefs = null;
	public static SharedPreferences jPushfs = null;
	public static String prfsName = null;
	public static String jPushfsName = null;
	public static HttpClient httpAsyncClient; // httpAsyncClient
	public static long currThreadId; // currThreadId
	public static int unCheckedCount;
	/********************** 内网 ********************/
	// public final static String HOST =
	// "http://192.168.1.250/jingjingphp/public/api/";
	// public final static String IMAGE_HOST =
	// "http://192.168.1.250/jingjingphp/public/";
	/********************************************/
	/********************** 外网 ********************/
	 public final static String HOST = "http://jingjing.255.f3322.net/api/";
	 public final static String IMAGE_HOST = "http://jingjing.255.f3322.net/";

	/*****************************************/
	/********************** 生产 ********************/
//	public final static String HOST = "http://www.jjing.org/api/";
//	public final static String IMAGE_HOST = "http://www.jjing.org/";

	/*****************************************/
	public final static String INTERESTS = HOST + "user/interests";
	public final static String GET_CAPTCHA = HOST + "user/get-captcha";
	public final static String REGISTER = HOST + "user/register";
	public final static String LOGIN = HOST + "user/login";
	public final static String CITY = HOST + "user/city";
	public final static String SAERCH_CITYS = HOST + "user/cities";
	public final static String PRIVACY = HOST + "user/privacy";
	public final static String UPDA_PRIVACY = HOST + "user/update-privacy";
	public final static String SEARCH_INTEREST = HOST + "interest/search";
	public final static String GET_INTEREST = HOST + "interest/init";
	public final static String ACTIVITY_LIST = HOST + "activity/list";
	public final static String ACCTIVITY_DETAIL = HOST + "activity/detail";
	public final static String ACTIVITY_JOINER_INFO = HOST + "activity/joiner-info";
	public final static String ACTIVITY_JOINERS = HOST + "activity/joiners";
	public final static String IMAGE_UPLOAD = IMAGE_HOST + "image/upload";
	public final static String IMAGE_DOWNLOAD = IMAGE_HOST + "image/get/";
	public final static String USER_GROUP = HOST + "activity/user-groups";
	public final static String PROVINCES = HOST + "activity/provinces";
	public final static String CITYS = HOST + "activity/cities";
	public final static String AREAS = HOST + "activity/areas";
	public final static String GROUP_CREATE = HOST + "group/create";
	public final static String CREATE_ACTIVE = HOST + "activity/create";
	public final static String PUBLISH_COMMMENT = HOST + "activity/publish-comment";
	public final static String ACTIVITY_COMMENTS = HOST + "activity/comments";
	public final static String GROUP_LIST = HOST + "group/list";
	public final static String GROUP_DETAIL = HOST + "group/detail";
	public final static String GROUO_MEMBERS = HOST + "group/members";
	public final static String GROUP_JOIN = HOST + "group/join";
	public final static String GROUP_EXIT = HOST + "group/exit";
	public final static String UPDATE_USER = HOST + "user/update";
	public final static String ACTIVITY_JOIN = HOST + "activity/join";
	public final static String ACTIVITY_CANCEL = HOST + "activity/cancel";
	public final static String ACTIVITY_EXIT = HOST + "activity/exit";
	public final static String ACTIVITY_HOST_CITY = HOST + "activity/hot-city";
	public final static String USER_MESSAGES = HOST + "user/messages";
	public final static String ACTIVITY_IMGEGES = HOST + "activity/images";
	public final static String ACTIVITY_COUNT = HOST + "activity/count";
	public final static String ACTIVITY_GROUND = HOST + "activity/around";
	public final static String ACTIVITY_AREA_ACTIVTY = HOST + "activity/area-activity";
	public final static String USER_SEND_MESSAGE = HOST + "user/send-message";
	public final static String USER_RESET_PWD = HOST + "user/reset-pwd";
	public final static String USER_ACTIVITY = HOST + "user/activity";
	public final static String GROUP_COUNT = HOST + "group/count";
	public final static String GROUP_AREA_GROUPS = HOST + "group/area-groups";
	public final static String GROUP_AROUND = HOST + "group/around";
	public final static String UPDATE_APP = IMAGE_HOST + "api/user/update-app";
	public final static String READ_MESSAGE = HOST + "user/read-message";
	public final static String GROUP_UPDATE = HOST + "group/update";
	public final static String GROUP_REMOVE = HOST + "group/remove";
}