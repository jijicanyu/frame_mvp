package com.base.protocal.ServiceHttpCom;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.base.app.BaseActivity;

/**
 * 数据监听
 */
public class HttpReceiver extends BroadcastReceiver {

    Context act;
    public HttpReceiver(Context con)
    {
        act=con;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(act instanceof BaseActivity)
        {
            ((BaseActivity)act).Receive(intent);
        }
    }
}