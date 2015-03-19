package com.base.utils.type;

import java.util.regex.Pattern;

/**
 * 正则表达式验证类
 */
public class TypeCheckUtils {

    //——————常量定义
    /**
     * Email正则表达式=^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$
     */
    public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * 电话号码正则表达式= (^(\d{2,4}[-_－—]?)?\d{3,8}([-_－—]?\d{3,8})?([-_－—]?\d{1,7})?$)|(^0?1[35]\d{9}$)
     */
    public static final String PHONE = "(^(\\d{2,4}[-_－—]?)?\\d{3,8}([-_－—]?\\d{3,8})?([-_－—]?\\d{1,7})?$)|(^0?1[35]\\d{9}$)";
    /**
     * 手机号码正则表达式=^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\d{8}$
     */
    public static final String MOBILE = "^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}$";
    /**
     * IP地址正则表达式 ((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))
     */
    public static final String IPADDRESS = "((?:(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d)\\.){3}(?:25[0-5]|2[0-4]\\d|[01]?\\d?\\d))";
    /**
     * Integer正则表达式 ^-?(([1-9]\d*$)|0)
     */
    public static final String INTEGER = "^-?(([1-9]\\d*$)|0)";
    /**
     * 正整数正则表达式 >=0 ^[1-9]\d*|0$
     */
    public static final String INTEGER_NEGATIVE = "^[1-9]\\d*|0$";
    /**
     * 负整数正则表达式 <=0 ^-[1-9]\d*|0$
     */
    public static final String INTEGER_POSITIVE = "^-[1-9]\\d*|0$";
    /**
     * Double正则表达式 ^-?([1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0)$
     */
    public static final String DOUBLE = "^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$";
    /**
     * 正Double正则表达式 >=0  ^[1-9]\d*\.\d*|0\.\d*[1-9]\d*|0?\.0+|0$
     */
    public static final String DOUBLE_NEGATIVE = "^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0$";
    /**
     * 负Double正则表达式 <= 0  ^(-([1-9]\d*\.\d*|0\.\d*[1-9]\d*))|0?\.0+|0$
     */
    public static final String DOUBLE_POSITIVE = "^(-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*))|0?\\.0+|0$";
    /**
     * 年龄正则表达式 ^(?:[1-9][0-9]?|1[01][0-9]|120)$ 匹配0-120岁
     */
    public static final String AGE = "^(?:[1-9][0-9]?|1[01][0-9]|120)$";
    /**
     * 邮编正则表达式  [1-9]\d{5}(?!\d) 国内6位邮编
     */
    public static final String CODE = "[1-9]\\d{5}(?!\\d)";
    /**
     * 匹配由数字、26个英文字母或者下划线组成的字符串 ^\w+$
     */
    public static final String STR_ENG_NUM_ = "^\\w+$";
    /**
     * 匹配由数字和26个英文字母组成的字符串 ^[A-Za-z0-9]+$
     */
    public static final String STR_ENG_NUM = "^\\w+$";
    /**
     * 匹配由26个英文字母组成的字符串  ^[A-Za-z]+$
     */
    public static final String STR_ENG = "^[A-Za-z]+$";
    /**
     * 匹配中文字符 ^[\u0391-\uFFE5]+$
     */
    public static final String STR_CHINA = "^[\u0391-\uFFE5]+$";

    /**
     * 只能输英文 数字 中文 ^[a-zA-Z0-9\u4e00-\u9fa5]+$
     */
    public static final String STR_ENG_CHA_NUM = "^[a-zA-Z0-9\u4e00-\u9fa5]+$";
    /**
     * 验证RMB输入金额,两位小数
     */
    public static final String STR_RMB_AMOUNT = "^(([1-9]\\d*)|0)(\\.\\d{1,2})?$";
    /**
     * 验证是否合法的用户名
     * 4-16个字符(包括4、16)或2-8个汉字， 请用英文小写、汉字、数字、下划线，不能全部是数字，下划线不能在末尾
     */
    public static final String STR_LOGIN_NAME = "^([\\u4e00-\\u9fa5]{2,8})$|^(?!\\d+$)(\\w{3,15}[A-Za-z0-9\\u4e00-\\u9fa5])$";
/**
 *
 */
    /**
     * 日期正则 支持：
     * YYYY-MM-DD
     * YYYY/MM/DD
     * YYYY_MM_DD
     * YYYYMMDD
     * YYYY.MM.DD的形式
     */
    public static final String DATE_ALL = "((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(10|12|0?[13578])([-\\/\\._]?)(3[01]|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(11|0?[469])([-\\/\\._]?)(30|[12][0-9]|0?[1-9])$)" +
            "|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._]?)(0?2)([-\\/\\._]?)(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([3579][26]00)" +
            "([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][0][48])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][0][48])([-\\/\\._]?)" +
            "(0?2)([-\\/\\._]?)(29)$)" +
            "|(^([1][89][2468][048])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._]?)(0?2)" +
            "([-\\/\\._]?)(29)$)|(^([1][89][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$)|" +
            "(^([2-9][0-9][13579][26])([-\\/\\._]?)(0?2)([-\\/\\._]?)(29)$))";
    /**
     * URL正则表达式
     * 匹配 http www ftp
     */
    public static final String URL = "^(http|www|ftp|)?(://)?(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*((:\\d+)?)(/(\\w+(-\\w+)*))*(\\.?(\\w)*)(\\?)?" +
            "(((\\w*%)*(\\w*\\?)*(\\w*:)*(\\w*\\+)*(\\w*\\.)*(\\w*&)*(\\w*-)*(\\w*=)*(\\w*%)*(\\w*\\?)*" +
            "(\\w*:)*(\\w*\\+)*(\\w*\\.)*" +
            "(\\w*&)*(\\w*-)*(\\w*=)*)*(\\w*)*)$";
    /**
     * 身份证正则表达式
     */
    public static final String IDCARD = "((11|12|13|14|15|21|22|23|31|32|33|34|35|36|37|41|42|43|44|45|46|50|51|52|53|54|61|62|63|64|65)[0-9]{4})" +
            "(([1|2][0-9]{3}[0|1][0-9][0-3][0-9][0-9]{3}" +
            "[Xx0-9])|([0-9]{2}[0|1][0-9][0-3][0-9][0-9]{3}))";
    /**
     * 1.匹配科学计数 e或者E必须出现有且只有一次 不含Dd
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$
     */
    public final static String SCIENTIFIC_A = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))$";
    /**
     * 2.匹配科学计数 e或者E必须出现有且只有一次 结尾包含Dd
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$
     */
    public final static String SCIENTIFIC_B = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))[dD]?$";
    /**
     * 3.匹配科学计数 是否含有E或者e都通过 结尾含有Dd的也通过（针对Double类型）
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$
     */
    public final static String SCIENTIFIC_C = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?[dD]?$";
    /**
     * 4.匹配科学计数 是否含有E或者e都通过 结尾不含Dd
     * 正则 ^[-+]?(\d+(\.\d*)?|\.\d+)([eE]([-+]?([012]?\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$
     */
    public final static String SCIENTIFIC_D = "^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)([eE]([-+]?([012]?\\d{1,2}|30[0-7])|-3([01]?[4-9]|[012]?[0-3])))?$";


    public static boolean CheckStr(String checkstr, String checktype) {
        Pattern pattern = Pattern.compile(checktype);
        boolean tf = pattern.matcher("" + checkstr).matches();
        return tf;
    }
}
