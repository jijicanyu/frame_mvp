package com.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cores.utils.image.GlideLoderUtils;
import com.mvp.R;
import com.widgets.wigetmodel.ADInfoModel;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 花大量的时间重构了这个自定义控件  caocaocao
 */
public class AutoScrollPageView extends RelativeLayout {

    private static final int DEFUAL_CUR_DRAW = R.drawable.round_markcurrent;
    private Drawable cur_draw;
    private static final int DEFUAL_NOR_DRAW = R.drawable.round_marknormal;
    private Drawable nor_draw;
    private static final int DEFUAL_INDICATOR_WIDTH_DP =  4 * 2;
    private int indicator_width_px;

    private static final int DEFUALT_WATTINGTIME_MS = 5000;
    private int wating_loop_ms;
    private static final int DEFUALT_ROLLDURATION_MS = 300;
    private int duration_ms;

    private static final float DEFUAL_ADV_IMG_RATIO = 2.00f;
    private double adv_img_ratio;

    private Context context;

    private ViewPager advPager = null;

    // indicator
    private ImageView[] indicator = null;

    // 展示图片的页码（一排点标志展示哪张图）
    private ViewGroup group;

    private AtomicInteger atomicInteger = new AtomicInteger(0);

    private boolean isOnTouch = false;
    boolean isPause = false;

    //是否无限滚动
    boolean isMaxPoll =false;

    private static final boolean DEFUAL_ADV_MAXPOLL = false;

    List<ADInfoModel> datalist;

    Timer timer;

    private static final int MSG_SET_CUR = 0x000231;

    //虽然我讨厌再加一个这个handler 因为我已经写了timertask
    //但是 timertask无法操作view  所以必须加这个handler 看了代码好烦的caocaocaocao
    Handler handler_ui;

    public AutoScrollPageView(Context context) {
        super(context);
        this.context = context;

        initDefualtAutoView();
    }


