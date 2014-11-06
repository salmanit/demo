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
	private int winNum = 1;// ��ǰ��������λ�ã��н�λ�ã�Ĭ��û�н�
	private int run_count = 4;// �ܵ�Ȧ��run_count+1
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
			tv1.setText("���ڳ齱");
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
			System.out.println("�鵽�ĺ���Ϊ��"+winNum);
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

		// ���¿̶Ȳ���
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

		// ����UI����
		@Override
		protected void onPostExecute(Void result) {
			//�����齱���
			tv1.setText(lotteryResult);
			super.onPostExecute(result);
		}

	}
    /**
     * ��Ʒλ�����ã�
     * 0  ===   ��𾻻������г���3888
     * 1  ===   ����������
     * 2  ===   ����ţ���ᣬ�г���258
     * 3  ===   ��è���� 8
     * 4  ===   ��������ɹ¶���г���70
     * 5  ===   ��è����18
     * 6  ===   ¶��Ũ�����ǣ��г���1198
     * 7  ===   58��è����
     * 8  ===   ����õ�徫�ͣ��г���388
     * 9  ===   ��è����188
     * 10 ===   ���ﾻˮ�����г���1480
     * 11 ===   ��è����1888
     * ����1��3�н�����Ϊ69%   ����ʣ�����
     * ����5�н�����Ϊ25%     �н�����Ϊ5008��������7507
     * ����7�н�����Ϊ4%      �н�����Ϊ4008��������4407
     * ����9�н�����Ϊ1%      �н�����Ϊ708��������807
     * ����11�н�����Ϊǧ��֮3  �н�����Ϊ8208��������8237
     * ����4�н�����Ϊǧ��֮3   �н�����Ϊ3208��������3237
     * ����2�н�����Ϊǧ��֮2   �н�����Ϊ1208��������1227
     * ����8�н�����Ϊǧ��֮1   �н�����Ϊ408��������417
     * ����6�н�����Ϊ���֮6   �н�����Ϊ188��388��588��1888��2888��3888
     * ����10�н�����Ϊ���֮3  �н�����Ϊ8��88��888
     * ����0�н�����Ϊ���֮1  �н�����Ϊ8888
     */
	private int getCurrPosition() {
		Random random = new Random();
		int ran_count = random.nextInt(10001);
		return ran_count;
	}
	
	private void winResult() {
		int ran_num = getCurrPosition();
		System.out.println("�������Ϊ��"+ran_num);
		if (ran_num == 8888) {
			winNum = 0;
			lotteryResult = "��ϲ������˴�𾻻������г���3888";
		} else if (ran_num == 8 || ran_num == 88 || ran_num == 888) {
			winNum = 10;
			lotteryResult = "��ϲ����������ﾻˮ�����г���1480";
		} else if (ran_num == 188 || ran_num == 388 || ran_num == 588 || ran_num == 1888 || ran_num == 2888 || ran_num == 3888) {
			winNum = 6;
			lotteryResult = "��ϲ�������¶��Ũ�����ǣ��г���1198";
		} else if (ran_num >= 408 && ran_num < 418) {
			winNum = 8;
			lotteryResult = "��ϲ������˰���õ�徫�ͣ��г���388";
		} else if (ran_num >= 1208 && ran_num < 1228) {
			winNum = 2;
			lotteryResult = "��ϲ�����������ţ���ᣬ�г���258";
		} else if (ran_num >= 3208 && ran_num < 3238) {
			winNum = 4;
			lotteryResult = "��ϲ�������������ɹ¶���г���70";
		} else if (ran_num >= 8208 && ran_num < 8238) {
			winNum = 11;
			lotteryResult = "��ϲ���������è����1888";
		} else if (ran_num >= 708 && ran_num < 808) {
			winNum = 9;
			lotteryResult = "��ϲ���������è����188";
		} else if (ran_num >= 4008 && ran_num < 4408) {
			winNum = 7;
			lotteryResult = "��ϲ�������58��è����";
		} else if (ran_num >= 5008 && ran_num < 7508) {
			winNum = 5;
			lotteryResult = "��ϲ���������è����18";
		} else {
			winNum = getRandom();
			if (winNum == 3) {
				//winNum = 3;
				lotteryResult = "��ϲ���������è���� 8";
			} else {
				//winNum = 1;
				lotteryResult = "���ź�����û���н�������������";
			}
		}
	}
	
    /**
     * ��ȡ�����е�����һ�����Ԫ��
     */
    private int getRandom() {
        int ran_i = 0;
        int[] array_ran = {1, 3};
        //����0-(strs.length-1)������ֵ,Ҳ�����������
        int index=(int) (Math.random()*array_ran.length);
        ran_i = array_ran[index];
        return ran_i;
    }

    
    
    
  private BaseAdapter adapter=new BaseAdapter() {
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView tView=new TextView(NOtCircle.this);
		tView.setText("���Խ��������������"+position);
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
