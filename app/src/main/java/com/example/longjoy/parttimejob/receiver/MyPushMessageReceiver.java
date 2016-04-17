package com.example.longjoy.parttimejob.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import cn.bmob.push.PushConstants;

/**
 * 类描述：
 * 创建人：陈彬
 * 创建时间：2016/4/17 9:32
 */
public class MyPushMessageReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
            Log.d("chenbin", "客户端收到推送内容：" + intent.getStringExtra("msg"));
        }
    }
}
