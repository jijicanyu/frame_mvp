package com.base.protocal.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.cores.utils.FastJSONHelper;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.apache.http.entity.StringEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by aa on 2015/6/19.
 * <p/>
 * 使用Xutils的数据请求,只不过重新封装了一层
 * 有些东西没有配置  比如编码  还有超时时间 httputil都有默认的值  详情就看 源码吧 我不多说了 看源码
 * 默认超时时间为 private static final int DEFAULT_CONN_TIMEOUT = 15000;
 */
public class HttpTool {


    /**
     * 创建一个CallBack对象  由于get post请求最后使用的callback都是一样的 所以单独抽出来了这段代码
     * @param cls
     * @param handler
     * @param msgwhat
     * @return
     */
    private static final  RequestCallBack createCallBack(final Class<?> cls, final Handler handler, final int msgwhat)
    {
        RequestCallBack mCallback = new RequestCallBack() {
            @Override
            public void onSuccess(ResponseInfo responseInfo) {
                Object bean = FastJSONHelper
                        .parseToObject((String) responseInfo.result, cls);

                Message msg = Message.obtain();
                msg.what = msgwhat;
                msg.obj = bean;
                Log.d("RequestCallBack",""+responseInfo.result);
                handler.sendMessage(msg);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("createCallBack",e.getMessage());
                sendHandError(handler,msgwhat);
            }
        };
        return mCallback;
    }


    /**
     *
     * 发送错误消息 可以自定义 所以这里单独  抽出来
     * 这里没有做复杂的操作但是还是抽出来了 方便后期修改
     * @param handler
     * @param what
     */
    private static void sendHandError(final Handler handler,int what)
    {
        Message msg = Message.obtain();
        msg.what =what;

        /*try {//一堆简单的操作非得傻逼似的写一堆操作
            obj = (ResponseMsg)cls.newInstance();
            obj.setMsg("网络出错");
            obj.setResult(ERROR_RESULT);
            msg.obj = obj;
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }*/

        handler.sendMessage(msg);
    }




    /**
     * 一个get请求  完全面向对象的实现方式 req res 都封装好的
     *
     * @param req  req里面千万别带含有File类型的value  get请求无法提交bitmap  除非你特么转byte[]转base64
     * @param cls
     * @param handler
     *                <p/>
     *                没有设置超时时间 默认情况下  有一个超时时间的  不用去管
     */
    public static void requestGet(final RequestMsg req, final Class<?> cls, final Handler handler) {
        //1.实现Params
        RequestParams mRequestParams = new RequestParams();
        for (Map.Entry<String, Object> mEntry : req.getParamEntry()) {
            String mEntryKey = mEntry.getKey();
            Object mEntryValue = mEntry.getValue();
            if (TextUtils.isEmpty(mEntryKey))
                continue;

            mRequestParams.addQueryStringParameter(mEntryKey, mEntryValue.toString());
        }

        //2.实现callback
        RequestCallBack mCallback=createCallBack(cls, handler, req.getResPonseMsgWhat());

        //3.别再废话了赶紧发送请求吧
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, req.getUrl(), mRequestParams, mCallback);
    }


    /**  ======这个方法可以提交file到服务器=====
     *
     * 一个post请求  完全面向对象的实现方式 req res 都封装好的
     *
     * @param req
     * @param cls
     * @param handler
     *                <p/>
     *                没有设置超时时间 默认情况下  xutils 有一个超时时间的  不用去管  具体时间是多少  请看源码
     */
    public static void requestPost(final RequestMsg req, final Class<?> cls, final Handler handler) {
        //1.实现Params
        RequestParams mRequestParams = new RequestParams();
        for (Map.Entry<String, Object> mEntry : req.getParamEntry()) {
            String mEntryKey = mEntry.getKey();
            Object mEntryValue = mEntry.getValue();
            if (TextUtils.isEmpty(mEntryKey))
                continue;
            if (mEntryValue instanceof File) {//如果包含图片上传的类型 File自动会执行这句
                mRequestParams.addBodyParameter(mEntryKey, (File) mEntryValue);
            } else {
                mRequestParams.addQueryStringParameter(mEntryKey, mEntryValue.toString());
            }
        }

        //2.实现callback
        RequestCallBack mCallback=createCallBack(cls,handler,req.getResPonseMsgWhat());

        //3.别再废话了赶紧发送请求吧
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, req.getUrl(), mRequestParams, mCallback);
    }



    /**
     * 一个post请求  完全面向对象的实现方式 req res 都封装好的
     *
     * @param req   req不可含有File类型的Value
     * @param cls
     * @param handler
     * @param msgwhat 消息机制的回调
     *                <p/>
     *                没有设置超时时间 默认情况下  有一个超时时间的  不用去管
     */
    public static void requestPostJson(final RequestMsg req, final Class<?> cls, final Handler handler, final int msgwhat){
        //1.实现Params
        RequestParams mRequestParams = new RequestParams();

        try {
            mRequestParams.setBodyEntity(new StringEntity(req.getJsonBody(), "utf-8"));

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            //这里如果出错 我擦  肯定是你传File类型进来了 导致他无法encode 草草草
        }

        //2.实现callback
        RequestCallBack mCallback=createCallBack(cls,handler,msgwhat);

        //3.别再废话了赶紧发送请求吧
        HttpUtils http = new HttpUtils();
        http.send(HttpRequest.HttpMethod.POST, req.getUrl(), mRequestParams, mCallback);
    }


}
