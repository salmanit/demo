package org.crazyit.res.twogridview;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.crazyit.res.R;
import org.crazyit.res.playgif.Numberprogress;
import org.crazyit.res.widget.MyOnePicImageView;

import com.jess.ui.TwoWayGridView;

import android.R.anim;
import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class TwoAcitivty extends Activity {

	private TwoWayGridView twogView;
	private ListAdapter adapter;
	private ImageView iv;
	private TextView tView;
	private Button btnGetCode;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypicxml);

		initUI();
		twogView.setAdapter(adapter);
		// try {
		// iv.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("f.zip/ddy.gif")));
		// } catch (IOException e) {
		// System.out.println("error");
		// e.printStackTrace();
		// }
		// try {
		// // InputStream is=getAssets().open("test/testfile.txt");
		// // byte[] buffer=new byte[is.available()];
		// // is.read(buffer);
		// // tView.setText(buffer.toString());
		// iv.setImageBitmap(BitmapFactory.decodeStream(getAssets().open("test/icon520.png")));
		// } catch (IOException e) {
		// System.out.println("error");
		// e.printStackTrace();
		// }

		// houtai();

		btnGetCode = (Button) findViewById(R.id.btngetcode);
		btnGetCode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				time = 30;
				btnGetCode.setEnabled(false);
				runnable.run();
			}
		});
		((Numberprogress)findViewById(R.id.numberpg)).setProgress(30);
	}

	private int time = 30;
	public Runnable runnable = new Runnable() {

		@Override
		public void run() {

			if ((time) <= 0) {
				btnGetCode.setText("获取验证码");
				btnGetCode.setEnabled(true);
			} else {
				btnGetCode.setText(time + "秒后重试");
				btnGetCode.postDelayed(this, 1000);
			}
			time--;
		}
	};

	ArrayList<String> names = new ArrayList<String>();
	private MyOnePicImageView myiv;

	private void houtai() {
		new AsyncTask<String, Integer, Boolean>() {

			@Override
			protected Boolean doInBackground(String... params) {
				AssetManager manager = getAssets();
				try {
					ZipInputStream zis = new ZipInputStream(
							manager.open("f.zip"));
					int start = 0;
					byte[] buffer;
					while (true) {
						ZipEntry entityEntry = zis.getNextEntry();
						if (entityEntry == null) {
							break;
						}
						int count = (int) entityEntry.getSize();
						buffer = new byte[count];

						// iv.setImageBitmap(BitmapFactory.decodeByteArray(buffer,
						// 0, buffer.length));
						names.add(entityEntry.getName());
						System.out.println("getName==" + entityEntry.getName());
						System.out.println("getComment=="
								+ entityEntry.getComment());
						System.out.println("getCompressedSize="
								+ entityEntry.getCompressedSize());
						System.out.println("getSize=" + entityEntry.getSize());
						System.out.println("compression method ="
								+ entityEntry.getMethod());
						System.err
								.println("getExtra=" + entityEntry.getExtra());
						System.out.println("=============");

						start += count;

					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

		}.execute("");
	}

	private void initUI() {
		 myiv=(MyOnePicImageView) findViewById(R.id.myiv);
		tView = (TextView) findViewById(R.id.tv);
		tView.setTextColor(Color.parseColor("#68BD44"));
		iv = (ImageView) findViewById(R.id.ivresult);
		twogView = (TwoWayGridView) findViewById(R.id.twogv);
		adapter = new MyAdapter();
		findViewById(R.id.btnselect).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int temp = new Random().nextInt(101);
				((Button) v).setText("" + temp);
				((BaseAdapter)adapter).notifyDataSetChanged();
				twogView.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						//twogView.setSelection(temp);
						myiv.setPercent(temp);
					}
				}, 5);
				
			}
		});
	}

	int[] pics = { R.drawable.choujiang1, R.drawable.choujiang10,
			R.drawable.choujiang11, R.drawable.choujiang12,
			R.drawable.choujiang5, R.drawable.choujiang11,
			R.drawable.choujiang12, R.drawable.choujiang6,
			R.drawable.choujiang10, };

	class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return pics.length;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(TwoAcitivty.this).inflate(
						R.layout.twogvitem, null);
			}
			ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
			TextView tvnumber = (TextView) convertView
					.findViewById(R.id.tvnumber);
			tvnumber.setText("" + position);
			iv.setImageResource(pics[position]);

			return convertView;
		}

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.testmenu1, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
}
