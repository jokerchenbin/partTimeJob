package com.example.longjoy.parttimejob.tools;

import android.content.Context;
import android.widget.Toast;

/**
 *  自定义Toast
  * @ClassName: ToastDiy
  * @Description: TODO
  * @author bin_chen@loongjoy.com
  * @date 2016年2月26日 下午3:44:25
  *
 */
public class ToastDiy {

	public static void showShort(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static void showLong(Context context,String msg){
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
