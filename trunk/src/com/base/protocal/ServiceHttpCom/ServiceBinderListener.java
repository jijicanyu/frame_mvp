package com.base.protocal.ServiceHttpCom;

import android.app.Activity;
import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.base.app.BaseActivity;
import com.base.config.GlobalConfig;

/**
 * 实现后台http服务的绑定以及取Binder对象
 */
public class ServiceBinderListener implements ServiceConnection {

    Activity con;

    public ServiceBinderListener(Activity con) {
        this.con = con;
    }

    @Override//绑定成功时  返回Binder对象
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        GlobalConfig.binder = (AsyHttpService.HttpBinder) iBinder;
    }

    @Override//只有在service因异常而断开连接的时候，这个方法才会调用
    public void onServiceDisconnected(ComponentName componentName) {
        if (con instanceof BaseActivity)
            ((BaseActivity) con).toast("服务绑定出现异常");
    }
}
