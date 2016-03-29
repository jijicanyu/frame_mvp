package com.cores.utils.image;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.cores.FrameApplication;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.mvp.R;

import java.io.InputStream;

/**
 * Created by app computer on 2015/4/9.
 */
public class XutilsLoderUtils {

    private static BitmapDisplayConfig roundConfig=new BitmapDisplayConfig();
    private static BitmapDisplayConfig cornerConfig=new BitmapDisplayConfig();
    private static BitmapDisplayConfig newconfig=new BitmapDisplayConfig();
    private static PorterDuffXfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    /* 消除锯齿重要参数，两个一起用效果才好。最好开启activity的硬件加速，但是开启硬件加速 内存消耗就会增加。
    PaintFlagsDrawFilter pfd = new PaintFlagsDrawFilter(0,Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
    canvas.setDrawFilter(pfd);   paint.setAntiAlias(true)；
    */
    private static PaintFlagsDrawFilter pdf = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

    private static BitmapLoadCallBack roundCallback=new BitmapLoadCallBack() {
        @Override
        public void onLoadCompleted(View view, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
            ((ImageView)view).setImageBitmap(toRoundBitmap(bitmap));
        }

        @Override
        public void onLoadFailed(View view, String s, Drawable drawable) {
            ((ImageView)view).setImageDrawable(drawable);
        }
    };

    public static void init(Context context)
    {
        //只是修改了faild  图片  其他不修改 理论上来说 其他配置应该是使用全局的配置
        roundConfig.setLoadFailedDrawable(context.getResources().getDrawable(R.drawable.empty_avatar));
        cornerConfig.setLoadFailedDrawable(context.getResources().getDrawable(R.mipmap.empty_logo));
        newconfig.setLoadFailedDrawable(context.getResources().getDrawable(R.mipmap.empty_logo));
        newconfig.setLoadingDrawable(context.getResources().getDrawable(R.mipmap.empty_logo));
    }


    //圆角的弧度  现在给的是4  数值小 圆角小就越大
    private static BitmapLoadCallBack cornerCallback =new BitmapLoadCallBack() {
        @Override
        public void onLoadCompleted(View view, String s, Bitmap bitmap, BitmapDisplayConfig bitmapDisplayConfig, BitmapLoadFrom bitmapLoadFrom) {
            ((ImageView)view).setImageBitmap(getRoundedCornerBitmap(bitmap,4));
        }

        @Override
        public void onLoadFailed(View view, String s, Drawable drawable) {
            ((ImageView)view).setImageDrawable(drawable);
        }
    };

    //显示圆形图片
    public static void displayRoundImg(ImageView img,String url)
    {
        FrameApplication.bitmapUtils.display(img,url,roundConfig,roundCallback );
    }



    //正常显示图片
    public static void displayImg(ImageView img,String url)
    {
        FrameApplication.bitmapUtils.display(img, url);
    }



    //正常显示图片
    public static void displayAppImg(ImageView img,String url)
    {
        FrameApplication.bitmapUtils.display(img, url,newconfig);
    }


    //显示圆角图片
    public static void displayCornerImg(ImageView img,String url)
    {
        FrameApplication.bitmapUtils.display(img, url,cornerConfig, cornerCallback);
    }



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
     * 获取圆角Bitmap图片
     *
     * @param bitmap ：原图片
     * @return：切圆角后的图片  圆弧的宽度roundper 小于等于2都是圆形
     */
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,int roundper) {
        float roundPx = 10;
        if (null == bitmap || bitmap.isRecycled()) {
            return null;
        }

        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                    bitmap.getWidth(), Bitmap.Config.ARGB_8888);
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
            roundPx = bitmap.getWidth() / roundper;//此处如除以2了  则就是圆图了  就不是圆角了
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(xfermode);
            canvas.drawBitmap(bitmap, rect, rect, paint);
            // bitmap.recycle();
            return output;
        } catch (Exception e) {
            Log.e("ImagersUtils",
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
            Bitmap output = Bitmap.createBitmap(ovalLen, ovalLen, Bitmap.Config.ARGB_8888);
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

}
