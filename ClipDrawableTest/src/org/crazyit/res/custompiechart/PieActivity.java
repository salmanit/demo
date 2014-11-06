package org.crazyit.res.custompiechart;

import java.util.ArrayList;

import org.crazyit.res.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PieActivity extends Activity {

	private PieView pView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.piechart);
		pView = (PieView) findViewById(R.id.pieview);
		initItem();
		TextView tvshow=(TextView) findViewById(R.id.tvshow);
		String action=getIntent().getAction();
		String type=getIntent().getType();
		if(Intent.ACTION_SEND.equals(action)&&type!=null){
			System.out.println("not null");
			String string=getIntent().getAction()+"\n";
			string+=getIntent().getStringExtra(Intent.EXTRA_SUBJECT)+"\n";
			string+=getIntent().getStringExtra(Intent.EXTRA_TEXT);
			tvshow.setText(string);
		}
	}

	@Override
	protected void onResume() {
		pView.setRotateEnabled(true);
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (pView != null)
			pView.setRotateEnabled(false);
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if (pView != null)
			pView.setDone(true);
		super.onDestroy();
	}

	/**
	 * 
	 * Description:初始化转盘的颜色，文字
	 * 
	 */
	public void initItem() {
		int color[] = new int[] { Color.BLUE, Color.GREEN, Color.RED,
				Color.WHITE, Color.YELLOW };
		float percent[] = new float[] { 0.2f, 0.3f, 0.1f, 0.05f, 0.35f };
		String names[] = new String[] { "BLUE", "GREEN", "RED", "WHITE",
				"YELLOW" };

		ArrayList<ChartProp> acps = pView.createCharts(5);
		int size = acps.size();
		for (int i = 0; i < size; i++) {
			ChartProp chartProp = acps.get(i);
			chartProp.setColor(color[i]);
			chartProp.setPercent(percent[i]);
			chartProp.setName(names[i]);

		}

		pView.initPercents();
		pView.setChartPropChangeListener(new ChartPropChangeListener() {

			@Override
			public void getChartProp(ChartProp chartProp) {
				showToast(chartProp.getName());
			}
		});
		pView.start();
	}

	private void showToast(String  str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
}
