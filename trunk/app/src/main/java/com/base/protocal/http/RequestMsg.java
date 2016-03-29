package com.base.protocal.http;

import android.text.TextUtils;
import com.cores.utils.FastJSONHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据请求的 req res都独立封装了
 * 用于数据请求的req   其他的都不介绍了 代码都简单的不能再简单了
 *
 * 这是一个虚拟类  必须要继承才能使用
 */
public abstract class RequestMsg {
    //请求参数
    protected HashMap<String, Object> params = new HashMap<String, Object>();

    //消息回调的时候需要一个msgwhat
    protected int resmsgwhat = 0;

    public int getResPonseMsgWhat() {
        return resmsgwhat;
    }

    public void setResPonseMsgWhat(int msgwhat) {
        this.resmsgwhat = msgwhat;
    }

    public String getUrl() {
        if (!TextUtils.isEmpty(getActionName())) {
            return HttpConfig.getServer() + getActionName();
        } else
            return null;
    }

    public HashMap<String, Object> getHashMapParams() {
        return params;
    }


    public Set<Map.Entry<String, Object>> getParamEntry() {
        return params.entrySet();
    }

    public String getJsonBody() {
        String query = "";
        query = FastJSONHelper.toJSONStr(params);
        return query;
    }

    abstract public String getActionName();

    public void put(String key, String value) {
        params.put(key, value);
    }

    public void put(String key, int value) {
        Integer integer = new Integer(value);
        params.put(key, integer);
    }

    public void put(String key, double value) {
        Double d = new Double(value);
        params.put(key, d);
    }

    public void put(String key, boolean value) {
        Integer integer;
        if (value)
            integer = new Integer(1);
        else
            integer = new Integer(0);

        params.put(key, integer);
    }

    public void put(String key, List l) {
        params.put(key, l);
    }


}