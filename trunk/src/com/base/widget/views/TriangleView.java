package com.base.widget.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.mvp.R;

/**
 * Created by aa on 2015/1/12.
 * 画一个三角形  三个点  1.最左上角  2.最左下角  3.右边的中间点;  如图:
 * @*****
 * *****@
 * @*****
 *
 * 用法<com.oki.widget.views.TriangleView
 * xmlns:tri="http://schemas.android.com/apk/res/包名"
 * android:layout_width="12dp"
 * android:layout_height="12dp"
 * tri:tricolor="@color/ios_blue" />
 */
public class TriangleView  extends View {
    //默认颜色是透明的色彩
    int maincolor= Color.TRANSPARENT;
    //是否为等边三角
    boolean isEquilateral=true;


    //tri:tricolor="@color/ios_blue"
    //TypedArray是一个用来存放由context.obtainStyledAttributes获得的属性的数组
    //在使用完成后，一定要调用recycle方法
    //属性的名称是styleable中的名称+“_”+属性名称

    TypedArray array = null;
    public TriangleView(Context context) {
        super(context);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getAttr(context, attrs);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs);
    }

    private void getAttr(Context context, AttributeSet attrs)
    {
        array = context.obtainStyledAttributes(attrs, R.styleable.TriangleViewAttr);
        maincolor = array.getColor(R.styleable.TriangleViewAttr_tricolor, maincolor); //TriangleViewAttr_tricolor 就是TriangleViewAttr.tricolor
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 创建画笔
        Paint p = new Paint();
        p.setColor(maincolor);// 设置红色
        p.setStyle(Paint.Style.FILL);//设置填满

        //获得几个点的数值
        int width=getWidth();
        int height=getHeight();
        int loc_x=getLeft();
        int loc_y=getTop();
        Log.d("TriangleView", width + "  " + height);


        // 绘制这个三角形,你可以绘制任意多边形
        Path path = new Path();
        path.moveTo(0, 0);// 此点为多边形的起点

        if(isEquilateral)
            path.lineTo((height/2)*1.73205f, height/2);   ///这里使用*1.73205f  是因为如果要画一个等边三角形的话需要用这个比例根号三一条直角边是另外一条直角边的 1.73205f 倍。
        else
            path.lineTo(width, height/2);

        path.lineTo(0, height);
        path.close(); // 使这些点构成封闭的多边形
        canvas.drawPath(path, p);
    }


    /**重新设置颜色
     * @param color
     * (0xe96f4a)无效  (0xffe96f4a)这样就可以了
     */
    public void showColor(int color)
    {
        maincolor=color;
        this.invalidate();
    }

}
