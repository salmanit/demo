package org.crazyit.res;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import android.widget.Toast;

public class MytestProvider extends ActionProvider implements OnMenuItemClickListener{

	private Context mContext;
	public MytestProvider(Context context) {
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
		MenuItem item_1=subMenu.add(0, R.id.submenu_1, 0, "submenu1").setIcon(R.drawable.choujiang10).setOnMenuItemClickListener(this);
		MenuItem item_2=subMenu.add(0, R.id.submenu_2, 0,"submenu2").setIcon(R.drawable.choujiang11).setOnMenuItemClickListener(this);
		
	}

	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.submenu_1:
			showToast("submenu1");
			break;
		case R.id.submenu_2:
			showToast("submenu2");
			break;
		default:
			break;
		}
		return false;
	}
	
	private void showToast(String str){
		Toast.makeText(mContext, str, 100).show();
	}
}
