package org.crazyit.res.irregulariv;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.widget.Button;

public class MyOnePicButton extends Button {

	private int width;
	private int height;
	public MyOnePicButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initMyButton();
	}

	
	   private void initMyButton(){  
   		Paint p=new Paint();
   		p.setTextSize(getTextSize());
   		Rect bounds=new Rect();
			p.getTextBounds(getText().toString(), 0, getText().toString().length(), bounds);
			System.out.println("bounds=="+bounds.toShortString());
			 width=(bounds.centerX()+padding+offset)*2;
			 height=(30+padding)*2;
		   setBackgroundDrawable(newSelector());  
	    }  
	    /** 
	     * 传入改变亮度前的bitmap，返回改变亮度后的bitmap 
	     * @param srcBitmap 
	     * @return 
	     */  
	    private Drawable changeBrightnessBitmap(Bitmap srcBitmap){  
	         Bitmap bmp = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(),    
	                Config.ARGB_8888);    
	        int brightness = 60 - 127;    
	        ColorMatrix cMatrix = new ColorMatrix();    
	        cMatrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1,    
	                0, 0, brightness,// 改变亮度     
	                0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });    
	        Paint paint = new Paint();    
	        paint.setColorFilter(new ColorMatrixColorFilter(cMatrix));    
	        Canvas canvas = new Canvas(bmp);    
	        // 在Canvas上绘制一个Bitmap  
	        canvas.drawBitmap(srcBitmap, 0, 0, paint);    
	       return new BitmapDrawable(bmp);  
	    }  
	    /** 设置Selector。 */    
	    	private StateListDrawable newSelector() {    
	        StateListDrawable bg = new StateListDrawable();    
	        Drawable normal =getBackground();  
	        Drawable pressed =changeBrightnessBitmap(((BitmapDrawable) getBackground()).getBitmap());;    
	        
	        normal=clipDrawable(normal);
	        pressed=clipDrawable(pressed);
	        // View.PRESSED_ENABLED_STATE_SET     
	        bg.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);    
	        // View.ENABLED_STATE_SET     
	        bg.addState(new int[] { android.R.attr.state_enabled }, normal);  
	       // bg.addState(new int[] { -android.R.attr.state_enabled }, getResources().getDrawable(R.drawable.graybg));    
	        // View.EMPTY_STATE_SET     
	        bg.addState(new int[] {}, normal);    
	        
	        return bg;    
	    } 
	    	private int offset=15;
	    	private int padding=10;
	    	public Drawable clipDrawable(Drawable drawable){
	    		System.out.println("width="+width+"height="+height);
	    		Path path1=new Path();
	    		path1.moveTo(0, 0);
	    		path1.lineTo(offset,height/2);
	    		path1.lineTo(0, height);
	    		path1.lineTo(width-offset, height);
	    		path1.lineTo(width, height/2);
	    		path1.lineTo(width-offset, 0);
	    		path1.close();
	    		Bitmap bitmap=Bitmap.createBitmap(width, height, Config.ARGB_8888);
	    		Canvas canvas=new Canvas(bitmap);
	    		canvas.clipPath(path1);
	    		if(drawable!=null){
	    			drawable.setBounds(0, 0, width, height);
	    			drawable.draw(canvas);
	    		}
	    		return new BitmapDrawable(getResources(), bitmap);
	    	}
}
