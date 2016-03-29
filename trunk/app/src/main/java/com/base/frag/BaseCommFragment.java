package com.base.frag;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.base.presenter.BaseCommPresenter;
import com.base.protocal.http.RequestMsg;
import com.base.view.IBaseCommView;
import com.cores.utils.SystemBarTintManager;
import com.cores.widget.Topbar;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.mvp.R;

//如果不想要顶部标题栏可以采用先添加后gone的方式
//或者继承的子类里面override  findAButton方法

public abstract class BaseCommFragment<P extends BaseCommPresenter> extends Fragment implements IBaseCommView, OnClickListener {
    //上下弹出隐藏的 activity动画
    final int BOTTOM_ENTER_ANIM = R.anim.bottom_enter;
    final int NORMALDISPLAY = R.anim.normaldisplay;


    protected P presenter;

    private Handler mHandler;
    protected static final String REQ_ERR_TOSET_STR = "呜呜,请求失败了...";


    /*dialog popupwindow begin  */
    //自定义dlg
    //protected WaitingProgressDialog wating_dialog;
    protected final String wating_txt = "请稍等...";
    /*dialog popupwindow end  */


    //顶部位置的topbar  内部实现了左边按钮的点击销毁的click事件
    @ViewInject(R.id.topbar)
    Topbar topbar;
    private Activity attachActivity;

    public BaseCommFragment() {

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    default:
                        handlerMsg(msg);
                }

            }
        };


        try {
            presenter = getPsClass().newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


        presenter.setIView(this);
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 基类必须要重写getLayoutId() 请看下面 ||||||||
        View rootView = inflater.inflate(getLayoutId(), container, false);
        ViewUtils.inject(this, rootView);

        presenter.initData(savedInstanceState);
        initAllWidget(rootView);
        return rootView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.firstShow();
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
        return getActivity().getIntent();
    }


    @Override
    public void to(Class<?> cls, Bundle bundles) {
        Intent intent = new Intent(getAttachActivity(), cls);
        intent.putExtras(bundles);
        startActivity(intent);
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {

        presenter.onPause();
        super.onPause();
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
    abstract protected void initAllWidget(View view);

    protected abstract void clickView(View v);



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
    public void clearResource()
    {
        getAttachActivity().finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hideProgressBar();
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


    /**
     * 开始数据请求
     *
     * @param req
     * @param resCls
     */
    protected void sendHttpPost(RequestMsg req, final Class<?> resCls) {
        //HttpTool.requestPost(req, resCls, getHandler());
    }

    @Override
    public void onAttach(Activity activity) {
        attachActivity = activity;
        super.onAttach(activity);
    }

    public Activity getAttachActivity() {
        return attachActivity;
    }
}