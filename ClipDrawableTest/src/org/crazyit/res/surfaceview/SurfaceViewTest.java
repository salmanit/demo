package org.crazyit.res.surfaceview;

import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.res.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Description:
 * <br/>site: <a href="http://www.crazyit.org">crazyit.org</a> 
 * <br/>Copyright (C), 2001-2012, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author  Yeeku.H.Lee kongyeeku@163.com
 * @version  1.0
 */
public class SurfaceViewTest extends Activity
{private TimerTask task;
	// SurfaceHolder负责维护SurfaceView上绘制的内容
	private SurfaceHolder holder;
	private Paint paint;
	private int number=0;
	private Timer timer;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.surfacexml);
		paint = new Paint();	
		SurfaceView surface = (SurfaceView) findViewById(R.id.sshow);
		// 初始化SurfaceHolder对象
		holder = surface.getHolder();
		timer=new Timer();
		holder.addCallback(new Callback()
		{
			@Override
			public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
				int arg3)
			{
				System.err.println("changed="+(number++));
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder)
			{
				System.err.println("created="+(number++));
				// 锁定整个SurfaceView
				Canvas canvas = holder.lockCanvas();
				// 绘制背景
				Bitmap back = BitmapFactory.decodeResource(
					SurfaceViewTest.this.getResources(), R.drawable.sun);
				//绘制背景
				canvas.drawBitmap(back, 0, 0, null);
				// 绘制完成，释放画布，提交修改
				holder.unlockCanvasAndPost(canvas);
				//重新锁一次，"持久化"上次所绘制的内容
//				holder.lockCanvas(new Rect(0, 0, 0, 0));
//				holder.unlockCanvasAndPost(canvas);
				drawline();
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder)
			{
				if(timer!=null)
				timer.cancel();
				System.err.println("destoryed="+(number++));
			}
		});
		
		//为surface的触摸事件绑定监听器
		surface.setOnTouchListener(new OnTouchListener()
		{
			

			@Override
			public boolean onTouch(View source, MotionEvent event)
			{
				// 只处理按下事件
				if (event.getAction() == MotionEvent.ACTION_DOWN)
				{
					drawline();
					if(task != null)
					{
						task.cancel();
					}
					task = new TimerTask()
					{
						public void run()
						{
							
							int y=screenheight/2-(int)(100 * 
									Math.sin((x - 5) * 2 * Math.PI / 150));
							Canvas canvas2=holder.lockCanvas(new Rect(x, y-2, x+4, y+2));
							canvas2.drawPoint(x, y, paint);
							x++;
							if(x>screenwidth){
								this.cancel();
								task=null;
							}
							holder.unlockCanvasAndPost(canvas2);
							
						}					
					};
					timer.schedule(task , 0 , 30);		
					
					
					
					
//					int cx = (int) event.getX();
//					int cy = (int) event.getY();
//					// 锁定SurfaceView的局部区域，只更新局部内容
//					Canvas canvas = holder.lockCanvas(new Rect(cx - 50,
//						cy - 50, cx + 50, cy + 50));
//					// 保存canvas的当前状态
//					canvas.save();
//					// 旋转画布
//					canvas.rotate(30, cx, cy);
//					paint.setColor(Color.RED);
//					// 绘制红色方块
//					canvas.drawRect(cx - 40, cy - 40, cx, cy, paint);
//					// 恢复Canvas之前的保存状态
//					canvas.restore();
//					paint.setColor(Color.GREEN);
//					// 绘制绿色方块
//					canvas.drawRect(cx, cy, cx + 40, cy + 40, paint);
//					// 绘制完成，释放画布，提交修改
//					holder.unlockCanvasAndPost(canvas);
				}
				return false;
			}
		});
	}
	int screenwidth,screenheight;
	private int yoff=100;
	private int x=5;
	public void drawline(){
		//获取屏幕的宽高
		  screenwidth=getResources().getDisplayMetrics().widthPixels;
		   screenheight=getResources().getDisplayMetrics().heightPixels;
		 Canvas canvas=holder.lockCanvas();
		canvas.drawColor(Color.WHITE);//画个白色的背景
		
		 Paint paint=new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(10);
		canvas.drawLine(0, screenheight/2, screenwidth, screenheight/2, paint);
		canvas.drawLine(5, 0, 5, screenheight, paint);
		holder.unlockCanvasAndPost(canvas);
		//重新锁一次，"持久化"上次所绘制的内容
		holder.lockCanvas(new Rect(0, 0, 0, 0));
		holder.unlockCanvasAndPost(canvas);
//		timer=new Timer();
//		timer.schedule(new TimerTask() {
//			
//			@Override
//			public void run() {
//				int y=screenheight/2-(int)(100 * 
//						Math.sin((x - 5) * 2 * Math.PI / 150));
//				Canvas canvas2=holder.lockCanvas(new Rect(x, y-2, x+4, y+2));
//				canvas2.drawPoint(x, y, paint);
//			
//				System.out.println("x=="+x+"  y="+y);
//				x++;
//				holder.unlockCanvasAndPost(canvas2);
//				if(x>screenwidth){
//					this.cancel();
//				}
//			}
//		}, 0,30);
		
	}
	
	
	
	
	
}