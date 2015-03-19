package com.base.protocal;

import android.util.Log;
import org.apache.commons.lang.StringUtils;
import org.apache.http.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public class BaseJsonResponseMsg extends ResponseMsg
{

	protected String strResult;

	protected JSONObject resultJson;

	@Override
	public void init(String result)
	{
		try
		{
			this.strResult=result;
			if(StringUtils.isNotBlank(strResult))
			{
				resultJson = new JSONObject(result);
                if (resultJson != null)
                {
                    resuldcode = resultJson.optString("result", "no resultcode");
                    description = resultJson.optString("desc", "no resultdesc");
                }
			}
            else
            {

            }
		}
		catch (ParseException e)
		{
			Log.e(this.getClass().getName(), "Parse error", e);
		}
		catch (JSONException e)
		{
			Log.e(this.getClass().getName(), "JSON error", e);
		}
	}

	/**
	 * 返回BODY
	 */
	public String getStrResult()
	{
		return strResult;
	}
    public void setStrResult(String str)
    {
        strResult=str;
    }

    public String getDescription()
    {
        if(StringUtils.isBlank(description))
            description="网络好像有问题了呢...";
        return description;
    }
	
	/**
	 * 1:正常;0:无数据;异常
	 */
	@Override
	public boolean isOK()
	{
		return "1".equals(resuldcode);
	}

    public String getResultcode()
    {
        if(resuldcode!=null)
            return  resuldcode;
        else
            return  "";
    }

}
