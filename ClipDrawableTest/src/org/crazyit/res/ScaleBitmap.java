package org.crazyit.res;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

public class ScaleBitmap extends ImageView {

	private int bitmap_W;
	private int bitmap_H;
	private int MAX_W;
	private int MAX_H;
	private int MIN_W;
	private int MIN_H;
	private int start_Top;
	private int start_Left;
	private int start_Bottom;
	private int start_Right;
	private MODE mode;
	private int current_x;
	private int current_y;
	private int start_x;
	private int start_y;
	private float beforeLenght;
	private int current_Left;
	private int current_Top;
	private int current_Right;
	private int current_Bottom;
	private int screen_H;
	private boolean isControl_V;
	private boolean isControl_H;
	private int screen_W;
	private boolean isScaleAnim;
	private float afterLenght;
	private float scale_temp;
private Activity mActivity;
	public ScaleBitmap(Context context) {
		super(context);
		mActivity=(Activity) context;
	}

	public ScaleBitmap(Context context, AttributeSet attrs) {
		super(context, attrs);
		mActivity=(Activity) context;
	}
	
	/***
	 * 设置显示图片
	 */
	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		/** 获取图片宽高 **/
		bitmap_W = bm.getWidth();
		bitmap_H = bm.getHeight();

		MAX_W = bitmap_W * 3;
		MAX_H = bitmap_H * 3;

