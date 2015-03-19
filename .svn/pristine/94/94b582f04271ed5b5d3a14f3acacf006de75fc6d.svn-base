package com.base.app;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;
import com.baidu.frontia.FrontiaApplication;
import com.base.config.GlobalConfig;
import com.base.utils.CrashHandler;
import com.base.utils.activity.WSOFTActivityManager;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.io.DataOutputStream;

public class MainApplication extends FrontiaApplication {

    Process process = null;

    DataOutputStream os = null;

    private static MainApplication mInstance = null;

    NotificationManager mManager = null;

    Notification notification = null;
    private WSOFTActivityManager activityManager = null;

    public WSOFTActivityManager getActivityManager() {

        return activityManager;
    }

    public void setActivityManager(WSOFTActivityManager activityManager) {

        this.activityManager = activityManager;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressWarnings("unused")
    @Override
    public void onCreate() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectAll().penaltyDialog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll().penaltyDeath().build());
        }
        super.onCreate();
        mInstance = this;

        CrashHandler crashHandler = CrashHandler.getInstance();
        // 注册crashHandler
        crashHandler.init(getApplicationContext());

        // 得到通知消息的管理器对象，负责管理 Notification 的发送与清除消息等
        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                getApplicationContext())
//                .threadPoolSize(3)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .memoryCacheSize(30 * 1024 * 1024)
//                        // 3Mb
//                .denyCacheImageMultipleSizesInMemory()
//                .discCacheFileNameGenerator(new Md5FileNameGenerator())
//                .enableLogging() // Not necessary in common
//                .discCacheFileCount(1000).build();
//        ImageLoader.getInstance().init(config);

        //初始化自定义Activity管理器
        activityManager = activityManager.getScreenManager();
        initImageLoader(getApplicationContext());


    }

    private void openNotifation(String title, String content) {
//        String appName = getResources().getString(R.string.app_name);
//        notification = new Notification(R.drawable.ic_launcher, appName + " 消息提醒.", System.currentTimeMillis());
//
//        // 设置在通知栏中点击后Notification自动消失
//
//        notification.flags = Notification.FLAG_AUTO_CANCEL;
//        // 设置点击后转跳的新activity
//
//        Intent intent = new Intent(this, MsgDetailActivity.class);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        intent.putExtra("title", title);
//        intent.putExtra("content", content);
//
//        // 设置通知栏中显示的内容
//
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//
//                R.string.app_name, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        notification.setLatestEventInfo(this, title, content, contentIntent);
//
//        mManager.notify(0, notification);
    }


    public static MainApplication getInstance() {
        return mInstance;
    }


    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        Rect screenrect = getScreenRect(MainApplication.getInstance());
        DisplayImageOptions localDisplayImageOptions = new DisplayImageOptions.Builder().cacheInMemory(false).cacheOnDisc(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
        //如果图片尺寸大于了这个参数，那么就会这按照这个参数对图片大小进行限制并缓存
                .memoryCacheExtraOptions(screenrect.width(), screenrect.height())// default=device screen dimensions
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .memoryCacheSize(5 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(500)
                .threadPoolSize(6)//设置6个线程
                //.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
                .memoryCacheSizePercentage(13) // default
                .diskCacheSize(50 * 1024 * 1024)
                .memoryCache(new LRULimitedMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .defaultDisplayImageOptions(localDisplayImageOptions)
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
        GlobalConfig.scrhei=screenrect.height();
        GlobalConfig.scrwid=screenrect.width();
        Log.d("LYApplication:initImageLoader() 宽高 :", screenrect.width() + " " + screenrect.height());
    }

    /**
     * 获取屏幕区域
     *
     * @param activity
     */
    public static Rect getScreenRect(MainApplication activity) {
        DisplayMetrics displayMetric = new DisplayMetrics();
        displayMetric = activity.getResources().getDisplayMetrics();
        Rect rect = new Rect(0, 0, displayMetric.widthPixels, displayMetric.heightPixels);
        return rect;
    }

    @Override
    public void onLowMemory() {
        System.gc();
        Toast.makeText(MainApplication.this, "内存不足了，清理内存后再打开,好不好？", Toast.LENGTH_SHORT).show();
        super.onLowMemory();
    }

}
