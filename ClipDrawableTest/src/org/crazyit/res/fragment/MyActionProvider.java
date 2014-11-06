package org.crazyit.res.fragment;

import org.crazyit.res.R;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

public class MyActionProvider extends ActionProvider {

	private  Context mContext;
	public MyActionProvider(Context context) {
		super(context);
		mContext=context;
	}

	@Override
	public View onCreateActionView() {
		
		return null;
	}

	@Override
	public boolean hasSubMenu() {
		return true;
	}
	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		subMenu.add("第一个").setIcon(R.drawable.choujiang10).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showToast("第一个");
				return true;
			}
		});
		subMenu.add("第2个").setIcon(R.drawable.choujiang11).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showToast("第2个");
				return true;
			}
		});
		subMenu.add("第2个").setIcon(R.drawable.choujiang12).setOnMenuItemClickListener(new OnMenuItemClickListener() {
			
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				showToast("第2个");
				return true;
			}
		});
		
		
	}
	
	public void showToast(String str){
		Toast.makeText(mContext, str, Toast.LENGTH_SHORT).show();
	}
}
