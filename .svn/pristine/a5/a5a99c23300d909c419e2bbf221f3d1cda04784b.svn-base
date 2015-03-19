package com.base.protocal.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.base.config.GlobalConfig;
import com.base.protocal.message.activitymsg.ReFreshRes;
import com.base.utils.activity.ActivityUtil;
import com.base.utils.newwork.NetWorkUtils;


/**
 * Created by aa on 2014/12/30.
 *
 * 后台网络监听的广播接收器
 * 用来监听系统网路变化
 */

public class NetReceiver extends BroadcastReceiver {
    public static final String ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    //广播通知的Activity的list
    private  final String[] needSendBoardActList = {"MainActivity"};

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if (ACTION.equals(intent.getAction())) {
            //获得网络状态
            GlobalConfig.isConnect= NetWorkUtils.GetInternetstate(context);

            if(GlobalConfig.isConnect)
            {
                if(ActivityUtil.isAppRunnig(context))
                {
                    sendListRefresh();
                }
            }

        }
    }

    //用广播通知某个ListActivity列表更新
    protected void sendListRefresh()
    {
        for (String actname:needSendBoardActList) {
            ReFreshRes res = new ReFreshRes();
            res.actname = "" + actname;
            GlobalConfig.binder.sendResponse(res);
        }
    }

}
