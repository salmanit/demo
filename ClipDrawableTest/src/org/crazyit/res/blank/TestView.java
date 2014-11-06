package org.crazyit.res.blank;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class TestView extends View {

	public TestView(Context context) {
		super(context);
		 bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.sharepic);
	}

	public TestView(Context context, AttributeSet attrs) {
		super(context, attrs);
		 bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.sharepic);
	}
	public static final int NONE=0;
	public static final int DRAG=1;
	public static final int SCALE=2;
	int mode=NONE;
	private PointF pointF=new PointF();//记录2个手指的中心点坐标
	private Matrix matrix=new Matrix();
	private float oldDistance,newDistance;
	private Bitmap bitmap;
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(bitmap, matrix, null);
	}
	private PointF oldF=new PointF();
	private PointF newF=new PointF();
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		switch (event.getAction()&MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			oldF.x=event.getX();
			oldF.y=event.getY();
			mode=DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode=SCALE;
			computeCenter(pointF, event);
			oldDistance=getdistance(event);
			break;
		case MotionEvent.ACTION_MOVE:
			if(mode==SCALE){
			newDistance=getdistance(event);
			float factor=newDistance/oldDistance;
			matrix.postScale(factor, factor, pointF.x, pointF.y);
			invalidate();
			oldDistance=newDistance;
			}else if(mode==DRAG){
				newF.x=event.getX();
				newF.y=event.getY();
				matrix.postTranslate(newF.x-oldF.x, newF.y-oldF.y);
				invalidate();
				oldF.x=newF.x;
				oldF.y=newF.y;
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			mode=NONE;
			break;
		case MotionEvent.ACTION_UP:
			 mode=NONE;
			break;
		
		default:
			break;
		}
		return true;
	}
	/**compute the distance of two points*/
	public float getdistance(MotionEvent event){
		float x=event.getX(0)-event.getX(1);
		float y=event.getY(0)-event.getY(1);
		return (float) Math.sqrt(x*x+y*y);
	}
	
	public void computeCenter(PointF pointF,MotionEvent event){
		pointF.x=(event.getX(0)+event.getX(1))/2f;
		pointF.y=(event.getY(0)+event.getY(1))/2f;
	}
	
}
