package org.crazyit.res.thumpic;

import java.io.InputStream;

import org.crazyit.res.R;

import com.jess.ui.TwoWayGridView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PicActivity extends Activity {

	private TwoWayGridView twogv;
	private float density;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mypicxml);
		twogv=(TwoWayGridView) findViewById(R.id.twogv);
		density=getResources().getDisplayMetrics().density;
		twogv.setAdapter(new Myadapter());
	}
	
	 class Myadapter extends BaseAdapter{

		// private String pic_path="/storage/emulated/0/Microclasses/白板传输XML文件格式说明 V1.1/2/_background.png";
		 private String pic_path="/storage/emulated/0/PDFimages/白板传输XML文件格式说明 V1.1/2.png";
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 5;
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
			if(convertView==null){
				convertView=LayoutInflater.from(PicActivity.this).inflate(R.layout.picitem, parent,false);
			}
			ImageView iv=(ImageView) convertView.findViewById(R.id.ivshow);
			TextView tView=(TextView) convertView.findViewById(R.id.tvnumber);
			tView.setText(""+position);
			switch (position%4) {
			case 0:
				iv.setImageBitmap(getSmallBitmap(pic_path, 300, 400));
				break;
			case 1:
				Bitmap bitmap=ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(pic_path), 300, 400);
				iv.setImageBitmap(bitmap);
				break;
			case 2:
				iv.setImageBitmap(getSmallBitmap(pic_path, ((int)(300*density)), ((int)(400*density))));
				break;
			case 3:
			
				//通过openRawResource获取一个inputStream对象
				InputStream inputStream = getResources().openRawResource(R.drawable.m4);
				//通过一个InputStream创建一个BitmapDrawable对象
				BitmapDrawable drawable = new BitmapDrawable(inputStream);
				//通过BitmapDrawable对象获得Bitmap对象
				Bitmap bitmap3 = drawable.getBitmap();
				//利用Bitmap对象创建缩略图
				bitmap3 = ThumbnailUtils.extractThumbnail(bitmap3, 300, 400);
				//imageView 显示缩略图的ImageView
				iv.setImageBitmap(bitmap3);
			
				break;
			default:
				break;
			}
			return convertView;
		}
		
	}
	 
		public  Bitmap getSmallBitmap(String filePath,int width,int height) {
			final BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;//下边的方法返回的bitmap为空，只是返回原图的属性而已。optiion不为空。
			BitmapFactory.decodeFile(filePath, options);
			options.inSampleSize = calculateInSampleSize(options, width, height);//insamplesize为4，则缩放为原图的1/4.小于1的，按照1处理。
			options.inJustDecodeBounds = false;//改为false，下边方法才能返回bitmap。
			return BitmapFactory.decodeFile(filePath, options);
		} 
		/**计算缩放因子**/
		public  int calculateInSampleSize(BitmapFactory.Options options,
				int reqWidth, int reqHeight) {
			final int height = options.outHeight;
			final int width = options.outWidth;
			int inSampleSize = 1;
			if (height > reqHeight || width > reqWidth) {
				final int heightRatio = Math.round((float) height
						/ (float) reqHeight);
				final int widthRatio = Math.round((float) width / (float) reqWidth);
				inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
			}
			return inSampleSize;
		}
}
