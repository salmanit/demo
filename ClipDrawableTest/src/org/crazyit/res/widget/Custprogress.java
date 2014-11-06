package org.crazyit.res.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class Custprogress extends View {

	public Custprogress(Context context, AttributeSet attrs) {
		super(context, attrs);
		initPaint();
	}
	public Custprogress(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initPaint();
	}
	public Custprogress(Context context) {
		super(context);
		initPaint();
	}

	Paint bgPaint=new Paint();
	Paint pgPaint=new Paint();
	private int angle=0;
	public void setAngle(int angle) {
		this.angle = angle;
		postInvalidate();
	}
	private void initPaint(){
		bgPaint.setColor(Color.GRAY);
		bgPaint.setAntiAlias(true);
		bgPaint.setStrokeWidth(10);
		bgPaint.setStyle(Style.STROKE);
		
		pgPaint.setColor(Color.RED);
		pgPaint.setAntiAlias(true);
		
		pgPaint.setStyle(Style.FILL_AND_STROKE);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawCircle(width/2, height/2, diameter/2-5, bgPaint);//因为圆圈是有宽度的，所以这里的半径减了5，防止画到外边
		if(oval!=null&&angle!=0){
			canvas.drawArc(oval, -90, angle, true, pgPaint);
		}
	}
	
	private int width,height;
	private int diameter;//取宽高比较小的一个值，作为直径
	private RectF oval;
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(width==0||height==0){
			 width=MeasureSpec.getSize(widthMeasureSpec);
			 height=MeasureSpec.getSize(heightMeasureSpec);
			 if(width!=0&&height!=0){
				 diameter=width<height?width:height;
				 
				 oval=new RectF(width/2-diameter/2, height/2-diameter/2, width/2+diameter/2, height/2+diameter/2);
			 }
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
}
