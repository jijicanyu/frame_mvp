package com.base.config;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import com.base.protocal.ServiceHttpCom.AsyHttpService;
import com.base.protocal.object.UserInfo;
import com.base.utils.CommonUtil;
import com.base.utils.activity.ActivityUtil;
import com.base.utils.file.FileUtil;
import com.base.utils.newwork.NetWorkUtils;
import com.mvp.R;

import java.io.File;
import java.io.IOException;


public class GlobalConfig {
	public static DeviceInfo deviceInfo ; // 设备信息
	private static GlobalConfig globalConfig;// 唯一配置实例
	private static String projectName = "layou";//项目制定文件目录

    public static boolean isConnect=false;

    public static AsyHttpService.HttpBinder binder;
	public static String sdPath; // 用于存放日志，缓存等到外置SD卡上
	public static String sdRootPath; // 外置存储根目录
	public static String dataPath;// 用户存放数据等到私有目录下
	public static String imagePath ; // 图像目录
	public static String sdcachePath ; // 缓存目录
	public static String sdlogPath; // 日志目录
	public static String databasePath ;// 数据库目录
	public static String dataFileName = "layou.db"; // 数据库文件名称
	public static boolean isneedMindMSg=false;//是否需要显示出红点点  作为新消息提示
    public static UserInfo userInfo;
    public static int enterAnim = R.anim.in_from_right;
    public static int exitAnim =R.anim.out_to_left;
    //屏幕宽度高度
    public static int scrhei=1920;
    public static int scrwid=1080;
    public static float fivedp=10;//5dp的像素数


    /** 获取全局唯一实例 */
	public static GlobalConfig getGlobalConfig(Context context) {
		if (globalConfig == null) {
			globalConfig = new GlobalConfig();
			globalConfig.init(context);
		}
		return globalConfig;
	}

	/** 加载配置信息 */
	public static void init(Context context) {

		// 加载设备信息
        scrwid = ActivityUtil.getScreenWidth((Activity) context);
        scrhei = ActivityUtil.getScreenHeight((Activity)context);
        fivedp= CommonUtil.dip2px(context, 5);//计算5dp的像素数
		deviceInfo = new DeviceInfo();
        //读取本地缓存的服务者数据
        userInfo = UserInfo.getModel();

		deviceInfo.initDeviceInfo(context);
		// 初始化文件目录
		String fileSeparator = System.getProperty("file.separator");
		//sd卡目录
		sdPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() ;
		//wyt根目录
		sdRootPath = sdPath + fileSeparator + projectName+fileSeparator;
		//普通数据
		dataPath = sdRootPath  + "data" + fileSeparator;
		//数据库
		databasePath = sdRootPath +"db" + fileSeparator;
		//图片
		imagePath = sdRootPath + ".image" + fileSeparator;
		//缓存
		sdcachePath = sdRootPath + "cache" + fileSeparator;
		//日志
		sdlogPath = sdRootPath + ".log" + fileSeparator;
		//初始化数据库，数据，图片文件夹,日志文件夹
		checkAndCreatePrivateDirectory();
		//初始化缓存文件夹
		checkAndCreateSdDirectory();
        isConnect = NetWorkUtils.GetInternetstate(context);
	}

	/** 检查并创建私有文件夹 */
	private static void checkAndCreatePrivateDirectory() {
		// 创建私有文件夹
		String[] path = {  sdRootPath,databasePath ,dataPath,imagePath,sdcachePath, sdlogPath };
		//这里创建目录要先创建sd/layou/  再创建sd/layou/image/  否则创建失败
		for (String x : path) {
			File fi = new File(x);
			if (!fi.exists()) {
				fi.mkdir();
			} 
		}
	}

	/** 每次启动需要删除的旧文件夹 */
	private static void checkAndCreateSdDirectory() {

		if ((!TextUtils.isEmpty(sdPath) && new File(sdPath).canRead())) {

			String[] path = { sdcachePath,imagePath };
			for (String x : path) {
				File fi = new File(x);
				if (!fi.exists()) {
					fi.mkdir();
				} else {
					FileUtil.DeleteFile(x);
					fi.mkdir();
				}
			}
			// 禁止系统Media搜索程序目录;
			String nomediapath = imagePath + ".nomedia";
			File nomedia = new File(nomediapath);
			try {
				if (!nomedia.exists())
					nomedia.createNewFile();
			} catch (IOException e) {
			}
		}
	}
	
	/** 程序退出清空缓存数据*/
	public static void exit() {
		FileUtil.DeleteFile(sdcachePath);
		FileUtil.DeleteFile(imagePath);
	}

}