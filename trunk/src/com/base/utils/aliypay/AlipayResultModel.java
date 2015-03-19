package com.base.utils.aliypay;

import java.io.Serializable;

/**
 * Created by aa on 2014/8/20.
 */
public class AlipayResultModel  implements Serializable {
    public String resultStatuscode="";//返回的代码   成功是 9000
    public String resultStatus="";//成功  失败
    public String partner="";
    public String notify_id="";//通知校验ID
    public String out_trade_no="";//商户网站唯一订单号
    public String subject="";//商品名称
    public String body="";//商品介绍
    public String total_fee="";
    public String seller_id="";
    public String sign_type="RSA";//签名方式
    public String sign="";//sign
    public String result="";

    public boolean isSucces()
    {
        return resultStatuscode.equals("9000");
    }

}
