package org.crazyit.res.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;

public class MyHorizontalScroll extends HorizontalScrollView {

	public MyHorizontalScroll(Context context) {
		super(context);
		
	}
	public MyHorizontalScroll(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}

	public MyHorizontalScroll(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		System.out.println("left="+l+"top="+t+" old l="+oldl+" oldtop="+oldt);
		if(stateListener!=null){
			View view=getChildAt(0);
			if(view==null)
				return;
			if(view.getLeft()==getScrollX())
				stateListener.onLeft();
			else if(view.getRight()==getScrollX()+getWidth())
				stateListener.onRight();
			else {
				stateListener.onCenter();
			}
		}
		super.onScrollChanged(l, t, oldl, oldt);
	}

	
	
interface StateListener{
	void onLeft();
	void onRight();
	void onCenter();
}	

private StateListener  stateListener;

public void setStateListener(StateListener stateListener) {
	this.stateListener = stateListener;
}

	
}
