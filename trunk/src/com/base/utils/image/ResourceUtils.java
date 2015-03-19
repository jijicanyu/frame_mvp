package com.base.utils.image;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.ImageView;
import com.base.config.GlobalConfig;

import java.io.InputStream;

/**
 * Created by aa on 2014/11/27.
 *
 * 谷歌官方推荐的 高效省内存加载resource的方法  但是效果感觉不怎么好
 */
public class ResourceUtils {


    //将assets文件中资源取出,并将图片从bitmap转换成drawable格式
    public static Bitmap getDrawableFromAssetFile(Context context, String fileName) {
        Bitmap image = null;
        BitmapDrawable drawable = null;
        try {
            AssetManager am = context.getAssets();
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            drawable = new BitmapDrawable(context.getResources(), image);
            is.close();
        } catch (Exception e) {
        }
        return drawable.getBitmap();
    }

    /**
     * 获得一个Resource的宽高
     * @param context
     * @param id
     * @return
     */
    public static final Size getResourceSize(Context context,int id)
    {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), id, options);
        int imageHeight = options.outHeight;
        int imageWidth = options.outWidth;
        Size size=new  Size(imageWidth,imageHeight);
        return size;
    }


    /**
     * 计算size
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static  final int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    /**
     * 谷歌官方推荐
     * @param res
     * @param resId
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static  final Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // 第一次decode时 设置inJustDecodeBounds=true
        // 这样不返回Bitmap只是先获得,尺寸大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);//将这个Resource的一些状态传给option
        if(reqWidth<2||reqHeight<2) {
            reqWidth=options.outWidth;
            reqHeight=options.outHeight;
        }
        if(reqWidth>GlobalConfig.scrwid)
            reqWidth=GlobalConfig.scrwid;
        if(reqHeight>GlobalConfig.scrhei)
            reqHeight=GlobalConfig.scrhei;

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inPreferredConfig = Bitmap.Config.RGB_565;//内存消耗降低一半
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 以节省内存的方式,给指定的ImageView显示一个指定的id的drawable
     * * @param context
     * @param imgview  图片控件
     * @param drawableid 指定资源的图片
     */
    public static final void  DisplayID(ImageView imgview,int drawableid)
    {
        imgview.setImageBitmap(decodeSampledBitmapFromResource(imgview.getContext().getResources(),drawableid,imgview.getWidth(),imgview.getHeight()));
        Log.d("图片控件尺寸:", imgview.getWidth() + "  " + imgview.getHeight());
    }

}
