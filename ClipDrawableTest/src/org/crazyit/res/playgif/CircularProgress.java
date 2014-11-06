package org.crazyit.res.playgif;

import org.crazyit.res.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.view.View;

/**
 * 
 * @author caijia
 *
 */
public class CircularProgress extends View {
	
	private Paint mRoundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mRoundProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private Paint mRoundTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private float mProgress ;
	private float mMax ;
	
	// the colors of the rings
	private int mRoundColor ;
	
	//The progress of the color ring
	private int mRoundProgressColor ;
	
	//Ring width
	private float mRoundWidth ;
	
	//Circular progress percentage color
	private int mRoundProgressTextColor ;
	
	//Circular progress percentage size
	private float mRoundProgressTextSize ; 
	
	//The ring radius
	private float mRoundradius ;
	
	//Whether to display the intermediate percentage
	private boolean mDisplayPercentage ;
	
	
	public CircularProgress(Context context) {
		super(context);
	}

	public CircularProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		if(isInEditMode()) return ;
		
		final Resources res = getResources();
		
		int defaultRoundColor = res.getColor(R.color.default_round_progress_roundColor);
		int defaultRoundProgressColor = res.getColor(R.color.default_round_progress_roundProgressColor);
		int defaultRoundProgressTextColor = res.getColor(R.color.default_round_progress_roundProgressTextColor);
		float defaultRoundWidth = res.getDimension(R.dimen.default_round_progress_roundWidth);
		float defaultRoundRadius = res.getDimension(R.dimen.default_round_progress_roundRadius);
		float defaultRoundProgressTextSize = res.getDimension(R.dimen.default_round_progress_roundProgressTextSize);
		boolean defaultDisplayPercentage = res.getBoolean(R.bool.default_round_progress_displayPercentage);
		int defaultMax=res.getInteger(R.integer.default_round_progress_max);
		int defaultProgress=res.getInteger(R.integer.default_round_progress_progress);
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
		mRoundColor = a.getColor(R.styleable.RoundProgress_roundColor, defaultRoundColor);
		mRoundProgressColor = a.getColor(R.styleable.RoundProgress_roundProgressColor, defaultRoundProgressColor);
		mRoundProgressTextColor = a.getColor(R.styleable.RoundProgress_roundProgressTextColor, defaultRoundProgressTextColor);
		mRoundWidth = a.getDimension(R.styleable.RoundProgress_roundWidth, defaultRoundWidth);
		mRoundradius = a.getDimension(R.styleable.RoundProgress_roundRadius, defaultRoundRadius);
		mRoundProgressTextSize = a.getDimension(R.styleable.RoundProgress_roundProgressTextSize, defaultRoundProgressTextSize);
		mDisplayPercentage = a.getBoolean(R.styleable.RoundProgress_displayPercentage, defaultDisplayPercentage);
		mMax=a.getInteger(R.styleable.RoundProgress_roundMax, defaultMax);
		mProgress=a.getInteger(R.styleable.RoundProgress_roundProgress, defaultProgress);
		mRoundPaint.setColor(mRoundColor);
		mRoundPaint.setStrokeWidth(mRoundWidth);
		mRoundPaint.setStyle(Paint.Style.STROKE);   //A hollow circle
		
		mRoundProgressPaint.setColor(mRoundProgressColor);
		mRoundProgressPaint.setStrokeWidth(mRoundWidth);
		mRoundProgressPaint.setStyle(Paint.Style.STROKE);   //A hollow circle
		
		mRoundTextPaint.setColor(mRoundProgressTextColor);
//		mRoundTextPaint.setTypeface(Typeface.DEFAULT_BOLD);
		//To prevent the font size is greater than that of the round radius
		mRoundTextPaint.setTextSize(mRoundProgressTextSize);
		mRoundTextPaint.setStrokeWidth(1);
		
