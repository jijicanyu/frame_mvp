package com.base.widget;


import android.content.Context;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import com.base.app.BaseActivity;

public class EraseView extends View {

    private boolean isMove = false;
    private Bitmap bitmap = null;
    private Bitmap frontBitmap = null;
    private Path path;
    private Canvas mCanvas;
    private Paint paint;
    private Paint textPaint;
    private String content = "";
    private int is_first=0;
    private int  erasecount=0;
    private Context mContext;
    private float mX,mY;
    public EraseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext=context;
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(38);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        is_first++;
        if (mCanvas == null) {
            EraseBitmp();
        }
        mCanvas.drawPath(path, paint);
        canvas.drawBitmap(bitmap,0,0,null);

    }



    public void EraseBitmp() {
        erasecount=0;
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        paint = new Paint();
        paint.setAlpha(0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(25);
        path = new Path();
        mCanvas = new Canvas(bitmap);
        mCanvas.drawColor(Color.GRAY);
        content="刮卡区";
        int xPos = (int) (getWidth() -  (int) textPaint.measureText(content))/2;
        int yPos = (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        mCanvas.drawText(content, xPos-30, yPos, textPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return touch(event);
    }

    public  boolean touch(MotionEvent event)
    {
        float ax = event.getX();
        float ay = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            path.reset();
            path.moveTo(ax, ay);
            mX=ax;
            mY=ay;
            //((BaseActivity)mContext).findViewById(R.id.gallery).postInvalidate();
            return  true;

        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float dx = Math.abs(ax - mX);
            float dy = Math.abs(ay - mY);
            path.lineTo(dx, dy);
           // path.quadTo(mX, mY, (ax + mX)/2, (ay + mY)/2);
            mX=ax;
            mY=ay;
            mCanvas.drawPath(path, paint);
            path.reset();
            erasecount++;
            //((BaseActivity)mContext).findViewById(R.id.gallery).postInvalidate();
            return  true;
        }
        else
        if(event.getAction()== MotionEvent.ACTION_UP)
        {
            if(erasecount>=35)
            {
                if(mContext  instanceof BaseActivity)
                {
                    //((BaseActivity)mContext).activeGold();
                }
            }
        }


        return super.onTouchEvent(event);
    }




    public Bitmap CreateBitmap(int color, int width, int height) {
        int[] rgb = new int[width * height];

        for (int i = 0; i < rgb.length; i++) {
            rgb[i] = color;
        }
        return Bitmap.createBitmap(rgb, width, height, Config.ARGB_4444);

    }

    @Override
    public  void onDetachedFromWindow()
    {
//        path=null;
//        paint=null;
//        textPaint=null;
        mCanvas=null;
        if(bitmap!=null&&!bitmap.isRecycled())
        {
            bitmap.recycle();
        }
        if(frontBitmap!=null&&!frontBitmap.isRecycled())
        {
            frontBitmap.recycle();
        }

    }

}