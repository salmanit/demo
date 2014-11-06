package org.crazyit.res.blank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.graphics.Paint.Style;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {

	private MyObject myObject;

	public CustomView(Context context) {
		super(context);
		initData();
	}

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initData();
	}

	private float factor = 500;

	private void initData() {
		String obj = readxml();
		if (obj != null)
			myObject = new MyObject().parseXml(obj);
		movepoint = new boolean[myObject.getPointList().size()];
	}

	public MyObject getData() {
		return myObject;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawColor(Color.WHITE);
		// 画点
		paint.setColor(Color.RED);
		for (int i = 0; i < myObject.getPointList().size(); i++) {
			MyPoint point = myObject.getPointList().get(i);
			canvas.drawCircle(point.getXcoordinate() * factor,
					point.getYcoordinate() * factor, 2, paint);
		}

		// 画文字
		paint.setColor(Color.BLACK);
		for (int i = 0; i < myObject.getTextList().size(); i++) {
			MyPointText text = myObject.getTextList().get(i);
			canvas.drawText(text.getText(), text.getXcoordinate() * factor,
					text.getYcoordinate() * factor, paint);
		}

		// 画线
		for (int i = 0; i < myObject.getLineList().size(); i++) {
			MyLine line = myObject.getLineList().get(i);
			paint.setColor(Color.parseColor(line.getColor()));
			paint.setStrokeWidth(line.getLineSize());
			float startX = 0, startY = 0, stopX = 0, stopY = 0;
			for (int j = 0; j < myObject.getTextList().size(); j++) {
				MyPointText text = myObject.getTextList().get(j);
				MyPoint point = myObject.getPointList().get(j);
				if (text.getDp() == line.getDp_from()) {
					startX = point.getXcoordinate();
					startY = point.getYcoordinate();
				} else if (text.getDp() == line.getDp_to()) {
					stopX = point.getXcoordinate();
					stopY = point.getYcoordinate();
				}
			}
			canvas.drawLine(startX * factor, startY * factor, stopX * factor,
					stopY * factor, paint);
		}

		paint.setStyle(Style.STROKE);
		paint.setColor(Color.GREEN);
		path.reset();
		path.addRect(downx, downy, movex, movey, Direction.CW);
		canvas.drawPath(path, paint);
	}

	public String readxml() {

		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				getResources().openRawResource(R.raw.object)));

		try {
			while (bReader.ready()) {
				try {
					return bReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean drawRegion = true;// 是否在画选择框
	private float downx, downy;// 按下的坐标点
	private float movex, movey;// 移动中的坐标点
	Path path = new Path();// 选择框 的路径
	private boolean[] movepoint;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (drawRegion) {
			drawRegion(event);
		} else {
			resetPicture(event);
		}

		return true;
	}

	// 画一个选定区域先
	public void drawRegion(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			movex = downx = event.getX();
			movey = downy = event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_MOVE:
			movex = event.getX();
			movey = event.getY();
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			// 抬起以后判断下，有无框到我们的立方体的几个点。
			Region region = new Region((int) downx, (int) downy, (int) movex,
					(int) movey);
			boolean haspoint = false;
			ArrayList<MyPoint> pointlist = myObject.getPointList();
			for (int i = 0; i < movepoint.length; i++) {

				if (region.contains(
						(int) (pointlist.get(i).getXcoordinate() * factor),
						(int) (pointlist.get(i).getYcoordinate() * factor))) {
					movepoint[i] = true;
					haspoint = true;
				}
			}
			if (haspoint) {
				drawRegion = false;
			}
			break;

		}

	}

	// 选定区域的平移，以及图形的重绘
	float oldx, oldy;
	float newx, newy;

	public void resetPicture(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			oldx = event.getX();
			oldy = event.getY();
			Region region = new Region((int) downx, (int) downy, (int) movex,
					(int) movey);
			if (region.contains((int) oldx, (int) oldy)) {

			} else {
				drawRegion = true;
				for (int i = 0; i < movepoint.length; i++) {
					movepoint[i] = false;
				}
				drawRegion(event);
			}
			break;
		case MotionEvent.ACTION_MOVE:
			newx = event.getX();
			newy = event.getY();
			float deltx = newx - oldx;
			float delty = newy - oldy;

			oldx = newx;
			oldy = newy;

			for (int i = 0; i < movepoint.length; i++) {
				if (movepoint[i]) {
					MyPoint point = myObject.getPointList().get(i);
					point.setXcoordinate(point.getXcoordinate() + deltx
							/ factor);
					point.setYcoordinate(point.getYcoordinate() + delty
							/ factor);
					MyPointText text = myObject.getTextList().get(i);
					text.setXcoordinate(text.getXcoordinate() + deltx / factor);
					text.setYcoordinate(text.getYcoordinate() + delty / factor);

				}
			}
			downx += deltx;
			downy += delty;
			movex += deltx;
			movey += delty;
			invalidate();
			break;
		case MotionEvent.ACTION_UP:

			break;

		}

	}

}
