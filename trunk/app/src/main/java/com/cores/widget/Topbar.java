package com.cores.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mvp.R;

/**
 * Created by aa on 2015/6/25.
 * 不加注释了 就这个几个方法没得说了
 */
public class Topbar extends RelativeLayout implements View.OnClickListener {
    Context context;

    //顶部一些控件 begin
    TextView txt_btn_back;
    TextView txt_title;
    TextView txt_btn_right;
    ImageView img_right;
    //顶部一些控件 end

    public Topbar(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public Topbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public Topbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.layout_header, this);
        txt_btn_back =(TextView) findViewById(R.id.txt_btn_back);
        txt_title = (TextView) findViewById(R.id.txt_title);
        img_right = (ImageView) findViewById(R.id.img_right);
        txt_btn_right = (TextView) findViewById(R.id.txt_btn_right);
        txt_btn_back.setOnClickListener(this);
    }


    public void setTitle(String title) {
        txt_title.setText("" + title);
    }

    public void setRightImg(int resId) {
        img_right.setImageDrawable(context.getResources().getDrawable(resId));
        img_right.setVisibility(View.VISIBLE);
    }

    public void setRightText(String str_right) {
        txt_btn_right.setText(str_right);
    }

    public void setRightClickListener(OnClickListener listener) {
        img_right.setOnClickListener(listener);
        txt_btn_right.setOnClickListener(listener);
    }
    public int getRightTxtId()
    {
        return R.id.txt_btn_right;
    }
    public int getRightImgId()
    {
        return R.id.img_right;
    }

    public TextView getRightTextView()
    {
        return txt_btn_right;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_btn_back: {
                if (context instanceof Activity) {

                    //避免bug  代码需要加判断  而不是用try 来逃避bug
                    if (!((Activity) context).isFinishing())
                        ((Activity) context).finish();
                }
            }
            break;
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            findViewById(R.id.txt_btn_back).performClick();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}