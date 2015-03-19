package com.base.protocal.message.activitymsg;

import com.base.protocal.BaseJsonResponseMsg;
import com.base.utils.encoder.AESKEYCoder;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 上传进度返回的百分比消息
 */
public class UpLoadingMSGRes extends BaseJsonResponseMsg {

    public int persentage =0;
    public UpLoadingMSGRes()
    {
        setMsgno(UploadingCount_MSGNO);
    }

    //此处非常傻逼的拼接出一个json
    // 还非常傻逼的进行加密,是不想修改AsyHttpService 加密传输原则和BaseActivity中解密后重新调用initHandler的方法 进行解密

    public void setPercentageAndEypt(long per)
    {
        if(per<101&&per>-1)
            persentage = (int)per;
        else
            persentage =100;

        strResult="{\"data\":{\"persentage\":"+ persentage +"},\"desc\":\"自定义回调图片上传百分比\",\"result\":1}";
        strResult= AESKEYCoder.encrypt(strResult);
    }

    public void setPersentage(long per)
    {
        if(per<101&&per>-1)
            persentage = (int)per;
        else
            persentage =100;

        strResult="{\"data\":{\"persentage\":"+ persentage +"},\"desc\":\"自定义回调图片上传百分比\",\"result\":1}";

    }

    @Override
    public void init(String result) {
        super.init(result);

        if (resultJson != null)
        {
            try
            {
                boolean isNull = resultJson.isNull("data");
                if (isNull)
                {
                    return;
                }
                Object object = resultJson.get("data");
                if (null == object)
                {
                    return;
                }
                if (object instanceof JSONObject)
                {
                    JSONObject singleObject = (JSONObject)object ;

                    persentage =singleObject.getInt("persentage");
                    if(persentage<5)
                        persentage=5;

                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }
}
