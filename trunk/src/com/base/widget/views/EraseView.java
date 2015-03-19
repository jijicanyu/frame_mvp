package com.base.widget.views;

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
    private Context mContext;
    private int xPos;
    private int yPos;
    private String content = "刮卡区";
    private int is_first=1;
    private int erasecount=0;
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
        if (mCanvas == null) {
            EraseBitmp();
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
        mCanvas.drawPath(path, paint);
        super.onDraw(canvas);
    }

    public void EraseBitmp() {
        bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_4444);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20);
        path = new Path();
        mCanvas = new Canvas(bitmap);
        mCanvas.drawColor(Color.GRAY);
        xPos = (int) (getWidth() -  (int) textPaint.measureText(content))/2;
        yPos = (int) ((getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2));
        mCanvas.drawText(content, xPos, yPos, textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float ax = event.getX();
        float ay = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            isMove = false;
            path.reset();
            path.moveTo(ax, ay);
            invalidate();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            isMove = true;
            erasecount++;
            path.lineTo(ax, ay);
            invalidate();

            return true;
        }
        else if(event.getAction()== MotionEvent.ACTION_UP)
        {
            if(erasecount>=30)
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
