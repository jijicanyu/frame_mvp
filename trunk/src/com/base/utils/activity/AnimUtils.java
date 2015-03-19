package com.base.utils.activity;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;

/**
 * Created by aa on 2014/7/12.
 */
public class AnimUtils {

    public static void startRotation(View v,float start, float end) {
        RotateAnimation myAnimation_Rotate=new RotateAnimation(start, end, Animation.RELATIVE_TO_SELF,0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        myAnimation_Rotate.setDuration(500);
        v.startAnimation(myAnimation_Rotate);
    }

    public static void startScale(View v)
    {
        ScaleAnimation scaleAnimation=new ScaleAnimation(0.25f,0,0.25f,0);
    }

}
