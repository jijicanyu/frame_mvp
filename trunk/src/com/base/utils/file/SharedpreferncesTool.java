package com.base.utils.file;

import android.util.Log;

import java.lang.reflect.Field;


/**
 * 简单调用缓存文件的个工具类
 */
public class SharedpreferncesTool {

    /**
     * 获得类的一个实例
     * @param xmlname 缓存的xml文件名称
     * @return 返回一个实例化的对象
     */
    public static DefSharedprefernces getSharedPreferences(String xmlname) {
        return new DefSharedprefernces(xmlname);
    }

    /**
     * 获得类的一个实例
     *                没有像上面的方法传缓存的xml文件名称  就用包名作为xml缓存的文件名
     * @return 返回一个实例化的对象
     */
    public static DefSharedprefernces getSharedPreferences() {
        return new DefSharedprefernces();
    }


    /**
     * 写入本地一个基本类型的变量
     * @param key
     * @param deftvalue
     * @return
     */
    public static boolean setPreference(String key, Object deftvalue) {

        if(deftvalue==null)
            return false;
        if (deftvalue instanceof Integer
                || deftvalue instanceof String
                || deftvalue instanceof Double
                || deftvalue instanceof Float
                || deftvalue instanceof Long
                || deftvalue instanceof Boolean) {
            boolean isSaveOK = true;
            try {
                DefSharedprefernces settings = getSharedPreferences();
                settings.put(key, deftvalue);
                isSaveOK = settings.commit();
            } catch (Exception e) {
                Log.e("SharedpreferncesTool", "ActivityUtil.setPreference(): " + e.getMessage());
            }
            return isSaveOK;
        } else {
            Log.d("SharedpreferncesTool.setPreference(" + key + "," + deftvalue + ")", "参数类型有误");
            return false;
        }
    }


    /**
     * 取得本地缓存中一个基本类型的变量
     * 返回值和 deftvalue类型必须一致
     * @param key   key值
     * @param deftvalue  默认值
     * @return  返回取得的结果
     */
    public static Object getPreference(String key, Object deftvalue) {
        if (deftvalue instanceof Integer
                || deftvalue instanceof Double
                || deftvalue instanceof Float
                || deftvalue instanceof Long
                || deftvalue instanceof Boolean
                || deftvalue instanceof String) {
            Object ret_obj = null;
            try {

                DefSharedprefernces settings = getSharedPreferences();

                ret_obj = settings.get(key, deftvalue);
                return ret_obj;
            } catch (Exception e) {
                Log.e("SharedpreferncesTool", "getPreference(): " + e.getMessage());
            }
            ret_obj = deftvalue;
            return ret_obj;
        } else {
            Log.d("SharedpreferncesTool.getPreference(" + key + "," + deftvalue + ")", "参数类型有误");
            return deftvalue;
        }
    }

    //由于读取本地缓存文件是一个IO读写过程耗时比较厉害  取多个变量的时候 不建议 使用  setPreference
    // 这样多次调用的话会多次打开关闭xml文件 确实会产生性能问题  所以建议将要存的对象放到一个对象中这样一次调用写入一个xml文件了
    /**
     *非基本类型的对象存储到本地缓存里面
     * @param oc
     * @return
     */
    public static boolean setObjectToSharedpreferences(Object oc) {
        if (oc == null)
            return false;
        if (oc instanceof Integer
                || oc instanceof String
                || oc instanceof Double
                || oc instanceof Float
                || oc instanceof Long
                || oc instanceof Boolean)
            return false;

        Field[] fields = oc.getClass().getDeclaredFields();

        DefSharedprefernces settings = getSharedPreferences(oc.getClass().getSimpleName());//取对象的类名作为文件名

        for (Field f : fields) {
            f.setAccessible(true);
        }
        if (fields.length == 0)
            return false;
        //输出所有属性
        for (int i = 0; i < fields.length; i++) {

            String field = fields[i].toString().substring(fields[i].toString().lastIndexOf(".") + 1);         //取出属性名称
            try {

                if (fields[i].get(oc) instanceof String)
                    settings.put(field, fields[i].get(oc));
                else if (fields[i].get(oc) instanceof Integer)
                    settings.put(field,  fields[i].get(oc));
                else if (fields[i].get(oc) instanceof Double)
                    settings.put(field, fields[i].get(oc));
                else if (fields[i].get(oc) instanceof Float)
                    settings.put(field, fields[i].get(oc));
                else if (fields[i].get(oc) instanceof Boolean)
                    settings.put(field,  fields[i].get(oc));
                else if (fields[i].get(oc) instanceof Long)
                    settings.put(field, fields[i].get(oc));

            } catch (IllegalArgumentException e) {
                Log.d("setObjectToSharedpreferences()", "IllegalArgumentException " + oc.getClass().getSimpleName());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Log.d("setObjectToSharedpreferences()", "IllegalAccessException " + oc.getClass().getSimpleName());
                e.printStackTrace();
            }
        }
        return settings.commit();
    }


    //由于读取本地缓存文件是一个IO读写过程耗时比较厉害  取多个变量的时候 不建议 使用  getPreference  这样多次调用的话会多次打开关闭xml文件
    // 确实会产生性能问题    所以建议将要存的对象放到一个对象中这样一次调用写入一个xml文件了
    /**
     * 取本地缓存  写入到一个非基本类型的对象中
     * @param oc  对象
     * @return
     */
    public static boolean getObjectFromSharedpreferences(Object oc) {
        if (oc == null)
            return false;
        if (oc instanceof Integer || oc instanceof String || oc instanceof Double || oc instanceof Float || oc instanceof Boolean)
            return false;

        Field[] fields = oc.getClass().getDeclaredFields();

        DefSharedprefernces settings = getSharedPreferences(oc.getClass().getSimpleName());//这里一定是.getSimpleName()  如果是getCLassname肯定报错


        for (Field f : fields) {
            f.setAccessible(true);
        }
        if (fields.length == 0)
            return false;
        //输出所有属性
        for (int i = 0; i < fields.length; i++) {

            String field = fields[i].toString().substring(fields[i].toString().lastIndexOf(".") + 1);         //取出属性名称
            try {

                if (fields[i].get(oc) instanceof String) {
                    String s = (String)settings.get(field,fields[i].get(oc));
                    fields[i].set(oc, s);
                } else if (fields[i].get(oc) instanceof Integer) {
                    int s = (Integer)settings.get(field,fields[i].get(oc));
                    fields[i].set(oc, s);
                } else if (fields[i].get(oc) instanceof Double) {
                    double s = (Double)settings.get(field,fields[i].get(oc));
                    fields[i].set(oc, s);
                } else if (fields[i].get(oc) instanceof Float) {
                    float s = (Float)settings.get(field,fields[i].get(oc));
                    fields[i].set(oc, s);
                } else if (fields[i].get(oc) instanceof Boolean) {
                    boolean s = (Boolean)settings.get(field, fields[i].get(oc));
                    fields[i].set(oc, s);
                } else if (fields[i].get(oc) instanceof Long) {
                    long s = (Long)settings.get(field,fields[i].get(oc));
                    fields[i].set(oc, s);
                }

            } catch (IllegalArgumentException e) {
                Log.d("getObjectToSharedpreferences()", "IllegalArgumentException " + oc.getClass().getSimpleName());
                return false;
            } catch (IllegalAccessException e) {
                Log.d("getObjectToSharedpreferences()", "IllegalAccessException " + oc.getClass().getSimpleName());
                return false;
            }
        }

        return true;
    }

}
