package org.crazyit.res;

import com.iflytek.cloud.SpeechUtility;

import android.app.Application;

public class MyAPP extends Application {

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SpeechUtility.createUtility(MyAPP.this, "appid="+"53ad0c9d");
	}
}
