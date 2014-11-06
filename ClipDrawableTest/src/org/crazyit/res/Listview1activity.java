package org.crazyit.res;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.text.ChoiceFormat;
import java.util.ArrayList;

import org.crazyit.res.aaa.CityAdapter;
import org.crazyit.res.temp.SMSreceiver;

import android.R.anim;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.app.ListActivity;
import android.content.IntentFilter;
import android.database.MatrixCursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AlphabetIndexer;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.widget.Toast;

import android.widget.TextView;

public class Listview1activity extends Activity implements OnScrollListener{

	ArrayList<String>  list=new ArrayList<String>();
	ArrayList<String>  list_temp=new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	private EditText eText;
	private TextView tView;
	private ListView lv;
	ImageView iv;
	private Animation up,down;
	private ExpandableListView explv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewdemo);
		explv=(ExpandableListView) findViewById(R.id.explv);
		up=AnimationUtils.loadAnimation(this, R.anim.upanim);
		down=AnimationUtils.loadAnimation(this, R.anim.down_anim);
		eText=(EditText) findViewById(R.id.et);
		tView=(TextView) findViewById(R.id.tv);
		lv=(ListView) findViewById(R.id.lv);
		list.add("add");
		for(int i=0;i<122;i++){
			list.add("new"+i);
		}
		//list_temp.addAll(list);
		adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list_temp);
		lv.setAdapter(adapter);
		lv.setOnScrollListener(this);
		lv.setItemsCanFocus(false);
		lv.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showToast(list_temp.get(position));
			}
		});
		//getActionBar().setDisplayShowHomeEnabled(false);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		SeekBar sBar=(SeekBar) findViewById(R.id.sb);
		sBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				lv.setEmptyView(findViewById(R.id.tvempty));
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				list_temp.clear();
				for(int i=0;i<progress;i++){
					list_temp.add("temp"+i);
				}
				adapter.notifyDataSetChanged();
			}
		});
		
		
//		SMSreceiver receiver=new SMSreceiver();
//		IntentFilter filter=new IntentFilter();
//		filter.addAction("android.provider.Telephony.SMS_RECEIVED");
//		filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
//		registerReceiver(receiver, filter);
		
		iv=(ImageView) findViewById(R.id.ivtop);
			iv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(lv.getAdapter().getCount()>10)
				lv.setSelection(9);
				showToast("click");
			}
		});
			lv.setOnTouchListener(new OnTouchListener() {
				
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					float temp_y=event.getY();
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						old_y=temp_y;
						break;
					case MotionEvent.ACTION_MOVE:
						if(temp_y-old_y>5){
							old_y=temp_y;
							if(iv.getVisibility()==View.VISIBLE)
								return false;
							iv.startAnimation(up);
							iv.setVisibility(View.VISIBLE);
							
						}else if(temp_y-old_y<-5){
							old_y=temp_y;
							if(iv.getVisibility()==View.INVISIBLE)
								return false;
							iv.startAnimation(down);
							iv.setVisibility(View.INVISIBLE);
							
						}
						break;
					default:
						break;
					}
					return false;
				}
			});
		
