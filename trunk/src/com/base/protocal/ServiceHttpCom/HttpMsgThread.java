package com.base.protocal.ServiceHttpCom;

/**
 * Created by aa on 2015/1/29.
 */

import android.util.Log;
import com.base.protocal.BaseJsonResponseMsg;
import com.base.protocal.RequestMsg;
import com.base.protocal.ResponseMsg;
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

/**
 * 独立HTTP传输线程  从BaseActivity中复制去过来的代码
 */
public class HttpMsgThread extends Thread {
    final String TAG="HttpMsgThread";
    private RequestMsg request;
    private ResponseMsg response;

    public HttpMsgThread(RequestMsg request, ResponseMsg response) {
        this.request = request;
        this.response = response;
    }

    public void run() {  //开始
        synchronized (this) {
            int outtimems = 8 * 1000;
            try {
                this.setName("Thread pr (" + response.getMsgname() + ")");

                //取得post服务器路径
                String url = request.getUrl();
                //取得加密的json数据
                String json = request.createRequest();
                Log.d(TAG + "请求->:" + request.getClass().getSimpleName(), json);
                final String reqString = AESKEYCoder.encrypt(json);
                //如果json为空,则退出
                if (null == reqString)
                    return;

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
                                //sendBordRes(response);

                            }

                            @Override  //请求失败
                            public void onFailure(HttpException error, String failuremsg) {
                                //sendHttpMsg();//请求 队列排队用的
                                response.setNetWorkError(true);
                                response.setErrInfo(AESKEYCoder.encrypt("请求超时，请检查网络连接情况"));
                                //sendBordRes(response);
                            }

                            @Override  //返回上传数据的百分比  *isUploading  用于判断是否是开始上传的时候的相应loading  因为有时候只是上传开始而已
                            public void onLoading(long total, long current, boolean isUploading) {
                                if ((request.isHasFileUpload() || request.isAvatarUpload())) {
                                    //计算得出百分比
                                    //long percentage = 100 * current / total;

//                                        UpLoadingMSGRes res = new UpLoadingMSGRes();
//                                        res.actname = response.actname;
//                                        res.setPercentageAndEypt(percentage);
//                                        sendBordRes(res);
                                    Log.d("收到 百分比", "loading" + 100 * current / total);
                                }
                                super.onLoading(total, current, isUploading);
                            }
                        }
                );
            } catch (Exception e) {
                Log.e(this.getClass().getName(), "", e);
            }
        }
        //结尾
    }

}
