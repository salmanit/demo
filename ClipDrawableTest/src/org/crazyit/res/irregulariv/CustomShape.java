package org.crazyit.res.irregulariv;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

public class CustomShape extends View {

	private Drawable drawable;
	public CustomShape(Context context, AttributeSet attrs) {
		super(context, attrs);
		drawable=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), R.drawable.choujiang13));
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Rect rect=new Rect(0, 0, getWidth(), getWidth());
		super.onDraw(canvas);
		Path path=new Path();
		path.addCircle(getWidth()/2, getWidth()/2, getWidth()/2, Direction.CW);
		canvas.clipPath(path);
		 Drawable d = drawable;
	        if (d != null && d.isStateful()) {
	            d.setState(getDrawableState());
	        }
	       
		drawable.setBounds(rect );
		drawable.draw(canvas);
	}
}
