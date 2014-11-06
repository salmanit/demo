package org.crazyit.res.deletetest;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.widget.SectionIndexer;
import android.widget.SimpleAdapter;

public class test1  extends SimpleAdapter implements SectionIndexer{

	public test1(Context context, List<? extends Map<String, ?>> data,
			int resource, String[] from, int[] to) {
		super(context, data, resource, from, to);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPositionForSection(int section) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	
}
