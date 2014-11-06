package org.crazyit.res.scaleimage;

import org.crazyit.res.R;
import org.crazyit.res.blank.TestView;
import org.crazyit.res.blank.TestView1;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class ScaleImageActivity extends Activity {

	private ViewPager myPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scaleimagexml);
		myPager=(ViewPager) findViewById(R.id.mypager);
		myPager.setAdapter(new MyAdapter());
		TextView tView=(TextView) findViewById(R.id.tv1);
		Matrix matrix=new Matrix();
		matrix.postScale(10, 20, 0, 0);
		float[] values=new float[9];
		matrix.getValues(values);
		tView.setText("scalex="+values[Matrix.MSCALE_X]+" scaley="+values[matrix.MSCALE_Y]);
	
	}
	
	public class MyAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			
			return 1;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			
			return arg0==arg1;
		}
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeViewAt(position);
		}
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			TestView1 ts=new TestView1(ScaleImageActivity.this);
			ts.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			container.addView(ts);
			return container.getChildAt(position);
		}
	}
	
}
