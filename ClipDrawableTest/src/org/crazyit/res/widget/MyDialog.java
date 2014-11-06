package org.crazyit.res.widget;


import org.crazyit.res.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDialog extends Dialog {

	public MyDialog(Context context) {
		super(context,R.style.Dialog_bocop);
		initView(context);
	}


	
	private TextView tvdot;
	private ImageView ivCircle;
	private RotateAnimation rotateAnimation;
	private void  initView(Context context){
		View v=LayoutInflater.from(context).inflate(R.layout.mydialog, null);
		tvdot=(TextView) v.findViewById(R.id.tvdot);
		ivCircle=(ImageView) v.findViewById(R.id.ivcircle);
		setContentView(v);
		initAnim();
		
	}

	private void initAnim() {
		 rotateAnimation=new RotateAnimation(360, 0, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setRepeatCount(Animation.INFINITE);
		rotateAnimation.setRepeatMode(Animation.RESTART);
		rotateAnimation.setDuration(2000);
		rotateAnimation.setInterpolator(new AccelerateInterpolator());
		//getWindow().setWindowAnimations(android.R.anim.slide_in_left);
	}

	@Override
	public void show() {
		
		super.show();
		ivCircle.startAnimation(rotateAnimation);
		handler.sendEmptyMessage(0x123);
	}
	private int num=0;
	private int maxNum=3;
	
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0x123:
				StringBuilder builder=new StringBuilder();
				if(num>maxNum){
					num=0;
				}
				for(int i=0;i<num;i++){
					builder.append(".");
				}
				num++;
				tvdot.setText(builder.toString());
				if(isShowing()){
					handler.sendEmptyMessageDelayed(0x123,200);
				}else {
					num=0;
				}
				break;

			default:
				break;
			}
		};
	};
	
	public void dismiss() {
		super.dismiss();
		rotateAnimation.cancel();
	};
}
