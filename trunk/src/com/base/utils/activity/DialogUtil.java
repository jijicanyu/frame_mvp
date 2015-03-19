package com.base.utils.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.mvp.R;


public class DialogUtil
{

	/**
	 * 自定义dialog的title
	 * 
	 * @param cx
	 * @param textIndex
	 * @param textSize
	 * @param textBgImg
	 * @return
	 */
	public static TextView getCustTitle(Context cx, int textIndex, int textSize, int textBgcolor, int textBgImg)
	{
		TextView title = new TextView(cx);
		title.setText(textIndex);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		if (-1 != textBgImg)
		{
			title.setBackgroundResource(textBgImg);
		}

		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, 80);
		title.setLayoutParams(lp);
		if (textBgcolor != Color.TRANSPARENT)
		{
			title.setBackgroundColor(textBgcolor);
		}
		title.setTextSize(textSize);
		return title;
	}

	/**
	 * 自定义dialog的title
	 * 
	 * @param cx
	 * @param text
	 * @param textSize
	 * @param textBgImg
	 * @return
	 */
	public static TextView getCustTitle(Context cx, String text, int textSize, int textBgcolor, int textBgImg)
	{
		TextView title = new TextView(cx);
		title.setText(text);
		title.setGravity(Gravity.CENTER);
		title.setTextColor(Color.WHITE);
		if (-1 != textBgImg)
		{
			title.setBackgroundResource(textBgImg);
		}

		LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT, 80);
		title.setLayoutParams(lp);
		if (textBgcolor != Color.TRANSPARENT)
		{
			title.setBackgroundColor(textBgcolor);
		}
		title.setTextSize(textSize);
		title.setSingleLine();
		title.setEllipsize(TextUtils.TruncateAt.END);
		return title;
	}
	
	/**
	 * 弹出普通选择提示框
	 * 
	 * @param cx
	 */
	public static void showNormalDialog(final Context cx, DialogInterface.OnClickListener listener,String[] items)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(cx);
		builder.setCustomTitle(getCustTitle(cx, R.string.img_title, 22, Color.TRANSPARENT, -1));
		builder.setItems(items, listener);
		builder.show();
		return;
	}
}