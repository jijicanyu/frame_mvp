package com.base.utils.memory;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

/**
 *内存的工具类
 */
public class MemoryUtils {

    private static final String TAG="MemoryUtils";

    /**
     * 打印当前手机内存信息应用的内存信息
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void printMemoryInfo()
    {
        final String TAG="MemoryUtils.printMemoryInfo()";
        //打印当前APP内存信息

        //开启了  android:largeHeap="true" 后,就是启用了流氓应用的内存限制
        //打印当前应用内存信息
        Runtime rt = Runtime.getRuntime();
        Log.d(TAG, "APP当前内存状态: 最大可申请内存:" + rt.maxMemory() / 1024 / 1024 + "MB 已申请内存:" + rt.totalMemory() / 1024 / 1024 + "MB 空闲内存:" + rt.freeMemory() / 1024 / 1024 + "MB");

    }


    /**
     * 获得app可用内存的字节数  这个类不需要try,catch理论上不会报错
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static long getAppSurplusMe()
    {
        final String TAG="MemoryUtils.getAppSurplusMe()";

        Runtime rt = Runtime.getRuntime();
        //一下参数单位为字节数
        long totalMemory=rt.totalMemory();//这个是已经申请的内存,等于已经使用的内存加上空闲内存
        long maxMemory=rt.maxMemory();//最大内存限制
        long freeMemory=rt.freeMemory();

        //假如最大内存限制是64M,已经申请了34M,空闲4M,那么其实当前使用的是:(34-4)M,而实际当前有效可使用的内存是:64-(34-4)=34;
        //64-(34-4)=34   请允许我引用高数老师的那句话:"同理可得" 64-34+4
        //so
        long surplusMemory=maxMemory-totalMemory+freeMemory;
        Log.d(TAG, "系统当前内存状态: 最大可申请内存:" + rt.maxMemory() / 1024 / 1024 + "MB 已申请内存:" + rt.totalMemory() / 1024 / 1024 + "MB 空闲内存:" + rt.freeMemory() / 1024 / 1024 + "MB");

        return surplusMemory;
    }

    /**
     * 获得手机可用内存的字节数  这个类不需要try,catch,理论上不会报错
     *
     * 这个方法要慎用  容易导致崩溃  特别在引导页的时候  低内存手机容易发生崩溃
     *
     * @param context
     * @return
     */
    public static long getPhoneSurplusMe(Context context)
    {
        ActivityManager am = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

}
