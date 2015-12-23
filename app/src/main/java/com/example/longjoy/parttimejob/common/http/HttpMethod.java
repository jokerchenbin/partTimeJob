/**   
 * @{#} HttpRequest.java Create on 2013-7-15 PM 3:39:29   
 * @author tuxiaohui
 * Copyright (c) 2013 by loongjoy.
 * Http request params handle
 */
package com.example.longjoy.parttimejob.common.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.example.longjoy.parttimejob.AppConfig;


/**
 * @{# HttpRequest.java Create on 2014-5-5 PM 1:03:19
 * @author xiaohui_tu Http request Method
 */

public class HttpMethod {
	/*
	 * get all the params
	 */
	public static List<NameValuePair> getParams(Context context, Map<Object, Object> outParamMap) {

		TreeMap<Object, Object> totalMap = new TreeMap<Object, Object>();
		totalMap.put("udId", AppConfig.udId == null ? "" : AppConfig.udId);
		totalMap.put("ver", AppConfig.version);
		totalMap.put("apiVer", AppConfig.apiVersion);
		totalMap.put("device", AppConfig.device);
		totalMap.put("os", AppConfig.os);
		// totalMap.put("longitude", AppConfig.longitude);
		// totalMap.put("latitude", AppConfig.latitude);
		totalMap.put("resolution", AppConfig.screenWidth + "X" + AppConfig.screenHeight);

		// out params
		if (outParamMap != null) {
			Iterator<Entry<Object, Object>> outIter = outParamMap.entrySet().iterator();
			while (outIter.hasNext()) {
				Entry<Object, Object> outEntry = outIter.next();
				Object outKey = outEntry.getKey();
				Object outVal = outEntry.getValue();
				totalMap.put(outKey, outVal);
			}
		}

		String signStr = "";
		Iterator<Entry<Object, Object>> totalIter = totalMap.entrySet().iterator();
		while (totalIter.hasNext()) {
			Entry<Object, Object> totalEntry = totalIter.next();
			Object totalVal = totalEntry.getValue();
			signStr += totalVal;
		}

		// String sign = getMD5Str(signStr + AppConfig.APISALT);

		// totalMap.put("sign", sign);

		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (Entry<Object, Object> entry : totalMap.entrySet()) {
			list.add(new BasicNameValuePair(entry.getKey().toString(), entry.getValue().toString()));
		}
		return list;
	}

	/**
	 * 拼接get请求的url
	 * 
	 * @author long_xia@loongjoy.com
	 *
	 * @param url
	 * @param params
	 * @return
	 */
	public static String getUrl(String url, Map<Object, Object> params) {
		TreeMap<Object, Object> totalMap = new TreeMap<Object, Object>();
		totalMap.put("udId", AppConfig.udId == null ? "" : AppConfig.udId);
		totalMap.put("versionCode", AppConfig.version);
		totalMap.put("apiVer", AppConfig.apiVersion);
		totalMap.put("device", AppConfig.device);
		totalMap.put("os", AppConfig.os);
		// totalMap.put("longitude", AppConfig.longitude);
		// totalMap.put("latitude", AppConfig.latitude);
		totalMap.put("resolution", AppConfig.screenWidth + "X" + AppConfig.screenHeight);

		// out params
		if (params != null) {
			Iterator<Entry<Object, Object>> outIter = params.entrySet().iterator();
			while (outIter.hasNext()) {
				Entry<Object, Object> outEntry = outIter.next();
				Object outKey = outEntry.getKey();
				Object outVal = outEntry.getValue();
				totalMap.put(outKey, outVal);
			}
		}
		StringBuffer sb = null;

		for (Entry<Object, Object> entry : totalMap.entrySet()) {
			if (sb == null) {
				sb = new StringBuffer();
				sb.append("?");
			} else {
				sb.append("&");
			}
			sb.append(entry.getKey().toString());
			sb.append("=");
			try {
				sb.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		url += sb.toString();
		return url;
	}

	/*
	 * MD5
	 */
	public static String getMD5Str(String string) {
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(string.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			System.exit(-1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
		}
		return md5StrBuff.toString();
	}
}
