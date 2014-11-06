package org.crazyit.res.testdemo;

import org.crazyit.res.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Notification;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class AnimatorTestActivity extends Activity {
	private float density;
	private ImageView iv;
	private AnimatorSet set;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.testobjectanimatorxml);
		density=getResources().getDisplayMetrics().density;
		
	}
	
	public void btnclickTest(View v){
		if(v.getId()==R.id.btncancel){
			if(set!=null)
				set.cancel();
			return;
		}
		float deltx=0,delty=0;
		switch (v.getId()) {
		case R.id.btnstart:
			deltx=50*density;
			delty=50*density;
			 iv=(ImageView) findViewById(R.id.ivfront);
			break;
		case R.id.btnlb:
			deltx=50*density;
			delty=200*density;
			 iv=(ImageView) findViewById(R.id.ivlb);
			break;
		case R.id.btnrt:
			deltx=200*density;
			delty=50*density;
			 iv=(ImageView) findViewById(R.id.ivrt);
			break;
		case R.id.btnrb:
			deltx=200*density;
			delty=200*density;
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
		ObjectAnimator transX = null,transy = null,scaleX,scaleY;
		if(v.getId()==R.id.btn00){
			transX=ObjectAnimator.ofFloat(iv, "X", 50*density,100*density);
			transy=ObjectAnimator.ofFloat(iv, "Y", 50*density,100*density);
			
		}else{
			transX=ObjectAnimator.ofFloat(iv, "X",deltx, 100*density);
			transy=ObjectAnimator.ofFloat(iv, "Y",delty, 100*density);
			//如果有旋转的话，中心轴是视图的中心，则需要先左上移，结束的时候再右下移。
//			transX=ObjectAnimator.ofFloat(iv, "X",deltx-iv.getWidth()/2, 100*density+iv.getWidth()/2);
//			transy=ObjectAnimator.ofFloat(iv, "Y",delty-iv.getHeight()/2, 100*density+iv.getHeight()/2);
		}
		iv.setPivotX(0);
		iv.setPivotY(0);//默认的中心轴，是视图的中心，我们设置为左上角，
		scaleX=ObjectAnimator.ofFloat(iv, "scaleX", 1f,2f);
		scaleY=ObjectAnimator.ofFloat(iv, "scaleY", 1f,2f);
		
		//ObjectAnimator rotate=ObjectAnimator.ofFloat(iv, "rotation", 0,360);

		 set=new AnimatorSet();
		set.setInterpolator(new LinearInterpolator());
		set.setDuration(7000);
		set.playTogether(transX,transy,scaleX,scaleY);
		//set.playTogether(transX,transy,scaleX,scaleY,rotate);
		
		set.addListener(new AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				System.out.println("start");
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				System.err.println("repeat");
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
				
				System.err.println("end");
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				System.err.println("cancel");
			}
		});
		
		set.start();
	}
	
	
	
}
