package com.base.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by aa on 14-4-10.
 */
public class LocationUtils {

    private Context mContext;
    public LocationUtils(Context con)
    {
        mContext=con;
    }

    public String GetLocation()
    {
        double latitude=-1;
        double longitude=-1;
    //获取经纬度
    LocationManager locationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);


    if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
        Location location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            latitude = location.getLatitude(); //经度
            longitude = location.getLongitude(); //纬度
        }
    }else{
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) { //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
                if (location != null) {
                    Log.i("SuperMap", "Location changed : Lat: "
                            + location.getLatitude() + " Lng: "
                            + location.getLongitude());
                }
            }
            public void onProviderDisabled(String provider) {
// Provider被disable时触发此函数，比如GPS被关闭
            }
            public void onProviderEnabled(String provider) {
// Provider被enable时触发此函数，比如GPS被打开
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {
// Provider的转态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(location != null){
            latitude = location.getLatitude(); //经度
            longitude = location.getLongitude(); //纬度
            }
        }
        return latitude+""+longitude;
    }
}
