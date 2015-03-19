/*
 * Copyright (C) 2010 The MobileSecurePay Project
 * All right reserved.
 * author: shiqun.shi@alipay.com
 *
 *  提示：如何获取安全校验码和合作身份者id
 *  1.用您的签约支付宝账号登录支付宝网站(www.alipay.com)
 *  2.点击“服务者服务”(https://b.alipay.com/order/myorder.htm)
 *  3.点击“查询合作者身份(pid)”、“查询安全校验码(key)”
 */
package com.base.utils.aliypay;


//
// 请参考 Android平台安全支付服务(msp)应用开发接口(4.2 RSA算法签名)部分，并使用压缩包中的openssl RSA密钥生成工具，生成一套RSA公私钥。
// 这里签名时，只需要使用生成的RSA私钥。
// Note: 为安全起见，使用RSA私钥进行签名的操作过程，应该尽量放到服务者服务器端去进行。
public final class Keys {
    //合作身份者id，以2088开头的16位纯数字
    public static final String DEFAULT_PARTNER = "2088511311037077";

    //收款支付宝账号
    public static final String DEFAULT_SELLER ="2088511311037077";

    //商户私钥，自助生成
    //public static final String PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALpRFk7SDP7QuBrlLp1YD7AvHzwNk3G+dKBwav85XOWfzw93RVEtLf6bwvbLDE0yyPreVeTwAQrgj0pQQCaMzTbH5zjsCyfs7dvM0dL9hh8tyej5/Kyjim8M2yBDLFuT2M+nU+7Ns3DE8JIR5D3iggtJd/GC8E2b7/YpzC7+w7KtAgMBAAECgYEAktTUf8mJ9EcI0ClNUzLTKkX4l5sbV8iAoO/3YqwSSeRnigi02ASC+uRGAbiDOVOMkCgoCQQbzjaqtiYIaFkOX4UYbX+FoI8AvKCrT1yi3UqPdiWrkFoysJbWKT4B5TJnOySAio5oSQZVqH1VVZwVVZRzRAZp1KaQbQTo8+coyAECQQDkWPUjTii1TTpG3ovZ0eG7RZJgXlKGVugghX367fmAxJqCy/rAmYtDjsxK6NYYFKI7xijNFIPSBEDBuec9sD11AkEA0OEgdBprSdyHWGOniUi7KQQtGPnDFjUPSEvMsLbA+4rw4Z4+NSL62Hdtz+AHvnorn0GuurTz5fhM7xmcsMFhWQJAcgk++w+4YrqbpPLVEsWvHpAjBr90JSTXrg4cmSkpVjZZF4L4yiCkHOv+eFaJPONpFcLjc2+QWVzIXjcSFYujVQJAczebJS/lemqQparioRFjW66YCazLdZZzBZf6IofMT3RGhs041yqiX4ERK5cR7nmJUmFytj5WQsYB+emQytcAkQJAMUEj/lq1XQH4m39c3GEdwcRbNQ+4jcaw6a2ErgLVQ+quEkKOXZSr2q8Rm9t2r4s0yqJ7lr+iBn4oUCW1UvoYaQ==";
    //public static final String PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

    public static final String PRIVATE = "III";
    public static final String PUBLIC = "GGG";
}
