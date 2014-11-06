package org.crazyit.res.authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class AccountSyncService extends Service {
	private final String TAG = getClass().getSimpleName();
	// Storage for an instance of the sync adapter
	//private static YoushiThreadedSyncAdapter sSyncAdapter = null;
	// Object to use as a thread-safe lock
	private static final Object sSyncAdapterLock = new Object();

	@Override
	public void onCreate() {
		super.onCreate();
//		synchronized (sSyncAdapterLock) {
//			if (sSyncAdapter == null) {
//				sSyncAdapter = new YoushiThreadedSyncAdapter(
//						getApplicationContext(), true);
//			}
//		}
	}

	@Override
	/**
	 * Logging-only destructor.
	 */
	public void onDestroy() {
		super.onDestroy();
		Log.i(TAG, "Service destroyed");
	}

	@Override
	public IBinder onBind(Intent intent) {
		System.out.println("onbind");
		return null;
	}

//	@Override
//	public IBinder onBind(Intent intent) {
//		// TODO Auto-generated method stub
//		return sSyncAdapter.getSyncAdapterBinder();
//	}

}
