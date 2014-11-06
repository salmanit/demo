package org.crazyit.res;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.crazyit.res.DragGridView.OnChanageListener;
import org.crazyit.res.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

/**
 * Description: <br/>
 * site: <a href="http://www.crazyit.org">crazyit.org</a> <br/>
 * Copyright (C), 2001-2012, Yeeku.H.Lee <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name: <br/>
 * Date:
 * 
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class ClipDrawableTest extends Activity {

	private List<HashMap<String, Object>> dataSourceList = new ArrayList<HashMap<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gridviewINIT();
		initClipdrawble();

		findViewById(R.id.ivresult);

	}

	public void btnclick(View view) throws Exception {
		switch (view.getId()) {
		case R.id.btn1: {
			View v = getWindow().getDecorView();
			v.setDrawingCacheEnabled(true);
			v.buildDrawingCache();
			Bitmap bitmap = v.getDrawingCache();

			if (Environment.getExternalStorageState().equals(
					Environment.MEDIA_MOUNTED)) {
				String sdcardpath = Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/cliptest";
				File file = new File(sdcardpath);
				if (!file.exists()) {
					file.mkdir();
				}
				File picFile = new File(sdcardpath + "/temp.png");
				bitmap.compress(CompressFormat.PNG, 80, new FileOutputStream(
						picFile));
//				{
//					Intent intent = new Intent(Intent.ACTION_SEND);
//					intent.setType("image/*");
//					intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(picFile));
//					intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
//					intent.putExtra(Intent.EXTRA_TITLE, "title");
//
//					intent.putExtra(Intent.EXTRA_TEXT,
//							"I have successfully share my message through my app ");
//
//					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					startActivity(Intent.createChooser(intent, "���������"));
//				}
				 cropImage(this, Uri.fromFile(picFile), 300, 300, 10);
			} else {
				showToast("sdcard is not exist");
			}
		}
			break;
		case R.id.btn2:
//			Intent intent = new Intent(Intent.ACTION_SEND);
//			intent.setType("image/*");
//			intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
//			intent.putExtra(Intent.EXTRA_TEXT,
//					"I have successfully share my message through my app http://www.baidu.com");
//
//			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			startActivity(Intent.createChooser(intent, getTitle()));
		
			
			break;
		default:
			break;
		}
	}

	private void showToast(String string) {
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 10:
			if (resultCode == Activity.RESULT_OK) {
				ImageView ivresult = (ImageView) findViewById(R.id.ivresult);
				ivresult.setImageBitmap((Bitmap) data
						.getParcelableExtra("data"));
			}
			break;

		default:
			break;
		}
	}

	/**
	 * @Description �ü�ͼƬ
	 * @return
	 */
	public void cropImage(Activity activity, Uri uri, int outputX, int outputY,
			int requestCode) {
		// �ü�ͼƬ��ͼ
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// �ü���ı�����1��1
		// intent.putExtra("aspectX", 1);
		// intent.putExtra("aspectY", 1);
		intent.putExtra("scale", false);
		// �ü������ͼƬ�ĳߴ��С
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		// ͼƬ��ʽ
		intent.putExtra("outputFormat", "PNG");
		intent.putExtra("noFaceDetection", true);// �沿ʶ��
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, requestCode);
		
	}

	/**
	 * gridview ��ʼ������
	 */
	private void gridviewINIT() {
		DragGridView mDragGridView = (DragGridView) findViewById(R.id.dragGridView);
		for (int i = 0; i < 30; i++) {
			HashMap<String, Object> itemHashMap = new HashMap<String, Object>();
			itemHashMap.put("item_image",
					R.drawable.com_tencent_open_notice_msg_icon_big);
			itemHashMap.put("item_text", "��ק " + Integer.toString(i));
			dataSourceList.add(itemHashMap);
		}

		final DragAdapter mDragAdapter = new DragAdapter(this, dataSourceList);

		mDragGridView.setAdapter(mDragAdapter);

		mDragGridView.setOnChangeListener(new OnChanageListener() {

			@Override
			public void onChange(int from, int to) {
				HashMap<String, Object> temp = dataSourceList.get(from);

				// ����Ĵ�����Ҫע����
				if (from < to) {
					for (int i = from; i < to; i++) {
						Collections.swap(dataSourceList, i, i + 1);
					}
				} else if (from > to) {
					for (int i = from; i > to; i--) {
						Collections.swap(dataSourceList, i, i - 1);
					}
				}

				dataSourceList.set(to, temp);

				// �����µ���item���أ����õ���notifyDataSetChanged��ˢ�½��棬��ΪsetItemHide�������������
				mDragAdapter.setItemHide(to);

			}
		});
		mDragGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showToast("position="
						+ arg2
						+ " "
						+ (CharSequence) (((DragAdapter) arg0.getAdapter())
								.getItem(arg2).get("item_text")));

			}
		});
		mDragGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				return false;
			}
		});
		mDragGridView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
					scrollView.requestDisallowInterceptTouchEvent(true);
				} else {
					ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView1);
					scrollView.requestDisallowInterceptTouchEvent(false);
				}
				return false;
			}
		});

	}

	/**
	 * clipdrawable�ļ�ʵ��
	 * */
	private void initClipdrawble() {
		ImageView ivclip2 = (ImageView) findViewById(R.id.ivcenter);
		// ��ȡͼƬ����ʾ��ClipDrawable����
		final ClipDrawable clip2 = (ClipDrawable) ivclip2.getDrawable();
		ImageView ivclip1 = (ImageView) findViewById(R.id.ivresult);
		// ��ȡͼƬ����ʾ��ClipDrawable����
		final ClipDrawable clipDrawable = (ClipDrawable) ivclip1.getDrawable();

		ImageView imageview = (ImageView) findViewById(R.id.image);
		// ��ȡͼƬ����ʾ��ClipDrawable����
		final ClipDrawable drawable = (ClipDrawable) imageview.getDrawable();
		final Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// �������Ϣ�Ǳ����������͵�
				if (msg.what == 0x1233) {
					// �޸�ClipDrawable��levelֵ
					drawable.setLevel(drawable.getLevel() + 200);
					clipDrawable.setLevel(clipDrawable.getLevel() + 200);
					clip2.setLevel(clip2.getLevel() + 200);
				}
			}
		};
		final Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = 0x1233;
				// ������Ϣ��֪ͨӦ���޸�ClipDrawable�����levelֵ��
				handler.sendMessage(msg);
				// ȡ����ʱ��
				if (drawable.getLevel() >= 10000) {
					timer.cancel();
				}
			}
		}, 0, 300);
	}
}