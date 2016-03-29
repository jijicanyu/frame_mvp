package com.cores.widget;

/**
 * Created by aa on 2014/9/14.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/*
* 保持最原始风格的progressbar  只是添加了一个百分比
*
* */
public class ShowPercentProgressBar extends ProgressBar {
    String text;
    Paint mPaint;

    public ShowPercentProgressBar(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        onPaint();
    }

    public ShowPercentProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        onPaint();
    }

    public ShowPercentProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        onPaint();
    }

    @Override
    public synchronized void setProgress(int progress) {
        // TODO Auto-generated method stub
        setText(progress);
        super.setProgress(progress);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        Rect rect = new Rect();
        this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
        int x = (getWidth() / 2) - rect.centerX();
        int y = (getHeight() / 2) - rect.centerY();
        canvas.drawText(this.text, x, y, this.mPaint);
    }

    //初始化，画笔
    private void onPaint() {
        this.mPaint = new Paint();
        this.mPaint.setTextSize(24);
        this.mPaint.setColor(Color.WHITE);
    }

    //设置文字内容
    private void setText(int progress) {
        int i = (progress * 100) / this.getMax();
        this.text = String.valueOf(i) + "%";
    }

}