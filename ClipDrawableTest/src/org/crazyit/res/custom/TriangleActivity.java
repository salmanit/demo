package org.crazyit.res.custom;

import org.crazyit.res.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class TriangleActivity extends Activity {

	private LinearLayout main_linearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trianglexml);
		main_linearLayout=(LinearLayout)findViewById(R.id.layout);
		main_linearLayout.addView(new TriangleView(this,0,40)); 
	}
	
}
