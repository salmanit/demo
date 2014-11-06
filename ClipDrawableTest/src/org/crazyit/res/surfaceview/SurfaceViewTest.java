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
	// SurfaceHolder����ά��SurfaceView�ϻ��Ƶ�����
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
		// ��ʼ��SurfaceHolder����
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
				// ��������SurfaceView
				Canvas canvas = holder.lockCanvas();
				// ���Ʊ���
				Bitmap back = BitmapFactory.decodeResource(
					SurfaceViewTest.this.getResources(), R.drawable.sun);
				//���Ʊ���
				canvas.drawBitmap(back, 0, 0, null);
				// ������ɣ��ͷŻ������ύ�޸�
				holder.unlockCanvasAndPost(canvas);
				//������һ�Σ�"�־û�"�ϴ������Ƶ�����
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
		
		//Ϊsurface�Ĵ����¼��󶨼�����
		surface.setOnTouchListener(new OnTouchListener()
		{
			

			@Override
			public boolean onTouch(View source, MotionEvent event)
			{
				// ֻ�������¼�
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
//					// ����SurfaceView�ľֲ�����ֻ���¾ֲ�����
//					Canvas canvas = holder.lockCanvas(new Rect(cx - 50,
//						cy - 50, cx + 50, cy + 50));
//					// ����canvas�ĵ�ǰ״̬
//					canvas.save();
//					// ��ת����
//					canvas.rotate(30, cx, cy);
//					paint.setColor(Color.RED);
//					// ���ƺ�ɫ����
//					canvas.drawRect(cx - 40, cy - 40, cx, cy, paint);
//					// �ָ�Canvas֮ǰ�ı���״̬
//					canvas.restore();
//					paint.setColor(Color.GREEN);
//					// ������ɫ����
//					canvas.drawRect(cx, cy, cx + 40, cy + 40, paint);
//					// ������ɣ��ͷŻ������ύ�޸�
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
		//��ȡ��Ļ�Ŀ��
		  screenwidth=getResources().getDisplayMetrics().widthPixels;
		   screenheight=getResources().getDisplayMetrics().heightPixels;
		 Canvas canvas=holder.lockCanvas();
		canvas.drawColor(Color.WHITE);//������ɫ�ı���
		
		 Paint paint=new Paint();
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(10);
		canvas.drawLine(0, screenheight/2, screenwidth, screenheight/2, paint);
		canvas.drawLine(5, 0, 5, screenheight, paint);
		holder.unlockCanvasAndPost(canvas);
		//������һ�Σ�"�־û�"�ϴ������Ƶ�����
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