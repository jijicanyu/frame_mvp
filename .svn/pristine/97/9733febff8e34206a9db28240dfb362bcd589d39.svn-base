package com.base.utils.image;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.base.app.BaseActivity;
import com.base.config.ConstUtil;
import com.base.config.GlobalConfig;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.db.annotation.NotNull;
import com.mvp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import org.apache.commons.lang.StringUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DrawableUtil {
    // tag 类日志名
    public static final String tag_ = "DrawableUtil";
    private static PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    /* 消除锯齿重要参数，两个一起用效果才好。最好开启activity的硬件加速，但是开启硬件加速 内存消耗就会增加。
    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    canvas.setDrawFilter(pfd);   paint.setAntiAlias(true)；
    */
    private static PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    //节省内存的options 一个像素消耗内存2B
    public static DisplayImageOptions image_display_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.empty_photo)
            .showImageOnFail(R.drawable.empty_photo)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    //真实色彩的options 一个像素消耗内存4B
    public static DisplayImageOptions realimg_display_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.empty_photo)
            .showImageOnFail(R.drawable.empty_photo)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .build();


    public static DisplayImageOptions fade_display_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.empty_photo)
            .showImageOnFail(R.drawable.empty_photo)
            .cacheInMemory(false)
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static DisplayImageOptions avaimg_rounddisplay_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.round_empty_avatar)
            .showImageOnFail(R.drawable.round_empty_avatar)
            .cacheInMemory(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Config.RGB_565)
            .build();

    public static DisplayImageOptions avaimg_display_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.empty_avatar)
            .showImageOnFail(R.drawable.empty_avatar)
            .cacheInMemory(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Config.RGB_565)
            .build();


    /**
     * 产生一个fadeIn动画显示图片
     */
    public static class AnimateFirstDisplayListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }
    }


    /**
     * 产生一个fadeIn动画显示Round图片
     */
    public static class AnimateFirstDisplayRoundListener extends SimpleImageLoadingListener {
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    displayedImages.add(imageUri);
                    FadeInDisplay(imageView, toRoundBitmap(loadedImage));
                } else
                    imageView.setImageBitmap(toRoundBitmap(loadedImage));

            }
        }
    }


    /**
     * 以最省内存的方式读取本地资源的图片   hdpi文件夹下
     *
     * @param context
     * @param resId
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap readBitMap(Context context, int resId) {
        Bitmap bmp = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        try {
            opt.inPreferredConfig = Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inJustDecodeBounds = true;//只返回宽高,不返回bitmap的Byte
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            bmp = BitmapFactory.decodeStream(is, null, opt);
            int hi = (opt.outHeight / GlobalConfig.scrhei);//以屏幕高度作为显示依据
            int wi = (opt.outWidth / GlobalConfig.scrwid);//以屏幕高度作为显示依据
            int be = 0;
            if (wi > hi)
                be = hi;
            else
                be = wi;

            if (be > 0)
                opt.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
            opt.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(is, null, opt);
            //Log.d("readBitMap", GlobalConfig.scrhei + "内存消耗:" + bmp.getByteCount() / 1024 + "K " + " 尺寸: " + bmp.getWidth() + " " + bmp.getHeight());
        } catch (OutOfMemoryError error) {
            Log.d("readBitMap", " 内存OOM" + GlobalConfig.scrwid + "   " + GlobalConfig.scrhei);
        } catch (Exception ex) {
            Log.d("readBitMap", "" + ex.getMessage());
        }
        return bmp;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap readBIGBitMap(Context context, int resId) {
        Bitmap bmp = null;
        BitmapFactory.Options opt = new BitmapFactory.Options();
        try {
            opt.inPreferredConfig = Config.ARGB_8888;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            opt.inJustDecodeBounds = true;//只返回宽高,不返回bitmap的Byte
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            bmp = BitmapFactory.decodeStream(is, null, opt);
            int hi = (opt.outHeight / GlobalConfig.scrhei);//以屏幕高度作为显示依据
            int wi = (opt.outWidth / GlobalConfig.scrwid);//以屏幕高度作为显示依据
            int be = 0;
            if (wi > hi)
                be = hi;
            else
                be = wi;

            if (be > 0)
                opt.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
            opt.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeStream(is, null, opt);
            //Log.d("readBIGBitMap", "内存消耗:" + bmp.getByteCount() / 1024 + "K " + " 尺寸: " + bmp.getWidth() + " " + bmp.getHeight());
        } catch (OutOfMemoryError error) {
            Log.d("readBIGBitMap", " 内存OOM" + GlobalConfig.scrwid + "   " + GlobalConfig.scrhei);
        } catch (Exception ex) {
            Log.d("readBIGBitMap", "" + ex.getMessage());
        }
        return bmp;
    }


    /**
     * 获取圆角Bitmap图片
     *
     * @param bitmap ：原图片
     * @return：切圆角后的图片
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        float roundPx = 5;
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }

        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getWidth(), Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(),
                    bitmap.getWidth());
            final RectF rectF = new RectF(rect);
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);

            canvas.setDrawFilter(pdf);
            roundPx = bitmap.getWidth() / 10;//此处如除以2了  则就是圆图了  就不是圆角了
            float roundPy = bitmap.getHeight() / 2;
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(bitmap, rect, rect, paint);
            // bitmap.recycle();
            return output;
        } catch (Exception e) {
            Log.e(tag_,
                    "DrawableUtil.getRoundedCornerBitmap(): " + e.getMessage());
            return null;
        }
    }

    /**
     * 获取圆形Bitmap图片
     *
     * @param bitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            int ovalLen = Math.min(width, height);
            Rect src = new Rect((width - ovalLen) / 2, (height - ovalLen) / 2, (width - ovalLen) / 2 + ovalLen, (height - ovalLen) / 2 + ovalLen);
            Rect dst = new Rect(0, 0, ovalLen, ovalLen);
            Bitmap output = Bitmap.createBitmap(ovalLen, ovalLen, Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            canvas.setDrawFilter(pdf);
            canvas.drawOval(new RectF(0, 0, ovalLen, ovalLen), paint);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(bitmap, src, dst, paint);
            return output;
        } else {
            return null;
        }
    }

    public static Bitmap decodeUriAsBitmap(Context cx, Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeStream(cx.getContentResolver().openInputStream(uri));
        } catch (FileNotFoundException e) {
            Log.e(tag_,
                    "DrawableUtil.decodeUriAsBitmap(): " + e.getMessage());
            return null;
        }
        return bitmap;
    }

    /**
     * 用于显示网络图片,采用不是很真实的色彩杜绝OOM
     * 用法  getBitmapUtils(getThis).display(img, "img1.gtimg.com/news/pics/hv1/63/26/1451/94357968.jpg";
     *
     * @param img (不可传本地图片"file://sdcard0/94357968.jpg")
     * @param url
     */
    public static void DisplayImg(final ImageView img, final String url) {
        if (StringUtils.isBlank(url)) return;
        final Context con = img.getContext();
        String globurl = ConstUtil.HTTP_SERVER_FOR_IMAGE + url;

        if (con instanceof BaseActivity)
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, image_display_options, new AnimateFirstDisplayListener());
        else
            ImageLoader.getInstance().displayImage(globurl, img, image_display_options, new AnimateFirstDisplayListener());
    }

    /**
     * 显示真实色彩的图片
     *
     * @param img
     * @param url
     */
    public static void DisplayRealImg(final ImageView img, final String url) {
        if (StringUtils.isBlank(url)) return;
        final Context con = img.getContext();
        String globurl = ConstUtil.HTTP_SERVER_FOR_IMAGE + url;

        if (con instanceof BaseActivity)
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, realimg_display_options, new AnimateFirstDisplayListener());
        else
            ImageLoader.getInstance().displayImage(globurl, img, realimg_display_options, new AnimateFirstDisplayListener());
    }


    public static void DisplayImgAndFade(final ImageView img, final String url) {

        if (StringUtils.isBlank(url)) return;
        String globurl = ConstUtil.HTTP_SERVER_FOR_IMAGE + url;
        final Context con = img.getContext();
        //用ImageLoader显示
        if (!(con instanceof BaseActivity)) {
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, image_display_options, new AnimateFirstDisplayListener());
        } else
            ImageLoader.getInstance().displayImage(globurl, img, new AnimateFirstDisplayListener());
    }


    /*  显示本地图片
    * @param  url
    *参数可以  file://  为开头
    * 也可以 /strong/oki/img/img.png
    * */
    public static void DisplayLocImg(final ImageView img, final String url) {
        if (StringUtils.isBlank(url))
            return;
        final Context con = img.getContext();
        String globurl = url;
        if (globurl.startsWith("file:") == false)
            globurl = "file:/" + globurl;

        //用ImageLoader显示
        if (con instanceof BaseActivity)
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, image_display_options);
        else
            ImageLoader.getInstance().displayImage(globurl, img, image_display_options, new AnimateFirstDisplayListener());
    }


    public static void DisplayImgID(final ImageView img, final int id) {
        if (img == null || id < 0)
            return;
        String globurl = "drawable://" + id;
        //用ImageLoader显示
//        if (con instanceof BaseActivity)
//            ((BaseActivity) con).imageLoader.displayImage(globurl, img, image_display_options,new AnimateFirstDisplayListener());
//        else
//            Constants.imageLoader.displayImage(globurl, img, image_display_options,new AnimateFirstDisplayListener());

        FadeInDisplay(img, readBitMap(img.getContext(), id));

    }


    public static void DisplayARGBImgID(final ImageView img, final int id) {
        String globurl = "drawable://" + id;
        //用ImageLoader显示
//        if (con instanceof BaseActivity)
//            ((BaseActivity) con).imageLoader.displayImage(globurl, img, realimg_display_options,new AnimateFirstDisplayListener());
//        else
//            Constants.imageLoader.displayImage(globurl, img, realimg_display_options,new AnimateFirstDisplayListener());

        FadeInDisplay(img, readBIGBitMap(img.getContext(), id));

    }

    public static void DisplayImgRoundID(final ImageView img, final int id) {
        String globurl = "drawable://" + id;
        final Context con = img.getContext();
        ImageLoadingListener lodlise = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                DrawableUtil.DisplayRoundImgID(img, R.drawable.empty_photo);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                img.setImageBitmap(getRoundedCornerBitmap(bitmap));
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        };
        //用ImageLoader显示
        if (con instanceof BaseActivity)
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, image_display_options, lodlise);
        else
            ImageLoader.getInstance().displayImage(globurl, img, image_display_options, lodlise);

    }

    /**
     * 用于显示http开头的路径的图片
     *
     * @param img 图片控件
     * @param url 路径
     */
    public static void DisplayHttpheadImg(final ImageView img, final String url) {
        if (StringUtils.isBlank(url)) return;
        final Context con = img.getContext();
        final String notheadurl = url.substring(6, url.length());

        String globurl = url;
        //用ImageLoader显示
        if (con instanceof BaseActivity)
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, image_display_options, new AnimateFirstDisplayListener());
        else
            ImageLoader.getInstance().displayImage(globurl, img, image_display_options, new AnimateFirstDisplayListener());
    }



        /*显示圆图
    * 产生一个BitmapUtils对象,用于显示网络图片或者本地图片,杜绝OOM
    * @param con
    *   :Activity
    * @param  img
    * 图片控件
    * @param url
    *图片路径
    * @param isround
    * 是否显示原图
    * */

    public static void DisplayRoundImg(final ImageView img, final String url) {
        if (StringUtils.isBlank(url))
            img.setImageBitmap(readBitMap(img.getContext(), R.drawable.round_empty_avatar));
        final Context con = img.getContext();
        String globurl = ConstUtil.HTTP_SERVER_FOR_IMAGE + url;


        //用ImageLoader显示
        if (con instanceof BaseActivity)
            ((BaseActivity) con).imageLoader.displayImage(globurl, img, avaimg_rounddisplay_options, new AnimateFirstDisplayRoundListener());
        else
            ImageLoader.getInstance().displayImage(globurl, img, avaimg_rounddisplay_options, new AnimateFirstDisplayRoundListener());

    }

    //根据ID显示圆形图片
    public static void DisplayRoundImgID(final ImageView img, final int id) {
        img.setImageBitmap(toRoundBitmap(readBitMap(img.getContext(), id)));
    }

    //显示圆角图片
    public static void DisplayRoundedCornerImg(final ImageView img, final String url, final boolean isneedfadeanim) {
        if (StringUtils.isBlank(url)) return;
        final Context con = img.getContext();
        String globurl = "";

        globurl = ConstUtil.HTTP_SERVER_FOR_IMAGE + url;
        //用ImageLoader显示
//        ((BaseActivity) con).imageLoader.loadImage(globurl, new ImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                img.setImageDrawable(((BaseActivity) con).config.getLoadFailedDrawable());
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                if (isneedfadeanim)
//                    FadeInDisplay(img, getRoundedCornerBitmap(loadedImage));
//                else
//                    img.setImageBitmap(getRoundedCornerBitmap(loadedImage));
//            }
//
//            @Override
//            public void onLoadingCancelled(String imageUri, View view) {
//
//            }
//        });
        final BitmapUtils utils = new BitmapUtils(con);

        BitmapDisplayConfig config;
        config = new BitmapDisplayConfig();
//        config.setLoadingDrawable(con.getResources().getDrawable(R.drawable.head_logo));
        config.setLoadFailedDrawable(con.getResources().getDrawable(R.drawable.empty_photo));
        com.lidroid.xutils.bitmap.core.BitmapSize size =
                new com.lidroid.xutils.bitmap.core.BitmapSize(GlobalConfig.scrwid, GlobalConfig.scrhei);
        config.setBitmapMaxSize(size);
        BitmapLoadCallBack loadCallBack = new BitmapLoadCallBack() {
            @Override
            public void onLoadCompleted(View view, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
                if (isneedfadeanim)
                    FadeInDisplay(img, getRoundedCornerBitmap(bitmap));
                else
                    img.setImageBitmap(getRoundedCornerBitmap(bitmap));

            }

            @Override
            public void onLoadFailed(View view, String s, Drawable drawable) {

            }
        };

        utils.display(img, globurl, config, loadCallBack);
    }

    //根据Resources  id
    /*
    * param
    * id
    * R.drawble.empty_Ava;
    * */
    public static void DisplayRoundedCornerImgID(final ImageView img, final int id, boolean isneedfadeanim) {
        final Context con = img.getContext();
        if (isneedfadeanim)
            FadeInDisplay(img, getRoundedCornerBitmap(readBitMap(con, id)));
        else
            img.setImageBitmap(getRoundedCornerBitmap(readBitMap(con, id)));
    }


    /**
     * 得到本地或者网络上的bitmap url - 网络或者本地图片的绝对路径,比如:
     * <p/>
     * A.网络路径: url="http://blog.foreverlove.us/girl2.png" ;
     * <p/>
     * B.本地路径:url="file://mnt/sdcard/photo/image.png";
     * <p/>
     * C.支持的图片格式 ,png, jpg,bmp,gif等等
     *
     * @param path
     * @return
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap GetLocalOrNetBitmap(String path) {
        final String TAG = "GetLocalOrNetBitmap";
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
                be = wi;
            else
                be = hi;

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
        } catch (Exception ex) {
            if (bitmap != null && !bitmap.isRecycled())
                bitmap.recycle();
            System.gc();  //提醒系统及时回收
        }
        return bitmap;
    }


    //同上,最后一个参数可选,配置是否返回高色彩的Bitmap
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    public static Bitmap GetLocalOrNetBitmap(String path, boolean isRGb_565) {
        final String TAG = "GetLocalOrNetBitmap";
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
                be = wi;
            else
                be = hi;

            Log.d(TAG, "缩放比:" + be); //缩放之后
            Log.d(TAG, "原图宽高:" + options.outWidth + " " + options.outHeight); //缩放之后

            if (be <= 0)
                be = 1;
            options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
            if (isRGb_565)
                options.inPreferredConfig = Bitmap.Config.RGB_565;//使用RGB_565后会是内存降低一半但是,不适合decode带有透明区域的PNG
            bitmap = BitmapFactory.decodeFile(path, options);
            int w = bitmap.getWidth();
            int h = bitmap.getHeight();
            Log.d(TAG, "缩放后:宽" + w + " 高" + h); //缩放之后
            Log.d(TAG, "RGB_565格式内存:" + bitmap.getByteCount() / 1024 + "KB");
        } catch (Exception ex) {
            if (bitmap != null && !bitmap.isRecycled())
                bitmap.recycle();
            System.gc();  //提醒系统及时回收
        }
        return bitmap;
    }


    //渐变动画效果显示
    private static final ColorDrawable TRANSPARENT_DRAWABLE = new ColorDrawable(android.R.color.transparent);

    public static void FadeInDisplay(ImageView imageView, Bitmap bitmap) {
        final TransitionDrawable transitionDrawable =
                new TransitionDrawable(new Drawable[]{
                        TRANSPARENT_DRAWABLE,
                        new BitmapDrawable(imageView.getResources(), bitmap)
                });
        imageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(500);
    }


}
