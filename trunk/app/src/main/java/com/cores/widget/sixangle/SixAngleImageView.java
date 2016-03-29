package com.cores.widget.sixangle;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.ImageView;


//六边形ImageView  中间位置可以显示一个Img

public class SixAngleImageView extends ImageView implements OnClickListener
{
    private final String TAG = "";
    private Context ctx;
    private float radiusSquare = 0;
    private float edgeLength = 0;
    private float centerX,centerY;
    private Paint mPaint;
    private Path mPath;
    private int defaultColor = Color.argb(0x60, 0, 0, 0);
    private int highColor = Color.argb(0x60, 0, 0, 0xff);

    public SixAngleImageView(Context context) {
        super(context);
        init(context);
    }

    public SixAngleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context)
    {
        ctx = context;
        setClickable(true);
        setOnClickListener(this);
        mPath = new Path();

        getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() {
                edgeLength = ((float)getWidth())/2;
                radiusSquare = edgeLength*edgeLength*3/4;
                centerX = edgeLength;
                centerY = ((float)getHeight())/2;

                mPath.moveTo(getWidth(), getHeight()/2);
                mPath.lineTo(getWidth()*3/4, 0);
                mPath.lineTo(getWidth()/4, 0);
                mPath.lineTo(0, getHeight()/2);
                mPath.lineTo(getWidth()/4, getHeight());
                mPath.lineTo(getWidth()*3/4, getHeight());
                mPath.close();

                return true;
            }
        });

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Style.FILL);
        mPaint.setColor(defaultColor);
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick:tag=" + getTag().toString());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()== MotionEvent.ACTION_DOWN)
        {
            mPaint.setColor(highColor);
            invalidate();
        }
        else if(event.getAction()== MotionEvent.ACTION_UP)
        {
            mPaint.setColor(defaultColor);
            invalidate();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float dist = (event.getX()-centerX)*(event.getX()-centerX)+(event.getY()-centerY)*(event.getY()-centerY);
        if(dist > radiusSquare)
        {
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        super.onDraw(canvas);
    }
}