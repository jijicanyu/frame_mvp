package com.base.widget.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * Created by aa on 14-4-12.
 */
public class RaspcardGallery extends Gallery {

    public RaspcardGallery(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private boolean isScrollingLeft(MotionEvent e1, MotionEvent e2) {
        return e2.getX() > e1.getX();
    }

    @Override
    public boolean onFling(MotionEvent e1,MotionEvent e2, float velocityX,
                           float velocityY) {
        int keyCode;
        //这样能够实现每次滑动只滚动一张图片的效果
        if (isScrollingLeft(e1,e2)) {
            keyCode= KeyEvent.KEYCODE_DPAD_LEFT;
        }else{
            keyCode= KeyEvent.KEYCODE_DPAD_RIGHT;
        }
        onKeyDown(keyCode,null);
        return true;
    }
}