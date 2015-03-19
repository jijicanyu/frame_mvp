
package com.base.utils.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;
import com.base.app.BaseActivity;
import com.base.utils.IdcardUtil;
import com.base.utils.file.DefSharedprefernces;
import com.mvp.R;
import com.mvp.view.MainActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ActivityUtil {
    //包名
    private static String packageName_ = "com.oki.lyw";
    //系统栏高度
    private static int statusBarHeight_ = 0;
    //标题栏高度
    private static int titleBarHeight_ = 0;
    //屏幕显示高度
    private static int containerHeight_ = 0;
    //tag 类日志名
    public static final String tag_ = "ActivityUtil";
    //finishcode]
    public static final int FINISH_ACTIVITY = 998;
    //系统选择小图片消息号
    public static final int PICK_SMALL_PHOTO_FROM_SYSTEM = 1001;
    //拍照消息号
    public static final int TAKE_PHOTO_USING_CAMERA = 1002;

    //相册消息号
    public static final int TAKE_PHOTO_AND_CROP = 1003;

    //相册消息号
    public static final int TAKE_PHOTO_USING_GALLERY = 1004;


    //相册路径
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
    public static File currentPhotoFile_;

    private ActivityUtil() {
    }

    /**
     * 设置Activity为全屏模式
     *
     * @param activity
     */
    public static void setFullscreen(Activity activity) {
        activity.getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 获取屏幕区域
     *
     * @param activity
     */
    public static Rect getScreenRect(Activity activity) {
        DisplayMetrics displayMetric = new DisplayMetrics();
        displayMetric = activity.getResources().getDisplayMetrics();
        Rect rect = new Rect(0, 0, displayMetric.widthPixels, displayMetric.heightPixels);
        return rect;
    }

    /**
     * 获取屏幕宽度
     *
     * @param activity
     */
    public static int getScreenWidth(Activity activity) {
        return getScreenRect(activity).width();
    }

    /**
     * 获取屏幕高度
     *
     * @param activity
     */
    public static int getScreenHeight(Activity activity) {
        return getScreenRect(activity).height();
    }


    /**
     * 设置状态栏高度
     *
     * @param height
     */
    public static void setStatusBarHeight(int height) {
        statusBarHeight_ = height;
    }

    /**
     * 设置标题栏高度
     *
     * @param height
     */
    public static void setTitleBarHeight(int height) {
        titleBarHeight_ = height;
    }


    /**
     * 设置屏幕显示高度
     *
     * @param height
     */
    public static void setShowContainerHeight(int height) {
        containerHeight_ = height;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        return statusBarHeight_;
    }

    /**
     * 获取标题栏高度*
     */
    public static int getTitleBarHeight() {
        return titleBarHeight_;
    }


    /**
     * 获取屏幕显示高
     */
    public static int getShowContainerHeight() {
        return containerHeight_;
    }

    /**
     * 设置Activity没有titile
     *
     * @param activity
     */
    public static void setNoTitle(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //不补充下面这句的话   还是存在title
    }

    /**
     * 获取系统显示信息（宽，高）
     *
     * @param activity
     * @return
     */
    public static DisplayMetrics getDisplayInfo(Activity activity) {
        DisplayMetrics displayMetric = activity.getResources().getDisplayMetrics();
        return displayMetric;
    }


    /*
     * 隐藏输入法
     */
    public static void setInputMethodHidden(Activity activity) {
        activity.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    // 获取AndroidManifest.xml中android:versionName
    public static String getSoftwareVersion(Context context) {
        try {
            PackageInfo pkinfo = context.getPackageManager().getPackageInfo(packageName_,
                    PackageManager.GET_CONFIGURATIONS);
            return pkinfo.versionName;
        } catch (Exception e) {
            Log.e(tag_, "getSoftwareVersion(): " + e.getMessage());
        }
        return "";
    }

    // 获取AndroidManifest.xml中android:versionCode
    public static int getVersionCode(Context context) {
        int sv = 0;
        try {
            PackageInfo pkinfo = context.getPackageManager().getPackageInfo(
                    packageName_, PackageManager.GET_CONFIGURATIONS);
            sv = pkinfo.versionCode;
            return sv;
        } catch (NameNotFoundException e) {
            Log.e(tag_, "getVersionCode(): " + e.getMessage());
        }

        return 0;
    }

    //获取系统SharedPreferences存储值
    public static String getPreference(String key, String defaultValue) {
        String res = defaultValue;
        try {
            DefSharedprefernces settings = new DefSharedprefernces();
            if (settings != null) {
                res = (String)settings.get(key, defaultValue);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }


    //获取系统SharedPreferences存储值
    public static int getPreference(String key, int defaultValue) {
        int res = defaultValue;
        try {
            DefSharedprefernces settings = new DefSharedprefernces();
            if (settings != null) {
                res = (Integer)settings.get(key, defaultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //获取系统SharedPreferences存储值
    public static long getPreference(String key, long defaultValue) {
        long res = defaultValue;
        try {
            DefSharedprefernces settings = new DefSharedprefernces();
            if (settings != null) {
                res = (Integer)settings.get(key, defaultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    //获取系统SharedPreferences存储值
    public static boolean getPreference(String key, boolean defaultValue) {
        boolean res = defaultValue;
        try {
            DefSharedprefernces settings = new DefSharedprefernces();
            if (settings != null) {
                res = (Boolean)settings.get(key, defaultValue);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return res;
    }

    //设置系统SharedPreferences存储值
    public static boolean setPreference(String key, Object value) {
        boolean isSaveOK = true;
        try {
            DefSharedprefernces settings = new DefSharedprefernces();
            settings.put(key,value);
            isSaveOK = settings.commit();
        } catch (Exception e) {
            Log.e(tag_, "ActivityUtil.setPreference(): " + e.getMessage());
        }
        return isSaveOK;
    }

    /**
     * 程序推到后台
     *
     * @param activity
     */
    public static void backToDesk(Activity activity) {
        Intent MyIntent = new Intent(Intent.ACTION_MAIN);
        {
            MyIntent.addCategory(Intent.CATEGORY_HOME);
        }
        activity.startActivity(MyIntent);
        // activity.getParent().moveTaskToBack(true);
    }


    /**
     * 调用系统短信界面
     *                 权限已经关闭
     * @param act
     * @param phoneNumber :短信发送号码
     * @param content     :短信内容
     */
    public static void openSysSmsto(Context act, String phoneNumber, String content) {
        try {
            Uri smsUri = Uri.parse("smsto:");
            Intent intent = new Intent(Intent.ACTION_VIEW, smsUri);
            if (null != phoneNumber && phoneNumber.trim().length() > 0) {
                intent.putExtra("address", phoneNumber);
            }
            if (null != content && content.trim().length() > 0) {
                intent.putExtra("sms_body", content);
            }
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("vnd.android-dir/mms-sms");
            act.startActivity(intent);
        } catch (Exception e) {
            Log.e(tag_, "ActivityUtil.openSysSmsto(): " + e.getMessage());
        }
        return;
    }

    /**
     * 获取调用照片1:1裁剪的Intent
     *
     * @param inputUri 待剪切的位置   content://media/external/images/media/1456
     *                 或者 真实路径  /Strorege/SDCard0/img.jpg
     * @param photoUri 输出的位置   只能是真实路径
     *                 两个参数最好不要一样 因为输入地址上的图片已经打开了
     *                 再剪切后覆盖上去就会导致无法写入的问题
     * @return
     */
    public static void getCropImageIntent(Activity context, Uri inputUri, Uri photoUri, int width, int height) {
        if (inputUri.getPath().equals(photoUri.getPath())) {
            Toast.makeText(context, "保存的位置不能保存在当前位置上,因为当前位置图片已经打开了", Toast.LENGTH_LONG).show();
        }
        try {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(inputUri, "image/*");
            // crop为true是设置在开启的intent中设置显示的view可以剪裁
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX,outputY 是剪裁图片的宽高
            intent.putExtra("outputX", width);
            intent.putExtra("outputY", height);
            intent.putExtra("return-data", true);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            context.startActivityForResult(intent, TAKE_PHOTO_AND_CROP);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    // 选取图片事件
    public static void choosePhotoAndCrop(Activity context, Uri photoUri, int width, int height) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        intent.setType("image/*");
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        /* 取得相片后返回本画面 */
        context.startActivityForResult(intent, TAKE_PHOTO_AND_CROP);
    }



    //启动系统拍照并保存
    public static void startCamera(Activity cx, Uri photoUri) {
        String status = Environment.getExternalStorageState();
        if (!status.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(cx, R.string.camera_error_no_sdcard, Toast.LENGTH_LONG).show();
        } else {
            try {
                final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                intent.putExtra("return-data", false);
                cx.startActivityForResult(intent, TAKE_PHOTO_USING_CAMERA);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(cx, R.string.camera_error_no_sdcard, Toast.LENGTH_LONG).show();
            }
        }
        return;
    }


    // 选取图片事件
    public static void choosePhotoForGallery(Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setType("image/*");
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, TAKE_PHOTO_USING_GALLERY);
    }

    //获取拍照文件名
    public static String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy_MM_dd_HH_mm_ss");
        return dateFormat.format(date) + ".jpg";
    }


   /*
   根据media路径获得 真实路径
   @param con   activity
   @param midurl    是一个media 路径因为不是真实路径  所以需要调用系统的media数据库获取路径  如content://media/external/images/media/1456
   @return /storage/emulated/0/LoveWallpaper/save/224588-100.jpg

   */
    public static String getGalleryRealpath(Activity con,Uri midiaurl)
    {
        try {
            Uri originalUri =midiaurl; //data.getData();        //获得图片的uri

            //这里开始的第二部分，获取图片的路径：
            String[] proj = {MediaStore.Images.Media.DATA};
            //好像是android多媒体数据库的封装接口，具体的看Android文档

            Cursor cursor = con.managedQuery(originalUri, proj, null, null, null);

            //按我个人理解 这个是获得用户选择的图片的索引值
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //将光标移至开头 ，这个很重要，不小心很容易引起越界
            cursor.moveToFirst();

            //最后根据索引值获取图片路径

            String path = cursor.getString(column_index);

            Log.d("getRealpath", path + "");
            return path+"";
        }catch (Exception e) {
            Log.e("getRealpath", e.toString());
            return "";
        }
    }




    //有时候我们需要判断自己的应用是否在前台显示
    public static boolean isTopActivity(Activity activity) {
        String packageName = "com.oki.lyw";
        ActivityManager activityManager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasksInfo = activityManager.getRunningTasks(1);
        if (tasksInfo.size() > 0) {
            //应用程序位于堆栈的顶层
            if (packageName.equals(tasksInfo.get(0).topActivity.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 图文分享
     *
     * @param imgPath ：图片路径，如：/mnt/sdcard/NiMei/image/imageName.jpg
     * @param content ：文本内容
     * @param context
     */
    public static void share(String imgPath, String content, Context context) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        if (imgPath != null && imgPath != "") {
            File f = new File(imgPath);
            Uri uri = Uri.fromFile(f);
            if (uri == null)
                return;
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
            shareIntent.setType("image/jpeg");
            // 当用户选择短信时使用sms_body取得文字
            shareIntent.putExtra("sms_body", content);
        } else {
            shareIntent.setType("text/plain");
            // 当用户选择短信时使用sms_body取得文字
            shareIntent.putExtra("sms_body", content);
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        // 自定义选择框的标题
        context.startActivity(Intent.createChooser(shareIntent, "选择分享方式"));
        // 系统默认标题
        //context.startActivity(shareIntent);
    }

    //调用系统播放器播放视频
    public static void playVideo(String videoPath, Context con) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        String strend = "";
        if (videoPath.toLowerCase().endsWith(".mp4")) {
            strend = "mp4";
        } else if (videoPath.toLowerCase().endsWith(".3gp")) {
            strend = "3gp";
        } else if (videoPath.toLowerCase().endsWith(".mov")) {
            strend = "mov";
        } else if (videoPath.toLowerCase().endsWith(".wmv")) {
            strend = "wmv";
        }

        intent.setDataAndType(Uri.parse(videoPath), "video/" + strend);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        con.startActivity(intent);


    }


    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    //路径:   /Strorege/SDCard0/img.jpg
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比

        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;

        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }

        if (be <= 0) {
            be = 1;
        }

        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;
    }

    /**
     * 获取视频的缩略图
     * 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
     * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
     *
     * @param videoPath 视频的路径
     * @param width     指定输出视频缩略图的宽度
     * @param height    指定输出视频缩略图的高度度
     * @param kind      参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
     *                  其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
     * @return 指定大小的视频缩略图
     */
    //路径:   /Strorege/SDCard0/video.mp4
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height,
                                           int kind) {
        Bitmap bitmap = null;
        // 获取视频的缩略图
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        System.out.println("w" + bitmap.getWidth());
        System.out.println("h" + bitmap.getHeight());
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);

        return bitmap;
    }

    //隐藏软键盘
    public static void hideKeyBorder(Context con) {
        if (!(con instanceof Activity))
            return;
        ((InputMethodManager) con.getSystemService(con.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(((Activity) con).getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    //显示键盘
    public static void showKeyBorder(Context con, View view) {
        if (!(con instanceof Activity))
            return;
        //显示软键盘,控件ID可以是EditText,TextView
        ((InputMethodManager) con.getSystemService(con.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
    }

    //指定控件获取焦点打开键盘
    public static void showInputkeybordOnView(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, 0);
    }


    //指定控件得到焦点
    public static void getFouce(View v) {
        v.setFocusable(true);
        v.setFocusableInTouchMode(true);
        v.requestFocus();  // 初始不让EditText得焦点
        v.requestFocusFromTouch();
    }

    //打开短信界面,并且发送到指定的人,指定的信息
    public static void openSMS(String number, String details, Context con) {
        try {
            Long.parseLong(number);
        } catch (Exception e) {
            if (con instanceof Activity)
                Toast.makeText(con, "号码有误!", Toast.LENGTH_LONG).show();
            return;
        }
        Uri uri = Uri.parse("smsto:" + number);
        Intent it = new Intent(Intent.ACTION_SENDTO, uri);
        it.putExtra("sms_body", details);
        con.startActivity(it);
    }

    //获取当前应用的版本号：
    public static String getVersionName(Context con) {
        try {
            PackageManager packageManager = con.getPackageManager();
            // getPackageName()是你当前类的包名，0代表是获取版本信息
            PackageInfo packInfo = packageManager.getPackageInfo(con.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    /*
    * @param mContext :   Activity
    * @param bundle  : Bundle参数的集合
    * @param result : 返回的resultcode
    *  ::使用过此方法之前请勿finish掉activity
    *  ::前一个activity需要执行startActivityForResult
    * */
    public static void finishResultExtras(Activity mContext,Intent intent,  int result) {
        //设置返回数据
        mContext.setResult(result, intent);
        if (mContext instanceof BaseActivity) {
            ((BaseActivity) mContext).clearResource();
        } else
            mContext.finish();

    }


    public static void finishPutStringExtra(Activity mContext, String key, String strbund) {
        Intent resultIntent = new Intent(mContext, MainActivity.class);
        resultIntent.putExtra(key, strbund);
        mContext.setResult(FINISH_ACTIVITY, resultIntent);
        if (mContext instanceof BaseActivity)
            ((BaseActivity) mContext).clearResource();
        else
            mContext.finish();
    }

    public static void viewQuestFouce(View view) {
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.requestFocusFromTouch();
    }


    /**
     * 验证身份证号码
     *
     * @param regularExpression
     */

    public static boolean authMatches(String regularExpression) {
        if (regularExpression == null)
            return false;
        if (regularExpression.length() == 15) {
            if(regularExpression.matches("[1-9]\\d{7}((0\\d)|(1[0-2]))([0|1|2]\\d|3[0-1])\\d{3}"))  //15位)
                return IdcardUtil.isIdcard(regularExpression);
        } else if (regularExpression.length() == 18) {
            if(regularExpression.matches("[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|(\\d{3}[A-Z]))"))
                return IdcardUtil.isIdcard(regularExpression);
        }
        // return	regularExpression.matches("/^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[A-Z])$/");
        return false;
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String telRegex = "[1][3578]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles))
            return false;
        else
            return mobiles.matches(telRegex);
    }

    /**
     * 判断app是否在运行
     * @param context
     * @return
     */
    public static boolean isAppRunnig(Context context)
    {
        //判断应用是否在运行
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        boolean isAppRunning = false;
        String MY_PKG_NAME = context.getPackageName();
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(MY_PKG_NAME) || info.baseActivity.getPackageName().equals(MY_PKG_NAME)) {
                isAppRunning = true;
                Log.i("isAppcationRun", info.topActivity.getPackageName() + " info.baseActivity.getPackageName()=" + info.baseActivity.getPackageName());
                break;
            }
        }
        return isAppRunning;
    }

    /* 修改通知栏颜色 -- 且AndroidManifest.xml的android:theme不能为Theme.NoTitleBar */
    @TargetApi(19)
    public static void setNotibarColor(Activity context)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = context.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (true) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }

        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.headColor);

    }

}
