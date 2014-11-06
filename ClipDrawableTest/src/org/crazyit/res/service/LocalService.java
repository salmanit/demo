package org.crazyit.res.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {
	public static final String TAG = "servicedemo";

	@Override
	public IBinder onBind(Intent intent) {
		return new LocalBinder();
	}

	public void sayHelloWorld() {
		Toast.makeText(this.getApplicationContext(),
				"Hello World Local Service!", Toast.LENGTH_SHORT).show();
	}

	public class LocalBinder extends Binder {
		LocalService getService() {
			// Return this instance of LocalService so clients can call public
			// methods
			return LocalService.this;
		}

		public void start() {
			Log.i(TAG, "IN_sayHelloWorld!!!");
			sayHelloWorld();

		}
	}
}
