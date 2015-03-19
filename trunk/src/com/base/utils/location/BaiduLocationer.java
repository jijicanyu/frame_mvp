package com.base.utils.location;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.base.app.BaseActivity;
import com.base.config.GlobalConfig;
import com.base.utils.newwork.NetWorkUtils;

//这个类的使用方法 是直接调用  实例化即可
// BaiduLocationer(Context context)



    /*   个人总结
    *如果定位出现问题,可能是如下原因
    *   1.  key有问题
    *   安全码   如:   05:14:10:66:8D:19:2D:35:03:DF:47:A9:97:5B:E2:10:02:98:6D:08:com.oki.lyw.activity
    *   安全码里面有包名  新项目要去百度重新申请key
    *   2.  liblocSDK4d.so  没有导入
    *   3.  服务 service android:name="com.baidu.location.f"   没有加上来
    *   4.  如果定位不稳定但是可以定位到的话 就可能是  版本问题
    * */

public class BaiduLocationer {
    Handler lochandler;
    Context mContext;
    private LocationClient locationClient = null;
    private static final int UPDATE_TIME=100;


    public BaiduLocationer(Context context)//实例化并启动
    {
        //启动定位
        lochandler= new Handler();
        mContext=context;
        startLocation();
    }

    public void startLocation()
    {
        if(NetWorkUtils.GetInternetstate(mContext)) {
            lochandler.post(getpositionrunnable);
        }
        else
        {
            //循环等待网络连接  成功联网即可定位
            lochandler.postDelayed(loopwattinlocationrunnable, 3300);
        }
    }

    //循环等待的Runnable
    Runnable loopwattinlocationrunnable=new Runnable() {
        @Override
        public void run() {
            if(GlobalConfig.isConnect)
                lochandler.post(getpositionrunnable);
            else {
                lochandler.postDelayed(loopwattinlocationrunnable, 3300);
                Log.d("定位无法启动", "尚未打开网络");
            }
        }
    };

    //后台获取经纬度的Runnable
    Runnable getpositionrunnable = new Runnable() {
        @Override
        public void run() {
            try {
                locationClient = new LocationClient(mContext.getApplicationContext());

                locationClient.registerLocationListener(new BDLocationListener() {

                        @Override
                        public void onReceiveLocation(BDLocation location) {
                            // TODO Auto-generated method stub
                            if (location == null) {
                                Toast.makeText(mContext, "请打开定位!", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (location.getLatitude() > 1 && location.getLongitude() > 1) {
                                stopLoc();
                                if(((Activity)mContext).isFinishing())//如果当前的Activity已经被finish掉了)
                                    return;
                                if(mContext  instanceof BaseActivity)
                                    ((BaseActivity)mContext).opratBDLocation(location);
                                else
                                    Toast.makeText(mContext, "Context不是BaseActivity实例化的对象", Toast.LENGTH_LONG).show();
                            }

                    }

                });
                //设置定位条件
                LocationClientOption option = new LocationClientOption();
                option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
                option.setOpenGps(true);// 打开gps
                option.setCoorType("bd09ll"); // 设置坐标类型gcj02,bd09ll
                option.setOpenGps(true);
                option.setScanSpan(UPDATE_TIME);
                option.setIsNeedAddress(true);
                locationClient.setLocOption(option);
                //注册位置监听器
                locationClient.start();
            } catch (Exception e) {
                Toast.makeText(mContext, "请打开定位!", Toast.LENGTH_LONG).show();
            }
        }
    };


    public void  stopLoc()
    {
        //非空切未启动时停止定位
        if(locationClient!=null&&locationClient.isStarted())
            locationClient.stop();
        lochandler.removeCallbacks(getpositionrunnable);//终止线程
    }

}
