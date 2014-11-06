package org.crazyit.res;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.FloatMath;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class JUSTtest extends Activity {

	boolean big;
	ImageView iv;
	ScaleGestureDetector gestureDetector;
	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;

	private int mode = NONE;
	private float oldDist;
	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();
	private PointF start = new PointF();
	private PointF mid = new PointF();

	private Button btn;
	private float oldFactor=1.0f;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.justtest);
		iv = (ImageView) findViewById(R.id.iv1);
		btn=(Button) findViewById(R.id.btnnotify);
		gestureDetector=new ScaleGestureDetector(this, new OnScaleGestureListener() {
			
			@Override
			public void onScaleEnd(ScaleGestureDetector detector) {
			}
			
			@Override
			public boolean onScaleBegin(ScaleGestureDetector detector) {
				 //一定要返回true才会进入onScale()这个函数
				return true;
			}
			@Override
			public boolean onScale(ScaleGestureDetector detector) {
				float factor=detector.getScaleFactor();
				float nowFactory=factor*oldFactor;
				matrix.postScale(nowFactory, nowFactory, detector.getFocusX(), detector.getFocusY());
				//iv.setImageMatrix(matrix);
				System.out.println("gesture scale="+nowFactory+" x=="+detector.getFocusX()+" y=="+detector.getFocusY());
				oldFactor=nowFactory;
				return true;
			}
		});

		OnTouchListener touchListener1=new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
				savedMatrix.set(matrix);
				start.set(event.getX(), event.getY());
				mode = DRAG;
				break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_POINTER_UP:
				mode = NONE;
				break;
				//多点触控
				case MotionEvent.ACTION_POINTER_DOWN:
				oldDist = spacing(event);
				if (oldDist > 10f) {
				savedMatrix.set(matrix);
				midPoint(mid, event);
				mode = ZOOM;
				}
				break;
				case MotionEvent.ACTION_MOVE:
				if (mode == DRAG) {
				matrix.set(savedMatrix);
				matrix.postTranslate(event.getX() - start.x, event.getY()
				- start.y);
				} else if (mode == ZOOM) {
					
				
				float newDist = spacing(event);
				if (newDist > 10f) {
				matrix.set(savedMatrix);
				float scale = newDist / oldDist;
				//gestureDetector.onTouchEvent(event);
				matrix.postScale(scale, scale, mid.x, mid.y);
				
				}
				}
				break;
				}
				iv.setImageMatrix(matrix);
				return true;

			}
		};
		OnTouchListener touchListener2=new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()& MotionEvent.ACTION_MASK) {
				case MotionEvent.ACTION_DOWN:
					mode = 1;
					oldx = event.getRawX();
					oldy = event.getRawY();

					break;
				case MotionEvent.ACTION_MOVE:

					if (mode >= 2) {
						System.out.println("手指开始滑动");
					//	gestureDetector.onTouchEvent(event);
						float newDist = spacing(event);

						if (newDist > oldDist + 1) {
							zoom(newDist / oldDist);
							oldDist = newDist;
						}
						if (newDist < oldDist - 1) {
							zoom(newDist / oldDist);
							oldDist = newDist;
						}

					} else {
						newx = event.getRawX();
						newy = event.getRawY();
						int deltx = (int) (newx - oldx);
						int delty = (int) (newy - oldy);
						if (Math.abs(deltx) > 5 || Math.abs(delty) > 5) {
							FrameLayout.LayoutParams ivlp = (FrameLayout.LayoutParams) iv
									.getLayoutParams();
							int left = ivlp.leftMargin + deltx;
							int top = ivlp.topMargin + delty;
							int right = ivlp.rightMargin - delty;
							int bottom = ivlp.bottomMargin - deltx;
							System.out.println("````````" + left + "==" + top
									+ "==" + right + "==" + bottom);
							ivlp.setMargins(left, top, right, bottom);
							iv.requestLayout();
							oldx = newx;
							oldy = newy;
						}
					}

					break;
				case MotionEvent.ACTION_POINTER_DOWN:

					System.out.println("ACTION_POINTER_DOWN");
					mode += 1;
					oldDist = spacing(event);
					break;
				case MotionEvent.ACTION_POINTER_UP:
					System.out.println("ACTION_POINTER_UP");
					mode -= 1;
					System.out.println("====count"+event.getPointerCount());
					break;
				case MotionEvent.ACTION_UP:
					System.out.println("ACTION_UP");
					mode = 0;
					break;
				default:
					break;
				}
				return true;
			
			}
		};
		iv.setOnTouchListener(touchListener1);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			click1();
			}
		});
		notifyManger=(NotificationManager) getSystemService(Activity.NOTIFICATION_SERVICE);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	boolean tag=true;
	private int messageNum;
	private NotificationManager notifyManger;

	private void click(){
		TaskStackBuilder  stackBuilder=TaskStackBuilder.create(this);
		stackBuilder.addParentStack(JUSTtest.class);
		Intent intent=new Intent(this, JUSTtest.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pendingIntent=stackBuilder.getPendingIntent(11, PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder budiler=new NotificationCompat.Builder(this).setAutoCancel(true)
				.setTicker("来消息了哦")
				.setContentTitle("contenttitle")
				.setContentText("contenttext")
				.setSmallIcon(R.drawable.icon)
				
				.setContentIntent(pendingIntent);
		notifyManger.notify(1, budiler.build());
		finish();
	}
	private void click1(){
		Intent intent=new Intent(this, JUSTtest.class);
		//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
		PendingIntent pendingIntent=PendingIntent.getActivity(this,11,intent ,PendingIntent.FLAG_UPDATE_CURRENT);
		
		NotificationCompat.Builder budiler=new NotificationCompat.Builder(this).setAutoCancel(true)
				.setTicker("来消息了哦")
				.setContentTitle("contenttitle")
				.setContentText("contenttext")
				.setSmallIcon(R.drawable.icon)
				
				.setContentIntent(pendingIntent);
		notifyManger.notify(1, budiler.build());
		finish();
	}
	
	
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
		}

	
	public void zoom(float factor) {
		iv.setLayoutParams(new FrameLayout.LayoutParams(
				(int) (iv.getWidth() * factor), (int) (iv.getHeight() * factor)));
	}

	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	private float oldx, oldy, newx, newy;
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
}
