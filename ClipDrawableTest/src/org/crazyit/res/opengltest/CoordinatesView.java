package org.crazyit.res.opengltest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/*** @ClassName : CoordinatesView* @author : ZGX zhangguoxiao_happy@163.com* */
public class CoordinatesView extends View {
	/**
	 * �Զ���ؼ�һ��д�������췽�� CoordinatesView(Context context)����javaӲ���봴���ؼ� *
	 * �����Ҫ���Լ��Ŀؼ��ܹ�ͨ��xml�������ͱ����е�2�����췽�� CoordinatesView(Context context, *
	 * AttributeSet attrs) ��Ϊ��ܻ��Զ����þ���AttributeSet������������췽���������̳���View�Ŀؼ�
	 */
	public CoordinatesView(Context context) {
		super(context, null);
	}

	public CoordinatesView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/** �����ⲿ���� */
	Point pa = new Point(10, 10);
	Point pb = new Point(20, 40);
	/** Բ�ģ�����ֵ�������ؼ������Ͻǵģ� */
	Point po = new Point();
	/** �ؼ������ĵ� */
	int centerX, centerY;

	/** �ؼ��������֮������ʾ֮ǰ������������������ʱ���Ի�ȡ�ؼ��Ĵ�С ���õ����������������Բ�����ڵĵ㡣 */
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		centerX = w / 2;
		centerY = h / 2;
		po.set(centerX, centerY);
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/** �Զ���ؼ�һ�㶼������onDraw(Canvas canvas)�������������Լ���Ҫ��ͼ�� */
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		// ��������
		if (canvas != null) {
			canvas.drawColor(Color.WHITE);
			// ��ֱ��
			canvas.drawLine(0, centerY, 2 * centerX, centerY, paint);
			canvas.drawLine(centerX, 0, centerX, 2 * centerY, paint);
			// ��X���ͷ
			int x = 2 * centerX, y = centerY;
			drawTriangle(canvas, new Point(x, y), new Point(x - 10, y - 5),
					new Point(x - 10, y + 5));
			canvas.drawText("X", x - 15, y + 18, paint);
			// ��Y���ͷ
			x = centerX;
			y = 0;
			drawTriangle(canvas, new Point(x, y), new Point(x - 5, y + 10),
					new Point(x + 5, y + 10));
			canvas.drawText("Y", x + 12, y + 15, paint);
			// �����ĵ�����
			// �ȼ��������ǰ���ĵ������ֵ
			String centerString = "(" + (centerX - po.x) / 2 + ","
					+ (po.y - centerY) / 2 + ")";
			// Ȼ����ʾ����
			canvas.drawText(centerString, centerX - 25, centerY + 15, paint);
		}
		if (canvas != null) {
			/** TODO ������ �����ⲿ��Ҫ���������ϻ������ݣ�����������л��� */
			canvas.drawCircle(po.x + 2 * pa.x, po.y - 2 * pa.y, 2, paint);
			canvas.drawCircle(po.x + 2 * pb.x, po.y - 2 * pb.y, 2, paint);
			canvas.drawLine(po.x + 2 * pa.x, po.y - 2 * pa.y, po.x + 2 * pb.x,
					po.y - 2 * pb.y, paint);
			// canvas.drawPoint(pa.x+po.x, po.y-pa.y, paint);
		}
	}

	/*** �������� ���ڻ�������ļ�ͷ */
	private void drawTriangle(Canvas canvas, Point p1, Point p2, Point p3) {
		Path path = new Path();
		path.moveTo(p1.x, p1.y);
		path.lineTo(p2.x, p2.y);
		path.lineTo(p3.x, p3.y);
		path.close();
		Paint paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.FILL);
		// ������������
		canvas.drawPath(path, paint);
	}

	/** ���ڱ����϶�ʱ����һ�����λ�� */
	int x0, y0;

	/** �϶��¼����� */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int action = event.getAction();
		/** (x,y)��Ϊ�����¼�ʱ�ĵ㣬��������ֵΪ����ڸÿؼ����Ͻǵľ��� */
		int x = (int) event.getX();
		int y = (int) event.getY();
		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// ����
			x0 = x;
			y0 = y;
			Log.i("down", "(" + x0 + "," + y0 + ")");
			break;
		case MotionEvent.ACTION_MOVE:
			// �϶�
			/**
			 * �϶�ʱԲ����������˶� (x0,y0)������ǰһ���¼����������� *
			 * �϶���ʱ��ֻҪ�������������deltaֵ��Ȼ��ӵ�Բ���У����ƶ���Բ�ġ� *
			 * Ȼ�����invalidate()��ϵͳ����onDraw()ˢ��������Ļ����ʵ���������ƶ���
			 */
			po.x += x - x0;
			po.y += y - y0;
			x0 = x;
			y0 = y;
			Log.i("move", "(" + x0 + "," + y0 + ")");
			invalidate();
			break;
		case MotionEvent.ACTION_UP:
			// ����
			break;
		}
		/**
		 * ע�⣺����һ��Ҫ����true* ����false��super.onTouchEvent(event)���᱾����ֻ�ܼ�⵽������Ϣ *
		 * ������Ϊfalse��super.onTouchEvent(event)�Ĵ����Ǹ���ϵͳ�ÿؼ����ܴ�����������Ϣ�� *
		 * ����ϵͳ�Ὣ��Щ�¼��������ĸ���������
		 */
		return true;
	}
}
