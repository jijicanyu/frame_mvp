package com.base.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * Created by aa on 2015/6/18.
 *
 * 再多的言语无法解释这个类
 */
public interface IBaseCommView {

    ////获得一个Frag或者Act的handler  用于消息处理
    public Handler getHandler();

    //获得一个Intent  用户获得一些由上一个页面传过来的 一些数据
    public Intent getViewIntent();

    //获得这个Context上下文
    public Activity getActivity();

    //当前IView是否显示
    public boolean isVisible();

    public void handlerMsg(Message msg);


    //
    public void toast(String str_msg);


    //让页面跳转并传一堆数据
    public void to(Class<?> cls, Bundle bundles);

    public void clearResource();


    public void hideProgressBar();


    public void showProgressBar();

}
