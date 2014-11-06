package org.crazyit.res.luckdraw;
import java.util.Random;

import org.crazyit.res.R;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NOtCircle extends Activity implements OnClickListener {

	private ImageView[] imv_arrays = new ImageView[12];
	private ImageView start_lot;
	private TextView tv1;
	private int winNum = 1;// 当前数组索引位置，中奖位置，默认没中奖
	private int run_count = 4;// 跑的圈数run_count+1
	private String lotteryResult;
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_circel);
		setupView();
		lv.setAdapter(adapter);
	}

	private void setupView() {
		lv=(ListView) findViewById(R.id.lv);
		imv_arrays[0] = (ImageView) findViewById(R.id.imv1_out);
		imv_arrays[1] = (ImageView) findViewById(R.id.imv2_out);
		imv_arrays[2] = (ImageView) findViewById(R.id.imv3_out);
		imv_arrays[3] = (ImageView) findViewById(R.id.imv4_out);
		imv_arrays[4] = (ImageView) findViewById(R.id.imv5_out);
		imv_arrays[5] = (ImageView) findViewById(R.id.imv6_out);
		imv_arrays[6] = (ImageView) findViewById(R.id.imv7_out);
		imv_arrays[7] = (ImageView) findViewById(R.id.imv8_out);
		imv_arrays[8] = (ImageView) findViewById(R.id.imv9_out);
		imv_arrays[9] = (ImageView) findViewById(R.id.imv10_out);
		imv_arrays[10] = (ImageView) findViewById(R.id.imv11_out);
		imv_arrays[11] = (ImageView) findViewById(R.id.imv12_out);
		start_lot = (ImageView) findViewById(R.id.imv13);
		start_lot.setOnClickListener(this);
		tv1 = (TextView) findViewById(R.id.tv1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imv13:
			tv1.setText("正在抽奖");
			num=1;
			new NetAsyncTask().execute();
			break;
		}
	}
