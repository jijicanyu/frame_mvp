package com.base.utils.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.base.app.BaseActivity;
import com.base.config.GlobalConfig;
import com.mvp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateManager {
    private Context mContext;
    //用于当前下载状态的判断 如果当前是正在下载 这个类就不会去执行开始下载 否则会出现问题
    public boolean isdownload=false;
    private Timer mTimer;

    TimerTask task;
    //返回的安装包url
    private String apkUrl = "";

    private Dialog noticeDialog;

    private Dialog downloadDialog;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/updatedemo/";

    private static final String saveFileName = savePath + "UpdateDemoRelease.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;


    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;

    //取消下载的状态为
    private boolean interceptFlag = false;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:
                    downloadDialog.cancel();
                    isdownload=false;
                    installApk();
                    break;
                default:
                    break;
            }
        };
    };

    public UpdateManager(final Context context,String url) {
        apkUrl=url;
        this.mContext = context;

        mTimer = new Timer(true);
        task = new TimerTask() {
            public void run() {
                if(!GlobalConfig.isConnect)//如果网络已经断开
                {
                    interceptFlag=true;//取消下载
                    downloadDialog.cancel();//隐藏下载界面
                    isdownload=false;//设置状态下次可以继续下载
                }
                //每次需要执行的代码放到这里面。
            }
        };
        //firstTime为Date类型,period为long，表示从firstTime时刻开始，每隔period毫秒执行一次。
        mTimer.schedule(task, 3000, 3000);
    }


    public  void showDownloadDialog(){
        isdownload=true;
        if(mContext instanceof Activity &&((Activity)mContext).isFinishing())
            return ;
        downloadDialog = new AlertDialog.Builder(mContext).create();
        downloadDialog.setCanceledOnTouchOutside(false);
        downloadDialog.show();
        Window window = downloadDialog.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alert_updateversion);
        mProgress = (ProgressBar)window.findViewById(R.id.progress);

        LinearLayout hide_lay=(LinearLayout)window.findViewById(R.id.hide_lay);
        hide_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadDialog.hide();
                //interceptFlag = true;//为true即取消下载
            }
        });
        LinearLayout cannel_lay=(LinearLayout)window.findViewById(R.id.cancel_lay);
        cannel_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadDialog.cancel();

                interceptFlag = true;//为true即取消下载
                isdownload=false;//设置状态下次可以重新下载
            }
        });
        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);

                int count = 0;
                byte buf[] = new byte[1024];

                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     */

    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }
    /**
     * 安装apk
     */
    private void installApk(){
        File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
        ((BaseActivity)(mContext)).ClearAllactivity();
    }

}
