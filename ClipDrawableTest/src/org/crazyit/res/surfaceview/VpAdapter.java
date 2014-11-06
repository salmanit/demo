package org.crazyit.res.surfaceview;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class VpAdapter extends FragmentPagerAdapter {

	
	public VpAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			
		return new Fragment1();
		case 1:
			
			return new Fragment2();

		default:
			return new Fragment1();
		}
		
	}

	@Override
	public int getCount() {
		return 3;
	}

}
