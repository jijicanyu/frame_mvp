package com.base.utils.file;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.mvp.LYApplication;
import com.base.utils.encoder.AESKEYCoder;
import org.apache.commons.lang.StringUtils;

/**
 * 自定义的sharedprefernces
 * 并保留原有的调用模式
 * 1.多个键值对put之后  需要commit才有效
 * 2.get之后就直接取得值了 不需要commit
 *
 * PS:
 * 使用这个对象的优点 有如下两点
 * 1.加密  提高安全性  本身缓存机制写入到本地的就是xml  手机ROOT之后 用RE文件管理器打开之后就可以看到 路径下 /data/data/<package name>/shared_prefs
 * 2.好用  以前需要 putString putInt  putDOuble ...这样去调用现在只需要写一个方法put即可
 * 3.解决异常问题
 * 加入上次写入到指定key值为 int  这次取值当String取值 而带来的异常问题
 * putInt("isfirst",1)  之后下次再putString("isfirst","dfsf");或者getString("isfirst",""); 都会出现异常  因为类型不一样
 */
public class DefSharedprefernces {
    SharedPreferences mainprefer;
    SharedPreferences.Editor editor;
    final String TAGS = "DefSharedprefernces";
    final String FILENAME="HC83FDK467HF24672FJSOGKDJSF678DIYG";


    public DefSharedprefernces() {

            mainprefer = LYApplication.getInstance().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);//设置为私有  正常情况下不加密也看不到这个xml文件
            editor = mainprefer.edit();
    }

    public DefSharedprefernces(String xmlname) {
        mainprefer = LYApplication.getInstance().getSharedPreferences(xmlname, Context.MODE_PRIVATE);//设置为私有  正常情况下不加密也看不到这个xml文件
        editor = mainprefer.edit();
    }


    /**
     *
     * 这里 value可以传int  double  float boolean long
     * @param key  键
     * @param value 值可以 传int  double  float boolean long
     */
    public void put(String key, Object value) {

        try {
            if (StringUtils.isNotBlank(key))
                editor.putString(AESKEYCoder.encrypt(key), AESKEYCoder.encrypt(value + ""));//int double  long boolean类型发都要先转化成字符串再加密传递
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAGS + " key:" + key, e.getMessage());
        }
    }
    //put方法重载  降低 在代码的复杂性
    //begin
    //
//    public void put(String key, String value) {
//        if (StringUtils.isNotBlank(key) && value != null)  //只要value不为null 就行  空字符串"" 也可以
//            editor.putString(AESKEYCoder.encrypt(key), AESKEYCoder.encrypt(value + ""));
//    }
//
//    public void put(String key, double value) {
//        if (StringUtils.isNotBlank(key))
//            editor.putString(AESKEYCoder.encrypt(key), AESKEYCoder.encrypt(value + ""));
//    }
//
//    public void put(String key, float value) {
//        if (StringUtils.isNotBlank(key))
//            editor.putString(AESKEYCoder.encrypt(key), AESKEYCoder.encrypt(value + ""));
//    }
//
//    public void put(String key, long value) {
//        if (StringUtils.isNotBlank(key))
//            editor.putString(AESKEYCoder.encrypt(key), AESKEYCoder.encrypt(value + ""));
//    }
//
//    public void put(String key, boolean value) {
//        if (StringUtils.isNotBlank(key))
//            editor.putString(AESKEYCoder.encrypt(key), AESKEYCoder.encrypt(value + ""));
//    }
//    end
    public boolean commit() {
        if (editor != null)
            return editor.commit();
        else
            return false;
    }


    /**
     * 根据deft  进行数据返回
     * 这里 value可以传int  double  float boolean long
     * @param key  键
     * @param deft  传int  double  float boolean long
     * @return  根据deft的类型进行返回
     */
    public Object get(String key, Object deft) {

        if (StringUtils.isNotBlank(key)) {
            try {
                String value = mainprefer.getString(AESKEYCoder.encrypt(key), "");
                if (deft instanceof Integer) {
                    int i = Integer.valueOf(AESKEYCoder.decrypt(value));
                    return i;
                } else if (deft instanceof String) {
                    return AESKEYCoder.decrypt(value);
                } else if (deft instanceof Double) {
                    double i = Double.valueOf(AESKEYCoder.decrypt(value));
                    return i;
                } else if (deft instanceof Float) {
                    float i = Float.valueOf(AESKEYCoder.decrypt(value));
                    return i;
                } else if (deft instanceof Boolean) {
                    boolean i = Boolean.valueOf(AESKEYCoder.decrypt(value));
                    return i;
                } else if (deft instanceof Long) {
                    long i = Long.valueOf(AESKEYCoder.decrypt(value));
                    return i;
                }
            } catch (Exception ec) {
                Log.d(TAGS + " key:" + key, ec.getMessage());
            }
        }
        return deft;
    }

//    get依旧重载  调用简单
//    begin
//    public int get(String key,int deft) {
//
//        if (StringUtils.isNotBlank(key)) {
//            try {
//                String value = mainprefer.getString(AESKEYCoder.encrypt(key), "");
//                int i = Integer.valueOf(AESKEYCoder.decrypt(value));
//                return i;
//            }
//            catch (Exception ec)
//            {Log.d(TAGS+" key:"+key, ec.getMessage());}
//        }
//        return deft;
//    }
//
//    public String get(String key,String deft) {
//        if (StringUtils.isNotBlank(key)) {
//            String value=mainprefer.getString(AESKEYCoder.encrypt(key),"");
//            return AESKEYCoder.decrypt(value);
//        }
//        return deft;
//    }
//
//    public double get(String key,double deft) {
//
//        if (StringUtils.isNotBlank(key)) {
//            try {
//                String value = mainprefer.getString(AESKEYCoder.encrypt(key), "");
//                double i = Double.valueOf(AESKEYCoder.decrypt(value));
//                return i;
//            }
//            catch (Exception ec)
//            {Log.d(TAGS+" key:"+key, ec.getMessage());}
//        }
//        return deft;
//    }
//
//    public float get(String key,float deft) {
//
//        if (StringUtils.isNotBlank(key)) {
//            try {
//                String value = mainprefer.getString(AESKEYCoder.encrypt(key), "");
//                float i = Float.valueOf(AESKEYCoder.decrypt(value));
//                return i;
//            }
//            catch (Exception ec)
//            {Log.d(TAGS+" key:"+key, ec.getMessage());}
//        }
//        return deft;
//    }
//
//    public boolean get(String key,boolean deft) {
//
//        if (StringUtils.isNotBlank(key)) {
//            try {
//                String value = mainprefer.getString(AESKEYCoder.encrypt(key), "");
//                boolean i = Boolean.valueOf(AESKEYCoder.decrypt(value));
//                return i;
//            }
//            catch (Exception ec)
//            {
//                Log.d(TAGS+" key:"+key, ec.getMessage());
//            }
//        }
//        return deft;
//    }
//    end

}
