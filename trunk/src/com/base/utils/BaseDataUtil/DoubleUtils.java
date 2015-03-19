package com.base.utils.BaseDataUtil;

import java.math.BigDecimal;

/**
 * Created by aa on 2014/8/6.
 */
public class DoubleUtils {

    //四舍五入制定位的double 不控制小数位   四舍五入2.20000008  ->   2.2
    public static  double getDefinebit(double f, int bit) {
        BigDecimal b = new BigDecimal(f);
        double retdouble = b.setScale(bit, BigDecimal.ROUND_HALF_UP).doubleValue();
        return retdouble;
    }


    //获取Double的两位小数的字符串
    public static String getTwobit(double f) {

        java.text.DecimalFormat df=new java.text.DecimalFormat("########0.00");
        String retdouble=df.format(f);
        return retdouble;
    }
}
