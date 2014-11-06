package org.crazyit.res.custom;


import org.crazyit.res.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class Mytriangletest extends View {

	Paint paint;
	Bitmap defaultBitmap,meBitmap,choiceBitmap;
	float factor=0.8f;//所占的比列
	int lines=8;//总共有几行图片，每行个数和行号一样
	int[][] status=new int[lines][];
	int  sum=0;
	public Mytriangletest(Context context) {
		super(context);
		int chazhi=(int) ((1+lines)*lines/2*(1-factor));
		for(int i=0;i<lines;i++){
			for(int j=0;j<i+1;j++){
				status[i][j]=1;
			}
		}
		init();
	}

	private void init() {
		defaultBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.triangle_man_default);
		meBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.triangle_man_for);
		choiceBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.triangle_man_relinquish);
		
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}
	
}
