package org.crazyit.res.authenticator;

import java.net.Authenticator;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AuthenticationService extends Service {

	private MyAuthenticator mAuthenticator;
	private String TAG="AuthenticationService";
	@Override
	public void onCreate() {
	
		super.onCreate();
		 if (Log.isLoggable("AuthenticationService", Log.VERBOSE)) {
	            Log.v("AuthenticationService", "SampleSyncAdapter Authentication Service started.");
	        }
	        mAuthenticator = new MyAuthenticator(this);
	}
    @Override
    public IBinder onBind(Intent intent) {
        if (Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG,
                "getBinder()...  returning the AccountAuthenticator binder for intent "
                    + intent);
        }
        /**
         * Authenticator就是AbstractAccountAuthenticator的具体实现类的对象
         * 关于他的讲解请参看Authenticator。
         */
        return mAuthenticator.getIBinder();
    }
}
