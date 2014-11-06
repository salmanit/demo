package org.crazyit.res.custom;

import java.lang.reflect.Field;
import java.util.Arrays;

import org.crazyit.res.R;
import org.crazyit.res.R.drawable;
import org.crazyit.res.R.raw;
import org.crazyit.res.custom.MyHorizontalScroll.StateListener;
import org.crazyit.res.widget.MyDialog;

import android.R.integer;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;

public class TestScrollviewAC extends Activity {

	private MyHorizontalScroll myHorizontalScroll;
	private Button btnLeft, btnRight;
	//private LinearLayout linearLayout;
	private MyGridviewSimple mygv;
	private Context mContext;
	//private GridView gView;
	private float density;
	private ImageView iv;
	private ImageView iv11;
	private AnimationDrawable drawable;
	private ImageView ivtemp;
	private ImageView ivbg,ivbg1;
	private TextView tvindex;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testhorscrollview);
		mContext = this;
		initview();
		
	}

	private void initview() {
		tvindex=(TextView) findViewById(R.id.tvindex);
		
		
		ivbg1=(ImageView) findViewById(R.id.ivbg1);
		ivbg=(ImageView) findViewById(R.id.ivbg);
		float x=640*getResources().getDisplayMetrics().density-getResources().getDisplayMetrics().widthPixels;
		System.out.println("x=="+x);
		TranslateAnimation translateAnimation=new 
				TranslateAnimation(Animation.ABSOLUTE, 0f, Animation.ABSOLUTE, -x, Animation.RELATIVE_TO_PARENT, 0f, Animation.RELATIVE_TO_PARENT, 0f);
		translateAnimation.setRepeatMode(Animation.REVERSE);
		translateAnimation.setRepeatCount(Animation.INFINITE);
		translateAnimation.setDuration(5000);
		ivbg.startAnimation(translateAnimation);
		ivbg1.startAnimation(translateAnimation);
		
		ivtemp=(ImageView) findViewById(R.id.ivtemp);
		ivtemp.setImageBitmap(getBitmapFromView(getView(R.drawable.p4)));
		
		
		iv=(ImageView) findViewById(R.id.iv1);
		ClipDrawable clipDrawable=(ClipDrawable) iv.getDrawable();
		clipDrawable.setLevel(4000);
		iv11=(ImageView) findViewById(R.id.iv11);
		drawable=(AnimationDrawable) iv11.getDrawable();
		
		mygv = (MyGridviewSimple) findViewById(R.id.mygv);
		density = getResources().getDisplayMetrics().density;
		mygv.setColumnWidth((int) (150 * density));
		mygv.setStretchMode(GridView.NO_STRETCH);
		mygv.setNumColumns(6);
		mygv.setVerticalScrollBarEnabled(false);
		mygv.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (160 * 6 * density), LayoutParams.MATCH_PARENT));
		mygv.setAdapter(adapter);

		btnLeft = (Button) findViewById(R.id.btnLeft);
		btnRight = (Button) findViewById(R.id.btnRight);
		myHorizontalScroll = (MyHorizontalScroll) findViewById(R.id.myhor);

		myHorizontalScroll.setStateListener(new StateListener() {

			@Override
			public void onRight() {
				btnRight.setVisibility(View.GONE);
			}

			@Override
			public void onLeft() {
				btnLeft.setVisibility(View.GONE);
			}

			@Override
			public void onCenter() {
				btnLeft.setVisibility(View.VISIBLE);
				btnRight.setVisibility(View.VISIBLE);
			}
		});
		initdata();
		btnLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myHorizontalScroll.smoothScrollBy(-30, 0);
				{
					number++;
					ScaleAnimation scaleAnimation=new ScaleAnimation(1.0f, 0f, 1f, 1f, 
							Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
					scaleAnimation.setRepeatCount(1);
					scaleAnimation.setRepeatMode(Animation.REVERSE);
					scaleAnimation.setDuration(300);
					scaleAnimation.setAnimationListener(new AnimationListener() {
						
						@Override
						public void onAnimationStart(Animation animation) {
							System.out.println("start");
							
						}
						
						@Override
						public void onAnimationRepeat(Animation animation) {

							System.out.println("repeat");
							tvindex.setText(""+number);
						}
						
						@Override
						public void onAnimationEnd(Animation animation) {
							System.out.println("end");
							
						}
					});
					
					tvindex.startAnimation(scaleAnimation);
					
					
				}
				
				
			}
		});
		btnRight.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				myHorizontalScroll.smoothScrollBy(30, 0);
				new MyDialog(mContext).show();
			}
		});

		mygv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//startAnimation(iv);
				moveViewToScreenCenter(iv);
			}
		});
	}
