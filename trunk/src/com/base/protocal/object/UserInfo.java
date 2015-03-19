package com.base.protocal.object;

import android.util.Log;
import com.base.config.GlobalConfig;
import com.base.utils.activity.ActivityUtil;
import com.base.utils.file.DefSharedprefernces;

public class UserInfo
{
    public String nickname="尚未登录"; // 用户名称
    public String userId=""; // 用户guid
    public String real_name=""; // 用户真名
    public String userLoginKey=""; // key
    public String headImg="";
    public String mobilePhone="";//手机号
    public String userPrivacy="";
    public int offState=0;//是否是服务者
    public int authState=0;
    public String address="";
    public String cityLocation="南京市";
    public int isserver=0;

    public String getMobilePhone() {
        this.mobilePhone = ActivityUtil.getPreference( "mobilePhone", mobilePhone);
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        ActivityUtil.setPreference("mobilePhone", mobilePhone);
        this.mobilePhone = mobilePhone;
    }


    public int getSex() {
        this.sex = ActivityUtil.getPreference( "sex", 1);
        return sex;
    }

    public void setSex(int sex) {
        ActivityUtil.setPreference( "sex", sex);
        this.sex = sex;
    }

    public  int sex;


    public String getNickName() {
        this.nickname = ActivityUtil.getPreference( "nickname", nickname);
        return nickname;
    }

    public String getCityLocation() {
        this.cityLocation = ActivityUtil.getPreference( "cityLocation", "");
        if(cityLocation.length()>=2)
            return cityLocation;
        else
            return "南京市";
    }
    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;

        ActivityUtil.setPreference( "cityLocation", cityLocation);
    }

    public void setNickName(String username) {
        this.nickname = username;
        ActivityUtil.setPreference( "nickname", username);

    }

    public String getUserLoginKey() {
        this.userLoginKey = ActivityUtil.getPreference( "userLoginKey", userLoginKey);
        return userLoginKey;
    }

    public void setUserLoginKey(String userLoginKey) {
        ActivityUtil.setPreference( "userLoginKey", userLoginKey);
        this.userLoginKey = userLoginKey;
    }

    public String getGuidid() {
        try {
            this.userId = ActivityUtil.getPreference( "userId", userId);
        }
        catch (Exception ex){
            ex.printStackTrace();
            userId="";
        }
        if(GlobalConfig.userInfo.isLogin())
        {return userId;}
        else
        {return "";}
    }

    public void setGuidid(String userId) {
        ActivityUtil.setPreference( "userId", userId);
        this.userId = userId;
    }

    public String getReal_name() {
        this.real_name = ActivityUtil.getPreference( "real_name", real_name);
        return real_name;
    }

    public void setReal_name(String real_name) {
        ActivityUtil.setPreference( "real_name", real_name);
        this.real_name = real_name;
    }



    public String getHeadImg() {
        this.headImg = ActivityUtil.getPreference( "headImg", headImg);
        if(GlobalConfig.userInfo.isLogin())
        return headImg;
        else return "";
    }

    public void setHeadImg(String headImg) {
        ActivityUtil.setPreference( "headImg", headImg);
        this.headImg = headImg;
    }


    public boolean isLogin() {
        int login = ActivityUtil.getPreference( "islogin", 0);

        if (login==1){
           return true;
        }
        else{
            return false;
        }
    }

    public void setLogin(int login)//如果设置用户退出，使用SharePreferences的事物，讲其他用户信息置空
    {
        ActivityUtil.setPreference( "islogin", login);
    }

    public void setAuthstate(int i)
    {
        this.authState=i;
        ActivityUtil.setPreference( "authState", i);
    }

    public int getAuthstate()
    {
        authState=ActivityUtil.getPreference( "authState", authState);
        return authState;
    }

    //设置用户隐私
    public void setUserPrivacy(String i)
    {
        this.userPrivacy=i;
        ActivityUtil.setPreference( "userPrivacy", i);
    }

    //取得用户隐私
    public String getUserPrivacy()
    {
        userPrivacy=ActivityUtil.getPreference( "userPrivacy", userPrivacy);
        return userPrivacy;
    }

    public static void setModel(UserInfo user)//使用SharePreferences的事物，存储数据
    {

        long starttime= System.currentTimeMillis();
        DefSharedprefernces editor = new DefSharedprefernces();

        editor.put("nickname",user.nickname);
        editor.put("headImg",user.headImg);
        editor.put("userId", user.userId);
        editor.put("userLoginKey",user.userLoginKey);
        editor.put("mobilePhone",user.mobilePhone);
        editor.put("authState",user.authState);
        editor.put("sex",user.sex);
        editor.put("userPrivacy",user.userPrivacy);
        editor.put("offState",user.offState);
        editor.put("cityLocation",user.cityLocation);
        editor.commit();

        long endtime= System.currentTimeMillis();
        Log.d("UserInfo  setModel()耗时：" + (endtime - starttime) + "ms ", user.headImg);

    }


    public static UserInfo getModel()//使用SharePreferences的事物，存储数据
    {
        long starttime= System.currentTimeMillis();
        UserInfo model=new UserInfo();
        DefSharedprefernces settings = new DefSharedprefernces();
        model.nickname=(String)settings.get("nickname","尚未登录");
        model.headImg=(String)settings.get("headImg","");

        long endtime= System.currentTimeMillis();

        model.userId=(String)settings.get("userId", model.userId);
        model.userLoginKey=(String)settings.get("userLoginKey","");
        model.authState=(Integer)settings.get("authState",0);
        model.sex=(Integer)settings.get("sex",1);
        model.userPrivacy=(String)settings.get("userPrivacy",model.userPrivacy);
        model.mobilePhone=(String)settings.get("mobilePhone",model.mobilePhone);
        model.offState=(Integer)settings.get("offState",0);
        model.cityLocation=(String)settings.get("cityLocation",model.cityLocation);

        Log.d("UserInfo  getModel()耗时：" + (endtime - starttime) + "ms ", model.headImg);
        //这里耗时的数值不是很稳定  有时候是20+ms  有时候只有2ms
        return model;
    }

    //是不是服务者
    public boolean getIsServer() {
        isserver = ActivityUtil.getPreference( "offState", 0);
        return isserver==1;
    }

    //设置是是否为服务者
    public void setIsServer(int t) {
        if(t!=0) {
            isserver=1;
            ActivityUtil.setPreference( "offState", 1);
        }
        else {
            ActivityUtil.setPreference( "offState", 0);
            isserver=0;
        }
    }

}


