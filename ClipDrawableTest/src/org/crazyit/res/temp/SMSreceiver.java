package org.crazyit.res.temp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SMSreceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("===="+intent.getAction());
	}

}
