package org.crazyit.res.customdraw;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View.MeasureSpec;

public class MyDrawView extends View implements View.OnTouchListener{

	private int screenwidth;
	private int screenheight;
	private Bitmap bitmap;
	private Paint paint;
	private ScaleGestureDetector detector;
	
	public MyDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint=new Paint();
		paint.setAntiAlias(true);
		detector=new ScaleGestureDetector(context, mygestureListener);
		setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				//detector.onTouchEvent(event);
				switch (event.getAction()&MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					
					break;
				case MotionEvent.ACTION_MOVE:
					
					break;
				case MotionEvent.ACTION_POINTER_DOWN:
					
					break;
				case MotionEvent.ACTION_UP:
					
					break;

				default:
					break;
				}
				return true;
			}
		});
	}

	public void setBG(Bitmap bitmap){
		this.bitmap=bitmap;
		ViewGroup.LayoutParams params=getLayoutParams();
		params.width=bitmap.getWidth();
		params.height=bitmap.getHeight();
		setLayoutParams(params);
		//postInvalidate();
		System.out.println("bpwidth="+bitmap.getWidth()+"bpheight="+bitmap.getHeight()+" width="+screenwidth+" height="+screenheight);
		float scalex=((float)screenwidth)/params.width;
		float scaley=((float)screenheight)/params.height;
		old_factor=scalex<scaley?scalex:scaley;
		System.out.println("old factor=="+old_factor);
		matrix.postScale(old_factor, old_factor);
		
		postInvalidate();
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if(bitmap!=null){
			canvas.drawBitmap(bitmap, matrix, paint);
		}
			
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		  int width = MeasureSpec.getSize(widthMeasureSpec);
		 int height = MeasureSpec.getSize(heightMeasureSpec);
		 if((width!=0&&height!=0)&&(screenheight==0||screenwidth==0)){
			 screenheight=height;
			 screenwidth=width;
		 }
		System.out.println("onMeasure");
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		
		return false;
	}
	private float old_factor=1;
	private Matrix matrix=new Matrix();
	private int mode=0;
	public final static int NONE=0;
	public final static int ZOOM=1;
	OnScaleGestureListener mygestureListener=new OnScaleGestureListener() {
		
		@Override
		public void onScaleEnd(ScaleGestureDetector detector) {
			mode=NONE;
			
		}
		
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mode=ZOOM;
			return true;
		}
		
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float new_factor=detector.getScaleFactor();
				matrix.postScale(new_factor, new_factor);
				postInvalidate();
				
			return true;
		}
	};
}
