package com.base.utils.activity;

import android.app.Activity;

import java.util.Stack;

public class WSOFTActivityManager {
    private static Stack<Activity> activityStack=new Stack<Activity>();
    private static WSOFTActivityManager instance;
    private WSOFTActivityManager() {
    }

    public Stack<Activity> getActStack()
    {
        return  activityStack;
    }

    public static WSOFTActivityManager getScreenManager() {
        if (instance == null) {
            instance = new WSOFTActivityManager();
        }
        return instance;
    }
    //退出制定Activity
    public void popThisActivity(Activity activity) {
        if (activity != null) {
            //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    //只是remove并不进行finish方法
    public void removeThisAct(Activity activity)
    {
        activityStack.remove(activity);
    }

   //退出栈顶Activity
    public void popTopActivity() {
        if(!activityStack.empty())
        {
            Activity activity= activityStack.lastElement();
            //在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
            activity.finish();
            activityStack.remove(activity);
            activity = null;
        }
    }

    //获得当前栈顶Activity   
    public Activity currentActivity() {
        Activity activity = null;
        if(!activityStack.empty())
            activity= activityStack.lastElement();
        return activity;
    }

    /**
     * 从栈顶顺序往下finish掉一些activity
     * @param num
     */
    public void finishNumAct(int num)
    {
        if(activityStack.size()>num)
        {
            for(int i=0;i<num;i++)
            {
                if(!activityStack.empty()) {
                    Activity act=activityStack.lastElement();
                    activityStack.remove(act);
                    act.finish();
                }
            }
        }
    }

    //将当前Activity推入栈中   
    public void pushActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        activityStack.add(activity);
    }



    //退出栈中所有Activity
    public void  ClearStack()
    {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            popThisActivity(activity);//这个pop方法内部有finish方法,若不调用finish,则依旧出错
        }
        //加入上面的while出错,没能清空activityStack,则自清空
        activityStack.clear();
    }
}  