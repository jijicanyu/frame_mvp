package com.base.widget.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.*;
import android.view.animation.Transformation;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.TextView;
import com.mvp.R;

import java.util.Timer;
import java.util.TimerTask;

public class GalleryFlow extends Gallery
{

    public  int  maxindex=0;
    /**
     * The camera class is used to 3D transformation matrix.
     */
    private Camera mCamera = new Camera();
    private int maxRotation = 40;
    private  float unselectedScale= (float) 0.75;

    private float scaleDownGravity = (float)0.5;
    /**
     * The max rotation angle.
     */
    private int mMaxRotationAngle = 60;

    /**
     * The max zoom value (Z axis).
     */
    private int mMaxZoom = -90;
    public static final int ACTION_DISTANCE_AUTO = 20;

    /**
     * The center of the gallery.
     */
    private int mCoveflowCenter = 0;
    private int roll=0;
    private Timer timer=new Timer();
    private Context aContext;
    private int centerwidth=0;
    public GalleryFlow(Context context)
    {
        this(context, null);
    }

    public GalleryFlow(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
        aContext=context;

        timer = new Timer(true);

    }

    @TargetApi(Build.VERSION_CODES.ECLAIR_MR1)
    public GalleryFlow(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        aContext=context;
        // Enable set transformation.
        this.setStaticTransformationsEnabled(true);
        // Enable set the children drawing order.
        this.setChildrenDrawingOrderEnabled(true);
    }

    public int getMaxRotationAngle()
    {
        return mMaxRotationAngle;
    }

    public void setMaxRotationAngle(int maxRotationAngle)
    {
        mMaxRotationAngle = maxRotationAngle;
    }

    public int getMaxZoom()
    {
        return mMaxZoom;
    }

