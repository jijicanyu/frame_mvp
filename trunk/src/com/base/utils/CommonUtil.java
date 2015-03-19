package com.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;


public class CommonUtil
{

    public static String getTimeString(){
        String time="";
        Date date=new Date();
        SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        time=formatter.format(date);
        return time;
    }

    /**
	 * 输入流转化为bitmap
	 * 
	 * @param is
	 * @return
	 */
	public static Bitmap decodeStream(InputStream is)
	{
		BitmapFactory.Options options = new BitmapFactory.Options();
		int be = options.outHeight / 20;// 应该直接除200的，但这里出20是为了增加一位数的精度
		if (be % 10 != 0)
			be += 10; // 尽量取大点图片，否则会模糊
		be = be / 10;
		if (be <= 0) // 判断200是否超过原始图片高度
			be = 1; // 如果超过，则不进行缩放
		options.inSampleSize = be;
		return BitmapFactory.decodeStream(is, null, options);
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件名 以“/”开头表示绝对路径
	 * @return 文件File
	 */
	public static File createFile(String rootPath, String path)
	{
		// 需要检查SD卡是否存在，目前在/data/data/package/files目录下创建文件
		// String rootPath = rootPath;

		if (path.startsWith("./"))
		{
			path = path.substring(2);
		}

		File file = null;
		if (path.startsWith("/"))
		{
			// 是一个绝对路径文件
			file = new File(path);
		}
		else
			file = new File(rootPath + path);

		if (file.exists())
		{ // 文件存在删掉存在文件
			file.delete();
		}

		try
		{
			file.createNewFile();
		}
		catch (Exception e)
		{ // 目录不存在或其他错误
			try
			{
				String parent = file.getParent();
				File pfile = new File(parent);
				pfile.mkdirs(); // 创建目录
				file.createNewFile();

				return file;
			}
			catch (Exception x)
			{
				String parent = rootPath + path;
				parent = parent.replaceAll("\\\\", "/");
				parent = parent.substring(0, parent.lastIndexOf("/"));
				File pfile = new File(parent);
				pfile.mkdirs(); // 创建目录
				try
				{
					file.createNewFile();
					return file;
				}
				catch (Exception x1)
				{
					x1.printStackTrace();
				}
				return null;
			}
		}

		return file;
	}

	public static int px2dip(Context context, float pxValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	public static float dip2px(Context context, float dipValue)
	{
		final float scale = context.getResources().getDisplayMetrics().density;
		return  (dipValue * scale + 0.5f);
	}

	/**
	 * 得到指定文件类型,用于Intent调用系统方法打开
	 * 
	 * @param file
	 * @return 文件类型
	 */
	public static String getMIMEType(File file)
	{
		String type = "";
		String fName = file.getName();
		// 取得扩展名
		String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

		// 依扩展名的类型决定MimeType
		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg")
				|| end.equals("wav"))
		{
			type = "audio";
		}
		else if (end.equals("3gp") || end.equals("mp4"))
		{
			type = "video";
		}
		else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals("bmp"))
		{
			type = "image";
		}
		else if (end.equals("apk"))
		{
			// 安装包
			type = "application/vnd.android.package-archive";
		}
		else if (end.equalsIgnoreCase("doc") || end.equalsIgnoreCase("xls") || end.equalsIgnoreCase("octet-stream"))
		{
			type = "application/msword";
		}
		else if (end.equalsIgnoreCase("txt"))
		{
			type = "text/plain";
		}
		else if (end.equalsIgnoreCase("pdf"))
		{
			type = "application/pdf";
		}
		else if (end.equalsIgnoreCase("html"))
		{
			type = "application/vnd.ms-powerpoint";
		}
		else if (end.equalsIgnoreCase("ppt"))
		{
			type = "application/vnd.ms-powerpoint";
		}
		else
		{
			type = "*";
		}
		// 如果无法直接打开，就跳出软件列表给用户选择
		if (end.equals("apk"))
		{
		}
		else
		{
			if (type.lastIndexOf("/") == -1)
			{
				type += "/*";
			}
		}
		return type;
	}

//	// 设置相关参数
//	public static void setLocationOption(LocationClient mLocationClient)
//	{
//		try
//		{
//			LocationClientOption option = new LocationClientOption();
//			option.setOpenGps(true);
//
//			option.setAddrType("street_number");// 返回的定位结果包含地址信息
//			option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02(bd09ll)
//			option.setScanSpan(60 * 1000);// 设置发起定位请求的间隔时间为10 000ms
//
//			option.setPriority(LocationClientOption.NetWorkFirst); // 设置定位优先级
//			option.disableCache(true);// 禁止启用缓存定位
////			option.setPoiNumber(5); // 最多返回POI个数
//			option.setPoiDistance(1000); // poi查询距离
//
//			option.setPoiExtraInfo(false); // 是否需要POI的电话和地址等详细信息
//			mLocationClient.setLocOption(option);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//	}

	public static String buildQuery(Map<String, String> params, String charset) throws IOException
	{
		if (params == null || params.isEmpty())
		{
			return null;
		}

		StringBuilder query = new StringBuilder();
		Set<Entry<String, String>> entries = params.entrySet();
		boolean hasParam = false;

		for (Entry<String, String> entry : entries)
		{
			String name = entry.getKey();
			String value = entry.getValue();
			// 忽略参数名或参数值为空的参数
			if (areNotEmpty(name, value))
			{
				if (hasParam)
				{
					query.append("&");
				}
				else
				{
					hasParam = true;
				}

				query.append(name).append("=").append(URLEncoder.encode(value, charset));
			}
		}

		return query.toString();
	}

	/**
	 * 检查指定的字符串列表是否不为空。
	 */
	public static boolean areNotEmpty(String... values)
	{
		boolean result = true;
		if (values == null || values.length == 0)
		{
			result = false;
		}
		else
		{
			for (String value : values)
			{
				result &= !StringUtils.isEmpty(value);
			}
		}
		return result;
	}
	/**
	 * 
	 */
	public static String coverString(String string)
	{
		if(StringUtils.isEmpty(string))
		{
			return null;
		}
		else
		{
			return string;
		}
	}
	
	public static String getPayments(ArrayList<String> list)
	{
		String str="";
		if(null!=list)
		{
			for (int i = 0; i < list.size(); i++)
			{
				String string = list.get(i);
				if(StringUtils.isNotEmpty(str))
				{
					str+=";";
				}
				str+=string;;
			}
		}
		return str;
	}
	
	public static String getProductPayments(ArrayList<String> list)
	{
		String str="";
		if(null!=list)
		{
			for (int i = 0; i < list.size(); i++)
			{
				String string = list.get(i);
				if(StringUtils.isNotEmpty(str))
				{
					str+="@@";
				}
				str+=string;;
			}
		}
		return str;
	}

    /**
     *
     * @throws java.io.IOException
     */
    public static Bitmap convertBitmap(File file) throws IOException
    {
        Bitmap bitmap = null;
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        BitmapFactory.decodeStream(fis, null, o);
        fis.close();
        final int REQUIRED_SIZE = 180;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true)
        {
            if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inSampleSize = scale;
        fis = new FileInputStream(file.getAbsolutePath());
        bitmap = BitmapFactory.decodeStream(fis, null, op);
        fis.close();
        return bitmap;
    }
}
