package org.crazyit.res.custompiechart;


import java.util.ArrayList;
import org.crazyit.res.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class PieView extends View implements Runnable,OnTouchListener{

	private Paint mPaint;//Ĭ�ϻ���
	private Paint textPaint;//�������ֻ���
	private float startAngle;//��ʼ�Ƕ�
	private Bitmap myBg;//����λͼ
	private int screenHight;
	private int screenWidth;
	private Point centerPoint;//���ĵ�����
	private float radius;//�뾶
	private boolean firstEnter=true;//�����Ƿ��Ѿ��˳�
	private float startSpeed;//��ʼ�ٶ�
	private int itemCount;//ͼ��ĸ���
	private static ArrayList<ChartProp> mChartProps;
	private boolean rotateEnabled = false;//ͼ���Ƿ������ת
	public boolean isRotateEnabled() {
		return rotateEnabled;
	}

	public void setRotateEnabled(boolean rotateEnabled) {
		this.rotateEnabled = rotateEnabled;
	}

	private boolean tounched = false;
	private boolean done = false;//����runnable�Ƿ�ѭ���ġ�
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	private Canvas mCanvas;
	private float sweepAngle;
	private static float[] percents;//��������ռ�İٷֱȡ���������
	
	private static ChartProp mChartProp;//���±ߵ��Ǹ�ͼ��
	private static ChartProp tChartProp;
	private ChartPropChangeListener listener;
	private float m_x;//��ָ�������������
	private float m_y;
	public PieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		mChartProps = new ArrayList<ChartProp>();
		// ͼ�񻭱�
				mPaint = new Paint();
		mPaint.setAntiAlias(true);
		// ���ֻ���
				textPaint = new Paint();
				textPaint.setTextSize(22);
				textPaint.setColor(Color.WHITE);
				textPaint.setAntiAlias(true);
				//ͼ�γ�ʼ���Ƕ�
				startAngle = 90f;

				myBg = BitmapFactory.decodeResource(getResources(),
						R.drawable.mask_piechartformymoney);
				setOnTouchListener(this);
	}

	/**
	 * create charts' property ������״ͼ������
	 * 
	 * @param chartsNum
	 *            charts' number ��״ͼ�ĸ���
	 * @return charts' property's list ��״ͼ���Ե�list
	 */
	public ArrayList<ChartProp> createCharts(int itemCount) {
		this.itemCount = itemCount;
		percents = new float[itemCount];
		mChartProps.clear();
		createChartProp(itemCount);

		return mChartProps;
	}
	
	/**
	 * actually create chartProp objects. ���������������Եķ���
	 * 
	 * @param chartsNum
	 *            charts' number ��״ͼ�ĸ���
	 */
	private void createChartProp(int chartsNum) {
		for (int i = 0; i < chartsNum; i++) {
			ChartProp chartProp = new ChartProp(this);
			chartProp.setId(i);
			mChartProps.add(chartProp);
		}
	}
	// ��һ�β������иı��ĳ�ʼ������
	public void initPercents() {

		for (int i = 0; i < itemCount; i++) {

			percents[i] = mChartProps.get(i).getPercent();
			mChartProps.get(i).setPercent(0.01f);
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//System.out.println("onmeasure ...width="+widthMeasureSpec+" height="+heightMeasureSpec);
		screenHight=measureHeight(heightMeasureSpec);
		screenWidth=measureWidth(widthMeasureSpec);
		//Log.e("onmeasure..", "screenWidth01="+screenWidth);
		initResoure();
		//setMeasuredDimension((int) screenWidth, (int) screenHight);
		setMeasuredDimension(screenWidth, screenHight);
		
	}
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		//System.out.println("onlayout.."+"changed="+changed+" left="+left+" top=="+top +" right=="+right+" bottom=="+bottom);
		super.onLayout(changed, left, top, right, bottom);
	}
	
	@Override
	public void layout(int l, int t, int r, int b) {
		//System.out.println("layout.."+" left="+l+" top=="+t +" right=="+r+" bottom=="+b);
		super.layout(l, t, r, b);
	}
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		//System.out.println("onsizechanged..."+" width="+w+" height="+h+" oldw="+oldw+" oldh=="+oldh);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void initResoure() {
		float y=myBg.getHeight();
		float x=myBg.getWidth();
		float r;
		if(screenHight>screenWidth){
			r=screenWidth;
			myBg = createBitmap(myBg, screenWidth, (screenWidth * y) / x);
		}else{
			r=(screenHight * x) / y;
			myBg = createBitmap(myBg, (screenHight * x) / y, screenHight);
		}
		centerPoint = new Point();
		centerPoint.x = (int) (r / 2);
		centerPoint.y = (int) ((r * 19) / 32);
		radius = (centerPoint.x * 0.8843f);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//System.out.println("ondraw");
		
		super.onDraw(canvas);
	}
	
	@Override
	public void draw(Canvas canvas) {
		//System.out.println("draw");
		super.draw(canvas);
		

		if (firstEnter) {
			firstInit();
		} else {

			if (!tounched) {

				float middleAngle = ((mChartProp.getEndAngle() + mChartProp
						.getStartAngle()) / 2);
				float distance = (middleAngle - getBorderAngle());
				//System.out.println("startangle=="+startAngle+" borderangle=="+getBorderAngle());
				startAngle -= (distance / 2);//������Ϊ�˼����ƶ��ٶȡ���̬һЩ������һ���ƶ�һ�롣��
				
				
				//System.out.println("startangle=="+startAngle);
			}

		}

		mCanvas = canvas;

		float f1 = centerPoint.x;
		float f2 = centerPoint.y;
		// ��䱳��ɫ
		/*
		 * mCanvas.drawColor(0xff639EC3); mCanvas.save();
		 */

		// *********************************ȷ���ο�����*************************************
		float f3 = f1 - radius;// X�� - ��
		float f4 = f2 - radius; // Y�� - ��
		float f5 = f1 + radius; // X�� - ��
		float f6 = f2 + radius; // Y�� - ��
		RectF rectF = new RectF(f3, f4, f5, f6);

		// *********************************��ÿ���������ɫ��*********************************
		drawItem(rectF);
		// *********************************�����Ͻ����Բ������*******************************

		drawableCenterCircle(f1, f2);
		// ����Canvas������Ⱦ��ǰͼ��

		if (mChartProp!=null&&!mChartProp.equals(tChartProp)) {

			listener.getChartProp(mChartProp);
			tChartProp = mChartProp;
		}

	}
	
	/*********************************��ÿ������*********************************/
	public void drawItem(RectF localRectf) {
		float temp = startAngle;
		float borderAngle = getBorderAngle();

		for (int i = 0; i < itemCount; i++) {
			mPaint.setColor(mChartProps.get(i).getColor());
			// startAngleΪÿ���ƶ��ĽǶȴ�С
			sweepAngle = mChartProps.get(i).getSweepAngle();
			mChartProps.get(i).setStartAngle(temp);

			/*
			 * oval��Բ�����ڵ���Բ���� startAngle��Բ������ʼ�Ƕȡ�sweepAngle��Բ���ĽǶȡ�
			 * useCenter���Ƿ���ʾ�뾶���ߣ�true��ʾ��ʾԲ����Բ�ĵİ뾶���ߣ�false��ʾ����ʾ�� paint������ʱ��ʹ�õĻ���
			 */
			mCanvas.drawArc(localRectf, temp, sweepAngle, true, mPaint);
			mCanvas.save();
			/*
			 * drawText(localRectf, temp, sweepAngle, mChartProps.get(i)
			 * .getName());
			 */
			temp += sweepAngle;
			mChartProps.get(i).setEndAngle(temp);

			getCurrentChartProp(mChartProps.get(i), borderAngle);
		}
	}
	public void getCurrentChartProp(ChartProp chartProp, float f) {

		if ((chartProp.getStartAngle() <= f) && (chartProp.getEndAngle() > f)) {
			mChartProp = chartProp;
		}
	} 
	public void drawableCenterCircle(float x, float y) {
		/*
		 * Paint centerCirclePaint = new Paint();
		 * centerCirclePaint.setColor(Color.BLACK);
		 * 
		 * centerCirclePaint.setAlpha(150); mCanvas.drawCircle(x, y, radius / 3,
		 * centerCirclePaint);
		 */

		// Paint localPaint = new Paint();
		// ����ȡ�����Ч��
		// localPaint.setAntiAlias(true);
		// ���ΪԲ��
		/*
		 * localPaint.setStyle(Paint.Style.STROKE); // Բ�����
		 * localPaint.setStrokeWidth(circleRadius);
		 */
		// Բ������

		mCanvas.drawBitmap(myBg, 0, 0, null);
		mCanvas.save();

		textPaint.setColor(Color.WHITE);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setTextSize(x/15);
	 
		mCanvas.drawText("�ܼ�", x, y-x/30, textPaint);
		mCanvas.drawText("�ܼ�", x, y+x/10, textPaint);
		mCanvas.save();
	}
	
	
	
	float getBorderAngle() {
		
		float borderAngle;
		int circleCount = 0;
		if (startAngle >= 0) {
			circleCount = (int) (startAngle / 360);
			if ((startAngle % 360) > (90)) {
				borderAngle = (float) (90 + 360 * (circleCount + 1));
			} else {
				borderAngle = (float) (90 + 360 * circleCount);
			}

		} else {
			circleCount = (int) (startAngle / 360);
			if ((startAngle % 360) < (-270)) {
				borderAngle = (float) (90 + 360 * (circleCount - 1));
			} else {
				borderAngle = (float) (90 + 360 * circleCount);
			}
		}
		//System.out.println("startangle"+startAngle+" borderangle=="+borderAngle);
		return borderAngle;
	}
	
	
	private void firstInit() {
		startSpeed += 0.05f;
		if (startSpeed >= 1) {
			startSpeed = 0.9f;
		}
		float total = 0f;

		for (int i = 0; i < itemCount; i++) {
			float percent = mChartProps.get(i).getPercent();
			// writeLog("percent="+percent);
			float distancePercent = (percents[i] - percent);

			if (distancePercent < 0.0001) {
				mChartProps.get(i).setPercent(percents[i]);

			} else {

				mChartProps.get(i).setPercent(
						percent + (distancePercent * startSpeed));
			}

			total = total + mChartProps.get(i).getPercent();
		}
		if (total >= 1) {
			firstEnter = false;
		}

	}
	
	
	public void setChartPropChangeListener(ChartPropChangeListener listener) {
		this.listener = listener;
	}
	
	
	private Bitmap createBitmap(Bitmap b, float x, float y) {
		int w = b.getWidth();
		int h = b.getHeight();
		float sx = (float) x / w;
		float sy = (float) y / h;
		Matrix matrix = new Matrix();
		matrix.postScale(sx, sy);
		Bitmap resizeBmp = Bitmap.createBitmap(b, 0, 0, w, h, matrix, true);
		return resizeBmp;
	}

	private int measureWidth(int pWidthMeasureSpec) {
		int result = 0;
		int widthMode = MeasureSpec.getMode(pWidthMeasureSpec);// �õ�ģʽ
		int widthSize = MeasureSpec.getSize(pWidthMeasureSpec);// �õ��ߴ�

		switch (widthMode) {
		/**
		 * mode�������������ȡֵ�ֱ�ΪMeasureSpec.UNSPECIFIED, MeasureSpec.EXACTLY,
		 * MeasureSpec.AT_MOST��
		 * 
		 * 
		 * MeasureSpec.EXACTLY�Ǿ�ȷ�ߴ磬
		 * �����ǽ��ؼ���layout_width��layout_heightָ��Ϊ������ֵʱ��andorid
		 * :layout_width="50dip"������ΪFILL_PARENT�ǣ����ǿؼ���С�Ѿ�ȷ������������Ǿ�ȷ�ߴ硣
		 * 
		 * 
		 * MeasureSpec.AT_MOST�����ߴ磬
		 * ���ؼ���layout_width��layout_heightָ��ΪWRAP_CONTENTʱ
		 * ���ؼ���Сһ�����ſؼ����ӿռ�����ݽ��б仯����ʱ�ؼ��ߴ�ֻҪ���������ؼ���������ߴ缴��
		 * ����ˣ���ʱ��mode��AT_MOST��size�����˸��ؼ���������ߴ硣
		 * 
		 * 
		 * MeasureSpec.UNSPECIFIED��δָ���ߴ磬����������࣬һ�㶼�Ǹ��ؼ���AdapterView��
		 * ͨ��measure���������ģʽ��
		 */
		case MeasureSpec.UNSPECIFIED:
			break;
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			result = widthSize;
			break;
		}
		return result;
	}

	private int measureHeight(int pHeightMeasureSpec) {
		int result = 0;

		int heightMode = MeasureSpec.getMode(pHeightMeasureSpec);
		int heightSize = MeasureSpec.getSize(pHeightMeasureSpec);

		switch (heightMode) {
		case MeasureSpec.AT_MOST:
		case MeasureSpec.EXACTLY:
			result = heightSize;
			break;
		}
		return result;
	}	
	
	public void start(){
		Thread thread=new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (!done) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (rotateEnabled) {
				postInvalidate();
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		boolean enable = true;
		float x = centerPoint.x;
		float y = centerPoint.y;
		float y1 = event.getY();
		float x1 = event.getX();

		float x2 = v.getLeft();
		float y2 = v.getTop();

		float x3 = x1 - x2;
		float y3 = y1 - y2;

		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			m_x = x3;
			m_y = y3;
			tounched = true;

		}

		if (event.getAction() == MotionEvent.ACTION_UP) {
			m_x = 0;
			m_y = 0;
			tounched = false;

		}

		float d = (float) Math.sqrt((Math.pow(Math.abs((x - x3)), 2) + Math
				.pow(Math.abs((y - y3)), 2)));
		if (d > radius) {
			m_x = 0;
			m_y = 0;
			enable = false;

		}

		if (event.getAction() == MotionEvent.ACTION_MOVE) {

			if ((m_x == 0) && (m_y == 0)) {

				enable = false;
			}
			if (enable) {
				float c = (float) Math
						.sqrt((Math.pow(Math.abs((m_x - x3)), 2) + Math.pow(
								Math.abs((m_y - y3)), 2)));
				float a = (float) Math
						.sqrt((Math.pow(Math.abs(m_x - x), 2) + Math.pow(
								Math.abs((m_y - y)), 2)));
				float b = (float) Math
						.sqrt((Math.pow(Math.abs(x3 - x), 2) + Math.pow(
								Math.abs((y3 - y)), 2)));

				float cosc = (float) (Math.pow(a, 2) + Math.pow(b, 2) - Math
						.pow(c, 2)) / (2 * a * b);

				float m_angle = getAngle((m_x - x), (m_y - y));

				float angle3 = getAngle((x3 - x), (y3 - y));
				if ((90 > m_angle) && (angle3 > 270)) {
					m_angle = m_angle + (float) 360;
				}
				if ((90 > angle3) && (m_angle > 270)) {
					angle3 = angle3 + (float) 360;
				}
				if (angle3 - m_angle > 0) {
					startAngle = (float) (startAngle + Math.acos(cosc) * 180
							/ Math.PI);
				} else {
					startAngle = (float) (startAngle - Math.acos(cosc) * 180
							/ Math.PI);
				}
				m_x = x3;
				m_y = y3;
			}

		}
		return true;
	}
	

	
	private float getAngle(float x, float y) {

		if ((x == 0) && (y == 0)) {
			return 0;
		}

		float angle = (float) (Math.atan(y / x) * 180 / Math.PI);

		if (x == 0) {
			if (y > 0) {
				return 90;
			} else {
				return 270;
			}
		}

		if (x > 0) {
			if (y < 0) {
				return (float) 360 + angle;
			}
		}
		if (x < 0) {
			if (y > 0) {
				return (float) 180 + angle;
			} else if (y == 0) {
				return 180;
			} else if (y < 0) {
				return (float) 180 + angle;
			}

		}

		return angle;

	}
}
