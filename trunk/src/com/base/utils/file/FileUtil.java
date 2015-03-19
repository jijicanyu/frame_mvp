package com.base.utils.file;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.util.Log;
import org.apache.commons.lang.StringUtils;
import org.apache.http.util.ByteArrayBuffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileUtil
{
	public static final String tag = "FileUtil";

	public static String readFile(String path, Context context)
	{
		byte[] databytes = readFileBytes(path, context);
		if (databytes != null)
		{
			return new String(databytes);
		}
		return null;
	}

	public static byte[] readFileBytes(String path, Context context)
	{
		InputStream is = getFileInputStream(path, context);
		if (is != null)
		{
			byte[] data = new byte[1024];
			ByteArrayBuffer buf = new ByteArrayBuffer(1024);
			int n = 0;
			int receivedlength = 0;
			try
			{
				while ((n = is.read(data)) != -1)
				{
					buf.append(data, 0, n);
					receivedlength = receivedlength + n;
				}
				data = null;
				is.close();
			}
			catch (IOException e)
			{
				Log.e(tag, "readFileBytes(): " + e.getMessage());
			}
			return buf.toByteArray();
		}
		return null;
	}

	public static InputStream getFileInputStream(String filename, Context context)
	{
		InputStream inputstream = null;
		if (null == filename || filename.length() == 0)
		{
			return null;
		}

		try
		{
			File pfile = new File(filename);
			if (pfile.exists() && (!pfile.isDirectory()))
			{
				inputstream = new FileInputStream(pfile);
			}
		}
		catch (IOException e)
		{
			Log.e(tag, "getFileInputStream(): " + e.getMessage());
			return null;
		}

		return inputstream;
	}

	/**
	 * copy file form src to dest, the two files can be mounted on different
	 * point
	 * 
	 * @param srcFile
	 *            : src file
	 * @param destFile
	 *            : dest file
	 * @return: true if everything is OK otherwise false
	 */
	public static boolean copyFile(File srcFile, File destFile)
	{
		if ((null == srcFile) || (null == destFile) || !srcFile.exists() || srcFile.isDirectory())
		{
			return false;
		}

		if (!destFile.exists())
		{
			destFile = createFile(destFile.getAbsolutePath());
			return false;
		}

		boolean isOK = true;
		FileChannel out = null;
		FileChannel in = null;
		try
		{
			out = new FileOutputStream(destFile).getChannel();
			in = new FileInputStream(srcFile).getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(102400);
			int position = 0;
			int length = 0;
			while (true)
			{
				length = in.read(buffer, position);
				if (length <= 0)
				{
					break;
				}
				buffer.flip();
				out.write(buffer, position);
				position += length;
				buffer.clear();
			}
		}
		catch (Exception e)
		{
			isOK = false;
			Log.e(tag, "copyFile(): " + e.getMessage());
		}
		finally
		{
			if (out != null)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					Log.e(tag, "copyFile(): " + e.getMessage());
				}
			}
			if (in != null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					Log.e(tag, "copyFile(): " + e.getMessage());
				}
			}
		}
		return isOK;
	}

	public static void copyAllDirectory(String oldpath, String newpath, Context context)
	{
		if (null == oldpath || null == newpath)
		{
			return;
		}

		File oldFile = new File(oldpath);
		String[] appfiles = null;
		try
		{
			appfiles = oldFile.list();
		}
		catch (Exception e)
		{
			appfiles = null;
			Log.e(tag, "copyAllDirectory(): " + e.getMessage());
		}

		// 此时已经不是目录需要直接把文件移过去
		if (appfiles == null || appfiles.length == 0)
		{
			try
			{
				File outFile = new File(newpath);
				if (!outFile.exists())
				{
					createFile(newpath);
				}

				InputStream in = new FileInputStream(oldFile);
				OutputStream out = new FileOutputStream(outFile);
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0)
				{
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
			catch (Exception e)
			{
				Log.e(tag, "copyAllDirectory(): " + e.getMessage());
				return;
			}
			return;

		}
		else
		{// 需要递归的把所有文件都创建了
			// 首先应该把文件夹创建了
			File fi = new File(newpath + "/");
			if (!fi.exists())
			{
				fi.mkdirs();
			}
			int sz = appfiles.length;
			for (int i = 0; i < sz; i++)
			{
				String subdir = appfiles[i];
				String subPath = oldpath + "/" + subdir;
				String subRootPath = newpath + "/" + subdir;
				copyAllDirectory(subPath, subRootPath, context);
			}
		}
		return;
	}

	/**
	 * 创建文件
	 * 
	 * @param path
	 *            文件名 以“/”开头表示绝对路径
	 * @return 文件File
	 */
	public static File createFile(String path)
	{
		if (path.startsWith("./"))
		{
			path = path.substring(2);
		}

		File file = null;
		// 是一个绝对路径文件
		if (path.startsWith("/"))
		{
			file = new File(path);
		}
		else
		{
			//file = new File(GlobalConfig.FileRootPath + path);
		}

		if (file.exists())
		{ // 文件存在删掉存在文件
			file.delete();
		}

		try
		{
			file.createNewFile();
		}
		catch (Exception e)
		{
			Log.e(tag, "createFile(): " + e.getMessage());
			try
			{
				String parent = file.getParent() + "/";
				File pfile = new File(parent);
				pfile.mkdirs();
				file.createNewFile();
				return file;
			}
			catch (Exception x)
			{
				Log.e(tag, "createFile(): " + e.getMessage());
				return new File("");
			}
		}

		return file;
	}

	/**
	 * 删除文件或目录
	 * 
	 * @param path
	 * @return
	 */
	public static boolean DeleteFile(String path)
	{
		File f = new File(path);
		boolean ret = true;

		if (!f.exists())
		{
			ret = false;
		}
		else if (f.exists())
		{
			if (f.isDirectory())
			{
				File[] files = f.listFiles();
				for (int i = 0; i < files.length; i++)
				{
					if (!DeleteFile(files[i].getPath()))
					{
						ret = false;
					}
				}
			}
			else if (!f.delete())
			{
				ret = false;
			}
		}
		return ret;
	}
	
	public static boolean saveBitmapToFile(Bitmap mBitmap, String filepath)
	{
		if (null == mBitmap || null == filepath || 0 == filepath.trim().length())
		{
			return false;
		}

		boolean result = true;
		File file = createFile(filepath);
		try
		{
			FileOutputStream fos = new FileOutputStream(file);
			result = mBitmap.compress(CompressFormat.JPEG, 85, fos);
			fos.flush();
			fos.close();
		}
		catch (IOException e)
		{
			Log.e(tag, "saveBitmapToFile(): " + e.getMessage());
			result = false;
		}
		return result;
	}

    //获取文件目录
    public static String getFileDir(String pathstr)
    {
        if(StringUtils.isNotBlank(pathstr)) {
            // 获取相对路径
            int i = pathstr.lastIndexOf("/");
            return pathstr.substring(0, i);
        }
        else
            return "";
    }


    public static String getExtName(String s) {
        int i = s.lastIndexOf(".");
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? " " : s.substring(i, s.length()) : " ");
    }

    public static String getFileName(String s) {
        int i = s.lastIndexOf("/");
        int leg = s.length();
        return (i > 0 ? (i + 1) == leg ? "" : s.substring(i+1, s.length()) : " ");
    }


    /** *//**文件重命名
 * @param dir 文件目录
 * @param oldname  原来的文件名
 * @param newname 新文件名
 */
