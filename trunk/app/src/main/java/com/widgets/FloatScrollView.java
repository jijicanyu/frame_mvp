package com.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

import com.mvp.R;

/**
 * Created by weizongwei on 15-10-14.
 */
public class FloatScrollView extends ScrollView {

    //需要浮动区域的顶部的view的高度  这里强制要求浮动区域的控件往上到达ScrollView  必须得是一个View
    View mFloatView;
    //代替展示浮动view
    View mFlowView;

    int flowid;
    int floatid;

    public FloatScrollView(Context context) {
        super(context);
    }

    public FloatScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public FloatScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initView(attrs);
    }

    private void initView(AttributeSet attrs)
    {
        TypedArray obtainStyled = getContext().obtainStyledAttributes(attrs, R.styleable.FloatScrollView);
        flowid=obtainStyled.getResourceId(R.styleable.FloatScrollView_copyid,0);
        floatid=obtainStyled.getResourceId(R.styleable.FloatScrollView_floatid, 0);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        mFloatView = getRootView().findViewById(floatid);
        mFlowView = getRootView().findViewById(flowid);
        super.onDraw(canvas);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {

        super.onScrollChanged(l, t, oldl, oldt);



        if(mFloatView != null && mFlowView!=null) {

            if(t >= mFloatView.getHeight()) {
                mFlowView.setVisibility(View.VISIBLE);
            } else {
                mFlowView.setVisibility(View.GONE);
            }
        }
    }
}
