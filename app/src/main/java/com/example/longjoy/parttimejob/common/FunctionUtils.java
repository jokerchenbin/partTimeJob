/*
 * function common method 
 */
package com.example.longjoy.parttimejob.common;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.longjoy.parttimejob.AppApplication;
import com.example.longjoy.parttimejob.AppConfig;
import com.example.longjoy.parttimejob.R;
import com.example.longjoy.parttimejob.bean.MyUser;
import com.example.longjoy.parttimejob.widget.AppLoadingDialog;


public class FunctionUtils {

    private Logger logger = Logger.getInstance();
    private static AppLoadingDialog loadingDialog;

    /**
     * get screen width
     */
    public static void getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        AppConfig.screenWidth = dm.widthPixels;
        AppConfig.screenHeight = dm.heightPixels;
    }


    /*
     * get mobile udid
     */
    public static String getUdId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm == null) {
            return null;
        }
        return tm.getDeviceId();
    }

    /**
     * get app version name and version code
     */
    public static String getAppVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            // if (versionName == null || versionName.length() <= 0) {
            // return "";
            // }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        // AppConfig.versionCode = versionCode;
        return String.valueOf(versionCode);
    }

    public static String getapiVersion(Context context) {
        String versionName = "0.0.0";
        int versionCode = 1;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            // if (versionName == null || versionName.length() <= 0) {
            // return "";
            // }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        // AppConfig.versionCode = versionCode;
        return versionName;
    }

    /**
     * get mobile model
     */
    public static String getDevice() {
        return Build.MODEL;
    }

    /**
     * get mobile system version
     */
    public static String getOs() {
        return "android" + Build.VERSION.RELEASE;
    }

    /**
     * check the string is null
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        if (str == null || "".equals(str)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * get mobile phone number and replace china number
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager phoneMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String tel = phoneMgr.getLine1Number();
        if (tel != null) {
            tel = tel.replace("+86", "").trim();
        }
        return tel;
    }

    /*
     * check mobile network station
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * exit the app and finish all the activity
     *
     * @param context
     */
    public static void exitApp(Context context) {
        AppApplication appApplication = (AppApplication) context.getApplicationContext();
        List<Activity> list = appApplication.getActivities();
        for (Activity ac : list) {
            ac.finish();
        }
        list.clear();
        appApplication.onTerminate();
    }


    /**
     * @param bitmap     原图
     * @param edgeLength 希望得到的正方形部分的边长
     * @return 缩放截取正中部分后的位图。
     */
    public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
        if (null == bitmap || edgeLength <= 0) {
            return null;
        }

        Bitmap result = bitmap;
        int widthOrg = bitmap.getWidth();
        int heightOrg = bitmap.getHeight();

        // if (widthOrg > edgeLength && heightOrg > edgeLength) {
        // 压缩到一个最小长度是edgeLength的bitmap
        int longerEdge = (int) (edgeLength * Math.max(widthOrg, heightOrg) / Math.min(widthOrg, heightOrg));
        int scaledWidth = widthOrg > heightOrg ? longerEdge : edgeLength;
        int scaledHeight = widthOrg > heightOrg ? edgeLength : longerEdge;
        Bitmap scaledBitmap;

        try {
            scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true);
        } catch (Exception e) {
            return null;
        }

        // 从图中截取正中间的正方形部分。
        int xTopLeft = (scaledWidth - edgeLength) / 2;
        int yTopLeft = (scaledHeight - edgeLength) / 2;

        try {
            result = Bitmap.createBitmap(scaledBitmap, xTopLeft, yTopLeft, edgeLength, edgeLength);
            scaledBitmap.recycle();
        } catch (Exception e) {
            return null;
        }
        // }

        return result;
    }

    /**
     * 转换图片成圆形
     *
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right, (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }

    /**
     * 图片合成
     *
     * @return
     */
    public static Bitmap createBitmap(Bitmap src, Bitmap watermark) {
        if (src == null) {
            return null;
        }
        int w = src.getWidth();
        int h = src.getHeight();
        int ww = watermark.getWidth();
        int wh = watermark.getHeight();
        // create the new blank bitmap
        Bitmap newb = Bitmap.createBitmap(w, h + wh + 5, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
        Canvas cv = new Canvas(newb);
        // draw src into
        cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
        // draw watermark into
        int left = w / 2 - ww / 2;
        cv.drawBitmap(watermark, left, h + 5, null);// 在src的左下角画入水印
        // save all clip
        cv.save(Canvas.ALL_SAVE_FLAG);// 保存
        // store
        cv.restore();// 存储
        return newb;
    }

    public static String getDate() {
        String time = String.valueOf(System.currentTimeMillis());
        String str = null;
        Date dateTemp = convertStringToDate(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddss");
        str = sdf.format(dateTemp);
        return str;
    }

    public static Date convertStringToDate(String date) {
        try {
            return DEFAULT_SDF.parse(date);
        } catch (ParseException e) {
        }
        return new Date();
    }

    public final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public final static SimpleDateFormat DEFAULT_SDF = new SimpleDateFormat(DEFAULT_PATTERN);

    /**
     * 拍照
     */
    public static void startCameraAction(Activity activity, String fileName) {

        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        // fileName = "comment_" + FunctionUtils.getDate() + ".jpg";
        i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(getFilePath(), fileName)));
        activity.startActivityForResult(i, AppConfig.DEFAULT_CAMERA_RESULT);
    }

    /**
     * 路径
     */
    public static String getFilePath() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + AppConfig.PIC);
        file.mkdir();
        return file.getPath();
    }

    /**
     * 启动相册
     *
     * @author long_xia@loongjoy.com
     */
    public static void startPicSelect(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);

        /**
         * 下面这句话，与其它方式写是一样的效果，如果： intent.setData(MediaStore.Images
         * .Media.EXTERNAL_CONTENT_URI); intent.setType(""image/*");设置数据类型
         * 如果朋友们要限制上传到服务器的图片类型时可以直接写如 ："image/jpeg 、 image/png等的类型"
         * 这个地方小马有个疑问，希望高手解答下： 就是这个数据URI与类型为什么要分两种形式来写呀？有什么区别？
         */
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(intent, AppConfig.DEFAULT_REQUEST);
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 960);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    // 计算图片的缩放值
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static AppLoadingDialog getLoadingDialog(Activity activity) {
        if (loadingDialog != null) {
            loadingDialog = null;
        }
        loadingDialog = new AppLoadingDialog(activity, R.style.alert_dialog);
        return loadingDialog;

    }

    public static void showLoadingDialog(Activity activity) {
        AppLoadingDialog dialog = getLoadingDialog(activity);
        dialog.show();
    }

    public static void dissmisLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }

    /**
     * drawable2Bitmap
     *
     * @param drawable
     * @return
     * @author long_xia@loongjoy.com
     */
    public static Bitmap drawable2Bitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof NinePatchDrawable) {
            Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        } else {
            return null;
        }
    }

    public static Drawable bitmap2Drawable(Bitmap bitmap) {
        return new BitmapDrawable(bitmap);
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    // static Uri imgUri = Uri.parse("file:///sdcard/userLogo.jpg");
    public static void startPhotoZoom(Activity activity, String outFile, Uri uri, int width, int hight) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例为整数
        intent.putExtra("aspectX", width);
        intent.putExtra("aspectY", hight);
        // outputX outputY 是裁剪图片宽高z
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", hight);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(outFile)));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("return-data", false);
        activity.startActivityForResult(intent, AppConfig.DEFAULT_CROP_RESULT);

    }


    public static void imageBrower(Activity activity, int position, ArrayList<String> urls) {
        /*Intent intent = new Intent(activity, ImagePagerActivity.class);
        // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		//intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
		//intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		activity.startActivity(intent);*/
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }


    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范） 支付宝
     */
    public static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名 支付宝
     *
     * @param content 待签名订单信息
     */
    public static String sign(String content, String RSA_PRIVATE) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    public static String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * Created by 陈彬 on 2016/1/4  15:29
     * 方法描述: 将获取到用户基本信息写入本地
     */
    public static void writeUserInfoToLocal(MyUser user) {
        AppConfig.prefs.edit()
                .putString("id",user.getObjectId())
                .putString("username", user.getUsername())
                .putString("mobilePhoneNumber", user.getMobilePhoneNumber())
                .putInt("age", user.getAge())
                .putString("school", user.getSchool())
                .putString("height", user.getHeight())
                .putString("sex", user.getSex()).commit();
        if (!"".equals(user.getImageUrl())) {
            AppConfig.prefs.edit().putString("imageUrl", user.getImageUrl()).commit();
        }
    }


}
