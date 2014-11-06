package org.crazyit.res.widget;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class MyOnePicImageView extends ImageView {

	public MyOnePicImageView(Context context) {
		super(context);
		initMyButton();
	}

	public MyOnePicImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initMyButton();
	}

	public MyOnePicImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initMyButton();
	}

	private void initMyButton() {
		if (isInEditMode())
			return;
		//setImageDrawable(newSelector());
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

	}

	private int percent = 0;

	public void setPercent(int percent) {
		this.percent = percent;
		test();
		invalidate();
	}

	/**
	 * ����ı�����ǰ��bitmap�����ظı����Ⱥ��bitmap
	 * 
	 * @param srcBitmap
	 * @return
	 */
	private Drawable changeBrightnessBitmap(Bitmap srcBitmap) {
		Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(),
				srcBitmap.getHeight(), Config.ARGB_8888);
		int brightness = 60 - 127;
		ColorMatrix cMatrix = new ColorMatrix();
		cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
				brightness,// �ı�����
				0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
		Paint paint = new Paint();
		paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));
		Canvas canvas = new Canvas(bmp);
		// ��Canvas�ϻ���һ��Bitmap
		canvas.drawBitmap(srcBitmap, 0, 0, paint);
		return new BitmapDrawable(bmp);
	}

	/** ����Selector�� */
	private StateListDrawable newSelector() {
		StateListDrawable bg = new StateListDrawable();
		Drawable normal = getDrawable();
		Drawable pressed = changeBrightnessBitmap(((BitmapDrawable) normal)
				.getBitmap());
		;
		// View.PRESSED_ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_pressed,
				android.R.attr.state_enabled }, pressed);
		// View.ENABLED_STATE_SET
		bg.addState(new int[] { android.R.attr.state_enabled }, normal);
		bg.addState(new int[] { -android.R.attr.state_enabled }, getResources()
				.getDrawable(R.drawable.ic_launcher));
		// View.EMPTY_STATE_SET
		bg.addState(new int[] {}, normal);
		return bg;
	}

	private void test() {
		Drawable normal = getDrawable();
		// Drawable bg=getBackground();
		normal.setAlpha(percent* 255 / 100 );
		setImageDrawable(normal);
	}
}