		MIN_W = bitmap_W / 2;
		MIN_H = bitmap_H / 2;

	}
	/**接着我们在onLayout方法中我们获取最初的l,t,r,b*/
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);
		if (start_Top == -1) {
			start_Top = top;
			start_Left = left;
			start_Bottom = bottom;
			start_Right = right;
		}

	}
	
	public  enum MODE{
		NONE,DRAG,ZOOM
	}
	/***
	 * touch 事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/** 处理单点、多点触摸 **/
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			onTouchDown(event);
			break;
		// 多点触摸
		case MotionEvent.ACTION_POINTER_DOWN:
			onPointerDown(event);
			break;

		case MotionEvent.ACTION_MOVE:
			onTouchMove(event);
			break;
		case MotionEvent.ACTION_UP:
			mode = MODE.NONE;
			break;

		// 多点松开
		case MotionEvent.ACTION_POINTER_UP:
			mode = MODE.NONE;
			/** 执行缩放还原 **/
			if (isScaleAnim) {
				//doScaleAnim();
			}
			break;
		}

		return true;
	}
	
	/** 按下 **/
	void onTouchDown(MotionEvent event) {
		mode = MODE.DRAG;

		current_x = (int) event.getRawX();
		current_y = (int) event.getRawY();

		start_x = (int) event.getX();
		start_y = current_y - this.getTop();

	}	
	
	/** 两个手指 只能放大缩小 **/
	void onPointerDown(MotionEvent event) {
		if (event.getPointerCount() == 2) {
			mode = MODE.ZOOM;
			beforeLenght = getDistance(event);// 获取两点的距离
		}
	}
	
	private float getDistance(MotionEvent event) {
		float x1=event.getX(0);
		float y1=event.getX(0);
		float x2=event.getX(1);
		float y2=event.getY(1);
		return (float) Math.sqrt(Math.pow(Math.abs(x1-x2), 2)+Math.pow(Math.abs(y1-y2), 2));
	}

	/** 移动的处理 **/
	void onTouchMove(MotionEvent event) {
		int left = 0, top = 0, right = 0, bottom = 0;
		/** 处理拖动 **/
		if (mode == MODE.DRAG) {

			/** 在这里要进行判断处理，防止在drag时候越界 **/

			/** 获取相应的l，t,r ,b **/
			left = current_x - start_x;
			right = current_x + this.getWidth() - start_x;
			top = current_y - start_y;
			bottom = current_y - start_y + this.getHeight();

			/** 水平进行判断 **/
			if (isControl_H) {
				if (left >= 0) {
					left = 0;
					right = this.getWidth();
				}
				if (right <= screen_W) {
					left = screen_W - this.getWidth();
					right = screen_W;
				}
			} else {
				left = this.getLeft();
				right = this.getRight();
			}
			/** 垂直判断 **/
			if (isControl_V) {
				if (top >= 0) {
					top = 0;
					bottom = this.getHeight();
				}

				if (bottom <= screen_H) {
					top = screen_H - this.getHeight();
					bottom = screen_H;
				}
			} else {
				top = this.getTop();
				bottom = this.getBottom();
			}
			if (isControl_H || isControl_V)
			//	this.setPosition(left, top, right, bottom);

			current_x = (int) event.getRawX();
			current_y = (int) event.getRawY();

		}
		/** 处理缩放 **/
		else if (mode == MODE.ZOOM) {

			afterLenght = getDistance(event);// 获取两点的距离

			float gapLenght = afterLenght - beforeLenght;// 变化的长度

			if (Math.abs(gapLenght) > 5f) {
				scale_temp = afterLenght / beforeLenght;// 求的缩放的比例

				this.setScale(scale_temp);

				beforeLenght = afterLenght;
			}
		}

	}
	
	/** 处理缩放 **/
	void setScale(float scale) {
		int disX = (int) (this.getWidth() * Math.abs(1 - scale)) / 4;// 获取缩放水平距离
		int disY = (int) (this.getHeight() * Math.abs(1 - scale)) / 4;// 获取缩放垂直距离

		// 放大
		if (scale > 1 && this.getWidth() <= MAX_W) {
			current_Left = this.getLeft() - disX;
			current_Top = this.getTop() - disY;
			current_Right = this.getRight() + disX;
			current_Bottom = this.getBottom() + disY;

			this.setFrame(current_Left, current_Top, current_Right,
					current_Bottom);
			/***
			 * 此时因为考虑到对称，所以只做一遍判断就可以了。
			 */
			if (current_Top <= 0 && current_Bottom >= screen_H) {
				Log.e("jj", "屏幕高度=" + this.getHeight());
				isControl_V = true;// 开启垂直监控
			} else {
				isControl_V = false;
			}
			if (current_Left <= 0 && current_Right >= screen_W) {
				isControl_H = true;// 开启水平监控
			} else {
				isControl_H = false;
			}

		}
		// 缩小
		else if (scale < 1 && this.getWidth() >= MIN_W) {
			current_Left = this.getLeft() + disX;
			current_Top = this.getTop() + disY;
			current_Right = this.getRight() - disX;
			current_Bottom = this.getBottom() - disY;
			/***
			 * 在这里要进行缩放处理
			 */
			// 上边越界
			if (isControl_V && current_Top > 0) {
				current_Top = 0;
				current_Bottom = this.getBottom() - 2 * disY;
				if (current_Bottom < screen_H) {
					current_Bottom = screen_H;
					isControl_V = false;// 关闭垂直监听
				}
			}
			// 下边越界
			if (isControl_V && current_Bottom < screen_H) {
				current_Bottom = screen_H;
				current_Top = this.getTop() + 2 * disY;
				if (current_Top > 0) {
					current_Top = 0;
					isControl_V = false;// 关闭垂直监听
				}
			}

			// 左边越界
			if (isControl_H && current_Left >= 0) {
				current_Left = 0;
				current_Right = this.getRight() - 2 * disX;
				if (current_Right <= screen_W) {
					current_Right = screen_W;
					isControl_H = false;// 关闭
				}
			}
			// 右边越界
			if (isControl_H && current_Right <= screen_W) {
				current_Right = screen_W;
				current_Left = this.getLeft() + 2 * disX;
				if (current_Left >= 0) {
					current_Left = 0;
					isControl_H = false;// 关闭
				}
			}

			if (isControl_H || isControl_V) {
				this.setFrame(current_Left, current_Top, current_Right,
						current_Bottom);
			} else {
				this.setFrame(current_Left, current_Top, current_Right,
						current_Bottom);
				isScaleAnim = true;// 开启缩放动画
			}

		}

	}

	
	/***
	 * 回缩动画執行
	 */
	class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
		private int screen_W, current_Width, current_Height;

		private int left, top, right, bottom;

		private float scale_WH;// 宽高的比例

		/** 当前的位置属性 **/
		public void setLTRB(int left, int top, int right, int bottom) {
			this.left = left;
			this.top = top;
			this.right = right;
			this.bottom = bottom;
		}

		private float STEP = 5f;// 步伐

		private float step_H, step_V;// 水平步伐，垂直步伐

		public MyAsyncTask(int screen_W, int current_Width, int current_Height) {
			super();
			this.screen_W = screen_W;
			this.current_Width = current_Width;
			this.current_Height = current_Height;
			scale_WH = (float) current_Height / current_Width;
			step_H = STEP;
			step_V = scale_WH * STEP;
		}

		@Override
		protected Void doInBackground(Void... params) {

			while (current_Width <= screen_W) {

				left -= step_H;
				top -= step_V;
				right += step_H;
				bottom += step_V;

				current_Width += 2 * step_H;

				left = Math.max(left, start_Left);
				top = Math.max(top, start_Top);
				right = Math.min(right, start_Right);
				bottom = Math.min(bottom, start_Bottom);

				onProgressUpdate(new Integer[] { left, top, right, bottom });
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onProgressUpdate(final Integer... values) {
			super.onProgressUpdate(values);
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					setFrame(values[0], values[1], values[2], values[3]);
				}
			});

		}

	}


	public void setScreen_H(int i) {
		getLayoutParams().height=i;
		
	}

	public void setScreen_W(int window_width) {
		getLayoutParams().width=window_width;
		
	}	
	
	
	

}
