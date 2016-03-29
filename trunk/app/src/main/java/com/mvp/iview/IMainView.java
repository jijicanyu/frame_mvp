package com.mvp.iview;

/**
 * Created by wei on 15-10-29.
 */

import com.base.view.IBaseCommView;

/**
 * MainActivity的View
 */
public interface IMainView extends IBaseCommView {

    //在原有IBaseView 的初上加一个方法  要求MainActivity必须重载这个方法
    public void showMsg();
}
