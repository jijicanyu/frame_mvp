package com.base.utils.aliypay;

import android.util.Log;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Result {
    private static final Map<String, String> sResultStatus;

    static {
        sResultStatus = new HashMap<String, String>();
        sResultStatus.put("9000", "操作成功");
        sResultStatus.put("4000", "支付失败");
        sResultStatus.put("4001", "数据格式不正确");
        sResultStatus.put("4003", "该用户绑定的支付宝账户被冻结或不允许支付");
        sResultStatus.put("4004", "该用户已解除绑定");
        sResultStatus.put("4005", "绑定失败或没有绑定");
        sResultStatus.put("4006", "订单支付失败");
        sResultStatus.put("4010", "重新绑定账户");
        sResultStatus.put("6000", "支付服务正在进行升级操作");
        sResultStatus.put("6001", "用户中途取消支付操作");
        sResultStatus.put("7001", "网页支付失败");
    }

    private String mResult;
    String resultStatus = null;
    String memo = null;
    String result = null;
    boolean isSignOk = false;

    public Result(String result) {
        this.mResult = result;
    }

    public String getResult() {
        String src = mResult.replace("{", "");
        src = src.replace("}", "");

        return getContent(src, "memo=", ";result");
    }

    public AlipayResultModel getPayresultmodel()
    {
        AlipayResultModel model=new AlipayResultModel();

        try {
            String src = mResult.replace("{", "");
            src = src.replace("}", "");
            src = src.replace("\"", "");
            model.result=mResult.replace("\"", "|");
            model.resultStatuscode = getContent(src, "resultStatus=", ";memo");

            if (sResultStatus.containsKey(model.resultStatuscode)) {
                model.resultStatus = sResultStatus.get(model.resultStatuscode);//赋值  返回成功  失败原因  等
            } else {
                model.resultStatus = "其他错误";
            }

            String rst = getContent(src, "result=", null);
            JSONObject json = string2JSON(rst, "&");
            try{
                if(!json.isNull("body"))
                    model.body=json.getString("body");
                if(!json.isNull("out_trade_no"))
                    model.out_trade_no=json.getString("out_trade_no");
                if(!json.isNull("seller_id"))
                    model.seller_id=json.getString("seller_id");
                if(!json.isNull("sign"))
                    model.sign=json.getString("sign");
                if(!json.isNull("total_fee"))
                    model.total_fee=json.getString("total_fee");
                if(!json.isNull("sign_type"))
                    model.sign_type=json.getString("sign_type");
                if(!json.isNull("notify_id"))
                    model.notify_id=json.getString("notify_id");
                if(!json.isNull("seller_id"))
                    model.seller_id=json.getString("seller_id");
                if(!json.isNull("partner"))
                    model.partner=json.getString("partner");
                if(!json.isNull("subject"))
                    model.subject=json.getString("subject");

            }
            catch (Exception ex){}

        } catch (Exception e) {
            return null;
        }
        return model;
    }

    public void parseResult() {
        try {
            String src = mResult.replace("{", "");
            src = src.replace("}", "");

            String rs = getContent(src, "resultStatus=", ";memo");

            if (sResultStatus.containsKey(rs)) {
                resultStatus = sResultStatus.get(rs);
            } else {
                resultStatus = "其他错误";
            }

            resultStatus += ("(" + rs + ")");

            memo = getContent(src, "memo=", ";result");
            result = getContent(src, "result=", null);
            isSignOk = checkSign(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkSign(String result) {
        boolean retVal = false;

        try {
            JSONObject json = string2JSON(result, "&");

            int pos = result.indexOf("&sign_type=");
            String signContent = result.substring(0, pos);

            String signType = json.getString("sign_type");
            signType = signType.replace("\"", "");

            String sign = json.getString("sign");
            sign = sign.replace("\"", "");

            if (signType.equalsIgnoreCase("RSA")) {
                retVal = Rsa.doCheck(signContent, sign, Keys.PUBLIC);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Result", "Exception =" + e);
        }

        Log.i("Result", "checkSign =" + retVal);

        return retVal;
    }

    public JSONObject string2JSON(String src, String split) {
        JSONObject json = new JSONObject();

        try {
            String[] arr = src.split(split);

            for (int i = 0; i < arr.length; i++) {
                String[] arrKey = arr[i].split("=");
                json.put(arrKey[0], arr[i].substring(arrKey[0].length() + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return json;
    }

    private String getContent(String src, String startTag, String endTag) {
        String content = src;
        int start = src.indexOf(startTag);
        start += startTag.length();

        try {
            if (endTag != null) {
                int end = src.indexOf(endTag);
                content = src.substring(start, end);
            } else {
                content = src.substring(start);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }
}
