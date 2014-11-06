package org.crazyit.res;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class ScaleBitmapActivity extends Activity {

	private ScaleBitmap dragImageView;
	private ViewTreeObserver viewTreeObserver;
	
	private int state_height;
	protected int window_height;
	protected int window_width;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.scaleimageview);
		window_height=getResources().getDisplayMetrics().heightPixels;
		window_width=getResources().getDisplayMetrics().widthPixels;
		/** 测量状态栏高度 **/
		dragImageView=(ScaleBitmap) findViewById(R.id.div_main);
		viewTreeObserver = dragImageView.getViewTreeObserver();
		viewTreeObserver
				.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

				

					@Override
					public void onGlobalLayout() {
						if (state_height == 0) {
							// 获取状况栏高度
							Rect frame = new Rect();
							getWindow().getDecorView()
									.getWindowVisibleDisplayFrame(frame);
							state_height = frame.top;
							dragImageView.setScreen_H(window_height-state_height);
							dragImageView.setScreen_W(window_width);
						}

					}
				});
	}
	
	
	
}
