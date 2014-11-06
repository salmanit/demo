package org.crazyit.res.irregulariv;

import org.crazyit.res.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class BottomcutImageview extends View {

	StateListDrawable drawable;
	private int clip_heigth;
	public BottomcutImageview(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray  array=context.obtainStyledAttributes(attrs, R.styleable.IrregularPic);
		clip_heigth=(int) array.getDimension(R.styleable.IrregularPic_clip_height, 20);
//		int image_resid=array.getResourceId(R.styleable.IrregularPic_src, R.drawable.choujiang10);
//		drawable=new BitmapDrawable(getResources(), BitmapFactory.decodeResource(getResources(), image_resid));
		
		drawable=(StateListDrawable) array.getDrawable(R.styleable.IrregularPic_src);
		array.recycle();
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Path path=new Path();
		path.moveTo(0, 0);
		path.lineTo(0, getHeight());
		path.lineTo(getWidth(), getHeight()-clip_heigth);
		path.lineTo(getWidth(), 0);
		path.close();
		
		canvas.clipPath(path);
		if(drawable!=null){
			drawable.setBounds(0, 0, getWidth(), getHeight());
			drawable.draw(canvas);
		}
		
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		drawableStateChanged();
		invalidate();
		super.setOnClickListener(l);
	}
}
