package com.base.presenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.base.protocal.http.RequestMsg;
import com.base.view.IBaseCommView;

/**
 * Created by aa on 2015/6/18.
 *
 * 这是一个基类由于项目使用观察者模式
 * Perseter中尽量避免操作UI 所以UI操作都要放到IView中进行操作
 */
public abstract class BaseCommPresenter<V extends IBaseCommView> {

    protected V iView;

    public BaseCommPresenter()
    {
        //必要的时候请重写 这个默认构造函数  在这里加一些方法

        //很不幸现在框架只会执行默认构造函数 不会执行带参数构造函数
    }

    /**
     *  构造函数之后必须要设置setIView  否则后面一些东东西没法操作
     *  iv 是Activity
     */
    public void setIView( V iv)
    {
        iView=iv;
    }

    abstract  public void handMsg(Message msg);


    //实例化一些数据  可以根据bundle参数是否为空  非空则可以选择性的恢复一些数据  保存会在saveData中保存
    abstract  public void initData(Bundle saveInstnce);

    //save数据不强制要求重写 就没abstract
    public void saveData(Bundle saveInstnce){
        /*

        这里保存的时候使用了key  但是 key请不要拼写一堆的 "isuodafefjds"  这种字符串
        请使用规范的写法 在save之前写几个  static final String KEY_IS_UPDALOAD="isupload";
        像这样子  这样的好处就是  initData的时候直接使用的key如果拼错了IDE会自动给你指出来
        你如果使用"isupload"  就算你拼写错了IDE会认为你这是两个字符串不会给你指出错误

        */
    }

    protected Handler getHandler()
    {
        return iView.getHandler();
    }



    //几个必要的生命周期 用于一些数据必要的释放 begin
    public void onResume()
    {}
    public void onPause()
    {}
    public void onDestory()
    {}//destory的时候请选择性的释放一些数据
    //activity可见的时候
    public void onWindowShow() {}
    //几个必要的生命周期 用于一些数据必要的释放 end


    /**
     * 开始数据请求
     * @param req
     * @param resCls
     */
    protected  void sendHttpPost(RequestMsg req,final Class<?> resCls)
    {
        //HttpTool.requestPost(req, resCls, iView.getHandler());
    }

    //view层的act frag的控件都绑定完毕之后执行这个方法
    public void firstShow(){}


}
