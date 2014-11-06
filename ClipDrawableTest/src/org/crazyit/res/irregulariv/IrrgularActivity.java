package org.crazyit.res.irregulariv;

import java.io.File;
import java.lang.reflect.Field;

import org.crazyit.res.R;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

public class IrrgularActivity extends ActionBarActivity {

	Context mContext;
	private SearchView sv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.irregular_iv_test);
		mContext=this;
		
		
		
		 	sv=(SearchView) findViewById(R.id.sv);
			try {
			Field  field= SearchView.class.getDeclaredField("mSearchButton");
			field.setAccessible(true);
			ImageView view=	(ImageView) field.get(sv);
			view.setImageResource(R.drawable.choujiang1);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		findViewById(R.id.myOnePicButton1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/wk.apk");
//				Intent intent = new Intent();
//				intent.setAction(Intent.ACTION_VIEW);
//				intent.setDataAndType(Uri.fromFile(file),
//						"application/vnd.android.package-archive");
//				mContext.startActivity(intent);
				 Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
				 intent.setData(Uri.fromFile(file));
//				 intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
//				 intent.putExtra(Intent.EXTRA_RETURN_RESULT, true);
//				 intent.putExtra(Intent.EXTRA_ALLOW_REPLACE, true);
//				 intent.putExtra(Intent.EXTRA_INSTALLER_PACKAGE_NAME,
//				 mContext.getApplicationInfo().packageName);
				 IrrgularActivity.this.startActivityForResult(intent, 11);
				
			}
		});
		
		findViewById(R.id.bottom1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(IrrgularActivity.this, "haaaa", Toast.LENGTH_SHORT).show();
				
			}
		});
		
	}
}
