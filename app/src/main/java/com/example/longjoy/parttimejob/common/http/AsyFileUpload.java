/**
 * 
 */
package com.example.longjoy.parttimejob.common.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.example.longjoy.parttimejob.common.FunctionUtils;
import com.example.longjoy.parttimejob.common.Logger;


/** 
 * @author long_xia@loongjoy.com
 * @version 创建时间：2015年11月27日 下午1:57:56 
 * 
 */
/**
 * @author long_xia@loongjoy.com
 *
 */
public class AsyFileUpload extends AsyncTask<String, Integer, String> {
	FileUploadCallBackLisenter lisenter;

	/**
	 * @param ：string filepath,string url;
	 * @param listener
	 */
	public AsyFileUpload(FileUploadCallBackLisenter listener) {
		this.lisenter = listener;
	}

	public interface FileUploadCallBackLisenter {
		public void callBack(String value);
	}

	@Override
	protected void onPostExecute(String result) {
		lisenter.callBack(result);
		
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		Logger.getInstance().e("upload", "loading..." + values[0] + "%");
		
	}

	@Override
	protected String doInBackground(String... params) {
		String filePath = params[0];
		String uploadUrl = params[1];
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "******";
		try {
			URL url = new URL(uploadUrl);
			HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
			httpURLConnection.setDoInput(true);
			httpURLConnection.setDoOutput(true);
			httpURLConnection.setUseCaches(false);
			httpURLConnection.setRequestMethod("POST");
			httpURLConnection.setConnectTimeout(6 * 1000);
			httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
			httpURLConnection.setRequestProperty("Charset", "UTF-8");
			httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
			
			DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + end);
			dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
					+ filePath.substring(filePath.lastIndexOf("/") + 1) + "\"" + end);
			
			String type = filePath.substring(filePath.lastIndexOf(".")+1, filePath.length());
			dos.writeBytes("Content-Type: image/png"+end);
			dos.writeBytes(end);
			Bitmap bitmap = FunctionUtils.getSmallBitmap(filePath);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 50, baos);
			baos.toByteArray();
//			FileInputStream fis = new FileInputStream(filePath);
			ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
			long total = bis.available();
			String totalstr = String.valueOf(total/1024);
			Log.d("文件大小", totalstr);
			byte[] buffer = new byte[8192]; // 8k
			int count = 0;
			int length = 0;
			while ((count = bis.read(buffer)) != -1) {
				dos.write(buffer, 0, count);
				length += count;
				publishProgress((int) ((length / (float) total) * 100));
				// 为了演示进度,休眠500毫秒
				// Thread.sleep(500);
			}
			bis.close();
			dos.writeBytes(end);
			dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
			dos.flush();

			InputStream is = httpURLConnection.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			@SuppressWarnings("unused")
			String result = br.readLine();
			dos.close();
			is.close();
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getInstance().e("fileupError", e.toString());
			return "fail";
		}
	}
}
