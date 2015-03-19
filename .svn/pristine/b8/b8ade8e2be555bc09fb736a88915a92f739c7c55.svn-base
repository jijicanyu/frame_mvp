package com.base.widget.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.base.utils.CommonUtil;
import com.base.utils.image.DrawableUtil;
import com.mvp.R;

public class MyProgressDialog {


//      1.文字在下方   MyProgressDialog.show(guidanceMainActivity.this, true, false);
//      2.文字在右方   MyProgressDialog.show(guidanceMainActivity.this, true, true);

    /**
     * 显示一个等待框
     * <p/>
     * //@param context上下文环境
     * //@param isCancel是否能用返回取消
     * //@param isRighttrue文字在右边false在下面
     */


    public static void show(Context context, boolean isCancel, boolean isRight) {
        creatDialog(context, "", isCancel, isRight);
    }

    Dialog loadingDialog;

    public void setCancel(boolean isCancel) {
        loadingDialog.setCancelable(isCancel);// 是否可以用返回键取消
    }

    public MyProgressDialog(Context context)//使用new方法默认创建一个文字在下方的等待提示框
    {
        int dp_px = (int) CommonUtil.dip2px(context, 1);//计算1dp的像素数
        String msg = "";//显示的文字
        boolean isCancel = true;//默认可以取消
        boolean isRight = false;
        LinearLayout.LayoutParams wrap_content = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        //图标的大小
        LinearLayout.LayoutParams img_content = new LinearLayout.LayoutParams(30 * dp_px, 30 * dp_px);
        LinearLayout main = new LinearLayout(context);
        //main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.loading_bg));
        main.setBackgroundColor(Color.WHITE);
        if (isRight) {
            main.setOrientation(LinearLayout.HORIZONTAL);
            wrap_content.setMargins(10 * dp_px, 0, 35 * dp_px, 0);
            img_content.setMargins(35 * dp_px, 25 * dp_px, 0, 25 * dp_px);
        } else {
            main.setOrientation(LinearLayout.VERTICAL);
            wrap_content.setMargins(10 * dp_px, 5 * dp_px, 10 * dp_px, 15 * dp_px);
            img_content.setMargins(35 * dp_px, 25 * dp_px, 35 * dp_px, 0);
        }
        main.setGravity(Gravity.CENTER);
        ImageView spaceshipImage = new ImageView(context);
        spaceshipImage.setImageResource(R.drawable.publicloading);
        TextView tipTextView = new TextView(context);
        tipTextView.setTextColor(0xFFFFFFFF);
        tipTextView.setText("请稍候...");
// 加载旋转动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
// 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (msg != null && !"".equals(msg))
            tipTextView.setText(msg);// 设置加载信息,否则加载默认值
        loadingDialog = new Dialog(context, R.style.noneblackbg_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(isCancel);// 是否可以用返回键取消
        main.addView(spaceshipImage, img_content);
        main.addView(tipTextView, wrap_content);
        LinearLayout.LayoutParams fill_parent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setContentView(main, wrap_content);// 设置布局
        //loadingDialog.show();
    }

    public MyProgressDialog(Context context, String msg)//功能同上,但是显示的文字可选
    {
        int fivedp_px = (int)CommonUtil.dip2px(context, 5);//计算1dp的像素数
        boolean isCancel = true;//默认可以取消
        boolean isRight = true;
        LinearLayout.LayoutParams wrap_content = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams img_content = new LinearLayout.LayoutParams(
                6 * fivedp_px, 6 * fivedp_px);
        LinearLayout main = new LinearLayout(context);
        //main.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.loading_bg));
        main.setBackgroundColor(Color.TRANSPARENT);
        if (isRight) {
            main.setOrientation(LinearLayout.HORIZONTAL);
            wrap_content.setMargins(2 * fivedp_px, 0, 7 * fivedp_px, 0);
            img_content.setMargins(1 * fivedp_px, fivedp_px,fivedp_px, fivedp_px);
        } else {
            main.setOrientation(LinearLayout.VERTICAL);
            wrap_content.setMargins(2 * fivedp_px, fivedp_px, 2 * fivedp_px, 3 * fivedp_px);
            img_content.setMargins(2 * fivedp_px, fivedp_px*3/2, 2 * fivedp_px,  fivedp_px/2);
        }
        main.setGravity(Gravity.CENTER);
        ImageView spaceshipImage = new ImageView(context);
        DrawableUtil.DisplayImgID(spaceshipImage, R.drawable.publicloading);
        TextView tipTextView = new TextView(context);
        tipTextView.setTextColor(0xFFFFFFFF);
        tipTextView.setText("请稍候...");
// 加载旋转动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
// 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (msg != null && !"".equals(msg))
            tipTextView.setText(msg);// 设置加载信息,否则加载默认值
        loadingDialog = new Dialog(context, R.style.noneblackbg_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(isCancel);// 是否可以用返回键取消
        main.addView(spaceshipImage, img_content);
        main.addView(tipTextView, wrap_content);
        loadingDialog.setContentView(main, wrap_content);// 设置布局
        //loadingDialog.();
    }

    public void show() {
        try {
            loadingDialog.show();
        } catch (Exception e) {
        }
    }

    public void hide() {
        try {
            loadingDialog.hide();
        } catch (Exception e) {
        }
    }


    /**
     * 显示一个等待框
     * // context上下文环境
     * // msg等待框的文字
     * //@param isCancel是否能用返回取消
     * //@param isRighttrue文字在右边false在下面
     */
    public static void show(Context context, String msg, boolean isCancel, boolean isRight) {
        creatDialog(context, msg, isCancel, isRight);
    }

    private static void creatDialog(Context context, String msg, boolean isCancel, boolean isRight) {

        int dp_px = (int)CommonUtil.dip2px(context, 1);//计算1dp的像素数
        LinearLayout.LayoutParams wrap_content = new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams img_content = new LinearLayout.LayoutParams(
                30 * dp_px, 30 * dp_px);
        LinearLayout main = new LinearLayout(context);
        main.setBackgroundColor(Color.WHITE);
        if (isRight) {
            main.setOrientation(LinearLayout.HORIZONTAL);
            wrap_content.setMargins(10 * dp_px, 0, 35 * dp_px, 0);
            img_content.setMargins(35 * dp_px, 25 * dp_px, 0, 25 * dp_px);
        } else {
            main.setOrientation(LinearLayout.VERTICAL);
            wrap_content.setMargins(10 * dp_px, 5 * dp_px, 10 * dp_px, 15 * dp_px);
            img_content.setMargins(35 * dp_px, 25 * dp_px, 35 * dp_px, 0);
        }
        main.setGravity(Gravity.CENTER);
        ImageView spaceshipImage = new ImageView(context);
        spaceshipImage.setImageResource(R.drawable.publicloading);
        TextView tipTextView = new TextView(context);
        tipTextView.setText("请稍候...");
// 加载旋转动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context,
                R.anim.loading_animation);
// 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (msg != null && !"".equals(msg))
            tipTextView.setText(msg);// 设置加载信息,否则加载默认值
        Dialog loadingDialog = new Dialog(context, R.style.noneblackbg_dialog);// 创建自定义样式dialog
        loadingDialog.setCancelable(isCancel);// 是否可以用返回键取消
        main.addView(spaceshipImage, img_content);
        main.addView(tipTextView, wrap_content);
        loadingDialog.setContentView(main, wrap_content);// 设置布局
        loadingDialog.show();
    }
}