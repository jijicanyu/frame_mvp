package com.base.protocal.http;

import com.cores.FrameApplication;

/**
 * Created by aa on 2015/6/19.
 */
public class HttpConfig {

    //服务器地址
    private static final boolean isDebug= FrameApplication.isDebug;
    private static final String DEBUG_SERVER="http://192.168.2.27:90/index.php?action=";
    private static final String OFICAL_SERVER="http://api.sushewifi.com/index.php?action=";


    public static final String HTTP_SERVER_FOR_IMAGE="";


    public static String getServer()
    {
        if(isDebug)
        {
            return DEBUG_SERVER;
        }
        else {
            return OFICAL_SERVER;
        }
    }

}