    public void setMaxZoom(int maxZoom)
    {
        mMaxZoom = maxZoom;
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i)
    {
        // Current selected index.
        int selectedIndex = getSelectedItemPosition() - getFirstVisiblePosition();
        if (selectedIndex < 0)
        {
            return i;
        }

        if (i < selectedIndex)
        {
            return i;
        }
        else if (i >= selectedIndex)
        {
            return childCount - 1 - i + selectedIndex;
        }
        else
        {
            return i;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        mCoveflowCenter = getCenterOfCoverflow();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private int getCenterOfView(View view)
    {
        return view.getLeft() + view.getWidth() / 2;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t)
    {
        //super.getChildStaticTransformation(child, t);

        final int childCenter = getCenterOfView(child);
        final int childWidth  = child.getWidth();

        int rotationAngle = 0;
        t.clear();
        t.setTransformationType(Transformation.TYPE_MATRIX);

        // If the child is in the center, we do not rotate it.
        if (childCenter == mCoveflowCenter)
        {
            transformImageBitmap(child, t, 0);
            centerwidth=child.getWidth();
        }
        else
        {
            // Calculate the rotation angle.
            rotationAngle = (int)(((float)(mCoveflowCenter - childCenter) / childWidth) * mMaxRotationAngle);

            // Make the angle is not bigger than maximum.
            if (Math.abs(rotationAngle) > mMaxRotationAngle)
            {
                rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;
            }

            transformImageBitmap(child, t, rotationAngle);
        }

        return true;
    }

    private int getCenterOfCoverflow()
    {
        return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
    }

    private void transformImageBitmap(View child, Transformation t, int rotationAngle)
    {
        mCamera.save();
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            child.invalidate();
        }

        final int coverFlowWidth = this.getWidth();
        final int coverFlowCenter = coverFlowWidth / 2;
        final int childWidth = child.getWidth();
        final int childHeight = child.getHeight();
        final int childCenter = child.getLeft() + childWidth / 2;
        final Matrix imageMatrix = t.getMatrix();
        final int actionDistance =  ACTION_DISTANCE_AUTO;

        // Calculate the abstract amount for all effects.
        final float effectsAmount = Math.min(1.0f, Math.max(-1.0f, (1.0f / actionDistance) * (childCenter - coverFlowCenter)));

        // Apply rotation.
        if (this.maxRotation != 0) {
            rotationAngle = (int) (-effectsAmount * this.maxRotation);
            //this.mCamera.save();
            this.mCamera.rotateY(rotationAngle);
            this.mCamera.getMatrix(imageMatrix);
            //this.mCamera.restore();
        }

        // Zoom.
        if (this.unselectedScale != 1) {
            final float zoomAmount = (this.unselectedScale - 1) * Math.abs(effectsAmount) + 1;
            // Calculate the scale anchor (y anchor can be altered)
            final float translateX = childWidth / 2.0f;
            final float translateY = childHeight * this.scaleDownGravity;
            imageMatrix.preTranslate(-translateX, -translateY);
            imageMatrix.postScale(zoomAmount, zoomAmount);
            imageMatrix.postTranslate(translateX, translateY);
        }
        mCamera.restore();
    }


    private static final int timerAnimation = 1;
    private static final int timerfinish= 2;
    private final Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case timerAnimation:
                    onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                    //抓取GalleryFlow
                    //GalleryFlow g= (GalleryFlow)((BaseActivity)aContext).findViewById(R.id.gallery);
                    GalleryFlow g=null;
                    if (g.getSelectedItemPosition()==12){
                        timer.cancel();
                        mHandler.sendEmptyMessage(timerfinish);
                    }
                    break;
                case timerfinish:
                    //CreateEraseView();
                    break;
                default:
                    break;
            }
        };
    };
    private final TimerTask task = new TimerTask()
    {
        public void run()
        {
            mHandler.sendEmptyMessage(timerAnimation);

//            if (roll==20){
//
//            }
        }
    };

    private void CreateEraseView()
    {
        LayoutInflater mInflater = LayoutInflater.from(aContext);
        View eraseview= mInflater.inflate(R.layout.item_eraseview, null);
        ViewGroup.LayoutParams layoutParams=this.getLayoutParams();
//        eraseview.setMinimumHeight(100);
//        eraseview.setMinimumWidth(300);
//        eraseview.setLayoutParams(layoutParams);
        TextView txt=(TextView)eraseview.findViewById(R.id.out_txt2);
        txt.setText("10个金币");
//        GalleryFlow g= (GalleryFlow)((GuaguaActivity)aContext).findViewById(R.id.gallery);
//        int w=dip2px(aContext,150);
//        int h=dip2px(aContext,370);
//        int h2=dip2px(aContext,50);
//        DisplayMetrics displayMetric = aContext.getResources().getDisplayMetrics();
//        int screenWidth = displayMetric.widthPixels;
//        int left= (getWidth()- centerwidth)/2;
//        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)eraseview.getLayoutParams();
//        params.setMargins(left, h, left, 0);// 通过自定义坐标来放置你的控件
//        params.width=200;
//        params.height=200;
//        ((GuaguaActivity)aContext).addContentView(eraseview,params);


        //抓取GalleryFlow
        //GalleryFlow g= (GalleryFlow)((BaseActivity)aContext).findViewById(R.id.gallery);

        GalleryFlow g=null;
        int ddd=g.getChildCount();
        ViewGroup.LayoutParams layoutParams2;
       // ((GuaguaActivity)aContext).addContentView(eraseview,  g.getChildAt(1).findViewById(R.id.eraseView1).getLayoutParams());

        FrameLayout.LayoutParams lp=new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.FILL_PARENT);
        eraseview.setLayoutParams(lp);
        //RelativeLayout rl= (RelativeLayout) g.getChildAt(2).findViewById(R.id.eraselayout);
        int d=g.getCount();

        //g.getChildAt(2).findViewById(R.id.eraseView1).setVisibility(INVISIBLE);

      //  RelativeLayout rl=(RelativeLayout)g.getChildAt(2).findViewById(R.id.eraselayout);
        //rl.addView(eraseview);
//        eraseview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return  v.onTouchEvent(event);
//            }
//        });
    }

    public static int dip2px(Context context, float dipValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dipValue * scale + 0.5f);
    }


    public void starttimer()
    {
        timer.schedule(task, 150, 100);
    }
    public void destroytimer()
    {
        timer.cancel();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

}
