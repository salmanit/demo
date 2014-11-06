package org.crazyit.res.testdemo;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

public class MatrixLearn1 extends View {

	private float density;
	public MatrixLearn1(Context context, AttributeSet attrs) {
		super(context, attrs);
		density=getResources().getDisplayMetrics().density;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		
		
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.choujiang5);
		Matrix matrix=new Matrix();
		
		canvas.drawBitmap(bitmap, matrix, null);
		matrix.postTranslate(50*density, 50*density);
		//canvas.drawBitmap(bitmap, matrix, null);
		matrix.postScale(2f, 2f);
		
		canvas.drawBitmap(bitmap, matrix, null);
		
		
		
		Paint paint=new Paint();
		paint.setColor(Color.parseColor("#00ff00"));
		paint.setAntiAlias(true);
		paint.setStyle(Style.FILL);
		canvas.drawCircle(50*density, 50*density, 5*density, paint);
		canvas.drawCircle(100*density, 100*density, 5*density, paint);
	}
	
}