public static void renameFileToPath(String dir,String oldname,String newname)
{
    if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
        File oldfile=new File(dir+"/"+oldname);
        File newfile=new File(dir+"/"+newname);
        if(!oldfile.exists()){
            return;//重命名文件不存在
        }
        if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            System.out.println(newname+"已经存在！");
        else{
            oldfile.renameTo(newfile);
        }
    }else{
        System.out.println("新文件名和旧文件名相同...");
    }
}

    /** *//**文件重命名
 * @param filepath 文件目录
 * @param newname 新文件名
 */
public static String renameToNewName(String filepath,String newname)
{
    String oldname=getFileName(filepath);
    String newpath="";
    if(!oldname.equals(newname)){//新的文件名和以前文件名不同时,才有必要进行重命名
        File oldfile=new File(filepath);
        newpath=getFileDir(filepath)+"/"+newname;
        File newfile=new File(newpath);
        if(!oldfile.exists()){
            return  "";//重命名文件不存在
        }
        if(newfile.exists())//若在该目录下已经有一个文件和新文件名相同，则不允许重命名
            System.out.println(newname+"已经存在！");
        else{
            oldfile.renameTo(newfile);
        }
    }else{
        System.out.println("新文件名和旧文件名相同...");
    }
    return newpath;
}


    //遍历删除目录
    public static void delDis(String filepath){
        try {
            File f = new File(filepath);//定义文件路径
            if (f.exists() && f.isDirectory()) {//判断是文件还是目录
                if (f.listFiles().length == 0) {//若目录下没有文件则直接删除
                    f.delete();
                } else {//若有则把文件放进数组，并判断是否有下级目录
                    File delFile[] = f.listFiles();
                    int i = f.listFiles().length;
                    for (int j = 0; j < i; j++) {
                        if (delFile[j].isDirectory()) {
                            delDis(delFile[j].getAbsolutePath());//递归调用del方法并取得子目录路径
                        }
                        delFile[j].delete();//删除文件
                    }
                }
                delDis(filepath);//递归调用
            }
        }
        catch (Exception ec){}
    }

    //遍历删除目录
    public static void delFile(String filename)
    {
        try{
            File file=new File(filename);
            if(file.isDirectory())
                return ;
            if(file.exists())
            {
                file.delete();
            }
        }
        catch (Exception ec)
        {}
    }

}