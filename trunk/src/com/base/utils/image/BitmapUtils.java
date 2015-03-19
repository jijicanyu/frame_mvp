package com.base.utils.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.Log;
import com.base.config.GlobalConfig;
import com.base.utils.memory.MemoryUtils;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 从DrawableUtil类中分离出来这个类,为了使代码看起来更加简洁明了
 * 用于Bitmap处理的一些方法
 */
public class BitmapUtils {
    public static final int ARGB8888_MODEL=4;//ARGB8888模式下消耗内存,为像素数乘以4个字节.
    public static final int RGB565_MODEL=2;//RGB565模式下消耗内存,为像素数乘以4个字节.
    /**
     * 获取APP剩余可申请内存,判断是否够大,以手机分辨率为基础,进行计算图片处理所需要消耗的内存.
     * @return
     *
     * !!!这里获取的不是手机的剩余内存而是APP的
     */
    public static boolean isBigSurpluseMemory(Context context)
    {
        boolean isbig=true;//默认
        long appsurplusMe= MemoryUtils.getAppSurplusMe();//这里单位是Byte
        long phonesurplusMe= MemoryUtils.getPhoneSurplusMe(context);//这里单位是Byte
        long bitmapoptNeedmemory=GlobalConfig.scrwid*GlobalConfig.scrhei*ARGB8888_MODEL;//单位依旧是Byte
        //在进行图片处理的时候会把图片分辨率缩小到屏幕分辨率大小,所以这里消耗内存的计算是以屏幕分辨率所谓基础的.
        if(bitmapoptNeedmemory*2 > appsurplusMe)//为了其他考虑,剩余内存应该大于所需要内存的两倍
        {
            return false;
        }


        if(bitmapoptNeedmemory*2>phonesurplusMe)
        {
            return  false;
        }//这里一定要判断手机剩余内存,上面的判断只是app的理论上的内存限制,而如果手机本身就已经没有内存了,
        // 会有两种情况 1.自动清理一些内存  来给当前app用,2.本身就是内存太小,现在占内存的应用都是系统级别的清理不掉,那就坑了
        // 举个例子：125M内存的手机上就会发生当手机时，我发现内存不够的时候，android的自动杀进程的机制就没法用了
        //请看如下的我在410M的手机上运行的结果

        return isbig;
    }
    /**
     12-11 16:52:35.260      441-441/com.oki.lyw.activity D/MemoryUtils.printMemoryInfo()﹕ 常规应用最大内存限制:64M 流氓应用最大内存限制:128M
     12-11 16:52:35.270      441-441/com.oki.lyw.activity D/MemoryUtils.printMemoryInfo()﹕ APP当前内存状态: 最大可申请内存:128MB 已申请内存:21MB 空闲内存:4MB
     12-11 16:52:35.270      441-441/com.oki.lyw.activity D/MemoryUtils.printMemoryInfo()﹕ 手机剩余内存:70.12109MB 手机总内存:410.54297MB
     这里可以明显看出app剩余可申请的内存要大于手机的剩余内存的.当申请更多内存的时候android就会自动清理
     */





    /**
     * 创建倒影图片
     * @throws java.io.IOException
     */
    public static Bitmap createReflectedBitmap(Bitmap srcBitmap) {
        if (null == srcBitmap) {
            return null;
        }

        // The gap between the reflection bitmap and original bitmap.
        final int REFLECTION_GAP = 4;

        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int reflectionWidth = srcBitmap.getWidth();
        int reflectionHeight = srcBitmap.getHeight() / 2;

        if (0 == srcWidth || srcHeight == 0) {
            return null;
        }

        // The matrix
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        try {
            // The reflection bitmap, width is same with original's, height is half of original's.
            Bitmap reflectionBitmap = Bitmap.createBitmap(
                    srcBitmap,
                    0,
                    srcHeight / 2,
                    srcWidth,
                    srcHeight / 2,
                    matrix,
                    false);

            if (null == reflectionBitmap) {
                return null;
            }

            // Create the bitmap which contains original and reflection bitmap.
            Bitmap bitmapWithReflection = Bitmap.createBitmap(
                    reflectionWidth,
                    srcHeight + reflectionHeight + REFLECTION_GAP,
                    Bitmap.Config.ARGB_8888);

            if (null == bitmapWithReflection) {
                return null;
            }

            // Prepare the canvas to draw stuff.
            Canvas canvas = new Canvas(bitmapWithReflection);

            // Draw the original bitmap.
            canvas.drawBitmap(srcBitmap, 0, 0, null);

            // Draw the reflection bitmap.
            canvas.drawBitmap(reflectionBitmap, 0, srcHeight + REFLECTION_GAP, null);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            LinearGradient shader = new LinearGradient(
                    0,
                    srcHeight,
                    0,
                    bitmapWithReflection.getHeight() + REFLECTION_GAP,
                    0x70FFFFFF,
                    0x00FFFFFF,
                    Shader.TileMode.MIRROR);
            paint.setShader(shader);
            paint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_IN));

            // Draw the linear shader.
            canvas.drawRect(
                    0,
                    srcHeight,
                    srcWidth,
                    bitmapWithReflection.getHeight() + REFLECTION_GAP,
                    paint);

