package org.crazyit.res.temp;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class TestDrawView extends View {

	public TestDrawView(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public void setbitmap(Bitmap bitmap){
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		
		super.onDraw(canvas);
		Drawable mDrawable=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.choujiang1));
		mDrawable.setBounds(0, 0, 200, 500);
//		 int saveCount = canvas.getSaveCount();
//         canvas.save();
		mDrawable.draw(canvas);
		
		//canvas.restoreToCount(saveCount);
	}
}
