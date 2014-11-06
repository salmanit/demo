package org.crazyit.res.sms;

import org.crazyit.res.R;
import org.crazyit.res.bgsms.SMSsendActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class ToastActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toastxml);
		findViewById(R.id.btntest).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(ToastActivity.this, "haha", Toast.LENGTH_SHORT).show();
				startActivity(new Intent(ToastActivity.this, SMSsendActivity.class));
			}
		});
	}
}
