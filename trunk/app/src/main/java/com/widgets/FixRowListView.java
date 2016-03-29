package com.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

//修复 与Scroll共同使用带来的 行数计算出错的问题
public class FixRowListView extends ListView {
	public FixRowListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FixRowListView(Context context) {
		super(context);
	}

	public FixRowListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
