package org.crazyit.res.custom;

import org.crazyit.res.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Schoollayout extends LinearLayout {

	public Schoollayout(Context context) {
		super(context);
	}

	public Schoollayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		
	}
private TextView tv1;
private ImageView iv1,iv2,iv3,iv4;
private int iv1_padding,iv2_padding,iv3_padding,iv4_padding;
	
@Override
protected void onFinishInflate() {
	super.onFinishInflate();
	this.tv1=(TextView) findViewById(R.id.tv1);
	this.iv1=(ImageView) findViewById(R.id.iv1);
	this.iv2=(ImageView) findViewById(R.id.iv2);
	this.iv3=(ImageView) findViewById(R.id.iv3);
	this.iv4=(ImageView) findViewById(R.id.iv4);
}
	
public void setData(String name,boolean iv1show,boolean iv2show,boolean iv3show,boolean iv4show){

	int i=0,j=0,k=0,l=0,m=0;
	tv1.setText(name);
	if(iv1show){
		if(iv1_padding==0){
			iv1_padding=BitmapFactory.decodeResource(getResources(), R.drawable.iv1).getWidth();
			iv1_padding+=getResources().getDisplayMetrics().density*6;
		}
		i=iv1_padding;
		iv1.setVisibility(View.VISIBLE);
	}else{
		iv1.setVisibility(View.INVISIBLE);
		}
	
	if(iv2show){
		if(iv2_padding==0){
			iv2_padding=BitmapFactory.decodeResource(getResources(), R.drawable.iv2).getWidth();
			iv2_padding+=getResources().getDisplayMetrics().density*6;
		}
		i+=iv2_padding;
		j=iv2_padding;
		iv2.setVisibility(View.VISIBLE);
	}else{
		iv2.setVisibility(View.INVISIBLE);
	}
	
	if(iv3show){
		if(iv3_padding==0){
			iv3_padding=BitmapFactory.decodeResource(getResources(), R.drawable.iv3).getWidth();
			iv3_padding+=getResources().getDisplayMetrics().density*6;
		}
		i+=iv3_padding;
		j+=iv3_padding;
		k=iv3_padding;
		
		iv3.setVisibility(View.VISIBLE);
	}else{
		iv3.setVisibility(View.INVISIBLE);
	}
	
	if(iv4show){
		if(iv4_padding==0){
			iv4_padding=BitmapFactory.decodeResource(getResources(), R.drawable.iv4).getWidth();
			iv4_padding+=getResources().getDisplayMetrics().density*6;
		}
		i+=iv4_padding;
		j+=iv4_padding;
		k+=iv4_padding;
		l=iv4_padding;
		
		iv4.setVisibility(View.VISIBLE);
	}else{
		iv4.setVisibility(View.INVISIBLE);
	}
	
	tv1.setPadding(tv1.getPaddingLeft(), tv1.getPaddingTop(), i, tv1.getPaddingBottom());
	iv1.setPadding(iv1.getPaddingLeft(), iv1.getPaddingTop(), j, iv1.getPaddingBottom());
	iv2.setPadding(iv2.getPaddingLeft(), iv2.getPaddingTop(), k, iv2.getPaddingBottom());
	iv3.setPadding(iv3.getPaddingLeft(), iv3.getPaddingTop(), l, iv3.getPaddingBottom());
}


}
