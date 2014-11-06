package org.crazyit.res.progressbar;

import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.res.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;

public class TestActivity extends Activity implements OnClickListener{

	
	private HorizontalProgressbar horProgressbar;
	private ProgressBar pBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progresstest);
		initview();
		
	}
	private Timer timer1;
	private void initview() {
		horProgressbar=(HorizontalProgressbar) findViewById(R.id.horpro);
		pBar=(ProgressBar) findViewById(R.id.pb);
		findViewById(R.id.btnstart).setOnClickListener(this);
		findViewById(R.id.btnstop).setOnClickListener(this);
		
	}
	private int number=1;
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnstart:
			number=1;
			timer1=new Timer();
			timer1.schedule(new TimerTask() {
				
				@Override
				public void run() {
					handler.sendEmptyMessage(0x111);
					
					if(number>100)
						cancel();
				}
			}, 0,200);
			break;
		case R.id.btnstop:
			if(timer1!=null)
				timer1.cancel();
			break;
		default:
			break;
		}
	}
	
	
private Handler handler=new Handler(){
	
	public void handleMessage(android.os.Message msg) {
		switch (msg.what) {
		case 0x111:
			horProgressbar.setProgress(number);
			pBar.setProgress(number);
			number++;
			break;
		case 0x222:
			
			break;
		default:
			break;
		}
	};
}	;
	
protected void onDestroy() {
	super.onDestroy();
	if(timer1!=null)
		timer1.cancel();
};

}
