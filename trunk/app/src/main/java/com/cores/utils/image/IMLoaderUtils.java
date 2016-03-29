package com.cores.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.base.protocal.http.HttpConfig;
import com.mvp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by weizongwei on 15-11-24.
 */
public class IMLoaderUtils {



    public static DisplayImageOptions idimg_display_options = new DisplayImageOptions.Builder()
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
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();

    public static DisplayImageOptions avaimg_display_options = new DisplayImageOptions.Builder()
            .showImageForEmptyUri(R.drawable.empty_avatar)
            .showImageOnFail(R.drawable.empty_avatar)
            .cacheInMemory(true)
            .cacheOnDisc(true)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    /*
    * 用于显示网络图片或者本地图片,杜绝OOM
    * @param con
    *   :Activity
    *
    *
    * 用法  getBitmapUtils(getThis).display(img, "http://img1.gtimg.com/news/pics/hv1/63/26/1451/94357968.jpg", config);
    * getBitmapUtils(getThis).display(img, "file://sdcard0/94357968.jpg", config);
    * */
    public static void display(final ImageView img, final String url) {
        if (TextUtils.isEmpty(url)) return;
        final Context con = img.getContext();
        String globurl = HttpConfig.HTTP_SERVER_FOR_IMAGE + url;
        ImageLoader.getInstance().displayImage(globurl, img, new AnimateFirstDisplayListener());
    }

    /*
* 产生一个BitmapUtils对象,用于显示网络图片或者本地图片,杜绝OOM
* @param con
*   :Activity
* @param  img
* 图片控件
* @param url
*图片路径
*   @param isround  是否显示圆角
* */
    public static void DisplayHttpheadImg(final ImageView img, final String url) {
        if (TextUtils.isEmpty(url)) return;
        final Context con = img.getContext();
        final String notheadurl = url.substring(6, url.length());

        String globurl = url;
        //用ImageLoader显示

        ImageLoader.getInstance().displayImage(globurl, img, new AnimateFirstDisplayListener());
    }


    public static void DisplayImgAndFade(final ImageView img, final String url) {

        if (TextUtils.isEmpty(url)) return;
        String globurl = HttpConfig.HTTP_SERVER_FOR_IMAGE + url;
        final Context con = img.getContext();
        //用ImageLoader显示

        ImageLoader.getInstance().displayImage(globurl, img, new AnimateFirstDisplayListener());
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
        if (TextUtils.isEmpty(url)) return;
        final Context con = img.getContext();
        String globurl = HttpConfig.HTTP_SERVER_FOR_IMAGE + url;
        ImageLoadingListener imglisen = new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                img.setImageBitmap(DrawableUtil.readBitMap(con, R.drawable.round_empty_avatar));
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

                img.setImageBitmap(DrawableUtil.toRoundBitmap(loadedImage));
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        };

        //用ImageLoader显示
        ImageLoader.getInstance().displayImage(globurl, img, avaimg_rounddisplay_options, imglisen);

    }


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

}