//			getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
//			getActionBar().setListNavigationCallbacks(adapter, new OnNavigationListener() {
//				
//				@Override
//				public boolean onNavigationItemSelected(int itemPosition, long itemId) {
//					switch (itemPosition) {
//					case 0:
//						showToast(""+itemPosition);
//						break;
//
//					default:
//						break;
//					}
//					return false;
//				}
//			});
			View view=findViewById(R.id.emptylayout);
			
			
			lv.setEmptyView(view);
			changeFastScrollThumb(lv);
			
			test();
			String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/microclass_tmp/Microclasses/微课车间APP_使用说明/"+1+mp3;
			System.out.println("path="+path);
			playvoice(path);
	}
	
	private String mp3="/_sound.mp3";
	private int index=2;
	private MediaPlayer mPlayer;
	private void playvoice(String path){
		try {
			mPlayer=new MediaPlayer();
			
			mPlayer.setDataSource(new FileInputStream(new File(path)).getFD());
			mPlayer.prepare();
			mPlayer.start();
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				mPlayer.release();
				mPlayer=null;
				if(index>9)
					return;
				for(int i=index;i<=9;i++){
				
				String path=Environment.getExternalStorageDirectory().getAbsolutePath()+"/microclass_tmp/Microclasses/微课车间APP_使用说明/"+index+mp3;
				if(new File(path).exists()){
					playvoice(path);
					index++;
					break;
				}
				
				
				}
			}
		});
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	
	
	
	
	
	
	
	
	
	  public void changeFastScrollThumb(ListView listView){
		  try
		  {
		   Field f = AbsListView.class.getDeclaredField("mFastScroller");
		   f.setAccessible(true);
		   Object o = f.get(listView);
		   
		   f = f.getType().getDeclaredField("mThumbDrawable");
		   f.setAccessible(true);
		   Drawable drawable = (Drawable) f.get(o);
		   drawable = getResources().getDrawable(R.drawable.scrollbarcus1);
		   f.set(o, drawable);
		  }
		  catch (Exception e)
		  {
		   throw new RuntimeException(e);
		  }
	  }

	
	
	
	
	private float old_y;
	private PopupMenu  popupMenu;
	private void init(){
		popupMenu=new PopupMenu(this, findViewById(R.id.tijiao));
		popupMenu.inflate(R.menu.submenu);
		try {
			Field mpopup=popupMenu.getClass().getDeclaredField("mPopup");
			mpopup.setAccessible(true);
			MenuPopupHelper mPopup = (MenuPopupHelper) mpopup.get(popupMenu);
			mPopup.setForceShowIcon(true);
		} catch (Exception e) {
			System.out.println("reflect error");
		}

		popupMenu.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				switch (item.getItemId()) {
				case R.id.action_save_menu:
					showToast("save");
					break;
				case R.id.action_cancel_menu:
					showToast("cancel");
					break;
				default:
					break;
				}
				return false;
			}
		});
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		String result="\tfirst"+view.getFirstVisiblePosition()+"\t last"+view.getLastVisiblePosition()+"\tcount="+view.getCount()+
				"\t childcount="+view.getChildCount();
		switch (scrollState) {
		case SCROLL_STATE_TOUCH_SCROLL://手指还在屏幕上
			tView.setText("SCROLL_STATE_TOUCH_SCROLL"+result);
			break;
		case SCROLL_STATE_FLING://手指离开后，还会自动滑动一段距离的时候
			tView.setText("SCROLL_STATE_FLING"+result);
			break;
		case SCROLL_STATE_IDLE://滑动结束的状态。
			tView.setText("SCROLL_STATE_IDLE"+result);
			break;
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		eText.setText("first="+firstVisibleItem+"\tvisiblecount="+visibleItemCount+"\ttotalcount="+totalItemCount);
	}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
	getMenuInflater().inflate(R.menu.main, menu);
	MenuItem item_search=menu.findItem(R.id.item_search);
	SearchView searchView=(SearchView) item_search.getActionView();
	searchView.setQueryHint("请输入查找内容");
	searchView.setOnQueryTextListener(new OnQueryTextListener() {
		
		@Override
		public boolean onQueryTextSubmit(String query) {
			
			return false;
		}
		
		@Override
		public boolean onQueryTextChange(String newText) {
			search_content(newText);
			return true;
		}
	});
	
	return true;
}


public void search_content(String newText){
	list_temp.clear();
	for(int i=0;i<list.size();i++){
		if(list.get(i).contains(newText)){
			list_temp.add(list.get(i));
		}
	}
adapter.notifyDataSetChanged();
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	case android.R.id.home:
		showToast("home");
		break;
	case R.id.submit_menu:
		if(popupMenu==null)
			init();
		popupMenu.show();
		break;
	case R.id.tijiao:
		showToast("tijiao");
		break;
	default:
		break;
	}
	return super.onOptionsItemSelected(item);
}
	
private void showToast(String str){
	Toast.makeText(this, str, 100).show();
}
public void test(){
	explv.setEmptyView(findViewById(R.id.tvempty111));
	String[] items={"_id","item"};
	MatrixCursor matrixCursor=new MatrixCursor(items);
	matrixCursor.addRow(new Object[]{1,"中国"});
	matrixCursor.addRow(new Object[]{2,"外国"});
	CityAdapter adapter=new CityAdapter(matrixCursor, this, true);
	
	explv.setAdapter(adapter);
}


}
