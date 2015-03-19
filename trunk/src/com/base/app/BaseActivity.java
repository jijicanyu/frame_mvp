package com.base.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.baidu.location.BDLocation;
import com.base.config.GlobalConfig;
import com.base.protocal.RequestMsg;
import com.base.protocal.ResponseMsg;
import com.base.protocal.ServiceHttpCom.AsyHttpService;
import com.base.protocal.ServiceHttpCom.HttpReceiver;
import com.base.protocal.ServiceHttpCom.ServiceBinderListener;
import com.base.protocal.message.activitymsg.ReFreshRes;
import com.base.utils.encoder.AESKEYCoder;
import com.base.widget.views.MyProgressDialog;
import com.lidroid.xutils.ViewUtils;
import com.mvp.LYApplication;
import com.mvp.R;
import com.mvp.view.LoginActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

import java.io.File;


//注意:这个类用来继承,集成此类的activity若需要正常启动,则需要满足如下条件
/*
1.包含顶部标题栏,xml文件里面需要include一下.(因为findbutton()执行时获取了顶部两三个控件)
2.initLayout   和 initHandler  不重新复写的话  编译器自动提示有误.
* */
//如果不想要顶部标题栏可以采用先添加后gone的方式
//或者继承的子类里面override  findAllButton方法  删除super.findAllButton();

public abstract class BaseActivity extends Activity implements BaseInterface,OnClickListener {
    //private Handler mHandler;//消息机制
    public HttpReceiver httpreceiver;//广播接收器
    LocalBroadcastManager localBroadcastManager;

    protected  boolean isShowAct=false;


    /*dialog popupwindow begin  */
    //自定义dlg
    protected MyProgressDialog wating_dialog;
    protected final String wating_txt = "请稍等...";

    //弹出图片选择
    /*dialog popupwindow end  */


    public ImageView backImage;//左上角回退图片
    public TextView titleText;//中间标题
    /**
     * 初始化布局
     */
    public abstract void initLayout();

    /**
     * 初始化Handler
     * @param msg
     */
    public abstract void initHandler(Message msg);

    //第三方框架显示图片
    //ImageLoader
    public ImageLoader imageLoader = ImageLoader.getInstance();

    protected final String STATE_PAUSE_ON_SCROLL = "STATE_PAUSE_ON_SCROLL";
    protected final String STATE_PAUSE_ON_FLING = "STATE_PAUSE_ON_FLING";
    protected boolean pauseOnScroll = false;
    protected boolean pauseOnFling = true;
    protected PauseOnScrollListener onscrolllisen;//listview  gridview滚动过程的类

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {

        pauseOnScroll = savedInstanceState.getBoolean(STATE_PAUSE_ON_SCROLL, false);
        pauseOnFling = savedInstanceState.getBoolean(STATE_PAUSE_ON_FLING, true);
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("收到", "BaseActivity onRestoreInstanceState");

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_PAUSE_ON_SCROLL, pauseOnScroll);
        outState.putBoolean(STATE_PAUSE_ON_FLING, pauseOnFling);
        super.onSaveInstanceState(outState);
        Log.d("收到", "BaseActivity onSaveInstanceState");

    }

    /**
     * 让ListView在滚动过程中停止加载图片(GridView  ListView)
     */
    public void setOnScrollListener(View list) {
        if (onscrolllisen == null)//防止空指针错误
            onscrolllisen = new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling);
        if (list instanceof ListView)
            ((ListView) list).setOnScrollListener(onscrolllisen);
        else if (list instanceof GridView)
            ((GridView) list).setOnScrollListener(onscrolllisen);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Iniframework();
        super.onCreate(savedInstanceState);


        //ActivityUtil.setNotibarColor(getThis());

        initLayout();// 初始化布局
        //调用ViewUtils方法
        ViewUtils.inject(this);
        findAllButton(); // 查找所有公共按钮
        initButtonCallBack(); // 设置按钮回调函数
        //initHandler();
        //将Activity加入栈,
        LYApplication application = (LYApplication) this.getApplication();
        application.getActivityManager().pushActivity(this);
        wating_dialog = new MyProgressDialog(getThis(), wating_txt);
    }

