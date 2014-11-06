package org.crazyit.res.drawrecord;

import java.util.ArrayList;
import java.util.List;

import org.crazyit.res.drawrecord.CountTouchView.ClickListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

import com.zeno.lib.draw.Brush;
import com.zeno.lib.draw.Drawing;
import com.zeno.lib.draw.DrawingFactory;
import com.zeno.lib.draw.DrawingId;
import com.zeno.lib.draw.WeikeDrawing;

public class DrawCanvasView extends View implements ClickListener {
	/** 轨迹集合 */
	private ArrayList<WeikeDrawing> DrawingList = new ArrayList<WeikeDrawing>();
	private ArrayList<WeikeDrawing> RecordList = new ArrayList<WeikeDrawing>();
	/** 初始化Drawing */
	private WeikeDrawing curDrawing;
	/** 初始化时间 */
	private long recordStartTime = 0;
	/** 是否开始录制 */
	private boolean isRecord = false;
	/** 录制监听 */
	private Bitmap backGroudBmp;
	boolean canDrawoutsize = true;
	private float toSize = -1;
	private SingleClickLisener SingleClickLisener;
	private int mDrawingId = DrawingId.DRAWING_PATHLINE;
	private FingerUpListener mFingerUp;
	private long offsetTime = 0;

	public void setFingerUpListener(FingerUpListener mFingerUp) {
		this.mFingerUp = mFingerUp;
	}

	public void removeBg() {
		this.backGroudBmp = null;
		postInvalidate();
	}

	public interface FingerUpListener {
		void resultCallBack(WeikeDrawing weikeDrawing);
	}

	public void setmDrawingId(int mDrawingId) {
		this.mDrawingId = mDrawingId;
	}

	public void setCanDrawoutsize(boolean canDrawoutsize) {
		this.canDrawoutsize = canDrawoutsize;
	}

	public ArrayList<WeikeDrawing> getDrawingList() {
		return DrawingList;
	}

	public ArrayList<WeikeDrawing> getRecorList() {
		return RecordList;
	}

	public interface SingleClickLisener {
		void Click(View v);
	}

	public void setSingleClickLisener(SingleClickLisener singleClickLisener) {
		SingleClickLisener = singleClickLisener;
	}

