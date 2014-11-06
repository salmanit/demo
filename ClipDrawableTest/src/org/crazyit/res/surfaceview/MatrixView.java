package org.crazyit.res.surfaceview;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MatrixView extends View {

	private Matrix matrix=new Matrix();


	public MatrixView(Context context) {
		super(context);
	}

	public MatrixView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//setMyMatrix(30);
	}
	
	public void setMyMatrix(int angle){
		
//		matrix.setValues(new float[]{(float) Math.cos(Math.PI/6),(float) -Math.sin(Math.PI/6),150,(float) Math.sin(Math.PI/6),(float) Math.cos(Math.PI/6),
//				300,0,0,1});
		matrix.setRotate(angle);
		//matrix.setTranslate(150, 200);
		//matrix.postTranslate(100, 200);
		matrix.postTranslate(100, 100);
		matrix.postScale(2f, 2f);
	}
	public Matrix getMyMatrix(){
		return matrix;
	}
	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.choujiang3);
		if(bb)
			canvas.drawColor(Color.BLACK);
		else
		canvas.drawColor(Color.RED);
		Paint paint=new Paint();
		paint.setAntiAlias(true);
//		float matrix_values[] = {1f, 0f, 0f, 0f, -1f, 0f, 0f, 0f, 1f};  
//		matrix.setValues(matrix_values);
//		matrix.postTranslate(0f, bitmap.getHeight() * 2f);  
//		//matrix.postTranslate(bitmap.getWidth(), 0);
//		canvas.drawBitmap(bitmap, 0, 0, paint);
//		canvas.drawBitmap(bitmap, matrix, paint);
//		paint.setColor(Color.RED);
//		
//		float matrix_value[]={-1f,0f,0f,0f,1f,0f,0f, 0f, 1f};
//		matrix.setValues(matrix_value);
//		matrix.postTranslate(bitmap.getWidth()*2f, 0);
//		canvas.drawBitmap(bitmap, matrix, paint);
//		
//		
//		matrix.setRotate(180, bitmap.getWidth()/2f, bitmap.getHeight()/2f);
//		matrix.postTranslate(0f, bitmap.getHeight()*2);
//		canvas.drawBitmap(bitmap, matrix, paint);
		
		
		Matrix m=new Matrix();
		m.preScale(1, -1);
		
		m.postTranslate(0, bitmap.getHeight());
		canvas.drawBitmap(bitmap, m, paint);
		
		
//		RectF rectF=new RectF(100, 200, 150, 400);
//		canvas.drawRect(rectF, paint);
	}
	
boolean bb=false;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int x=(int) event.getX();
		int y=(int) event.getY();
		bb=true;
		invalidate(x-50, y-100, x+50, y+100);
		return true;
	}
}
