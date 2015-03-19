package com.base.protocal.ServiceHttpCom;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.base.protocal.BaseJsonResponseMsg;
import com.base.protocal.RequestMsg;
import com.base.protocal.ResponseMsg;
import com.base.protocal.message.activitymsg.UpLoadingMSGRes;
import com.base.utils.encoder.AESKEYCoder;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.StringEntity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 开启后台异步请求的服务
 */
public class AsyHttpService extends Service {
    //网络请求最大线程池的数量
    private static final int MAX_THREAD_POOL_LEN=4;
    //网络线程池  使用线程池的最大优势在于  不用每次都启动一个线程  不断新建线程回收线程是耗时操作
    ExecutorService fixedThreadPool = Executors.newFixedThreadPool(MAX_THREAD_POOL_LEN);

    private LocalBroadcastManager localBroadcastManager;
    private final String TAG="AsyHttpService";

    @Override
    public IBinder onBind(Intent intent) {
        localBroadcastManager = LocalBroadcastManager.getInstance(AsyHttpService.this);
        //绑定成功返回Binder对象
        return new HttpBinder();
    }

    /**
     * 内部类用于返回代理人给Activity进行操作
     */
    public class HttpBinder extends Binder implements HttpCommonInterface {
        @Override
        public void sendHttp(RequestMsg req, ResponseMsg res) {
            sendHttpMsg(req, res);
        }
        @Override
        public void sendResponse(ResponseMsg responseMsg) {
            sendBordRes(responseMsg);
        }
    }


    /**
     * 向服务器发送请求
     */
    public void sendHttpMsg(RequestMsg request, ResponseMsg response) {
        try {
            //旧的方法 容易导致空线程太多
            //HttpMsgThread httpthread = new HttpMsgThread(request, response);
            //httpthread.start();

            //新的方法 线程数量有限制 超过时则等待
            fixedThreadPool.execute(createHttpRunable(request, response));

        } catch (Exception e) {
            Log.e(this.getClass().getName(), "", e);
            // throw new RuntimeException(e);
        }
    }


    /**
     * 创建一个httprunnable  用于存入线程池
     * @param request
     * @param response
     * @return
     */
    private Runnable createHttpRunable(final RequestMsg request, final ResponseMsg response)
    {
        return new Runnable() {
            @Override
            public void run() {
                int outtimems = 6 * 1000;
                try {
                    //取得post服务器路径
                    String url = request.getUrl();
                    //取得加密的json数据
                    String json = request.createRequest();
                    Log.d(TAG + "请求->:" + request.getClass().getSimpleName(), json);
                    final String reqString = AESKEYCoder.encrypt(json);
                    //如果json为空,则退出
                    if (null == reqString)
                        return ;

                    //请求参数
                    RequestParams params = new RequestParams();
                    if (request.isAvatarUpload()) {//如果是上传头像
                        //params.addBodyParameter("headImg", new File(request.getUploadFilePath()));
                        //params.addBodyParameter("userId", GlobalConfig.userInfo.getGuidid()+"");
                        outtimems = 20 * 10000;
                        Set<Map.Entry<String, Object>> entries = request.getHashMapParams().entrySet();
                        for (Map.Entry<String, Object> entry : entries) {
                            String name = entry.getKey();
                            Object value = entry.getValue();
                            if (StringUtils.isBlank(name))
                                continue;
                            params.addQueryStringParameter(name, value.toString());
                        }
                        params.addBodyParameter("headImg", new File(request.getUploadFilePath()));

                    } else if (request.isHasFileUpload())//如果是上传文件
                    {
                        outtimems = 20 * 10000;
                        Set<Map.Entry<String, Object>> entries = request.getHashMapParams().entrySet();
                        for (Map.Entry<String, Object> entry : entries) {
                            String name = entry.getKey();
                            Object value = entry.getValue();
                            if (StringUtils.isBlank(name))
                                continue;

                            params.addQueryStringParameter(name, value.toString());
                        }
                        String[] imglsit = request.getUploadFilePath().split(",");

                        for (int i = 0; i < imglsit.length; i++) {
                            if (StringUtils.isNotBlank(imglsit[i])) {
                                params.addBodyParameter("serverImg", new File(imglsit[i]));
                            }
                        }
                    } else   //如果不是上传文件,则设置请求的内容为加密后的json数据,并且设置编码
                    {
                        try {
                            params.setBodyEntity(new StringEntity(reqString, "utf-8"));
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }


                    //生成网络请求utils,并开始请求
                    final HttpUtils http = new HttpUtils();
                    http.configTimeout(outtimems);
                    http.send(HttpRequest.HttpMethod.POST,
                            url,
                            params,
                            new RequestCallBack<String>() {

                                @Override//请求成功返回数据
                                public void onSuccess(ResponseInfo<String> stringResponseInfo) {
                                    //sendHttpMsg();//请求 队列排队用的
                                    ((BaseJsonResponseMsg) response).setStrResult(stringResponseInfo.result + "");//直接将密文传进来  不在这里进行解密
                                    sendBordRes(response);

                                }

                                @Override  //请求失败
                                public void onFailure(HttpException error, String failuremsg) {
                                    //sendHttpMsg();//请求 队列排队用的
                                    response.setNetWorkError(true);
                                    response.setErrInfo(AESKEYCoder.encrypt("请求超时，请检查网络连接情况"));
                                    sendBordRes(response);
                                }

                                @Override  //返回上传数据的百分比  *isUploading  用于判断是否是开始上传的时候的相应loading  因为有时候只是上传开始而已
                                public void onLoading(long total, long current, boolean isUploading) {
                                    if ((request.isHasFileUpload() || request.isAvatarUpload())&& isUploading) {
                                        //计算得出百分比
                                        long percentage = 100 * current / total;

                                        Log.d("收到 百分比", "loading" + percentage);

                                        UpLoadingMSGRes res = new UpLoadingMSGRes();
                                        res.actname = response.actname;
                                        res.setPercentageAndEypt(percentage);
                                        sendBordRes(res);

                                    }
                                    super.onLoading(total, current, isUploading);
                                }

                                @Override
                                public void onStart() {
                                    super.onStart();
                                }
                            }
                    );
                } catch (Exception e) {
                    Log.e(this.getClass().getName(), "", e);
                }
            }
        };



    }


    //发送广播
    private void sendBordRes(ResponseMsg responseMsg) {

        String responsestr = ((BaseJsonResponseMsg) responseMsg).getStrResult();
        //发送特定action的广播
        Intent intent = new Intent();
        intent.setAction("android.intent.action." + responseMsg.actname);
        intent.putExtra("msgno", responseMsg.getMsgno());
        intent.putExtra("HttpMessage", "" + responsestr);//这里 为了数据传输安全性  发送广播的时候发的是未解密的数据 这样破解这个appp的时候 用广播抓接收器数据抓到的还是加密的数据
        localBroadcastManager.sendBroadcast(intent);
        //使用localBroadcastManager  将广播范围强制限制在应用程序内部  同时提高广播的性能  当然上面传输密文就没有安全意义了

        Log.d(TAG + "收到->: " + responseMsg.getClass().getSimpleName(), AESKEYCoder.decrypt(responsestr) + "");//
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }



}
