package org.crazyit.res.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class HorizontalProgressbar extends ProgressBar {

	public HorizontalProgressbar(Context context) {
		super(context);
	}

	public HorizontalProgressbar(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(true);
	}
	public HorizontalProgressbar(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		setHorizontalScrollBarEnabled(true);
	}


	@Override
	protected synchronized void onDraw(Canvas canvas) {
		canvas.rotate(-90);//反转90度，将水平ProgressBar竖起来
		canvas.translate(-getHeight(), 0);//将经过旋转后得到的VerticalProgressBar移到正确的位置,注意经旋转	后宽高值互换

		super.onDraw(canvas);
	}
	
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(h, w, oldw, oldh);
	}
	
}