	public void setScaleRect(final float width, final float height,
			final List<WeikeDrawing> wdList) {
		post(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				float widthScale = (float) getWidth() / width;
				float heightScale = (float) getHeight() / (float) height;
				if (widthScale != 0 && heightScale != 0) {
					Matrix matrix = new Matrix();
					toSize = (widthScale < heightScale) ? widthScale
							: heightScale;
					matrix.postScale(toSize, toSize);
					ViewGroup.LayoutParams params = getLayoutParams();
					params.height = (int) (height * toSize);
					params.width = (int) (width * toSize);
					setLayoutParams(params);
				}
				bgPaint = new Paint();
				if (wdList != null) {
					for (WeikeDrawing drawing : wdList) {
						drawing.getPaint().setStrokeWidth(
								drawing.getPaint().getStrokeWidth() * toSize);
						Matrix scaleMatrix = new Matrix();
						RectF rectF = new RectF();
						drawing.mPath.computeBounds(rectF, true);
						scaleMatrix.setScale(toSize, toSize, 0, 0);
						drawing.mPath.transform(scaleMatrix);
						getDrawingList().add(drawing);
					}
				}
				postInvalidate();
			}
		});
	}

	public void recyleBitmap() {
		if (this.backGroudBmp != null) {
			this.backGroudBmp.recycle();
			this.backGroudBmp = null;
			System.gc();
			postInvalidate();
		}
	}

	public void setBackGroudBmp(Bitmap backGroudBmp,
			final List<WeikeDrawing> wdList) {
		this.backGroudBmp = backGroudBmp;
		if (this.backGroudBmp != null)
			post(new Runnable() {

				@Override
				public void run() {
					if (getWidth() > 0 && getHeight() > 0) {
						float widthScale = (float) getWidth()
								/ (float) DrawCanvasView.this.backGroudBmp
										.getWidth();
						float heightScale = (float) getHeight()
								/ (float) DrawCanvasView.this.backGroudBmp
										.getHeight();
						if (widthScale != 0 && heightScale != 0) {
							Matrix bitmapmatrix = new Matrix();
							toSize = (widthScale < heightScale) ? widthScale
									: heightScale;
							bitmapmatrix.postScale(toSize, toSize);
							ViewGroup.LayoutParams params = getLayoutParams();
							params.height = (int) (DrawCanvasView.this.backGroudBmp
									.getHeight() * toSize);
							params.width = (int) (DrawCanvasView.this.backGroudBmp
									.getWidth() * toSize);
							setLayoutParams(params);
							
							DrawCanvasView.this.backGroudBmp = Bitmap
									.createBitmap(
											DrawCanvasView.this.backGroudBmp,
											0, 0,
											DrawCanvasView.this.backGroudBmp
													.getWidth(),
											DrawCanvasView.this.backGroudBmp
													.getHeight(), bitmapmatrix,
											false);
						}
					}
					bgPaint = new Paint();
					if (wdList != null) {
						for (WeikeDrawing drawing : wdList) {
							drawing.getPaint().setStrokeWidth(
									drawing.getPaint().getStrokeWidth()
											* toSize);
							Matrix scaleMatrix = new Matrix();
							RectF rectF = new RectF();
							drawing.mPath.computeBounds(rectF, true);
							scaleMatrix.setScale(toSize, toSize, 0, 0);
							drawing.mPath.transform(scaleMatrix);
							getDrawingList().add(drawing);
						}
					}
					postInvalidate();
				}
			});
	}
			/**还原画布到初始状态*/
			public void resetcanvas(){
				
				viewWidth=oldMeasuredWidth;
				viewHeight=oldMeasuredHeight;
				origWidth=oldMeasuredWidth;
				origHeight=oldMeasuredHeight;
				matrix.reset();
				fixTrans();
				DrawsaveScale=1f;
				postInvalidate();
			}
	public float getToSize() {
		return toSize;
	}

	// custom view action
	Matrix matrix;

	public boolean isRecord() {
		return isRecord;
	}

	public void setRecord(boolean isRecord) {
		this.isRecord = isRecord;
	}

	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	static final int DRAW = 3;
	int mode = NONE;

	// Remember some things for zooming
	PointF last = new PointF();
	PointF start = new PointF();
	float minScale = 1f;
	float maxScale = 6f;
	float[] m;

	int viewWidth, viewHeight;
	static final int CLICK = 3;
	float DrawsaveScale = 1f;
	protected float origWidth, origHeight;
	int oldMeasuredWidth, oldMeasuredHeight;

	ScaleGestureDetector mScaleDetector;
	private Paint bgPaint;
	Context context;
	ArrayList<CountTouchView> CountTouchViewList = new ArrayList<CountTouchView>();

	public DrawCanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		sharedConstructing(context);
		// TODO Auto-generated constructor stub
	}

	public DrawCanvasView(Context context) {
		super(context);
		sharedConstructing(context);
		// TODO Auto-generated constructor stub
	}

	public DrawCanvasView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sharedConstructing(context);
	}

	private void sharedConstructing(Context context) {
		super.setClickable(true);
		this.context = context;
		mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
		matrix = new Matrix();
		m = new float[9];
	}

	public void initRecord(long beginTime) {
		recordStartTime = beginTime;
		isRecord = true;
		postInvalidate();
	}

	private long pauseTime;

	public void pause() {
		pauseTime = System.currentTimeMillis();
	}

	public void restore() {
		offsetTime = offsetTime + System.currentTimeMillis() - pauseTime;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		// canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		ImageState imagestate = ImageState.castMapState(matrix, viewWidth,
				viewHeight);
		canvas.save();
		canvas.translate(imagestate.getLeft(), imagestate.getTop());
		canvas.scale(
				(imagestate.getRight() - imagestate.getLeft()) / viewWidth,
				(imagestate.getBottom() - imagestate.getTop()) / viewHeight);
		if (backGroudBmp != null)
			canvas.drawBitmap(backGroudBmp, 0, 0, bgPaint);
		for (int i = 0; i < CountTouchViewList.size(); i++) {
			CountTouchViewList.get(i).invaludate(canvas);
		}
		for (WeikeDrawing drawing : DrawingList) {
			drawing.draw(canvas);
		}
		canvas.restore();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		// getParent().requestDisallowInterceptTouchEvent(true);
		ImageState imageState = ImageState.castMapState(matrix, viewWidth,
				viewHeight);
		if (!isRecord) {
			for (int i = CountTouchViewList.size() - 1; i >= 0; i--) {
				CountTouchView ctv = CountTouchViewList.get(i);
				if (mode != DRAW
						&& ctv.contains(event.getX(), event.getY(),
								DrawsaveScale, imageState.getLeft(),
								imageState.getTop())) {
					ctv.onTouch(event, DrawsaveScale, imageState.getLeft(),
							imageState.getTop());
					return true;
				}
			}
		}
		if (canDrawoutsize) {
			if (event.getPointerCount() == 1) {
				drawMode(event);
			} else
				zoomMode(event);
		} else {
			if (event.getPointerCount() == 2)
				isTwoTouch = true;
			if (event.getAction() == MotionEvent.ACTION_UP) {
				if (!isTwoTouch) {
					if (SingleClickLisener != null)
						SingleClickLisener.Click(this);
				}
				isTwoTouch = false;
			}
			zoomMode(event);
		}
		return true;
	}

	private boolean isTwoTouch = false;

	private void zoomMode(MotionEvent event) {
		mScaleDetector.onTouchEvent(event);
		if (event.getPointerCount() != 2)
			return;
		PointF curr = new PointF(event.getX(), event.getY());
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_POINTER_DOWN:
			last.set(curr);
			start.set(last);
			mode = DRAG;
			break;

		case MotionEvent.ACTION_MOVE:
			curDrawing = null;
			float deltaX = curr.x - last.x;
			float deltaY = curr.y - last.y;
			float fixTransX = getFixDragTrans(deltaX, viewWidth, origWidth
					* DrawsaveScale);
			float fixTransY = getFixDragTrans(deltaY, viewHeight, origHeight
					* DrawsaveScale);
			matrix.postTranslate(fixTransX, fixTransY);
			fixTrans();
			last.set(curr.x, curr.y);
			break;
		}
		invalidate();
	}

	private void drawMode(MotionEvent event) {
		ImageState imageState = ImageState.castMapState(matrix, viewWidth,
				viewHeight);
		float x = (event.getX() - imageState.getLeft()) / DrawsaveScale;
		float y = (event.getY() - imageState.getTop()) / DrawsaveScale;
		long time = System.currentTimeMillis() - recordStartTime - offsetTime;
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			curDrawing = DrawingFactory.createDrawing(mDrawingId);
			curDrawing.setPaint(Brush.getPen().clone());
			DrawingList.add(curDrawing);
			curDrawing.fingerDown(x, y, time, isRecord);
			break;
		case MotionEvent.ACTION_MOVE:
			if (curDrawing != null)
				curDrawing.fingerMove(x, y, time, isRecord);
			break;
		case MotionEvent.ACTION_UP:
			if (mode == NONE && curDrawing != null) {
				curDrawing.fingerUp(x, y, time, isRecord);
				if (isRecord) {
					RecordList.add(curDrawing);
					if (mFingerUp != null)
						mFingerUp.resultCallBack(curDrawing);
				}
			} else {
				DrawingList.remove(curDrawing);
			}
			mode = NONE;
			break;
		}
		postInvalidate();
	}

	/**
	 * 录制监听
	 */
	public interface RecordCallBack {
		void Result(Drawing drawing);
	}

	/**
	 * 清除屏幕
	 */
	public void ClearCanvas() {
		DrawingList.clear();
		// DefaultList.clear();
		RecordList.clear();
		CountTouchViewList.clear();
		postInvalidate();
	}

	/**
	 * 撤销一笔
	 */
	public void BackLine() {
		if (DrawingList.size() > 0)
			DrawingList.remove(DrawingList.size() - 1);
		postInvalidate();
	}

	public void setMaxZoom(float x) {
		maxScale = x;
	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			mode = ZOOM;
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float mScaleFactor = detector.getScaleFactor();
			float origScale = DrawsaveScale;
			DrawsaveScale *= mScaleFactor;
			if (DrawsaveScale > maxScale) {
				DrawsaveScale = maxScale;
				mScaleFactor = maxScale / origScale;
			} else if (DrawsaveScale < minScale) {
				DrawsaveScale = minScale;
				mScaleFactor = minScale / origScale;
			}

			if (origWidth * DrawsaveScale <= viewWidth
					|| origHeight * DrawsaveScale <= viewHeight)
				matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2,
						viewHeight / 2);
			else
				matrix.postScale(mScaleFactor, mScaleFactor,
						detector.getFocusX(), detector.getFocusY());
			fixTrans();
			return true;
		}
	}

	void fixTrans() {
		matrix.getValues(m);
		float transX = m[Matrix.MTRANS_X];
		float transY = m[Matrix.MTRANS_Y];

		float fixTransX = getFixTrans(transX, viewWidth, origWidth
				* DrawsaveScale);
		float fixTransY = getFixTrans(transY, viewHeight, origHeight
				* DrawsaveScale);

		if (fixTransX != 0 || fixTransY != 0)
			matrix.postTranslate(fixTransX, fixTransY);
	}

	float getFixTrans(float trans, float viewSize, float contentSize) {
		float minTrans, maxTrans;

		if (contentSize <= viewSize) {
			minTrans = 0;
			maxTrans = viewSize - contentSize;
		} else {
			minTrans = viewSize - contentSize;
			maxTrans = 0;
		}

		if (trans < minTrans)
			return -trans + minTrans;
		if (trans > maxTrans)
			return -trans + maxTrans;
		return 0;
	}

	float getFixDragTrans(float delta, float viewSize, float contentSize) {
		if (contentSize <= viewSize) {
			return 0;
		}
		return delta;
	}

	public void addBitmap(Bitmap bitmap) {
		if (bitmap != null)
			CountTouchViewList.add(new CountTouchView(this, bitmap));
		invalidate();
	}

	// Bitmap mBitmap;
	// Paint mBitmapPaint;
	// @Override
	// protected void onSizeChanged(int w, int h, int oldw, int oldh) {
	// super.onSizeChanged(w, h, oldw, oldh);
	// mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
	// mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	// mCanvas = new Canvas(mBitmap);
	// }
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);
		if (oldMeasuredHeight == viewWidth && oldMeasuredHeight == viewHeight
				|| viewWidth == 0 || viewHeight == 0)
			return;
		oldMeasuredHeight = viewHeight;
		oldMeasuredWidth = viewWidth;

		if (DrawsaveScale == 1) {
			float scale = 1;
			matrix.setScale(scale, scale);
			origWidth = viewWidth;
			origHeight = viewHeight;
		}
		fixTrans();
	}

	@Override
	public void closeClick(CountTouchView ctv) {
		// TODO Auto-generated method stub
		CountTouchViewList.remove(ctv);
		postInvalidate();
	}

	@Override
	public void singleTap(CountTouchView ctv) {
		// TODO Auto-generated method stub
		int index;
		if ((index = CountTouchViewList.indexOf(ctv)) >= 0
				&& index < CountTouchViewList.size() - 1) {
			CountTouchView obj = CountTouchViewList.get(index + 1);
			CountTouchViewList.set(index + 1, ctv);
			CountTouchViewList.set(index, obj);
		}
		postInvalidate();
	}
}
