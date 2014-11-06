package org.crazyit.res.opengltest;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Point;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyCustomGraphic extends View {
	public MyCustomGraphic(Context context) {
		super(context);
	}
	public MyCustomGraphic(Context context, AttributeSet attrs) {
		super(context, attrs);
		getdata();
	}
	private void getdata() {
		list.clear();
		list.add(new Point(20, 100));
		list.add(new Point(110, 100));
		list.add(new Point(110, 190));
		list.add(new Point(20, 190));
		
		list.add(new Point(70, 60));
		list.add(new Point(160, 60));
		list.add(new Point(160, 150));
		list.add(new Point(70, 150));
		
		downx=downy=movex=movey=0;
	}
	String[] names={"A","B","C","D","AA","BB","CC","DD"};
	ArrayList<Point> list=new ArrayList<Point>();
	@Override
	protected void onDraw(Canvas canvas) {

		super.onDraw(canvas);
	
		if(ifclear){
			ifclear=false;
			return;
		}
		
		
		Paint paint=new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.RED);
		for(int i=0;i<4;i++){
			paint.setPathEffect(null);
			canvas.drawLine(list.get(i).x, list.get(i).y, list.get((i+1)%4).x, list.get((i+1)%4).y, paint);
			if(i==3){
				PathEffect effect=new DashPathEffect(new float[]{20,10,5,10}, 1);
				paint.setPathEffect(effect);
			}
			canvas.drawLine(list.get(i).x, list.get(i).y, list.get(i+4).x, list.get(i+4).y, paint);
		}
		for(int i=4;i<8;i++){
			if(i==6||i==7){//������
				PathEffect effect=new DashPathEffect(new float[]{20,10,5,10}, 1);
				paint.setPathEffect(effect);
			}else{
				paint.setPathEffect(null);
			}
			canvas.drawLine(list.get(i).x, list.get(i).y, list.get((i+1)%4+4).x, list.get((i+1)%4+4).y, paint);
		}
		
		paint.setPathEffect(null);
		paint.setColor(Color.GREEN);
		for(int i=0;i<8;i++){
			canvas.drawText(names[i], list.get(i).x, list.get(i).y, paint);
		}
		
		paint.setStyle(Style.STROKE);
		
		path.reset();
		path.addRect(downx, downy, movex, movey, Direction.CW);
		canvas.drawPath(path, paint);
		
	}
	boolean  ifclear=false;
	
	/**����*/
	public void  clearscreen(){
		ifclear=true;
		invalidate();
		
	}
	/**�ػ�*/
	public void resetScreen(){
		ifclear=false;
		getdata();
		invalidate();
	}
	/**�Ŵ�*/
	public void bigDraw(){
	for(int i=0;i<8;i++){
		list.get(i).x*=1.2;
		list.get(i).y*=1.2;
	}
	invalidate();
}
	/**��С*/
	public void smallDraw(){
	for(int i=0;i<8;i++){
		list.get(i).x*=0.8;
		list.get(i).y*=0.8;
	}
	invalidate();
}
private boolean drawRegion=true;//�Ƿ��ڻ�ѡ���
private float downx,downy;//���µ������
private float movex,movey;//�ƶ��е������
Path  path=new Path();//ѡ��� ��·��
private boolean[]  movepoint=new boolean[8];
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(drawRegion){
			drawRegion(event);
		}else{
			resetPicture(event);
		}
		
		return true;
	}


	//��һ��ѡ��������
	public void drawRegion(MotionEvent event){
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			movex=downx=event.getX();
			movey=downy=event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			movex=event.getX();
			movey=event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			//̧���Ժ��ж��£����޿����ǵ�������ļ����㡣
			Region region=new Region((int)downx, (int)downy, (int)movex, (int)movey);
			boolean haspoint=false;
			for(int i=0;i<8;i++){
				if(region.contains(list.get(i).x, list.get(i).y)){
					movepoint[i]=true;
					haspoint=true;
				}
			}
			if(haspoint){
				drawRegion=false;
			}
			break;
		
		}
		
	}

//ѡ�������ƽ�ƣ��Լ�ͼ�ε��ػ�
	float oldx,oldy;
	float newx,newy;
	public void resetPicture(MotionEvent event){
		switch(event.getAction()){
		case MotionEvent.ACTION_DOWN:
			oldx=event.getX();
			oldy=event.getY();
			Region region=new Region((int)downx, (int)downy, (int)movex, (int)movey);
			if(region.contains((int)oldx, (int)oldy)){
				
			}else{
				drawRegion=true;
				for(int i=0;i<8;i++){
					movepoint[i]=false;
				}
				drawRegion(event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			newx=event.getX();
			newy=event.getY();
			float deltx=newx-oldx;
			float delty=newy-oldy;
			
				oldx=newx;
				oldy=newy;
					
			for(int i=0;i<8;i++){
				if(movepoint[i]){
					list.get(i).x+=deltx;
					list.get(i).y+=delty;
				}
			}
			downx+=deltx;
			downy+=delty;
			movex+=deltx;
			movey+=delty;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			
			break;
		
		}
		
	}
	
	
}
