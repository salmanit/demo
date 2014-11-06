package org.crazyit.res;

import org.crazyit.res.opengltest.MyCustomGraphic;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class ViewToBitmap extends Activity {

	private ImageView iv;
	private  MyCustomGraphic myCustomGraphic;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewtobitmapxml);
		//addContentView(getView(), new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		iv=(ImageView) findViewById(R.id.ivresult);
		myCustomGraphic=(MyCustomGraphic) findViewById(R.id.mydraw);
		iv.setImageBitmap(getBitmapFromView(getView()));
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				iv.setImageBitmap(getBitmapFromView(getView()));
				
			}
		});
		findViewById(R.id.btnclear).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
			myCustomGraphic.clearscreen();	
			}
		});
		findViewById(R.id.btnreset).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				myCustomGraphic.resetScreen();
				
			}
		});
		findViewById(R.id.btnbig).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				myCustomGraphic.bigDraw();
			}
		});
		findViewById(R.id.btnsmall).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				myCustomGraphic.smallDraw();
			}
		});
	}
	
	public  Bitmap getBitmapFromView(View view) {
       view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        if(bitmap==null){
        	Toast.makeText(this, "null", Toast.LENGTH_SHORT).show();
        }
        return bitmap;
 }

	private View getView(){
		View view=LayoutInflater.from(this).inflate(R.layout.layoutview, null);
		
		TextView tvtitle=(TextView) view.findViewById(R.id.tvtitle);
		TextView tvcontent=(TextView) view.findViewById(R.id.tvcontent);
		ImageView ivpic=(ImageView) view.findViewById(R.id.ivpic);
		tvcontent.setText("我在用翼起学客户端，随时随地查看附近培训学校信息及环境，看学友评价和机构活动，推荐给正在为找培训而发愁的你，快去试试吧：http://www.189study.cn/");
		ivpic.setImageResource(R.drawable.image02);
		return view;
	}
}
