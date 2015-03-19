package com.base.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.base.app.BaseActivity;
import com.mvp.R;

/**
 * Created by aa on 14-4-29.
 */
public class UploadprogressAlert {


    private Context context;
    final AlertDialog dlg;
    public UploadprogressAlert(Context con)
    {
        context=con;
        dlg = new AlertDialog.Builder(context).create();
    }

    public void showProgressAlert(String strtitle) {
        try{
            if(context  instanceof  BaseActivity)
            if(((BaseActivity)context).isFinishing())
                return ;
            dlg.setCanceledOnTouchOutside(false);
            dlg.show();
            Window window = dlg.getWindow();

            // 设置窗口的内容页面,xml文件中定义view内容
            window.setContentView(R.layout.uploadalert);
            if(strtitle.length()>1)
            {
                TextView txt=(TextView)window.findViewById(R.id.title_txt);
                txt.setText(strtitle);
            }
        }
        catch(Exception ex){
        }
    }

    public void  setProgress(int  index)
    {
        if(index<1||index>=100)
        {
            return ;
        }
        Window window = dlg.getWindow();
        ProgressBar prog=(ProgressBar)window.findViewById(R.id.uploading_prg);
        prog.setProgress(index);
        TextView uploadtxt=(TextView)window.findViewById(R.id.uploading_txt);
        uploadtxt.setText(index+"%");
    }

    public void showFinish() throws InterruptedException {
        Window window = dlg.getWindow();
        ProgressBar prog=(ProgressBar)window.findViewById(R.id.uploading_prg);
        prog.setProgress(99);
        Thread.sleep(100);
        prog.setProgress(100);
        dlg.cancel();
    }

    public  void hide()
    {
        dlg.cancel();
    }


}
