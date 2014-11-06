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

public class TestView1 extends View {

	public TestView1(Context context) {
		super(context);
		 bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.choujiang1);
		 
	}

	public TestView1(Context context, AttributeSet attrs) {
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
	private int width;
	private int height;
	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(width==0||height==0){
			width=MeasureSpec.getSize(widthMeasureSpec);
			height=MeasureSpec.getSize(heightMeasureSpec);
			System.out.println("width="+width+" height="+height);
			Matrix m=new Matrix();
			 float scalex=width/(float)bitmap.getWidth();
			 float scaley=height/(float)bitmap.getHeight();
			 float scale=scalex<scaley?scalex:scaley;
			 System.out.println("scale="+scale);
			 m.postScale(scale, scale);
			 System.out.println("bitmap width="+bitmap.getWidth()+" height="+bitmap.getHeight());
			 bg=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);	
		}
		
	}
	private Bitmap twobg;
	private Bitmap bg;
	private boolean tag=false;
	private boolean which;
	public void changebg(){
		if(tag){
			tag=false;
			if(bg!=null&&!bg.isRecycled()){
				bg.recycle();
				bg=null;
				System.gc();
			}
			System.out.println("create begin");
			
			float[] values=new float[9];
			matrix.getValues(values);
			System.out.println("scale x="+values[matrix.MSCALE_X]+" scaley="+values[matrix.MSCALE_Y]);
			if(values[matrix.MSCALE_X]>3||values[matrix.MSCALE_Y]>3){
			which=true;	
			}else{
				which=false;
				bg=Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
			}
			System.out.println("create sucessful");
		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		//canvas.drawBitmap(bitmap, matrix, null);
		try {
			changebg();
			if(which){
				canvas.drawBitmap(bitmap, matrix, null);
			}else{
				canvas.drawBitmap(bg, new Matrix(), null);
			}
			
		} catch (Exception e) {
			System.out.println("====="+e);
		}
		
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
			tag=true;
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
