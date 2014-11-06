package org.crazyit.res.drawrecord;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.crazyit.res.R;
import org.crazyit.res.widget.Custprogress;
import org.crazyit.res.widget.ProgressWheel;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import android.widget.Toast;

public class DrawRecordActivity extends ActionBarActivity {

	private ViewPager vp;
	private MyPagerAdapter adapter;
	private ProgressWheel pWheel;
	private Custprogress cusp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weikedraw);
		 setOverflowShowingAlways(); 
		// list.add(R.drawable.choujiang1);
		vp=(ViewPager) findViewById(R.id.myvp);
		adapter=new MyPagerAdapter();
		adapter.setRes(list);
		vp.setAdapter(adapter);
		 findViewById(R.id.btnadd).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				list.add(R.drawable.choujiang12);
				adapter.notifyDataSetChanged();
			}
		});
		 
		  pWheel=(ProgressWheel) findViewById(R.id.progress);
		   cusp=(Custprogress) findViewById(R.id.myprog);
		  findViewById(R.id.btnstart).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(tag){
							if(angle>360)
								break;
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							angle++;
							cusp.setAngle(angle);
							pWheel.setAngle(angle);
						}
						
					}
				}).start();
			}
		});
		  
	}
	
	private int angle=0;
	private boolean tag=true;
	protected void onDestroy() {
		tag=false;
		super.onDestroy();
	};
	
	ArrayList<Integer> list=new ArrayList<Integer>();
	
	class MyPagerAdapter extends PagerAdapter{

		ArrayList<Integer> res=new ArrayList<Integer>();
		
		public ArrayList<Integer> getRes() {
			return res;
		}

		public void setRes(ArrayList<Integer> res) {
			this.res = res;
		}

		@Override
		public int getCount() {
			return res.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			ImageView iv=new ImageView(container.getContext());
			iv.setImageResource(res.get(position));
			container.addView(iv);
			return iv;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.testmenu, menu);
	MenuItem search_Item=	menu.findItem(R.id.item_search);
	
	SearchView searchView = (SearchView)search_Item.getActionView();
	searchView.setQueryHint("请输入查询类容");
	
	searchView.setOnQueryTextListener(new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String arg0) {
			showToast(arg0);
			
			return true;
		}
		
		@Override
		public boolean onQueryTextChange(String arg0) {
			return false;
		}
	});
	
		return super.onCreateOptionsMenu(menu);
	}
	
	/**overflow展开的菜单图标可见**/
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {  
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {  
                try {  
                    Method m = menu.getClass().getDeclaredMethod(  
                            "setOptionalIconsVisible", Boolean.TYPE);  
                    m.setAccessible(true);  
                    m.invoke(menu, true);  
                } catch (Exception e) {  
                }  
            }  
        }  
		return super.onMenuOpened(featureId, menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			
			break;
		case R.id.item_search:
			
			break;
		case R.id.item_add:
			
			break;
//		case R.id.item_submenu:
//			
//			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	/**屏蔽掉物理Menu键*/
	private void setOverflowShowingAlways() {  
        try {  
            ViewConfiguration config = ViewConfiguration.get(this);  
            Field menuKeyField = ViewConfiguration.class  
                    .getDeclaredField("sHasPermanentMenuKey");  
            menuKeyField.setAccessible(true);  
            menuKeyField.setBoolean(config, false);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
	
	
	public void showToast(String str){
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}
	
	
//	public void initdata(){
//		try
//		{
//			InputStream is = getAssets().open(
//					"utility_Province_City_District_List.xml");
//			ArrayList<CountryProvince> array = new CountryProvince().ToArray(inputStream2String(is));
//			if (array != null)
//				mPDCLinearlayout.setCountryProvinceList(array, null);
//		}
//		catch (IOException e)
//		{
//			Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
//		}
//	}
//	public  String inputStream2String(InputStream is) throws IOException
//	{
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		int i = -1;
//		while ((i = is.read()) != -1)
//		{
//			baos.write(i);
//		}
//		return baos.toString();
//	}
	
}
