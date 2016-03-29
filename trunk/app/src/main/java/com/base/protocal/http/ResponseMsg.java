package com.base.protocal.http;

/**
 * 这个类会的赋值 会通过JsonHelper 把json字符串转成这个类所以需要谨慎加减字段
 *
 * T是任意类型的对象  用于json中res字段直接转T类型的对象  T类型的对象建议放在 com.base.protocal.object包下面
 */
public abstract class ResponseMsg<T>
{

    //配置result对应的几种值代表的意思是
    private static final int SERVER_NOT_RES=0,REQUEST_NOT_JSON=6001,NETWORK_NOT_AVALID=60002,REQUEST_IS_CANCEL=6003;
    private static final String STR_SERVER_NOT_RES="服务器无响应",STR_REQUEST_NOT_JSON="服务器出错",STR_NETWORK_NOT_AVALID="网络出错",STR_REQUEST_IS_CANCEL="请求已被取消";
    //result错误的状态  可以继承的时候可以重新写  重新配置下 我这里只是做了个演示
    protected static final int[] STATE_ERROR_LIST={SERVER_NOT_RES,REQUEST_NOT_JSON,NETWORK_NOT_AVALID,REQUEST_IS_CANCEL};


    private static final int RES_SUC=1;
    protected static final int[] STATE_SUCC_LIST={RES_SUC,2,3};//之后两种待配置


    //一下三个字段用于json字符串 直接装java bean的时候字段解析
	protected int result; // 状态位
	protected String msg; // 消息名称
    protected T res;

    public T getData()
    {
        return res;
    }

    //判断result的状态位是否等于 配置好的error lsit
    public boolean isError()
    {
        for(int i_sta:STATE_ERROR_LIST)
        {
            if(result==i_sta)
                return true;
        }
        return false;
    }

    //这个可以重写有时候会有多种状态 都代表成功
    public boolean isSuc(){
        for(int i_sta:STATE_SUCC_LIST)
        {
            if(result==i_sta)
                return true;
        }
        return false;
    }



//一堆 get set 方法
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public void setSERV_NOR_RES()
    {
        setMsg(STR_SERVER_NOT_RES);
        setResult(SERVER_NOT_RES);
    }

    public void setNetworkNotAvalid()
    {
        setMsg(STR_NETWORK_NOT_AVALID);
        setResult(NETWORK_NOT_AVALID);
    }

    public void setRequestIsCancel()
    {
        setMsg(STR_REQUEST_IS_CANCEL);
        setResult(REQUEST_IS_CANCEL);
    }


    public void setRequestNotJson()
    {
        setMsg(STR_REQUEST_NOT_JSON);
        setResult(REQUEST_NOT_JSON);
    }
}