private int number=1;
	private void moveViewToScreenCenter( final View view ){
	    DisplayMetrics dm = new DisplayMetrics();
	    getWindowManager().getDefaultDisplay().getMetrics( dm );

	    int originalPos[] = new int[2];
	    view.getLocationOnScreen( originalPos );
	    System.out.println("====="+Arrays.toString(originalPos));;
	    int xDelta = (dm.widthPixels - view.getMeasuredWidth() - originalPos[0])/2;
	    int yDelta = (dm.heightPixels - view.getMeasuredHeight() - originalPos[1])/2;

	    AnimationSet animSet = new AnimationSet(true);
	    animSet.setFillAfter(true);
	    animSet.setDuration(2000);
	    animSet.setInterpolator(new AccelerateInterpolator());
	    TranslateAnimation translate = new TranslateAnimation( 0, xDelta , 0, yDelta);
	    animSet.addAnimation(translate);
	    ScaleAnimation scale = new ScaleAnimation(1f, 2f, 1f, 2f, ScaleAnimation.RELATIVE_TO_PARENT, .5f, ScaleAnimation.RELATIVE_TO_PARENT, .5f);
	    animSet.addAnimation(scale);
	    view.startAnimation(animSet);
	}
	
	
	
	
	
	
	
	
	private void startAnimation(final ImageView imageView) {
        final Animation popAnim = AnimationUtils.loadAnimation(this, R.anim.scaletest);
        imageView.startAnimation(popAnim);
        popAnim.setAnimationListener(new AnimationListener() {

            public void onAnimationStart(Animation animation) {

            }

            public void onAnimationRepeat(Animation animation) {

            }

            public void onAnimationEnd(Animation animation) {
                imageView.clearAnimation();
                imageView.setVisibility(View.GONE);
            }
        });
    }
	
	 
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		if(hasFocus){
			
			drawable.start();
		}else{
			drawable.stop();
		}
		super.onWindowFocusChanged(hasFocus);
	}
	
	
	
	
	
	
	
	private void initdata() {
		LinearLayout linearLayout = (LinearLayout) myHorizontalScroll
				.getChildAt(0);

		for (int i = 0; i < 5; i++) {
			ImageView iv = new ImageView(this);
			iv.setLayoutParams(new LinearLayout.LayoutParams(100, 50));
			iv.setPadding(5, 5, 5, 5);
			iv.setScaleType(ScaleType.FIT_XY);
//			int resid = getResources().getIdentifier("choujiang1", "drawable",
//					getPackageName());
//			System.out.println("resid==" + resid + " choujiang1=="
//					+ R.drawable.choujiang1);
			iv.setImageResource(R.drawable.choujiang1);
			linearLayout.addView(iv);
		}

	}

	private BaseAdapter adapter = new BaseAdapter() {

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int density = (int) getResources().getDisplayMetrics().density;
			ImageView iv = new ImageView(mContext);
			iv.setLayoutParams(new AbsListView.LayoutParams(150 * density,
					50 * density));
			iv.setImageResource(R.drawable.choujiang9);
			iv.setPadding(5 * density, 5 * density, 5 * density, 5 * density);
			return iv;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 6;
		}
	};

	/***将一个view转换为bitmap*/
	public  Bitmap getBitmapFromView(View view) {
	       view.destroyDrawingCache();
	        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
	                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
	        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
	        view.setDrawingCacheEnabled(true);
	        Bitmap bitmap = view.getDrawingCache(true);
	        if(bitmap==null){
	        	Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
	        }
	        return bitmap;
		}
		/**根据资源id获取布局view*/
		private View getView(int resid){
			View view=LayoutInflater.from(this).inflate(R.layout.circleimage, null);
			ImageView ivpic=(ImageView) view.findViewById(R.id.ivhead);
			Bitmap bitmap=BitmapFactory.decodeResource(getResources(), resid);
			//使用裁剪后的图片，如果不需要裁剪，那就不需要调用裁剪圆形的方法
			ivpic.setImageBitmap(toRoundBitmap(bitmap));
			return view;
		}	
		/**图片裁剪为圆形*/
		public static Bitmap toRoundBitmap(Bitmap bitmap) {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
					bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			Paint paint = new Paint();
			int width = bitmap.getWidth();
			int height = bitmap.getHeight();
			Rect rect;
			if (width >= height) {
				rect = new Rect((width - height) / 2, 0, (width - height) / 2
						+ height, height);
			} else {
				rect = new Rect(0, (height - width) / 2, width, width
						+ (height - width) / 2);
			}
			RectF rectF = new RectF(rect);
			canvas.drawOval(rectF, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
			canvas.drawBitmap(bitmap, rect, rectF, paint);
			return output;
		}
		

	
}
