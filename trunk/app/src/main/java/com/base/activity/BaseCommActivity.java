package com.base.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.base.presenter.BaseCommPresenter;
import com.base.protocal.http.RequestMsg;
import com.base.view.IBaseCommView;
import com.cores.utils.SystemBarTintManager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mvp.R;
import com.cores.widget.Topbar;

//如果不想要顶部标题栏可以采用先添加后gone的方式
//或者继承的子类里面override  findAButton方法

public abstract class BaseCommActivity<P extends BaseCommPresenter> extends FragmentActivity implements IBaseCommView,OnClickListener {
    //上下弹出隐藏的 activity动画
    final int BOTTOM_ENTER_ANIM = R.anim.bottom_enter;
    final int NORMALDISPLAY = R.anim.normaldisplay;


    //当前activity是否显示
    //不要以onPause onStop作为判断依据 真正的显示隐藏在这两个方法有时候是之前显示有时候是之后
    //2.0好像开始  api开始变动  详细的  东西请参考谷歌的api吧 我也记不清了 草草草!!!
    private boolean isShowThis;

    protected P presenter;

    private Handler mHandler;
    protected static final String REQ_ERR_TOSET_STR="呜呜,请求失败了...";


    /*dialog popupwindow begin  */
    //自定义dlg
    //protected WaitingProgressDialog wating_dialog;
    protected final String wating_txt = "请稍等...";
    /*dialog popupwindow end  */


    //顶部位置的topbar  内部实现了左边按钮的点击销毁的click事件
    Topbar topbar;

    public BaseCommActivity() {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what)
                {
                    default:handlerMsg(msg);
                }

            }
        };

        try {
            //看不懂这句去查资料 一句话说不清
            presenter = getPsClass().newInstance();

        } catch (InstantiationException e) {
            e.printStackTrace();//这个异常通常不会发生 除非你的泛型类型是 Integer Boolean Long 这些
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        presenter.setIView(this);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ViewUtils.inject(this);
        presenter.initData(savedInstanceState);

        setStatusBarRes(R.color.white);
        topbar=(Topbar)findViewById(R.id.topbar);
        initAllWidget();
        presenter.firstShow();
        LogUtils.e("ActivityName==>"+this.getClass().getSimpleName());


}

    @TargetApi(19)
    private void setStatusBarRes(int resid) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();

            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;

            //winParams.flags &= ~bits;

            win.setAttributes(winParams);

            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(resid);//通知栏所需颜色

        }
    }

    protected abstract Class<P> getPsClass();


    //这里只需要fragment的布局文件即可  return R.layout.xxxxxx
    protected abstract int getLayoutId();

    //实例化presenter 实例化之后可以做一些其他操作
    //protected abstract void initPresenter();

    @Override
    public Handler getHandler() {
        return mHandler;
    }

    @Override
    public void handlerMsg(Message msg) {
        //handlermsg  没有在new Handler的时候就进行处理 是为了方式presenter 会有新的presenter 带来的地址变更
        presenter.handMsg(msg);
    }

    //获得这个必要的Intent 用于处理一堆的数据
    @Override
    public Intent getViewIntent() {
        return getIntent();
    }

    @Override
    public Activity getActivity() {
        return BaseCommActivity.this;
    }

    @Override
    public void to(Class<?> cls, Bundle bundles) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundles);
        startActivity(intent);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        isShowThis = hasFocus;//这里才是真正的act显示与隐藏的方法  onPause onStop都不准确的
        if (hasFocus) {
            presenter.onWindowShow();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }



    // 不要继承这个 继承于clickView
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                clickView(view);
        }
    }

    //实例化一些控件 包含控件的一些事件绑定的问题
    abstract  protected void initAllWidget();


    protected abstract void clickView(View v);


    @Override  //利用重写这个方法设置新页面打开的动画
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }


    /**
     * 清理资源
     */
    @Override
    public synchronized void clearResource() {
        if (this != null) {
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            finish();
        }
    }


    //获取这个类的名字的字符串  如:LoginActivity
    public String getClassName() {
        return this.getClass().getSimpleName();
    }


    @Override
    public void hideProgressBar() {
    }


    @Override
    public void showProgressBar() {

    }




    @Override
    protected void onDestroy() {
        hideProgressBar();
        super.onDestroy();
        presenter.onDestory();
    }


    public void toast(String str) {
        Toast.makeText(getActivity(), "" + str, Toast.LENGTH_LONG).show();
    }


    public void to(Class<?> cls) {
        to(cls, new Bundle());
    }

    /**
     * 跳转页面
     *
     * @param context
     * @param cls
     */
    public void to(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }


    protected void bindClickView(int viewId) {

    }

    @Override
    public boolean isVisible() {
        return isShowThis;
    }

    @Override
    public void onBackPressed() {
        hideProgressBar();
        super.onBackPressed();
        clearResource();
    }
    public Topbar getTopbar()
    {
        return topbar;
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                return true;
        }
        return super.dispatchKeyEvent(event);
    }

    /*
     * 响应按键函数
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK: {
                hideProgressBar();
                clearResource();
                return true;
            }
        }
        return false;
    }


    /**
     * 开始数据请求
     * @param req
     * @param resCls
     */
    protected  void sendHttpPost(RequestMsg req,final Class<?> resCls)
    {
        //HttpTool.requestPost(req,resCls,getHandler());
    }
}