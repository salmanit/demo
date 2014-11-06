package org.crazyit.res.zititexiao;

import org.crazyit.res.R;
import org.crazyit.res.fragment.Fragment1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class TextviewActivity extends ActionBarActivity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.textviewxml);
		getSupportFragmentManager().beginTransaction().replace(R.id.content, new Fragment1(), "fragment1").commit();
	}
	
	
	
	@Override
	public void onBackPressed() {
		if(getSupportFragmentManager().getBackStackEntryCount()!=0){
			getSupportFragmentManager().popBackStack();
		}else{
			super.onBackPressed();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.testmenu, menu);
		myMenu=menu;
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private Menu myMenu;
	public void show_hidden_menu(boolean tag){
		if(myMenu==null)
			return;
		for(int i=0;i<myMenu.size();i++){
			myMenu.getItem(i).setVisible(tag);
		}
		System.out.println("menu count=="+myMenu.size());
	}
	
	public void home_state(boolean enabled){
		//getSupportActionBar().setDisplayHomeAsUpEnabled(enabled);
		getSupportActionBar().setDisplayOptions(enabled?ActionBar.DISPLAY_HOME_AS_UP:0,ActionBar.DISPLAY_HOME_AS_UP);
		//getSupportActionBar().setDisplayOptions(enabled?ActionBar.DISPLAY_SHOW_HOME:0,ActionBar.DISPLAY_SHOW_HOME);
		getSupportActionBar().setHomeButtonEnabled(enabled);
		findViewById(android.R.id.home).setEnabled(enabled);
	}
}
