package org.crazyit.res.testdemo;

import org.crazyit.res.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
/**��Ҫѧϰ��animationset��ʹ�ã�ƽ�ƺ�����һ�����*/
public class Testactivity extends Activity {

	private float density;
	private ImageView iv;
	private AnimationSet set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testxml);
		density=getResources().getDisplayMetrics().density;
	}
	
	public void btnclickTest(View v){
		if(v.getId()==R.id.btncancel){
			if(set!=null)
				set.cancel();//������������󣬻�ֱ��ȥִ�м�������endanimtion����
			return;
		}
		float deltx=0,delty=0;
		switch (v.getId()) {
		case R.id.btnstart:
			deltx=25*density;
			delty=25*density;
			 iv=(ImageView) findViewById(R.id.ivfront);
			break;
		case R.id.btnlb:
			deltx=25*density;
			delty=-50*density;
			 iv=(ImageView) findViewById(R.id.ivlb);
			break;
		case R.id.btnrt:
			deltx=-50*density;
			delty=25*density;
			 iv=(ImageView) findViewById(R.id.ivrt);
			break;
		case R.id.btnrb:
			deltx=-50*density;
			delty=-50*density;
			 iv=(ImageView) findViewById(R.id.ivrb);
			break;
		case R.id.btn00:
			deltx=50*density;
			delty=50*density;
			iv=(ImageView) findViewById(R.id.iv00);
			break;
		default:
			break;
		}
		TranslateAnimation translateAnimation=null;
		if(v.getId()==R.id.btn00){
			translateAnimation=new TranslateAnimation(50*density, deltx, 50*density, delty);
		}else{
			translateAnimation=new TranslateAnimation(0, deltx, 0, delty);
		}
		
		
		ScaleAnimation scaleAnimation=new ScaleAnimation(1f, 2f, 1f, 2f);
		
		 set=new AnimationSet(true);
		set.setInterpolator(new LinearInterpolator());
		set.setDuration(5000);
		set.setFillAfter(false);
		set.addAnimation(translateAnimation);
		set.addAnimation(scaleAnimation);
		set.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				//�������ظ���������Ż�ִ�У������ǲ�ִ�еġ�
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				iv.clearAnimation();//ȡ����iv�Ķ���
			}
		});
		iv.startAnimation(set);	
		
	}
}
