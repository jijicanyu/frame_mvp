package com.base.widget.views.animationview;

import android.animation.LayoutTransition;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 带动画的view  调用AddView时触发动画
 */
public class AnimFrameLayout extends FrameLayout {

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public AnimFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutTransition transition = new LayoutTransition();
        this.setLayoutTransition(transition);
    }

}