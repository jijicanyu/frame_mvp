package com.cores;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import com.cores.utils.image.XutilsLoderUtils;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.mvp.R;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by weizongwei on 15-11-24.
 */
public class FrameApplication extends Application {

    public static final String PROJECT_NAME="weisoft";

    public static final boolean isDebug=true;

    //单例模式的application
    static FrameApplication  appInstance;
    public static FrameApplication getInstance() {
        return appInstance;
    }

    public static BitmapUtils bitmapUtils;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application", "onCreate");

        appInstance = this;

        GlobalConfig.init(this);
        initXUtilsConfig();
        //选择性注释选择其中一个框架用
        //initGlide();
        //initImageLoader();
        intFresco();

        if(isDebug)
        {
            LeakCanary.install(this);
        }
    }



    private void intFresco()
    {

        ImagePipelineConfig config= ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565).build();
        Fresco.initialize(this,config);

    }


    // 配置网络线程数 和 图片 色彩缓存 size等等
    private void initXUtilsConfig()
    {
        bitmapUtils = new BitmapUtils(getApplicationContext());
        XutilsLoderUtils.init(getApplicationContext());


        // 设置图片压缩类型
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        // 默认背景图片
        // bitmapUtils.configDefaultLoadingImage(R.drawable.image_load);
        // 加载失败图片
        // bitmapUtils.configDefaultLoadFailedImage(R.drawable.image_load);

        // 设置网络线程数量
        HttpUtils mHttpUtils = new HttpUtils();
        mHttpUtils.configRequestThreadPoolSize(5);
    }


    /**
     *
     glide不要再写什么实例化的配置了
     默认的已经很完美了
     色彩也用RGB_565了
     glide在picasso上进行了重新封装，虽然增大了 jar包的代码量但是很大程度上 减少了 集成的难度
     */
    public static void initGlide()
    {
    }



    //实例化ImageLoader
    public static void initImageLoader() {

        DisplayImageOptions image_display_options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.drawable.empty_photo)
                .showImageOnFail(R.drawable.empty_photo)
                .cacheInMemory(false)
                .cacheOnDisc(true)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        Rect screenrect = getScreenRect();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getInstance())
                .memoryCacheExtraOptions(screenrect.width(), screenrect.height())
                .threadPriority(Thread.NORM_PRIORITY - 1)
                .memoryCacheSize(5 * 1024 * 1024)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(1000)
                .threadPoolSize(4)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13) // default
                .defaultDisplayImageOptions(image_display_options)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileCount(100)
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取屏幕区域
     *
     */
    public static Rect getScreenRect() {
        DisplayMetrics displayMetric =  Resources.getSystem().getDisplayMetrics();
        Rect rect = new Rect(0, 0, displayMetric.widthPixels, displayMetric.heightPixels);
        Log.d("initImageLoader() 宽高 :", rect.width() + " " + rect.height());
        return rect;
    }


}
