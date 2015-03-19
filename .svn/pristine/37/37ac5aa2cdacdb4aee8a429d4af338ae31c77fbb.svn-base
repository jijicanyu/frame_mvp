package com.base.utils.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by aa on 2014/9/3.
 */
public class CalendarUtil {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //获取当前日期
    public static String getCurrentdate()//"yyyy-MM-dd"
    {
        Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
        String curdate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
        return curdate;
    }

    //获取当前时间
    public static String getNowtime()//"yyyy-MM-dd HH:mm:ss"
    {
        return sdf.format(new Date());
    }

    //获取到未来的几天的日期
    public static String getNextNumDay(int num) {

        Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
        c.add(c.DATE, num);//1天后
        String curdate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DAY_OF_MONTH);
        return curdate;
    }

    //获取到未来的几天的日期
    public static String getFullNextNumDay(int num) {

        Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
        c.add(c.DATE, num);//1天后
        if ((c.get(Calendar.MONTH) + 1) < 10) {
            String curdate = c.get(Calendar.YEAR) + "-0" + (c.get(Calendar.MONTH) + 1) + "-";
            if (c.get(Calendar.DAY_OF_MONTH) > 9)
                curdate += c.get(Calendar.DAY_OF_MONTH);
            else
                curdate = curdate + "0" + c.get(Calendar.DAY_OF_MONTH);
            return curdate;
        } else {
            String curdate = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-";
            if (c.get(Calendar.DAY_OF_MONTH) > 9)
                curdate += c.get(Calendar.DAY_OF_MONTH);
            else
                curdate = curdate + "0" + c.get(Calendar.DAY_OF_MONTH);
            return curdate;
        }
    }

    //获取到未来的几天的日期
    public static String getFullNextMonthDay(int num) {

        Calendar c = Calendar.getInstance();//创建实例 默认是当前时刻
        c.add(c.DATE, num);//1天后
        if ((c.get(Calendar.MONTH) + 1) < 10) {
            String curdate = "0" + (c.get(Calendar.MONTH) + 1) + "-";
            if (c.get(Calendar.DAY_OF_MONTH) > 9)
                curdate += c.get(Calendar.DAY_OF_MONTH);
            else
                curdate = curdate + "0" + c.get(Calendar.DAY_OF_MONTH);
            return curdate;
        } else {
            String curdate = (c.get(Calendar.MONTH) + 1) + "-";
            if (c.get(Calendar.DAY_OF_MONTH) > 9)
                curdate += c.get(Calendar.DAY_OF_MONTH);
            else
                curdate = curdate + "0" + c.get(Calendar.DAY_OF_MONTH);
            return curdate;
        }
    }

    public static Date String2DateTime(String str_time) {
        Date date = null;
        try {
            date = sdf.parse(str_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    //后获取下一个小时的date
    public static Date getNextOneHours(String str_time) {
        Date date = null;
        try {
            date = sdf.parse(str_time);
        } catch (ParseException e) {
            return new Date();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, 1);
        return date;
    }

}
