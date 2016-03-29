package com.widgets.bugfixview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by aa on 2014/9/3.
 */
public class FixRowListView extends ListView {
    public FixRowListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FixRowListView(Context context) {
        super(context);
    }

    public FixRowListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}