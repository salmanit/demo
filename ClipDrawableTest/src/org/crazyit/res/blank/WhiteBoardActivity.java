package org.crazyit.res.blank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.crazyit.res.R;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class WhiteBoardActivity extends Activity {

	private SurfaceView sView;
	private SurfaceHolder holder;
	private Button btnNextStep;
	ArrayList<PagesEntity> list;
	private int screenheight, screenwidth;
	private MyObject myObject;
	private TextView tvshow, tvold;
	private CustomView customView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置成全屏模式
		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏

		setContentView(R.layout.l);
		btnNextStep = (Button) findViewById(R.id.btnNextStep);
		screenwidth = getResources().getDisplayMetrics().widthPixels;
		screenheight = getResources().getDisplayMetrics().heightPixels;
		customView = (CustomView) findViewById(R.id.customv);
		tvshow = (TextView) findViewById(R.id.tvshow);
		tvold = (TextView) findViewById(R.id.tvold);
		btnNextStep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String xmlString = MyObjectToXml.CreatXmlString(customView
						.getData());
				tvshow.setText(xmlString);

			}
		});
		findViewById(R.id.btnold).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tvold.setText(readxml());
			}
		});
	}

	public String readxml() {

		BufferedReader bReader = new BufferedReader(new InputStreamReader(
				getResources().openRawResource(R.raw.object)));
		try {
			while (bReader.ready()) {
				try {
					return bReader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void drawLine(SurfaceHolder holder) {

		Canvas canvas = holder.lockCanvas();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawColor(Color.WHITE);

		PagesEntity page = list.get(1);
		ArrayList<CellEntity> cells = page.getCelllist();
		for (int i = 0; i < cells.size(); i++) {
			CellEntity cell = cells.get(i);
			paint.setColor(Color.parseColor(cell.getColorString()));
			paint.setStrokeWidth(cell.getPaintSize());
			float[] xcode = cell.getxCode();
			float[] ycode = cell.getyCode();
			Path path = new Path();
			for (int j = 0; j < xcode.length && j < ycode.length; j++) {

				if (j == 0) {
					path.moveTo(xcode[j] * screenwidth, ycode[j] * screenheight);
				} else {
					path.lineTo(xcode[j] * screenwidth, ycode[j] * screenheight);
				}

			}
			canvas.drawPath(path, paint);
			holder.lockCanvas(new Rect(0, 0, 0, 0));
			holder.unlockCanvasAndPost(canvas);
		}
	}

	private float factor = 500;

	public void drawRect(SurfaceHolder holder) {
		Canvas canvas = holder.lockCanvas();
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		canvas.drawColor(Color.WHITE);
		// 画点
		paint.setColor(Color.RED);
		for (int i = 0; i < myObject.getPointList().size(); i++) {
			MyPoint point = myObject.getPointList().get(i);
			canvas.drawCircle(point.getXcoordinate() * factor,
					point.getYcoordinate() * factor, 2, paint);
		}

		// 画文字
		paint.setColor(Color.BLACK);
		for (int i = 0; i < myObject.getTextList().size(); i++) {
			MyPointText text = myObject.getTextList().get(i);
			canvas.drawText(text.getText(), text.getXcoordinate() * factor,
					text.getYcoordinate() * factor, paint);
		}

		// 画线
		for (int i = 0; i < myObject.getLineList().size(); i++) {
			MyLine line = myObject.getLineList().get(i);
			paint.setColor(Color.parseColor(line.getColor()));
			paint.setStrokeWidth(line.getLineSize());
			float startX = 0, startY = 0, stopX = 0, stopY = 0;
			for (int j = 0; j < myObject.getTextList().size(); j++) {
				MyPointText text = myObject.getTextList().get(j);
				MyPoint point = myObject.getPointList().get(j);
				if (text.getDp() == line.getDp_from()) {
					startX = point.getXcoordinate();
					startY = point.getYcoordinate();
				} else if (text.getDp() == line.getDp_to()) {
					stopX = point.getXcoordinate();
					stopY = point.getYcoordinate();
				}
			}
			canvas.drawLine(startX * factor, startY * factor, stopX * factor,
					stopY * factor, paint);
		}

		holder.unlockCanvasAndPost(canvas);

		holder.lockCanvas(new Rect(0, 0, 0, 0));
		holder.unlockCanvasAndPost(canvas);

	}

}
