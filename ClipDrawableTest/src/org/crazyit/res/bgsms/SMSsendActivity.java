package org.crazyit.res.bgsms;

import org.crazyit.res.R;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class SMSsendActivity extends Activity implements OnClickListener{

	private EditText etMynumber;
	private EditText etShowNUmber;
	private EditText etSendtoNumber;
	private EditText etContent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smssend);
		initUI();
		getMyNumber();
	}

	private void initUI() {
		findViewById(R.id.btncontact).setOnClickListener(this);
		findViewById(R.id.btnsend).setOnClickListener(this);
		findViewById(R.id.btnsendclock).setOnClickListener(this);
		etMynumber=(EditText) findViewById(R.id.etmyphone);
		etShowNUmber=(EditText) findViewById(R.id.etshownumber);
		etSendtoNumber=(EditText) findViewById(R.id.etsendtonumber);
		etContent=(EditText) findViewById(R.id.etcontent);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btncontact:
			
			break;
		case R.id.btnsend:
			
			break;
		case R.id.btnsendclock:
			
			break;
		default:
			break;
		}
		
	}
	
	private void getMyNumber(){
		StringBuilder sBuilder=new StringBuilder();
		TelephonyManager phoneMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		sBuilder.append("手机型号MODEL="+Build.MODEL);
		sBuilder.append("\n电话号码="+phoneMgr.getLine1Number());
		sBuilder.append("\nSDK版本号="+Build.VERSION.SDK);
		sBuilder.append("\n用户可见版本号="+Build.VERSION.RELEASE);
		etMynumber.setText(sBuilder.toString());
	}
}
