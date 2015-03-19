package com.base.utils;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import com.base.config.GlobalConfig;
import org.apache.commons.lang.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 在Application中统一捕获异常，保存到文件中下次再打开时上传
 */

public class CrashHandler implements UncaughtExceptionHandler {

    /**
     * 系统默认的UncaughtException处理类
     */

    private UncaughtExceptionHandler mDefaultHandler;

    /**
     * CrashHandler实例
     */

    private static CrashHandler INSTANCE;
    private Context mContext;
    public static final String TAG = "CrashHandler";

    /** 程序的Context对象 */

    // private Context mContext;

    /**
     * 保证只有一个CrashHandler实例
     */

    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */

    public static CrashHandler getInstance() {

        if (INSTANCE == null) {

            INSTANCE = new CrashHandler();

        }

        return INSTANCE;

    }

    /**
     * 初始化,注册Context对象,
     * <p/>
     * 获取系统默认的UncaughtException处理器,
     * <p/>
     * 设置该CrashHandler为程序的默认处理器
     *
     * @param ctx ctx必须为Activity,捕获异常才不会才不会出问题
     */

    public void init(Context ctx) {

        mContext = ctx;

        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(this);

    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else { // 如果自己处理了异常，则不会弹出错误对话框，则需要手动退出app
            Toast.makeText(mContext, "程序开个小差!", Toast.LENGTH_LONG).show();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        }

    }

    /**
     * 自定义错误处理,收集错误信息
     * <p/>
     * 发送错误报告等操作均在此完成.
     * <p/>
     * 开发者可以根据自己的情况来自定义异常处理逻辑
     *
     * @return true代表处理该异常，不再向上抛异常，
     * <p/>
     * false代表不处理该异常(可以将该log信息存储起来)然后交给上层(这里就到了系统的异常处理)去处理，
     * <p/>
     * 简单来说就是true不会弹出那个错误提示框，false就会弹出
     */

    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        // final String msg = ex.getLocalizedMessage();
        final StackTraceElement[] stack = ex.getStackTrace();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stack.length; i++) {
            StackTraceElement element = stack[i];
            sb.append(element.toString() + "\n");
        }
        final String strstack = sb.toString();
        final String message = ex.getMessage();
        // 使用Toast来显示异常信息
        new Thread() {

            @Override
            public void run() {
                Looper.prepare();
                try {
                    String fileName = "crash-" + System.currentTimeMillis() + ".log";
                    File file = new File(GlobalConfig.sdlogPath, fileName);
                    //StringUtils.isNotBlank(GlobalConfig.sdlogPath)为空时返回值为false
                    if(StringUtils.isNotBlank(GlobalConfig.sdlogPath)){
                        FileOutputStream fos = new FileOutputStream(file, true);
                        fos.write(strstack.getBytes());
                        if (message != null) {
                            fos.write(message.getBytes());
                        }
                        fos.flush();
                        fos.close();
                    }else{
                        Log.d("===strstack===>>", strstack);
                    }

                } catch (Exception e) {
                    Log.e("error", e.getMessage());
                }
                // 可以只创建一个文件，以后全部往里面append然后发送，这样就会有重复的信息，个人不推荐
                //Looper.loop();
//                if(mContext instanceof BaseActivity)
//                {
//                    Intent intent=new Intent(mContext,LYActivity.class);
//                    mContext.startActivity(intent);
//                }


                 android.os.Process.killProcess(android.os.Process.myPid());
                 System.exit(0);

            }

        }.start();
        return true;

    }

    // TODO 使用HTTP Post 发送错误报告到服务器

    // private void postReport(File file) {

    // }

}
