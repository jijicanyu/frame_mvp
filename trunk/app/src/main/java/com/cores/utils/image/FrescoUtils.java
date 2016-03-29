package com.cores.utils.image;

import android.content.res.Resources;
import android.net.Uri;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by wei on 16-3-24.
 */
public class FrescoUtils {

    /**
     * 设置加载中的图
     * @param view
     * @param resId
     */
    public static void setLodingImgRes(SimpleDraweeView view, int resId)
    {
        view.getHierarchy().setFadeDuration(0);
        view.getHierarchy().setPlaceholderImage(resId);
    }

    /**
     * 设置出错默认图
     * @param view
     * @param resId
     */
    public static void setFaildImgRes(SimpleDraweeView view, int resId)
    {
        view.getHierarchy().setFadeDuration(0);
        view.getHierarchy().setFailureImage(view.getResources().getDrawable(resId));
    }


    /**
     * 设置加载中的图和出错的图一致  一次代替上面两个方法
     * @param view
     * @param resId
     */
    public static void setDefaultImgRes(SimpleDraweeView view, int resId)
    {
        view.getHierarchy().setFadeDuration(0);
        view.getHierarchy().setPlaceholderImage(resId);
        view.getHierarchy().setFailureImage(view.getResources().getDrawable(resId));
    }

    /**
     * 给imgview实现圆形功能 但是不带包边
     * @param view
     */
    public static void setViewRound(SimpleDraweeView view)
    {
        RoundingParams roundingParams = new RoundingParams();

        roundingParams.setRoundAsCircle(true);
        roundingParams.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        view.getHierarchy().setRoundingParams(roundingParams);
    }

    /**
     * 给imgview实现圆形功能 并带有包边
     * @param view SimpleDraweeView
     * @param borderDP  border跨度
     * @param colRes 颜色资源
     */
    public static void setViewRound(SimpleDraweeView view, float borderDP, int colRes)
    {
        float border_width=dip2px(borderDP);
        RoundingParams roundingParams = new RoundingParams();
        roundingParams.setRoundAsCircle(true);
        roundingParams.setBorder(colRes,border_width);
        roundingParams.setPadding(border_width);//不设置内边距 不会显示出边框

        //BITMAP_ONLY 是采用BitmapShader的方式 会造成内存抖动 注意  大图注意
        roundingParams.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        view.getHierarchy().setRoundingParams(roundingParams);
        view.setAspectRatio(16.0f/9);
    }

    /**
     * 关掉 view设置bitmap时的动画
     * @param view
     */
    public static void closeAnim(SimpleDraweeView view)
    {
        view.getHierarchy().setFadeDuration(0);
    }


    /**
     * 关掉 view设置bitmap时的动画
     * @param view
     */
    public static void setAspectRatio(SimpleDraweeView view, float ratio)
    {
        view.setAspectRatio(ratio);
    }


    /**
     * 给imgview实现圆形功能
     * @param view
     */
    public static  void setViewCorner(SimpleDraweeView view)
    {
        RoundingParams cornerParams = RoundingParams.fromCornersRadius(7f);
        cornerParams.setOverlayColor(android.R.color.white);

        //BITMAP_ONLY 是采用BitmapShader的方式 会造成内存抖动 注意!!!  大图注意!!!
        cornerParams.setRoundingMethod(RoundingParams.RoundingMethod.BITMAP_ONLY);
        cornerParams.setCornersRadius(dip2px(9));
        view.getHierarchy().setRoundingParams(cornerParams);
    }

    public static float dip2px(float dipValue)
    {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return  (dipValue * scale + 0.5f);
    }

    public static void displayUrl(SimpleDraweeView view , String url)
    {
        view.setImageURI(Uri.parse(url));
    }


}