            return bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static Bitmap revitionImageSize(String path, int max_w, int max_h) throws IOException {
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= max_w)
                    && (options.outHeight >> i <= max_h)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }


    /**
     *将Bitmap保存到本地
     * @param bitmap
     * @param imgurl imgurl   不带file了开头的路径,如:  /strange/sdcard/asdasd.jpg
     */
    public static void savePictureToLocal(Bitmap bitmap, String imgurl) {
        FileOutputStream b = null;
        File file = new File(imgurl);
        file.getParentFile().mkdirs();// 创建文件夹


        //对图片质量进行压缩,这样保存的图片就变小了
        int yasuolv = 60;
        try {
            b = new FileOutputStream(imgurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, yasuolv, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
                //bitmap.recycle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p/>
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.本地路径:url="mnt/sdcard/photo/image.png";  不带 file: 头
     * <p/>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap GetLocalOrNetBitmap(String path) {
        final String TAG="GetLocalOrNetBitmap";
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
            options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int hi = (int) (options.outHeight / (float) GlobalConfig.scrhei);//以屏幕高度作为显示依据
            int wi = (int) (options.outWidth / (float) GlobalConfig.scrwid);//以屏幕高度作为显示依据

            int be = 0;
            if (wi > hi)
                be = hi;
            else
                be = wi;

            Log.d(TAG, "缩放比:" + be); //缩放之后
            Log.d(TAG, "原图宽高:" + options.outWidth + " " + options.outHeight); //缩放之后

            if (be <= 0)
                be = 1;
            options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
            options.inPreferredConfig = Bitmap.Config.RGB_565;//使用RGB_565后会是内存降低一半但是,不适合decode带有透明区域的PNG
            bitmap = BitmapFactory.decodeFile(path, options);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.d(TAG, "缩放后:宽" + w + " 高" + h); //缩放之后
            Log.d(TAG, "RGB_565格式内存:" + bitmap.getByteCount() / 1024 + "KB");
        }
        catch (Exception ex)
        {
            if(bitmap!=null&&!bitmap.isRecycled())
                bitmap.recycle();
            System.gc();  //提醒系统及时回收
        }
        return bitmap;
    }

    //同上,最后一个参数可选,配置是否返回高色彩的Bitmap
    //本地路径  不带 file: 头
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap GetLocalOrNetBitmap(String path, boolean isRGb_565) {
        final String TAG="GetLocalOrNetBitmap";
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
            options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int hi = (int) (options.outHeight / (float) GlobalConfig.scrhei);//以屏幕高度作为显示依据
            int wi = (int) (options.outWidth / (float) GlobalConfig.scrwid);//以屏幕高度作为显示依据

            int be = 0;
            if (wi > hi)
                be = hi;
            else
                be = wi;


            Log.d(TAG, "缩放比:" + be); //缩放之后
            Log.d(TAG, "原图宽高:" + options.outWidth + " " + options.outHeight); //缩放之后

            if (be <= 0)
                be = 1;
            options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
            if(isRGb_565)
                options.inPreferredConfig = Bitmap.Config.RGB_565;//使用RGB_565后会是内存降低一半但是,不适合decode带有透明区域的PNG
            bitmap = BitmapFactory.decodeFile(path, options);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.d(TAG, "缩放后:宽" + w + " 高" + h); //缩放之后
            Log.d(TAG, "RGB_565格式内存:" + bitmap.getByteCount() / 1024 + "KB");
        }
        catch (Exception ex)
        {
            if(bitmap!=null&&!bitmap.isRecycled())
                bitmap.recycle();
            System.gc();  //提醒系统及时回收
        }
        return bitmap;
    }



    //同上,最后一个参数可选,配置是否返回高色彩的Bitmap
    //本地路径  不带 file: 头
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap GetLocalOrNetBitmap(String path, boolean isRGb_565,int maxwidth,int maxheight) {
        final String TAG="GetLocalOrNetBitmap";
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Log.d("GetLocalOrNetBitmap", path);

            bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
            options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int hi = (int) (options.outHeight / (float) maxwidth);//以指定宽度作为显示依据
            int wi = (int) (options.outWidth / (float) maxheight);//以指定高度作为显示依据

            int be = 0;
            if (wi < hi)
                be = hi;
            else
                be = wi;


            Log.d(TAG, "缩放比:" + be); //缩放之后
            Log.d(TAG, "原图宽高:" + options.outWidth + " " + options.outHeight); //缩放之后

            if (be <= 0)
                be = 1;
            options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
            if(isRGb_565)
                options.inPreferredConfig = Bitmap.Config.RGB_565;//使用RGB_565后会是内存降低一半但是,不适合decode带有透明区域的PNG
            bitmap = BitmapFactory.decodeFile(path, options);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.d(TAG, "缩放后:宽" + w + " 高" + h); //缩放之后
            Log.d(TAG, "RGB_565格式内存:" + bitmap.getByteCount() / 1024 + "KB");
        }
        catch (Exception ex)
        {
            if(bitmap!=null&&!bitmap.isRecycled())
                bitmap.recycle();
            System.gc();  //提醒系统及时回收
        }
        return bitmap;
    }

    public static void createPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
    }

    //传递两个参数即可 第一个是bitmap  第二个是目录名称,第二个参数为空时  保存在SD卡上
    public static void saveMyBitmap(Bitmap bmp, String directory, String filename) {
        String direc = "";
        if (directory.length() < 1) {
            direc = GlobalConfig.sdPath + "/";
        } else {
            direc = GlobalConfig.sdPath + "/" + directory + "/";
            createPath(direc);
        }
        File f = null;
        if (filename.length() < 1) {
            try {
                Date date = new Date();
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH_mm_ss_");
                filename = "www.njoki.com__" + format.format(date);
            } catch (Exception e) {
                filename = "未知";
            }
            f = new File(direc + filename + ".png");
        } else {
            f = new File(direc + filename + ".png");
        }

        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
        //bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        bmp.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
