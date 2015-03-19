package com.base.utils.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by aa on 2014/10/10.
 */
public class AnimatUtils {

    //向下显示控件的动画  并且显示控件
   public static void ShowTranslateAnim(final View v)
   {
       if(v.getVisibility()== View.VISIBLE)//如果当前已经显示则不再显示动画
        return ;
       v.setVisibility(View.VISIBLE);
       TranslateAnimation anim= new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
               Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
               -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
       anim.setDuration(500);
       anim.setAnimationListener(new Animation.AnimationListener() {
           @Override
           public void onAnimationStart(Animation animation) {

           }

           @Override
           public void onAnimationEnd(Animation animation) {
           }

           @Override
           public void onAnimationRepeat(Animation animation) {

           }
       });
       v.startAnimation(anim);

   }

    //向上隐藏控件的动画  并且隐藏控件
    public static void HideTranslateAnim(final View v)
    {
        if(v.getVisibility()== View.GONE)//如果当前已经隐藏则不再显示动画
            return ;
        TranslateAnimation anim= new TranslateAnimation(Animation.RELATIVE_TO_SELF,
            0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
            -1.0f);
        anim.setDuration(500);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(anim);

    }
}
