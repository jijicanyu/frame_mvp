package com.base.widget.obj;

import android.graphics.Bitmap;
import android.net.Uri;

public class Media
{

	//本地临时存储
	private Bitmap bitmap;
	private boolean isLocal;
	private Uri uri;
	private String url;
	public Bitmap getBitmap()
	{
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap)
	{
		this.bitmap = bitmap;
	}
	public boolean isLocal()
	{
		return isLocal;
	}
	public void setLocal(boolean isLocal)
	{
		this.isLocal = isLocal;
	}
	public Uri getUri()
	{
		return uri;
	}
	public void setUri(Uri uri)
	{
		this.uri = uri;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	
}
