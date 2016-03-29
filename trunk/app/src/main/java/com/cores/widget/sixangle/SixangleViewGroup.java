package com.cores.widget.sixangle;

/**
 * Created by aa on 2014/8/23.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class SixangleViewGroup extends ViewGroup {

    private static final int SPACE = 10;// view��view֮��ļ��

    public SixangleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int lenght = (int) (getWidth() / 2.5) - SPACE;// ÿ����VIEW�ĳ���

        double radian30 = 30 * Math.PI / 180;
        float h = (float) (lenght * Math.cos(radian30));
        int bottomSpace = (int) ((lenght - 2 * h) / 2);// ��������ײ��ļ��

        int offsetX = lenght * 3 / 4 + SPACE;// X��ÿ��ƫ�Ƶĳ���
        int offsetY = lenght / 2;// Y��ÿ��ƫ�Ƶĳ���

        int rowIndex = 0;//���±�
        int childCount = 3;
        int tempCount = 3;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(i == childCount){
                rowIndex++;

                if(tempCount - 1 <= 0){
                    tempCount = 3;
                }else{
                    tempCount --;
                }
                childCount += tempCount;
            }

            int startL = i % 3 * offsetX;
            int startT = i % 3 * offsetY;
            if(tempCount == 1){
                startL -= offsetX;
                startT -= offsetY;
            }

            child.layout(startL, startT + rowIndex * lenght,
                    startL + lenght, startT + lenght
                            + rowIndex * lenght - bottomSpace);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure((int) (getWidth() / 2.5), (int) (getWidth() / 2.5));
        }
    }

}