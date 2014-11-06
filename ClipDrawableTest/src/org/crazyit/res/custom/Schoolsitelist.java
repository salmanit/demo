package org.crazyit.res.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crazyit.res.R;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class Schoolsitelist extends ListActivity {
private List<Map<String, Object>> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		initData();
		setListAdapter(new SchoolAdapter());
	}
	
	 private void initData() {
	list=new ArrayList<Map<String,Object>>();
	Map<String, Object>  map=new HashMap<String, Object>();
	map.put("name", "angle11111111");
	map.put("iv1show", true);
	map.put("iv2show", false);
	map.put("iv3show", false);
	map.put("iv4show", false);
	list.add(map);
	
	map=new HashMap<String, Object>();
	map.put("name", "angle222");
	map.put("iv1show", true);
	map.put("iv2show", true);
	map.put("iv3show", false);
	map.put("iv4show", false);
	list.add(map);
	
	map=new HashMap<String, Object>();
	map.put("name", "angle3222222222222222222222222222");
	map.put("iv1show", true);
	map.put("iv2show", true);
	map.put("iv3show", true);
	map.put("iv4show", false);
	list.add(map);
	
	map=new HashMap<String, Object>();
	map.put("name", "angle422222222222222222222222222222222222222222222222");
	map.put("iv1show", true);
	map.put("iv2show", true);
	map.put("iv3show", true);
	map.put("iv4show", true);
	list.add(map);
	
	}

	class SchoolAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(convertView==null)
			convertView=(Schoollayout) LayoutInflater.from(Schoolsitelist.this).inflate(R.layout.customitem, null);
			
			Schoollayout vv=(Schoollayout) convertView;
			Map<String, Object> map=list.get(position);
			vv.setData((String)map.get("name"), ((Boolean)map.get("iv1show")),  (Boolean)map.get("iv1show"),  (Boolean)map.get("iv1show"),  (Boolean)map.get("iv1show"));
			
			return convertView;
		}
		
		
		
	}
}