int num=0;
	public class NetAsyncTask extends AsyncTask<Void, Integer, Void> {

		@Override
		protected void onPreExecute() {
			winResult();
			System.out.println("抽到的号码为："+winNum);
			super.onPreExecute();
		}

		protected Void doInBackground(Void... params) {
			for(int x=winNum;x<imv_arrays.length;x++){
				publishProgress(x);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for (int cur_run = 0; cur_run < run_count; cur_run++) {
				for (int i = 0; i < imv_arrays.length; i++) {
					publishProgress(i);
					try {
						Thread.sleep((cur_run + 1) * 10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			for (int i = 0; i < winNum+1; i++) {
				publishProgress(i);
				try {
					Thread.sleep(winNum * 100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return null;
		}

		// 更新刻度操作
		@Override
		protected void onProgressUpdate(Integer... values) {
				imv_arrays[values[0]].setVisibility(View.VISIBLE);
				if(values[0]>0)
				imv_arrays[values[0]-1].setVisibility(View.GONE);
				else {
					imv_arrays[imv_arrays.length-1].setVisibility(View.GONE);
				}
			super.onProgressUpdate(values);
		}

		// 更新UI操作
		@Override
		protected void onPostExecute(Void result) {
			//发布抽奖结果
			tv1.setText(lotteryResult);
			super.onPostExecute(result);
		}

	}
    /**
     * 奖品位置设置：
     * 0  ===   大金净化器，市场价3888
     * 1  ===   再试试手气
     * 2  ===   闽宝牛角梳，市场价258
     * 3  ===   天猫积分 8
     * 4  ===   美肤宝防晒露，市场价70
     * 5  ===   天猫积分18
     * 6  ===   露华浓美容仪，市场价1198
     * 7  ===   58天猫积分
     * 8  ===   啊肤玫瑰精油，市场价388
     * 9  ===   天猫积分188
     * 10 ===   三达净水器，市场价1480
     * 11 ===   天猫积分1888
     * 其中1和3中奖几率为69%   其余剩余号码
     * 其中5中奖几率为25%     中奖号码为5008————7507
     * 其中7中奖几率为4%      中奖号码为4008————4407
     * 其中9中奖几率为1%      中奖号码为708————807
     * 其中11中奖几率为千分之3  中奖号码为8208————8237
     * 其中4中奖几率为千分之3   中奖号码为3208————3237
     * 其中2中奖几率为千分之2   中奖号码为1208————1227
     * 其中8中奖几率为千分之1   中奖号码为408————417
     * 其中6中奖几率为万分之6   中奖号码为188、388、588、1888、2888、3888
     * 其中10中奖几率为万分之3  中奖号码为8、88、888
     * 其中0中奖几率为万分之1  中奖号码为8888
     */
	private int getCurrPosition() {
		Random random = new Random();
		int ran_count = random.nextInt(10001);
		return ran_count;
	}
	
	private void winResult() {
		int ran_num = getCurrPosition();
		System.out.println("随机号码为："+ran_num);
		if (ran_num == 8888) {
			winNum = 0;
			lotteryResult = "恭喜你抽中了大金净化器，市场价3888";
		} else if (ran_num == 8 || ran_num == 88 || ran_num == 888) {
			winNum = 10;
			lotteryResult = "恭喜你抽中了三达净水器，市场价1480";
		} else if (ran_num == 188 || ran_num == 388 || ran_num == 588 || ran_num == 1888 || ran_num == 2888 || ran_num == 3888) {
			winNum = 6;
			lotteryResult = "恭喜你抽中了露华浓美容仪，市场价1198";
		} else if (ran_num >= 408 && ran_num < 418) {
			winNum = 8;
			lotteryResult = "恭喜你抽中了啊肤玫瑰精油，市场价388";
		} else if (ran_num >= 1208 && ran_num < 1228) {
			winNum = 2;
			lotteryResult = "恭喜你抽中了闽宝牛角梳，市场价258";
		} else if (ran_num >= 3208 && ran_num < 3238) {
			winNum = 4;
			lotteryResult = "恭喜你抽中美肤宝防晒露，市场价70";
		} else if (ran_num >= 8208 && ran_num < 8238) {
			winNum = 11;
			lotteryResult = "恭喜你抽中了天猫积分1888";
		} else if (ran_num >= 708 && ran_num < 808) {
			winNum = 9;
			lotteryResult = "恭喜你抽中了天猫积分188";
		} else if (ran_num >= 4008 && ran_num < 4408) {
			winNum = 7;
			lotteryResult = "恭喜你抽中了58天猫积分";
		} else if (ran_num >= 5008 && ran_num < 7508) {
			winNum = 5;
			lotteryResult = "恭喜你抽中了天猫积分18";
		} else {
			winNum = getRandom();
			if (winNum == 3) {
				//winNum = 3;
				lotteryResult = "恭喜你抽中了天猫积分 8";
			} else {
				//winNum = 1;
				lotteryResult = "很遗憾，你没有中奖，再试试手气";
			}
		}
	}
	
    /**
     * 获取数组中的任意一个随机元素
     */
    private int getRandom() {
        int ran_i = 0;
        int[] array_ran = {1, 3};
        //产生0-(strs.length-1)的整数值,也是数组的索引
        int index=(int) (Math.random()*array_ran.length);
        ran_i = array_ran[index];
        return ran_i;
    }

    
    
    
  private BaseAdapter adapter=new BaseAdapter() {
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tView=new TextView(NOtCircle.this);
		tView.setText("测试结果。。。。。。"+position);
		TranslateAnimation translateAnimation=new TranslateAnimation(Animation.RELATIVE_TO_SELF,-1f,Animation.RELATIVE_TO_SELF, 0f, 
				Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
		translateAnimation.setDuration(500);
		AlphaAnimation alphaAnimation=new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(500);
		tView.startAnimation(alphaAnimation);
		return tView;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getCount() {
		
		return 100;
	}
};  
    
    
}
