package org.crazyit.res.fragment;

import org.crazyit.res.R;
import org.crazyit.res.zititexiao.TextviewActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment2 extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment2xml,container,false);
	}
	
	@Override
	public void onResume() {
		
		super.onResume();
		((TextviewActivity)getActivity()).show_hidden_menu(false);
		((TextviewActivity)getActivity()).home_state(true);
	}
	
	
	@Override
	public void onDestroy() {
		
		((TextviewActivity)getActivity()).show_hidden_menu(true);
		((TextviewActivity)getActivity()).home_state(false);
		super.onDestroy();
	}
}