    public AutoScrollPageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initAutoView(attrs);
    }

    public AutoScrollPageView(Context context, AttributeSet attrs, int defsty) {
        super(context, attrs,defsty);
        this.context = context;

        initAutoView(attrs);
    }

    public void initAutoView(AttributeSet attrs) {

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.auto_vplayout, this, true);

        advPager = (ViewPager) findViewById(R.id.view_pager);
        group = (LinearLayout) findViewById(R.id.point_group);

        // 设置加速度
        // 通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间;）
        try {



            TypedArray obtainStyled = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollPageView);

            cur_draw = obtainStyled.getDrawable(R.styleable.AutoScrollPageView_indicatorcurrentdrawerble);
            if (cur_draw == null) {
                cur_draw = getResources().getDrawable(DEFUAL_CUR_DRAW);
            }
            nor_draw = obtainStyled.getDrawable(R.styleable.AutoScrollPageView_indicatornormaldrawerble);
            if (nor_draw == null) {
                nor_draw = getResources().getDrawable(DEFUAL_NOR_DRAW);
            }

            isMaxPoll=obtainStyled.getBoolean(R.styleable.AutoScrollPageView_maxpoll, DEFUAL_ADV_MAXPOLL);

            adv_img_ratio=obtainStyled.getFloat(R.styleable.AutoScrollPageView_advimg_ratio, DEFUAL_ADV_IMG_RATIO);
            indicator_width_px=(int)dip2px(context,DEFUAL_INDICATOR_WIDTH_DP);
            indicator_width_px =
                    obtainStyled.getDimensionPixelSize(
                            R.styleable.AutoScrollPageView_indicatorwidth,
                            indicator_width_px
                    );

            wating_loop_ms =
                    obtainStyled.getInt(R.styleable.AutoScrollPageView_wattingtime_ms, DEFUALT_WATTINGTIME_MS);
            duration_ms =
                    obtainStyled.getInt(R.styleable.AutoScrollPageView_rollduration_ms, DEFUALT_ROLLDURATION_MS);

            //好吧 这个代码好贱 强制使用反射  给一个字段赋值
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);

            FixedSpeedScroller mScroller = new FixedSpeedScroller(advPager.getContext(), new AccelerateInterpolator());
            mField.set(advPager, mScroller);

            mScroller.setmDuration(duration_ms);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initDefualtAutoView() {

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.auto_vplayout, this, true);
        advPager = (ViewPager) findViewById(R.id.view_pager);
        group = (LinearLayout) findViewById(R.id.point_group);

        // 设置加速度
        // 通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间;）
        try {

            cur_draw = getResources().getDrawable(DEFUAL_CUR_DRAW);
            nor_draw = getResources().getDrawable(DEFUAL_NOR_DRAW);

            adv_img_ratio=DEFUAL_ADV_IMG_RATIO;
            indicator_width_px = (int)dip2px(context,DEFUAL_INDICATOR_WIDTH_DP);
            wating_loop_ms = DEFUALT_WATTINGTIME_MS;
            duration_ms = DEFUALT_ROLLDURATION_MS;

            //好吧 这个代码好贱 强制使用反射  给一个字段赋值
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);


            FixedSpeedScroller mScroller = new FixedSpeedScroller(advPager.getContext(), new AccelerateInterpolator());
            mScroller.setmDuration(duration_ms);

            mField.set(advPager, mScroller);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setPhotoData(final List<ADInfoModel> photolist) {
        datalist = photolist;
        if (photolist == null || photolist.size() == 0)
            return;

        List<View> advPics = new ArrayList<View>();
        indicator = new ImageView[photolist.size()];
        group.removeAllViews();

        if(!isMaxPoll) {

            //不是无限滚动的情况下  不要设置这个  设置这个个之后会带来白屏

            advPager.setOffscreenPageLimit(photolist.size() - 1);
        }

        for (int i_adv_index = 0; i_adv_index < photolist.size(); i_adv_index++) {

            final ADInfoModel adInfoModel = photolist.get(i_adv_index);

            advPics.add(getAdvImgAndSaveRatio(adv_img_ratio, adInfoModel));


            indicator[i_adv_index] = getIndicatorImgV(indicator_width_px);
            group.addView(indicator[i_adv_index]);

            if (i_adv_index == 0) {
                indicator[i_adv_index].setBackgroundDrawable(cur_draw);
            } else {
                indicator[i_adv_index].setBackgroundDrawable(nor_draw);
            }

        }

        advPager.setOnPageChangeListener(new GuidePageChangeListener());
        advPager.setAdapter(new AdvAdapter(advPics));
        if(isMaxPoll)//如果需要无限滚动  先往后滚动   让用户可以往左无限滚动
        {
            advPager.setCurrentItem(advPics.size()*100);
        }
        advPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    {
                        pauseAutoRoll();
                        isOnTouch = true;//在触摸中}
                    }
                        break;
                    case MotionEvent.ACTION_MOVE: {

                    }
                    break;
                    case MotionEvent.ACTION_UP: {
                        isOnTouch = false;//不在触摸中
                        resumeAutoRoll();
                    }
                    break;
                }
                return false;
            }
        });

        startAutoRoll();
    }


    /**
     * @param d_wh_ratio  限制宽高比
     * @param adInfoModel
     * @return
     */
    private ImageView getAdvImgAndSaveRatio(double d_wh_ratio, final ADInfoModel adInfoModel) {
        ImageView img = new FixHeiImageView(context, d_wh_ratio);
        LayoutParams param = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        img.setLayoutParams(param);
        GlideLoderUtils.displayDrawId(img, adInfoModel.id);

        img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


            }

        });

        return img;
    }

    private ImageView getIndicatorImgV(int px_radius) {
        ImageView imageView = new ImageView(context);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(px_radius, px_radius);
        params.setMargins(px_radius/4, px_radius/8, px_radius/4, px_radius/8);

        imageView.setLayoutParams(params);

        return imageView;
    }


    public void startAutoRoll() {
        if (timer != null) {
            timer.cancel();
        }

        //写这个handler的原因看顶部
        handler_ui = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case MSG_SET_CUR: {

                        if(advPager.getAdapter().getCount()> ((Integer) msg.obj)) {
                            advPager.setCurrentItem((Integer) msg.obj);
                            Log.d(getClass().getSimpleName(),"ScrollPage==>"+msg.obj);
                        }
                    }
                    break;
                }
            }
        };

        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                //滚动的条件数据长度有效 不在暂停中 不在触摸中  才能滚动
                if (datalist != null && datalist.size() > 1 && !isPause && !isOnTouch) {
                    if (advPager.getCurrentItem() == datalist.size() - 1 && !isMaxPoll) {
                        int i_index = 0;

                        Message msg = Message.obtain();
                        msg.what = MSG_SET_CUR;
                        msg.obj = i_index;
                        handler_ui.sendMessage(msg);
                    } else {
                        int i_index = advPager.getCurrentItem() + 1;

                        Message msg = Message.obtain();
                        msg.what = MSG_SET_CUR;
                        msg.obj = i_index;
                        handler_ui.sendMessage(msg);
                    }
                }

            }
        };


        timer.schedule(timerTask, wating_loop_ms, wating_loop_ms);
    }


    public void stopAutoRoll()
    {
        if(timer !=null ) {

            timer.cancel();
        }
    }

    public void pauseAutoRoll()//停止翻页
    {
        isPause = true;
    }


    public void resumeAutoRoll()//恢复翻页
    {
        isPause = false;
    }

    private final class AdvAdapter extends PagerAdapter {
        private List<View> views = null;

        public AdvAdapter(List<View> views) {
            this.views = views;
        }

        @Override
        public void destroyItem(View arg0, int position, Object arg2) {
            Log.d("AUTOSCROLL destroyItem",position+"");
            ((ViewPager) arg0).removeView(views.get(position % views.size()));
        }

        @Override
        public void finishUpdate(View arg0) {

        }

        @Override
        public int getCount() {
            if(isMaxPoll)
                return Integer.MAX_VALUE;
            else
                return datalist==null?0:datalist.size();
        }

        @Override
        public Object instantiateItem(View arg0, int position) {
            try
            {
                ((ViewPager) arg0).addView(views.get(position % views.size()), 0);
            }catch(Exception e){
            //handler something
            }

        return views.get(position % views.size());
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {

        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {

        }


    }


    private final class GuidePageChangeListener implements OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int position) {
            position=position % datalist.size();
            atomicInteger.getAndSet(position);
            for (int i = 0; i < indicator.length; i++) {
                indicator[position].setBackgroundDrawable(cur_draw);
                if (position != i) {
                    indicator[i].setBackgroundDrawable(nor_draw);
                }
            }

        }

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        // 父容器传过来的宽度方向上的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        // 父容器传过来的高度方向上的模式
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 父容器传过来的宽度的值
        int width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingLeft()
                - getPaddingRight();
        // 父容器传过来的高度的值
        int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom()
                - getPaddingTop();

        height = (int) (width/adv_img_ratio + 0.5f);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    public static float dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dipValue * scale + 0.5f);
    }
}