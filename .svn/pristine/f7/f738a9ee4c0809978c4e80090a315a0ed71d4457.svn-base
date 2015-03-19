package com.base.widget.views;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * ScrollView反弹效果的实现
 *
 * @author RZMars
 *
 */
public class BounceScrollView extends ScrollView {
    /**
     *
     */
    private View childView;
    private float curY;
    private Rect childViewRect = new Rect();
    private boolean isFirst = false;

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public BounceScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
    }

    private View getChildView() {
        if (getChildCount() > 0) {
            childView = getChildAt(0);
        }
        return childView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (getChildView() != null) {
            myOnTouchEvent(ev);
        }

        return super.onTouchEvent(ev);
    }

    public void myOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    restoreAnimation();

                }
                break;

            case MotionEvent.ACTION_MOVE:
                final float preY = curY;// 按下时的y坐标
                float nowY = ev.getY();// 时时y坐标
                int deltaY = (int)(preY-nowY);// 滑动距离
                if (!isFirst) {
                    deltaY = 0;
                }
                curY = nowY;
                // 当滚动到最上或者最下时就不会再滚动，这时移动布局
                if (isNeedToMove()) {
                    // 初始化头部矩形
                    if (childViewRect.isEmpty()) {
                        // 保存当前（也是正常）的布局位置， 作为以后以便于移动位置的参考坐标
                        childViewRect.set(childView.getLeft(), childView.getTop(),
                                childView.getRight(), childView.getBottom());
                    }

                    // 移动布局
                    childView.layout(childView.getLeft(), childView.getTop()
                                    - deltaY / 2, childView.getRight(),
                            childView.getBottom() - deltaY / 2);
                }
                isFirst = true;
                break;

            default:
                break;
        }
    }

    public void restoreAnimation() {
        TranslateAnimation ta = new TranslateAnimation(0, 0,
                childView.getTop(), childViewRect.top);
        ta.setDuration(200);
        childView.startAnimation(ta);
        childView.layout(childViewRect.left, childViewRect.top,
                childViewRect.right, childViewRect.bottom);

        childViewRect.setEmpty();
        isFirst = false;
    }

    public boolean isNeedAnimation() {
        return !childViewRect.isEmpty();
    }

    public boolean isNeedToMove() {
        // inner.getMeasuredHeight():获取的是控件的总高度;getHeight()：获取的是屏幕的高度
        int offset = childView.getMeasuredHeight()-getHeight();
        int scrollY = getScrollY();
        // 0是顶部，后面那个是底部
        if (scrollY <= 0 || scrollY >= offset) {
            return true;
        }
        return false;

    }

}