package org.crazyit.res.fragment;

import org.crazyit.res.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class Fragment1 extends Fragment {

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		return inflater.inflate(R.layout.fragment1xml, container,false);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getView().findViewById(R.id.btnnext).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content, new Fragment2(),"fragment2")
				.addToBackStack(null)
				.commit();
			}
		});
	}
}
