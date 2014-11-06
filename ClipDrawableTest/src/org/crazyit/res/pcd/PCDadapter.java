package org.crazyit.res.pcd;

import java.util.ArrayList;

import com.zeno.lib.mcudata.NewMeetingT;

import android.R.anim;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PCDadapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<IDobject> pcdList;
	
	public ArrayList<IDobject> getPcdList() {
		return pcdList;
	}
	public void setPcdList(ArrayList<IDobject> pcdList) {
		this.pcdList = pcdList;
	}

	public PCDadapter(Context mContext){
		mInflater=LayoutInflater.from(mContext);
	}
	@Override
	public int getCount() {
		
		return pcdList==null?0:pcdList.size();
	}

	@Override
	public IDobject getItem(int position) {
		
		return pcdList==null?null:pcdList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView=mInflater.inflate(android.R.layout.simple_list_item_1, null);
		}
		TextView tView=(TextView) convertView;
		tView.setText(getItem(position).getName());
		return tView;
	}

}
