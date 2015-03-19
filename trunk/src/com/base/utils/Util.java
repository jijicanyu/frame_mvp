package com.base.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;
import com.base.config.GlobalConfig;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Util {
    public static final String tag = "Util";

    public static int getRealScreenLenth(int num) {
        return (int) (GlobalConfig.deviceInfo.density * num);
    }

    /**
     * 生成MD5摘要字符串值
     *
     * @param src
     * @return
     */
    public static String makeMD5(String src) {
        String des = new String();
        if (null == src || 0 == src.length()) {
            return des;
        }

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] result = md5.digest(src.getBytes());
            for (int i = 0; i < result.length; i++) {
                byte ch = result[i];
                des += Hex2Chr((byte) (ch >>> 4));
                des += Hex2Chr(ch);
            }
        } catch (Exception e) {
            Log.e(tag, "md5(): " + e.getMessage());
            return des;
        }
        return des;
    }

    /**
     * 4bit binary to char 0-F(将字节转换成16进制表示的字符)
     *
     * @param digit
     * @return
     */
    public static char Hex2Chr(byte digit) {
        char[] hexDigits =
                {
                        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
                };

        return hexDigits[digit & 0x0f];
    }


    /**
     * 得到指定文件类型,用于Intent调用系统方法打开
     *
     * @param file
     * @return 文件类型
     */
    public static String getMIMEType(File file) {
        String type = "";
        String fName = file.getName();
        // 取得扩展名
        String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

        // 依扩展名的类型决定MimeType
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
                || end.equals("wav")) {
            type = "audio";
        } else if (end.equals("3gp") || end.equals("mp4")) {
            type = "video";
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp")) {
            type = "image";
        } else if (end.equals("apk")) {
            // 安装包
            type = "application/vnd.android.package-archive";
        } else if (end.equalsIgnoreCase("doc")) {
            type = "application/msword";
        } else if (end.equalsIgnoreCase("docx")) {
            type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
        } else if (end.equalsIgnoreCase("xls")) {
            type = "application/vnd.ms-excel";
        } else if (end.equalsIgnoreCase("xlsx")) {
            type = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
        } else if (end.equalsIgnoreCase("txt")) {
            type = "text/plain";
        } else if (end.equalsIgnoreCase("pdf")) {
            type = "application/pdf";
        } else if (end.equalsIgnoreCase("ppt")) {
            type = "application/vnd.ms-powerpoint";
        } else if (end.equalsIgnoreCase("pptx")) {
            type = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
        } else {
            type = "*";
        }
        // 如果无法直接打开，就跳出软件列表给用户选择
        if (end.equals("apk")) {
        } else {
            if (type.lastIndexOf("/") == -1) {
                type += "/*";
            }
        }
        return type;
    }

    /**
     * 获取日期(月-日)与时间(时:分)
     *
     * @return
     */
    public static String getCustomDateAndTime() {
        String current = "";
        try {
            long curTime = System.currentTimeMillis();
            SimpleDateFormat sf = new SimpleDateFormat("MM-dd HH:mm");
            Calendar mCalendar = Calendar.getInstance();
            mCalendar.setTimeInMillis(curTime);
            current = sf.format(mCalendar.getTime());
        } catch (Exception e) {

        }
        return current;
    }

    /**
     * 发送指定内容的短信到特定号码，无界面显示
     *
     * @param phone   手机号码
     * @param content 发送短信内容
     * @return发送短信是否成功
     */
    public static boolean sendSms(String phone, String content, Context context) {
        SmsManager m = SmsManager.getDefault();
        try {
            ArrayList<String> phones = new ArrayList<String>();
            Util.splitStr(phone, ',', phones);
            for (int i = 0; i < phones.size(); i++) {
                m.sendTextMessage(phones.get(i).trim(), null, content, null, null);
            }
            if (null == context) {
                return false;
            }
            Toast.makeText(context, "短信已经发送", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            if (null == context) {
                return false;
            }
            Toast.makeText(context, "短信发送失败", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * 指定号码拨打电话
     */
    public static void tel(String phone, Context context) {
        if (!phone.startsWith("tel:")) {
            phone = "tel:" + phone;
        }
        Uri uri = Uri.parse(phone);
        Intent it = new Intent(Intent.ACTION_CALL, uri);
        context.startActivity(it);
    }


    /**
     * 判断远程文件是否存在
     *
     * @param URLstr
     * @return true:文件存在、 false：文件不存在
     */
    public static boolean HttpFileIsExist(String URLstr) {
        int HTTP_CONNECT_TIMEOUT = 8;
        boolean b = false;
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, HTTP_CONNECT_TIMEOUT);
        HttpGet httpGet = new HttpGet(URLstr);
        try {
            HttpResponse response = httpClient.execute(httpGet);
            int code = response.getStatusLine().getStatusCode();
            if (code == 200)
                b = true;
        } catch (Exception e) {
            Log.d("fileExist", "&&&&&&&&&&&&&&&&--fileIsExist()--&&&&&&&&&&&&=" + e.toString());
        }
        Log.d("fileExist", "...fileIsExist() return= " + b);
        return b;
    }

    public static void splitStr(String text, char c, ArrayList<String> result) {
        try {
            if (text.length() < 1) {
                return;
            }

            int pos1 = 0;
            int pos2 = text.indexOf(c, pos1);

            String strTemp;
            for (; pos2 >= 0; ) {
                strTemp = text.substring(pos1, pos2);
                result.add(strTemp);
                pos1 = pos2 + 1;
                pos2 = text.indexOf(c, pos1);
            }
            strTemp = text.substring(pos1);
            result.add(strTemp);
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.e(tag, "splitStr(): " + ex.getMessage());
        }
    }




}