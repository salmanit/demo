package org.crazyit.res.playgif;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class Numberprogress extends ProgressBar {
	//private Paint bgPaint;
	private Paint numberPaint;
	private String text="50%";
	public Numberprogress(Context context, AttributeSet attrs) {
		super(context, attrs);
		setHorizontalScrollBarEnabled(true);
		init();
	}

	private void init() {
	numberPaint=new Paint();
	numberPaint.setAntiAlias(true);
	numberPaint.setColor(Color.BLACK);
	numberPaint.setTextSize(25);
//	bgPaint=new Paint();
//	bgPaint.setColor(Color.YELLOW);
//	bgPaint.setAlpha(200);
//	bgPaint.setStyle(Style.FILL);
	
	}

	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Rect rect=new Rect();
		numberPaint.getTextBounds(text, 0, text.length(), rect);
//		int x=getWidth()/2-rect.centerX();
		int y=getHeight()/2-rect.centerY();
//		canvas.drawText(text, x, y, numberPaint);
		
//		rect.offset(getProgress()*(getWidth())/getMax(), y);
//		canvas.drawRect(rect, bgPaint);
		canvas.drawText(text, getProgress()*(getWidth())/getMax(), y, numberPaint);
		
	}
	
	@Override
	public synchronized void setProgress(int progress) {
		settext(progress);
		super.setProgress(progress);
	}
	
	public void settext(int progress){
		int number=progress*100/getMax();
		text=String.valueOf(number)+" %";
	}
	
}
