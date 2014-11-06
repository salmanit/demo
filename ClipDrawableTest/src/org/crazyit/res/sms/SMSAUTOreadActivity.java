package org.crazyit.res.sms;

import org.crazyit.res.R;


import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Intent;
import android.database.ContentObservable;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class SMSAUTOreadActivity extends Activity {

	private EditText eText;
	private Button btnagin;
							
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smsreadauto);
		eText=(EditText) findViewById(R.id.etresult);
		btnagin=(Button) findViewById(R.id.btnagain);
		btnagin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SmsContent content = new SmsContent(SMSAUTOreadActivity.this, new Handler(), eText);
				  // ×¢²á¶ÌÐÅ±ä»¯¼àÌý
				SMSAUTOreadActivity.this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, content);
				
			}
		});
//		getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, new ContentObserver(new Handler()) {
//			@Override
//			public boolean deliverSelfNotifications() {
//				
//				return super.deliverSelfNotifications();
//			}
//			@Override
//			public void onChange(boolean selfChange) {
//				
//				
//			}
//			
//		});
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(tag){
					try {
						Thread.sleep(5000);
						test();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		}).start();
		
	}
	boolean tag=true;
	public void  test(){
		WindowManager winManager=(WindowManager) getSystemService(WINDOW_SERVICE);
		
		KeyguardManager mykeyguard=(KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		if(mykeyguard.inKeyguardRestrictedInputMode()){
			tag=false;
			KeyguardLock lock=mykeyguard.newKeyguardLock("1");
			
			lock.disableKeyguard();
			Intent intent=new Intent(this, ToastActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			lock.reenableKeyguard();
			System.out.println("true");
		}else{
			System.out.println("false");
		}
		
	}
	
	
}
