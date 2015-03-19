package com.base.utils.database;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


/**
 * 
 * @描述：数据库操作类
 * @类名：BasicDataBase
 * @版权：Copyright
 */
public class BasicDataBase
{
	private SQLiteDatabase mpDB;
	private String dbPath;
	private boolean isOpenSuccess;
	private static final String TAG = "BasicDataBase";

	/**
	 * 构造函数
	 */
	public BasicDataBase()
	{
		
	}

	/**
	 * 打开指定名称数据库
	 * 
	 * @param dataName
	 *            数据库名称
	 * @param isCreate
	 *            是否创建
	 * @param path
	 *            数据库路径
	 * @param key
	 *            数据库密钥
	 * @return 是否打开成功
	 */
	public boolean open(String dataName, boolean isCreate, String path, String key)
	{
		isOpenSuccess = false;
		try
		{
			if ("".equals(path.trim()))
			{
				//dbPath = Global.getFileRootPath() + EventObj.SYSTEM_DIRECTORY_DATA_DATABASE + "/";
				dbPath = "";
			}
			else
			{
				path = path.endsWith("/") ? path : (path + "/");
				dbPath = path;
			}
			// 创建目录
			File dbFile = new File(dbPath);
			if (!dbFile.exists() && isCreate)
			{
				if (!dbFile.mkdirs())
				{
					return false;
				}
			}
			dbPath = dbPath + dataName;
			dbFile = new File(dbPath);
			// 如果数据库不存在且不创建
			if (!dbFile.exists())
			{
				if (!isCreate)
				{
					return false;
				}
				else
				{
					if (!dbFile.createNewFile())
					{
						return false;
					}
				}
			}

			mpDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE
                    + SQLiteDatabase.CREATE_IF_NECESSARY);

			if (null != mpDB)
			{
				isOpenSuccess = true;
				return true;
			}
			else
			{
				return false;
			}
		}
		catch (SQLException e)
		{
			Log.e(TAG, "database open false:" + e.getMessage());
			return false;
		}
		catch (Exception e)
		{
			Log.e(TAG, "database open false:" + e.getMessage());
			return false;
		}
	}

	/**
	 * 关闭已打开数据库
	 */
	public void close()
	{
		try
		{
			if (isOpenSuccess && null != mpDB)
			{
				mpDB.close();
				mpDB = null;
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "database close false:" + e.getMessage());
		}

	}

	/**
	 * 执行查询语句
	 * 
	 * @param sql
	 *            查询语句
	 * @return 返回查询结果集
	 */
	public ArrayList<String[]> executeQuery(String sql)
	{
		Cursor c = null;
		try
		{
			if (!isOpenSuccess)
			{
				return new ArrayList<String[]>();
			}
			c = mpDB.rawQuery(sql, null);
			ArrayList<String[]> result = new ArrayList<String[]>();

			if (c != null && c.getCount() >= 0)
			{
				int colcnt = c.getColumnCount();
				String[] colNames = new String[colcnt];
				for (int i = 0; i < colcnt; i++)
				{
					colNames[i] = c.getColumnName(i);
				}
				//result.add(colNames);
				if (!c.moveToFirst())
				{
					return result;
				}
				while (!c.isAfterLast())
				{
					String[] row = new String[colcnt];
					for (int i = 0; i < colcnt; i++)
					{
						row[i] = c.getString(i);
					}
					result.add(row);
					c.moveToNext();
				}
			}
			return result;
		}
		catch (Exception e)
		{
			return new ArrayList<String[]>();
		}
		finally
		{
			try
			{
				c.close();
			}
			catch (Exception e)
			{
				return new ArrayList<String[]>();
			}
		}
	}

	/**
	 * 
	 * @param sql
	 *            INSERT、UPDATE 或 DELETE 语句
	 * @return 返回执行是否成功
	 */
	public boolean executeUpdate(String sql)
	{
		if (!isOpenSuccess)
		{
			return false;
		}
		try
		{
			mpDB.execSQL(sql);
		}
		catch (SQLException e)
		{
			Log.e(TAG, "executeUpdate(): Sql = " + sql + ", Error = " + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 设置sql语句执行超时时间，毫秒单位
	 * 
	 * @param nMillisecond
	 *            超时时间
	 */
	public void setTimeOut(int nMillisecond)
	{
		return;
	}

	/**
	 * 
	 * @return 判断数据库表的个数是否为空
	 */
	private boolean isTableExist()
	{
		Cursor c = null;
		try
		{
			boolean result = false; // 表示不存在
			String str = "select count(*) xcount  from sqlite_master where type='table'";
			if (!isOpenSuccess)
			{
				return false;
			}
			c = mpDB.rawQuery(str, null);
			c.moveToFirst();
			int xcount = c.getInt(0);
			// 新建数据库含有android_metadata表
			if (xcount != 1)
			{
				result = true; // 表存在
			}

			return result;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			try
			{
				c.close();
				// mpDB.close();
			}
			catch (Exception e)
			{
				Log.e(TAG, "Cursor close false:" + e.getMessage());
			}

		}

	}

	/**
	 * 
	 * @param tabName
	 *            表名
	 * @return 当前打开数据库是否存在此表
	 */
	public boolean isTableExist(String tabName)
	{
		Cursor c = null;
		try
		{
			boolean result = false; // 表示不存在
			String str = "select count(*) xcount  from sqlite_master where tbl_name='" + tabName + "'";
			if (!isOpenSuccess)
			{
				return false;
			}
			c = mpDB.rawQuery(str, null);
			c.moveToFirst();
			int xcount = c.getInt(0);
			if (xcount != 0)
			{
				result = true; // 表存在
			}
			return result;
		}
		catch (Exception e)
		{
			return false;
		}
		finally
		{
			try
			{
				c.close();
			}
			catch (Exception e)
			{
				Log.e(TAG, "cursor close false:" + e.getMessage());
			}
		}
	}

	/**
	 * 开始事务
	 */
	public void beginTransaction()
	{
		try
		{
			if (mpDB != null)
			{
				mpDB.beginTransaction();
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "beginTransaction false:" + e.getMessage());
		}
		return;
	}

	/**
	 * Marks the current transaction as successful.
	 */
	public void setTransactionSuccessful()
	{
		try
		{
			if (mpDB != null)
			{
				mpDB.setTransactionSuccessful();
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "setTransactionSuccessful false:" + e.getMessage());
		}
		return;
	}

	/**
	 * 关闭事务
	 */
	public void commitTransaction()
	{
		try
		{
			if (mpDB != null)
			{
				mpDB.endTransaction();
			}
		}
		catch (Exception e)
		{
			Log.e(TAG, "commitTransaction false:" + e.getMessage());
		}
		return;
	}

}