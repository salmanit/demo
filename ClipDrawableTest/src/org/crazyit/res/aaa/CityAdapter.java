package org.crazyit.res.aaa;

import org.crazyit.res.R;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorTreeAdapter;
import android.widget.TextView;

public class CityAdapter extends CursorTreeAdapter {

	public CityAdapter(Cursor cursor, Context context, boolean autoRequery) {
		super(cursor, context, autoRequery);
		
	}

	@Override
	public int getGroupTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	@Override
	protected Cursor getChildrenCursor(Cursor groupCursor) {
		if(groupCursor.getInt(0)==1){
			
		}else{
			
		}
		
		return null;
	}

	@Override
	protected View newGroupView(Context context, Cursor cursor,
			boolean isExpanded, ViewGroup parent) {
		System.out.println("cursor.getPosition()=="+cursor.getPosition());
		View vv=null;
		switch (cursor.getPosition()) {
		case 0:
			vv=LayoutInflater.from(context).inflate(R.layout.groupitem1, parent,false);
			break;
		case 1:
			vv=LayoutInflater.from(context).inflate(R.layout.groupitem2, parent,false);
			break;
		default:
			break;
		}
		return vv;
	}

	@Override
	protected void bindGroupView(View view, Context context, Cursor cursor,
			boolean isExpanded) {
		TextView tv1=(TextView) view.findViewById(R.id.tvid);
		TextView tv2=(TextView) view.findViewById(R.id.tvname);
		tv1.setText("id=="+cursor.getInt(0));
		tv2.setText(cursor.getString(1));
//		tv1.setText("id=="+1);
//		tv2.setText("dfgg");
		
	}

	@Override
	protected View newChildView(Context context, Cursor cursor,
			boolean isLastChild, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void bindChildView(View view, Context context, Cursor cursor,
			boolean isLastChild) {
		// TODO Auto-generated method stub

	}

}
