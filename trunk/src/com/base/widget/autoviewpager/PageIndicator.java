package com.base.widget.autoviewpager;

import android.support.v4.view.ViewPager;

/**
 * Created by aa on 2015/1/5.
 */
public abstract interface PageIndicator extends ViewPager.OnPageChangeListener
{
    public abstract void notifyDataSetChanged();

    public abstract void setCurrentItem(int paramInt);

    public abstract void setOnPageChangeListener(ViewPager.OnPageChangeListener paramOnPageChangeListener);

    public abstract void setViewPager(ViewPager paramViewPager);

    public abstract void setViewPager(ViewPager paramViewPager, int paramInt);
}
