package org.crazyit.res.drawrecord;

import org.crazyit.res.R;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import com.zeno.lib.draw.Brush;

public class CountTouchView {
	private Paint TouchPain;
	PointF last = new PointF();
	PointF start = new PointF();
	static final int CLICK = 3;
	private DrawCanvasView view;
	public Path zoomCircle = new Path();
	public static final int ButtonSize = 48;
	private ContentStatus status = ContentStatus.NONE;
	private Bitmap contentBitmap;
	private Matrix bmpMatrix;
	private ClickListener clickListener;
	ScaleGestureDetector mScaleDetector;
	float DrawsaveScale = 1f;

	public interface ClickListener {
		void closeClick(CountTouchView ctv);

		void singleTap(CountTouchView ctv);
	}

	public enum ContentStatus {
		NONE, TRANS, ZOOM;
	}

	public CountTouchView(DrawCanvasView view, Bitmap bitmap) {
		super();
		this.view = view;
		try {
			clickListener = (ClickListener) view;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mScaleDetector = new ScaleGestureDetector(view.getContext(),
				new ScaleListener());
		TouchPain = Brush.getPen().clone();
		TouchPain.setColor(Color.RED);
		TouchPain.setStrokeWidth(2);
		contentBitmap = bitmap;
		bmpMatrix = new Matrix();
	}

	public void onTouch(MotionEvent event, float scal, float transX,
			float transY) {
		mScaleDetector.onTouchEvent(event);
		float x = (event.getX() - transX) / scal;
		float y = (event.getY() - transY) / scal;
		// if (Math.sqrt(Math
		// .pow((double) (getState().getRight() - x), (double) 2)
		// + Math.pow((getState().getBottom() - y), 2)) < 24
		// || status == ContentStatus.ZOOM) {
		// status = ContentStatus.ZOOM;
		// // scaleMode(event, scal);
		// } else
		if (Math.sqrt(Math
				.pow((double) (getState().getRight() - x), (double) 2)
				+ Math.pow((getState().getTop() - y), 2)) < 24) {
			if (event.getAction() == MotionEvent.ACTION_UP)
				clickListener.closeClick(this);
		} else {
			status = ContentStatus.TRANS;
			transMode(event, scal);
		}
	}

	private void scaleMode(MotionEvent event, float scal) {
		PointF curr = new PointF(event.getX(), event.getY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			last.set(curr);
			start.set(last);
			break;

		case MotionEvent.ACTION_MOVE:
			float deltaX = curr.x - last.x;
			float deltaY = curr.y - last.y;
			// if (Math.abs(deltaX) >= 3 && Math.abs(deltaY) >= 3) {
			float scaleX = (getState().getRight() - getState().getLeft() + deltaX
					/ scal)
					/ (getState().getRight() - getState().getLeft());
			float scaleY = (getState().getBottom() - getState().getTop() + deltaY
					/ scal)
					/ (getState().getBottom() - getState().getTop());
			float scale = Math.min(scaleX, scaleY);
			if ((getState().getRight() - getState().getLeft()) * scale <= contentBitmap
					.getWidth() / 2) {
				scale = 1;
			}
			bmpMatrix.postScale(scale, scale);
			last.set(curr.x, curr.y);
			// }
			break;

		case MotionEvent.ACTION_UP:
			status = ContentStatus.NONE;
			break;
		}
		view.invalidate();
	}

	private void transMode(MotionEvent event, float scal) {
		PointF curr = new PointF(event.getX(), event.getY());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			last.set(curr);
			start.set(last);
			break;

		case MotionEvent.ACTION_MOVE:
			float deltaX = curr.x - last.x;
			float deltaY = curr.y - last.y;
			bmpMatrix.postTranslate(deltaX / scal, deltaY / scal);
			Log.v("transMode", getState().toString());
			last.set(curr.x, curr.y);
			break;
		case MotionEvent.ACTION_UP:
			float diffx = curr.x - last.x;
			float diffy = curr.y - last.y;
			if (diffx <= 3 && diffy <= 3)
				clickListener.singleTap(this);
			status = ContentStatus.NONE;
			break;
		}

		view.invalidate();
	}

	public void invaludate(Canvas canvas) {
		canvas.drawBitmap(contentBitmap, bmpMatrix, new Paint());
		if (!view.isRecord())
			canvas.drawRect(getRect(), getTouchPain());
		if (!view.isRecord() && view.canDrawoutsize) {
			Bitmap close = BitmapFactory.decodeResource(view.getResources(),
					R.drawable.close);
			canvas.drawBitmap(close, getState().getRight() - close.getWidth()
					/ 2, getState().getTop() - close.getHeight() / 2,
					getTouchPain());
		}
	}

	public boolean contains(float x, float y, float scal, float transX,
			float transY) {
		x = (x - transX) / scal;
		y = (y - transY) / scal;
		if (x >= getState().getLeft()
				&& x <= getState().getRight()
				&& y >= getState().getTop()
				&& y <= getState().getBottom()
				|| status == ContentStatus.ZOOM
				|| (Math.sqrt(Math.pow((double) (getState().getRight() - x),
						(double) 2) + Math.pow((getState().getBottom() - y), 2)) < 24)
				|| (Math.sqrt(Math.pow((double) (getState().getRight() - x),
						(double) 2) + Math.pow((getState().getTop() - y), 2)) < 24))
			return true;
		else
			return false;

	}

	private class ScaleListener extends
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScaleBegin(ScaleGestureDetector detector) {
			return true;
		}

		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			float mScaleFactor = detector.getScaleFactor();
			float origScale = DrawsaveScale;
			DrawsaveScale *= mScaleFactor;
			if (DrawsaveScale > view.maxScale) {
				DrawsaveScale = view.maxScale;
				mScaleFactor = view.maxScale / origScale;
			} else if (DrawsaveScale < view.minScale) {
				if ((getState().getRight() - getState().getLeft())
						* mScaleFactor <= contentBitmap.getWidth() / 5) {
					mScaleFactor = 1;
				}
			}
			bmpMatrix.postScale(mScaleFactor, mScaleFactor,
					detector.getFocusX(), detector.getFocusY());
			return true;
		}
	}

	public ImageState getState() {
		return ImageState.castMapState(bmpMatrix, contentBitmap.getWidth(),
				contentBitmap.getHeight());
	}

	public RectF getRect() {
		return new RectF(getState().getLeft(), getState().getTop(), getState()
				.getRight(), getState().getBottom());
	}

	public Paint getTouchPain() {
		return TouchPain;
	}

	public void setTouchPain(Paint touchPain) {
		TouchPain = touchPain;
	}

	public ContentStatus getContentStatus() {
		return status;
	}
}
