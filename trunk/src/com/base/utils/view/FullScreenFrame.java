package com.base.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class FullScreenFrame extends FrameLayout {
	
	private int width_ = 0;
	private int height_ = 0;
	public FullScreenFrame(Context context) {
		super(context);
	}
	public FullScreenFrame(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FullScreenFrame(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public int getScreenWidth() {
		return width_;
	}

	public int getScreenHeight() {
		return height_;
	}
	
}
