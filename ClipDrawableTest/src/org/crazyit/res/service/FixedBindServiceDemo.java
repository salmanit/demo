package org.crazyit.res.service;

import org.crazyit.res.R;
import org.crazyit.res.service.LocalService.LocalBinder;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FixedBindServiceDemo extends Activity {
	protected static final String TAG = "servicedemo";
	private Button btn_cancel;// btn_update,
	private boolean isBinded;
	private ServiceConnection conn;
	protected LocalBinder binder;
	protected Intent it;
	private LinearLayout linearLayout;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.servicetest);
		btn_cancel = (Button) findViewById(R.id.btn_to_service);
		conn = new ServiceConnection() {
			@Override
			public void onServiceDisconnected(ComponentName name) {
				isBinded = false;
				
			}

			@Override
			public void onServiceConnected(ComponentName name, IBinder service) {
				binder = (LocalBinder) service;
				Log.i(TAG, "服务启动!!!");
				isBinded = true;
				binder.start();// 做点什么
			}
		};
		btn_cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "btn_pressed!!!");
				 it = new Intent(FixedBindServiceDemo.this,
						LocalService.class);
				startService(it);
				bindService(it, conn, Context.BIND_AUTO_CREATE);
				Log.i(TAG, "bindService!!!");
			}
		});
		
		findViewById(R.id.layout).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(FixedBindServiceDemo.this,
						"Hello", Toast.LENGTH_SHORT).show();
			}
		});
		
	}

	protected void onStop() {
		
		// must unbind the service otherwise the ServiceConnection will be
		// leaked.
		
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		
		if (isBinded) {
			System.out.println(" onDestroy   unbindservice");
			unbindService(conn);
		}
		if (binder != null) {
			System.out.println(" onDestroy  stopservice");
			
			stopService(it);
		}
		
		super.onDestroy();
	}
	// @Override
	// protected void onResume() {
	// // TODO Auto-generated method stub
	// super.onResume();
	// Intent it = new Intent(FixedBindServiceDemo.this,
	// LocalService.class);
	// startService(it);
	// bindService(it, conn, Context.BIND_AUTO_CREATE);
	// System.out.println(" notification  onresume");
	// }
	// @Override
	// protected void onNewIntent(Intent intent) {
	// // TODO Auto-generated method stub
	// super.onNewIntent(intent);
	// Intent it = new Intent(FixedBindServiceDemo.this,
	// LocalService.class);
	// startService(it);
	// bindService(it, conn, Context.BIND_AUTO_CREATE);
	// }
}
