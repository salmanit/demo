package org.crazyit.res.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class MyGridviewSimple extends GridView {

	public MyGridviewSimple(Context context) {
		super(context);
	}

	public MyGridviewSimple(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public MyGridviewSimple(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int height=MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, height);
	}
}
