package org.crazyit.res.customdraw;

import org.crazyit.res.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MydrawActivity extends Activity {

	private MyDrawView mydv;
	private int number=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mydrawview);
		mydv=(MyDrawView) findViewById(R.id.myDrawView1);
		findViewById(R.id.btnscale).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			mydv.setBG(BitmapFactory.decodeResource(getResources(), R.drawable.p2));
				((Button)findViewById(R.id.btnscale)).setText("sacle"+number++);
			}
		});
	}
}
