package com.base.protocal;

import android.util.Log;
import com.base.config.ConstUtil;
import com.base.protocal.object.RequestModelBase;
import com.base.utils.json.FastJSONHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public abstract class RequestMsg
{

	private String url = ConstUtil.HTTP_SERVER;
	
	private String uploadFilePath;
	
	protected HashMap<String,Object> params=new HashMap<String,Object>();

    private int  hasFileUpload;

    public boolean isHasFileUpload()//图片上传
    {
        return hasFileUpload==2;
    }

    public boolean isAvatarUpload()//头像上传
    {
        return hasFileUpload==1;
    }

    public void setHasFileUpload(int hasFileUpload)
    {
        this.hasFileUpload = hasFileUpload;
    }

	public String getUrl()
	{
		StringBuffer sb=new StringBuffer();
		sb.append(url);
		String actionName = getActionName();
		if(StringUtils.isNotEmpty(actionName))
		{
			sb.append(actionName);//拼接请求地址
			// "http://211.139.4.102/home/index.go?m=client"
			    //"a="+actionName
			// http://lyw.njoki.com:8081/client/
			    // actionName
		}
		return sb.toString();
	}

    public String getPostParams()
    {
        String query = "";
        Set<Map.Entry<String, Object>> entries = params.entrySet();

        query= FastJSONHelper.toJSONStr(params);
        Log.d("RequestMsg  getPostParams()", query);

        return query;
    }


	
	abstract public String getParams();

	abstract public String getActionName();
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String createRequest()
	{
        String newPostParams = "";
        try {
            newPostParams = getPostParams();//AESKEYCoder.encrypt(getPostParams());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newPostParams;
	}



	public String getUploadFilePath()
	{
		return uploadFilePath;
	}

	public void setUploadFilePath(String uploadFilePath)
	{
		this.uploadFilePath = uploadFilePath;
	}
	
	public void put(String key, String value)
	{
		params.put(key, value);
	}

    public void put(String key, int value)
    {
        Integer integer=new Integer(value);
        params.put(key, integer);
    }

    public void put(String key, double value)
    {
        Double d=new Double(value);
        params.put(key, d);
    }

    public void put(String key, boolean value)
    {
        Integer integer;
        if(value)
            integer=new Integer(1);
        else
            integer=new Integer(0);

        params.put(key, integer);
    }

    public void put(String key, List l)
    {
        params.put(key,l);
    }

    public  void put(String key,RequestModelBase s)
    {
        params.put(key,s);
    }


    public HashMap<String,Object> getHashMapParams()
    {
        return params;
    }

}