		a.recycle();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		//draw round 
		float cx = getPaddingLeft() + (getWidth() - getPaddingLeft() - getPaddingRight()) / 2f ;
		float cy = getPaddingTop() + (getHeight() - getPaddingTop() - getPaddingBottom())/2f ;
		canvas.drawCircle(cx, cy, mRoundradius, mRoundPaint);
		
		//draw progress
		if(mMax > 0 && mProgress > 0) {
			RectF oval = new RectF(cx - mRoundradius, cy - mRoundradius, cx + mRoundradius, cy + mRoundradius);
			canvas.drawArc(oval, 0, 360 * mProgress / mMax , false, mRoundProgressPaint);
			//draw percentage
			if(mDisplayPercentage)
			{
				int percentage = (int)((mProgress / mMax) * 100) ;
				float percentageTextWidth = mRoundTextPaint.measureText(percentage + "%");
				FontMetrics fm = mRoundTextPaint.getFontMetrics();
				float percentageTextHeight = (float) (fm.leading - fm.ascent) ;
				System.out.println("cx = "+ cx +"---cy = "+cy +" ---textsize = " +percentageTextHeight);
				canvas.drawText(percentage +"%", cx - percentageTextWidth / 2 , cy + percentageTextHeight / 2, mRoundTextPaint);
			}
		}
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
	}
	
	private int measureWidth(int measureSpec){
		float result = 0 ;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		if(mode == MeasureSpec.EXACTLY){
			result = size ;
		}
		else{
			result = 2*(mRoundradius + mRoundWidth) + getPaddingLeft() + getPaddingRight() ;
			if(mode == MeasureSpec.AT_MOST){
				result = Math.min(result, size);
			}
		}
		return (int) FloatMath.ceil(result);
	}
	
	
	private int measureHeight(int measureSpec){
		float result = 0 ;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		if(mode == MeasureSpec.EXACTLY){
			result = size ;
		}
		else{
			result = 2*(mRoundradius + mRoundWidth) + getPaddingTop() + getPaddingBottom() ;
			if(mode == MeasureSpec.AT_MOST){
				result = Math.min(result, size);
			}
		}
		return (int) FloatMath.ceil(result);
	}

	public synchronized void setProgress(float mProgress) {
		if(mProgress >= 0 && mProgress <= mMax)
		{
			this.mProgress = mProgress;
			postInvalidate() ;
		}
	}

	public synchronized void setMax(float mMax) {
		this.mMax = mMax;
	}

	public synchronized float getProgress() {
		return mProgress;
	}

	public synchronized float getMax() {
		return mMax;
	}

	public int getRoundColor() {
		return mRoundColor;
	}

	public void setRoundColor(int mRoundColor) {
		this.mRoundColor = mRoundColor;
	}

	public int getRoundProgressColor() {
		return mRoundProgressColor;
	}

	public void setRoundProgressColor(int mRoundProgressColor) {
		this.mRoundProgressColor = mRoundProgressColor;
	}

	public float getRoundWidth() {
		return mRoundWidth;
	}

	public void setRoundWidth(float mRoundWidth) {
		this.mRoundWidth = mRoundWidth;
	}

	public int getRoundProgressTextColor() {
		return mRoundProgressTextColor;
	}

	public void setRoundProgressTextColor(int mRoundProgressTextColor) {
		this.mRoundProgressTextColor = mRoundProgressTextColor;
	}

	public float getRoundProgressTextSize() {
		return mRoundProgressTextSize;
	}

	public void setRoundProgressTextSize(float mRoundProgressTextSize) {
		this.mRoundProgressTextSize = mRoundProgressTextSize;
	}

	public float getRoundradius() {
		return mRoundradius;
	}

	public void setRoundradius(float mRoundradius) {
		this.mRoundradius = mRoundradius;
	}

	public boolean isDisplayPercentage() {
		return mDisplayPercentage;
	}

	public void setDisplayPercentage(boolean mDisplayPercentage) {
		this.mDisplayPercentage = mDisplayPercentage;
	}
}
