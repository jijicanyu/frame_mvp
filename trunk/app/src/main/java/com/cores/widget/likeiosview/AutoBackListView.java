package com.widgets.likeiosview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ListView;

/**
 * Created by aa on 2014/8/16.
 * //自动回弹ListView
 *
 */

public class AutoBackListView extends ListView
{
    private static final int MAX_Y_OVERSCROLL_DISTANCE = 200;
    private Context mContext;
    private int mMaxYOverscrollDistance;

    public AutoBackListView(Context context)
    {
        super(context);
        mContext = context;
        initBounceListView();
    }

    public AutoBackListView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        mContext = context;
        initBounceListView();
    }

    public AutoBackListView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        mContext = context;
        initBounceListView();
    }

    private void initBounceListView()
    {
        //获取屏幕尺寸和去计算最大的距离

        final DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        final float density = metrics.density;

        mMaxYOverscrollDistance = (int) (density * MAX_Y_OVERSCROLL_DISTANCE);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent)
    {
        //奇迹发生,我们已经取代了传入maxOverScrollY mMaxYOverscrollDistance自定义变量;
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxYOverscrollDistance, isTouchEvent);
    }

    @Override  //包括修复android   ListView  自带的  高度Bug
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}