//    @TargetApi(19)
//    private void setTranslucentStatus(boolean on) {
//
//    }

    //实例化所需要的第三方框架
    private void Iniframework() {
        if(GlobalConfig.binder==null) {//当服务被停止时  需要重新启动
            Intent bindserv = new Intent(this, AsyHttpService.class);
            bindService(bindserv, new ServiceBinderListener(getThis()), BIND_AUTO_CREATE);//bind好这个activity
        }
        httpreceiver=new HttpReceiver(getThis());
        localBroadcastManager = LocalBroadcastManager.getInstance(getThis());
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action." + getClassName());
//        //注册
        localBroadcastManager.registerReceiver(httpreceiver, filter);
    }

    @Override
    public void onBackPressed() {
        hideProgressBar();
        super.onBackPressed();
        clearResource();
    }


    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                return true;
        }

        return super.dispatchKeyEvent(event);
    }

    /*
     * 响应按键函数
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK: {
                hideProgressBar();
                clearResource();
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        isShowAct=true;
        super.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        isShowAct=false;
    }

//    public Handler getmHandler() {
//        return mHandler;
//    }

//    private void initHandler() {
//        mHandler = new Handler() {
//            public void handleMessage(Message msg) {
//                if (msg.obj instanceof ResponseMsg) {
//                    ResponseMsg m = (ResponseMsg) msg.obj;
//                    if (m.isNetWorkError()) {
//                        hideProgressBar();
//                        if (m.getErrInfo() != null) {
//                            alertcustomDlg("网络不给力啊!");
//                        } else {
//                            alertcustomDlg("网络不给力啊!");
//                        }
//                        return;
//
//                    }
//                    if (!StringUtils.isEmpty(m.getResuldcode()))//当返回99  时说明帐号已经在其他设备登录
//                    {
//                        if (m.getResuldcode().equals("99")) {
//                            alertcustomDlg("\t帐号在其他手机登陆!");
//                        }
//                    }
//                }
//                else if (msg.obj instanceof String) {
//                    BaseJsonResponseMsg m = new BaseJsonResponseMsg();
//                    m.init((String)msg.obj);
//                    //msg.obj=m;
//                    if (m.isNetWorkError()) {
//                        hideProgressBar();
//                        if (m.getErrInfo() != null) {
//                            alertcustomDlg("网络不给力啊!");
//                        } else {
//                            alertcustomDlg("网络不给力啊!");
//                        }
//                        return;
//
//                    }
//                    if (!StringUtils.isEmpty(m.getResuldcode()))//当返回99 时说明帐号已经在其他设备登录
//                    {
//                        if (m.getResuldcode().equals("99")) {
//                            alertcustomDlg("\t帐号在其他手机登陆!");
//                        }
//                    }
//                }
//
//                initHandler(msg);
//            }
//
//        };
//    }


    /*
     * 查找到公共的按钮或图片
     * 这里没有使用ViewInject方法
     * 使用findViewbyId方法的好处在于 就算没有找到制定的控件 也不会崩溃,仅仅是为空而已
     */
    public void findAllButton() {

        backImage = (ImageView) findViewById(R.id.header_left); // 左上后退
        titleText = (TextView) findViewById(R.id.header_text); // 中间提示文本
    }

    /*
     * 设置每个按钮的响应函数
     */
    public void initButtonCallBack() {

    }

    @Override  //利用重写这个方法设置新页面打开的动画
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    //设置从下往上弹出新页面
    public void setBotEntAnim() {
        GlobalConfig.enterAnim = R.anim.bottom_enter;
        GlobalConfig.exitAnim = R.anim.normaldisplay;
    }

    /**
     * 清理资源
     */
    @Override
    public synchronized void clearResource() {
        if (this != null) {
            LYApplication application = (LYApplication) this.getApplication();
            application.getActivityManager().popThisActivity(this);
//            if (this instanceof LoginActivity)
//                overridePendingTransition(R.anim.normaldisplay, R.anim.bottom_exit);
//            else
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
    }

    @Override
    public Activity getThis() {
        return this;
    }

    //获取这个类的名字的字符串  如:LoginActivity
    public String getClassName()
    {
        return this.getClass().getSimpleName();
    }

    /*
     * 隐藏进度条
     */
    public void hideProgressBar() {
        if (getThis().isFinishing()&&wating_dialog==null) return;
        wating_dialog.hide();//显示自定义的等待提示框
    }

    /*
     * 显示进度条
     */
    public void showProgressBar() {
        wating_dialog = new MyProgressDialog(getThis(), wating_txt);
        wating_dialog.show();
    }

    public void showProgressBar(String txt) {
        wating_dialog = new MyProgressDialog(getThis(), txt);
        wating_dialog.show();
    }

    public void ClearAllactivity() {
        LYApplication application = (LYApplication) this.getApplication();
        application.getActivityManager().ClearStack();
    }


    @Override
    protected void onDestroy() {
        hideProgressBar();
        //不要忘了这一步
        localBroadcastManager.unregisterReceiver(httpreceiver);
        super.onDestroy();
        httpreceiver=null;
        System.gc();
    }

    /**
     * 向服务器发送请求
     *
     * @param request  請求
     * @param response 服務端響應
     */
    public void sendHttpMsg(final RequestMsg request, final ResponseMsg response) {
        String key = GlobalConfig.userInfo.userLoginKey+"";
        String uid = GlobalConfig.userInfo.userId+"";


        response.actname=getClassName();
        if(GlobalConfig.binder!=null) {
            request.put("clientUserId",""+uid);
            request.put("userLoginKey",""+key);

            //使用代理人调用service后台请求数据
            GlobalConfig.binder.sendHttp(request, response);
        }
        else
        {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent bindserv = new Intent(getThis(), AsyHttpService.class);
                    bindService(bindserv, new ServiceBinderListener(getThis()), BIND_AUTO_CREATE);//bind好这个activity
                    sendHttpMsg(request, response);
                }
            }, 500);
        }
    }



    /*各种弹出对话框 begin*/

    //弹出提示框,选择是否 退出程序
    protected void showExitAlert(String strtitle) {
        if (getThis().isFinishing())
            return;
        try {
            final AlertDialog dlg = new AlertDialog.Builder(this).create();
            dlg.setCanceledOnTouchOutside(false);
            dlg.show();
            Window window = dlg.getWindow();
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
            window.setContentView(R.layout.alert_exitdialog);
            if (strtitle.length() > 1) {
                TextView txt = (TextView) window.findViewById(R.id.title);
                txt.setText(strtitle);
            }
            // 为确认按钮添加事件,执行退出应用操作
            LinearLayout yes = (LinearLayout) window.findViewById(R.id.dia_yes);
            yes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dlg.cancel();
                    ExitAlertFunction();
                }
            });

            // 关闭alert对话框架
            LinearLayout no = (LinearLayout) window.findViewById(R.id.dia_no);
            no.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dlg.cancel();
                }
            });
        } catch (Exception ex) {

        }
    }

    protected void ExitAlertFunction() {
        try {
            clearResource();
            overridePendingTransition(R.anim.zoomin, R.anim.secondalpha);
            ClearAllactivity();
            File[] files = getCacheDir().listFiles();
            for (File f : files) {
                f.delete();
            }
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        } finally {
            System.exit(0);
        }
    }

    //弹出提示框,点击确定后消失
    protected void alertcustomDlg(String title) {
        if (getThis().isFinishing())
            return;

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertlayout);

        //修改提示框的标题
        TextView titletxt = (TextView) window.findViewById(R.id.title);
        titletxt.setText(title);
        // 为确认按钮添加事件,执行关闭activity操作
        LinearLayout yes = (LinearLayout) window.findViewById(R.id.dia_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
                customFunction();
            }
        });
    }

    protected void customFunction() {
    }


    //绑定百度云
    public void sendBaidubindid(String channelid, String uid) {
//        BindBaiduYunRes res = new BindBaiduYunRes();
//        BindBaiduYunReq req = new BindBaiduYunReq();
//        req.put("userId", GlobalConfig.userInfo.userId);
//        req.put("baidu_channel_id", ""+channelid);
//        req.put("baidu_user_id", ""+uid);
//        sendHttpMsg(req, res);
        Log.d(getThis().getClass().getSimpleName() + ":成功绑定 channelid:", channelid + "  userid:  " + uid);
    }
    //绑定百度云
    public void sendBaidubindid() {
//        String channelid=(String) SharedpreferncesTool.getPreference(BaiduPushChannel.CHANNEL_STR, "");
//        String userid=(String)SharedpreferncesTool.getPreference(BaiduPushChannel.USERID_STR, "");
//        if(StringUtils.isNotBlank(userid)&& StringUtils.isNotBlank(channelid))
//            sendBaidubindid(channelid,userid);
//        else {
//            Log.d(getThis().getClass().getSimpleName() + ":绑定百度id为空 channelid:", channelid + "  userid:  " + userid);
//            BaiduPushUtils.initWithApiKey(getThis());
//        }
    }

    //弹出提示框,选择是否  前往登陆界面
    protected void showLoginAlert(String strtitle) {
        if (getThis().isFinishing())
            return;
        try {
            final AlertDialog dlg = new AlertDialog.Builder(this).create();
            dlg.setCanceledOnTouchOutside(false);
            dlg.show();
            Window window = dlg.getWindow();
            // *** 主要就是在这里实现这种效果的.
            // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
            window.setContentView(R.layout.alert_exitdialog);
            if (strtitle.length() > 1) {
                TextView txt = (TextView) window.findViewById(R.id.title);
                txt.setText(strtitle);
            }
            // 为确认按钮添加事件,执行退出应用操作
            LinearLayout yes = (LinearLayout) window.findViewById(R.id.dia_yes);
            yes.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dlg.cancel();
                    LoginAlertFunction();
                }
            });

            // 关闭alert对话框架
            LinearLayout no = (LinearLayout) window.findViewById(R.id.dia_no);
            no.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dlg.cancel();
                }
            });
        } catch (Exception ex) {

        }
    }

    protected void LoginAlertFunction() {
        try {
            GlobalConfig.userInfo.setLogin(0);
            GlobalConfig.userInfo.setNickName("尚未登录");
            GlobalConfig.userInfo.setGuidid("");

            setBotEntAnim();
            Intent intent = new Intent(getThis(), LoginActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            //   Log.e("error",e.getMessage());
        }
    }

    //弹出提示框  是否可以退出
    protected void alertLoginDlg(String title, boolean iscancel) {
        if (getThis().isFinishing())
            return;

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.setCancelable(iscancel);
        dlg.setCanceledOnTouchOutside(false);
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,xml文件中定义view内容
        window.setContentView(R.layout.alertlayout);

        //修改提示框的标题
        TextView titletxt = (TextView) window.findViewById(R.id.title);
        titletxt.setText(title);
        // 为确认按钮添加事件,执行关闭activity操作
        LinearLayout yes = (LinearLayout) window.findViewById(R.id.dia_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
                LoginAlertFunction();
            }
        });

    }


    //弹出提示框 点击确定退出
    protected void alertfinishDlg(String title) {
        if (getThis().isFinishing())
            return;

        final AlertDialog dlg = new AlertDialog.Builder(this).create();
        dlg.setCanceledOnTouchOutside(false);
        dlg.setCancelable(false);
        dlg.show();
        Window window = dlg.getWindow();
        // *** 主要就是在这里实现这种效果的.
        // 设置窗口的内容页面,shrew_exit_dialog.xml文件中定义view内容
        window.setContentView(R.layout.alertlayout);

        //修改提示框的标题
        TextView titletxt = (TextView) window.findViewById(R.id.title);
        titletxt.setText(title);
        // 为确认按钮添加事件,执行关闭activity操作
        LinearLayout yes = (LinearLayout) window.findViewById(R.id.dia_yes);
        yes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dlg.cancel();
                finishDlgFunc();
            }
        });

    }

    protected void finishDlgFunc() {
        clearResource();
    }
    /*各种弹出对话框 end*/


    //Toast出 暂无更多数据
    public void showNotOther() {
        toast("没有数据了呢...");
    }

    public void toast(String str) {
        Toast.makeText(getThis(), "" + str, Toast.LENGTH_LONG).show();
    }

    /**
     * @param msgwhat 发送消息的what
     *                准备开始进行网络请求,然后在initHandler中进行处理
     * 这里放弃了handler模式 就是因为消息阻塞的问题 win,linux都存在消息阻塞的问题所以放弃了handler模式
     *  注意: 这里既然采用了 非handler模式,多个网络请求同时进行后,没了排队
     *  事实上线程是交替进行的，而且还是我们无法控制的，是由线程调度器控制的，而且每次执行的顺序都是不一样的！
     *  所以在同时sendMsg很多个请求的时候,需要做的是将顺序倒过来send发送
     */
    protected void sendMsg(int msgwhat)
    {
        Message msg=new Message();
        msg.what=msgwhat;
        initHandler(msg);
    }

    //HttpReceiver接收到response的时候 调用这个方法 这个方法是公用的
    public void Receive(Intent intent) {
        Bundle bundle = intent.getExtras();
        String resStr=bundle.getString("HttpMessage");
        int msgno=bundle.getInt("msgno");
        Message msg=new Message();
        msg.what=msgno;
        msg.obj= AESKEYCoder.decrypt(resStr);//密文解密
        initHandler(msg);
    }


    //用广播通知某个ListActivity列表更新
    protected void sendRefresh(String actname)
    {
        ReFreshRes res=new ReFreshRes();
        res.actname=""+actname;
        GlobalConfig.binder.sendResponse(res);
    }

    /**
     * 跳转页面
     * @param context
     * @param cls
     */
    public void to(Context context, Class<?> cls){

        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
//            case R.id.header_left:   // 设置左上角返回响应函数
//            {
//                clearResource();
//            }
//            break;
            default:bindClickView(view.getId());
        }
    }

    protected  void bindClickView(int viewId){

    }

    //当Activity使用  BaiduLocationer类进行定位时,返回locaion的方法  需要继承 重写这个方法
    public void opratBDLocation(BDLocation location) {

    }



}