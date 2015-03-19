package com.base.utils.view;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;

/**
 * Created by aa on 2014/8/26.
 */
public class ControlerUtils {


    /*
    * 单独给控件关闭硬件加速
    *
    * */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static void closehardwareAccelerated(View view)
    {
        if(view !=null)
            view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    /*
* 给View截图
* ::但是不支持SurfaceView
* */
    public static Bitmap convertViewToBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //上面2行必须加入，如果不加如view.getDrawingCache()返回null
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

}
