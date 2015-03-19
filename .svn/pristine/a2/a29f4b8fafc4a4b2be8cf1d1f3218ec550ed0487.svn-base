package com.base.widget.autoviewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * Created by aa on 2015/1/5.
 * AutoScrollViewPager  所需要的Scroller
 */

public class CustomDurationScroller extends Scroller {

    //自动滚动的 倍率
    private double scrollFactor = 15;
    private Context context;

    public CustomDurationScroller(Context context) {
        super(context);
    }

    public CustomDurationScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    /**
     * not exist in android 2.3
     *
     * @param context
     * @param interpolator
     * @param flywheel
     */
    // @SuppressLint("NewApi")
    // public CustomDurationScroller(Context context, Interpolator interpolator,
    // boolean flywheel){
    // super(context, interpolator, flywheel);
    // }

    /**
     * Set the factor by which the duration will change
     */
    public void setScrollDurationFactor(double scrollFactor) {
        this.scrollFactor = scrollFactor;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, (int) (duration * scrollFactor));
    }
}
