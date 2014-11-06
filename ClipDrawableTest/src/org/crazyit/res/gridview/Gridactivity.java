package org.crazyit.res.gridview;

import org.crazyit.res.R;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class Gridactivity extends Activity {

	GridView gView;
	private Button btn1;
	private Animation animation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mygridviewxml);
		gView=(GridView) findViewById(R.id.gv1);
		gView.setNumColumns(2);
		gView.setAdapter(adapter);
		gView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				gView.setItemChecked(position, true);
				adapter.notifyDataSetChanged();
			}
		});
		
	 //animation=AnimationUtils.loadAnimation(this, R.anim.rotateanim);
	 animation=AnimationUtils.loadAnimation(this, R.anim.translateanim);
	 animation.setAnimationListener(new Animation.AnimationListener() {
		
		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onAnimationEnd(Animation animation) {
			btn1.clearAnimation();
		}
	});
		 btn1=(Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btn1.startAnimation(animation);
			}
		});
		findViewById(R.id.btn2).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				animation.cancel();
			}
		});
		
		
	}
	
public  BaseAdapter adapter=new BaseAdapter() {
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("position=="+position);
		if(convertView==null)
		convertView=LayoutInflater.from(Gridactivity.this).inflate(R.layout.gvitem, parent,false);
		final View view=convertView;
		TextView tv=(TextView) view.findViewById(R.id.tv1);
		ImageView iv=(ImageView) view.findViewById(R.id.iv1);
		if(position%2==1){
		tv.setText("position=="+position+" 你说的我都懂，可你又何必这样了啊你说啥都行");
		view.setSelected(false);
		iv.setImageResource(R.drawable.choujiang10);
		}
		else {
			tv.setText("position=="+position);
			view.setSelected(true);
			iv.setImageResource(R.drawable.choujiang13);
		}
		return view;
	}
	
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 9;
	}
	
	private int selectedposition = 0;

	public int getSelectedposition() {
		return selectedposition;
	}

	public void setSelectedposition(int selectedposition) {
		this.selectedposition = selectedposition;
	}
};
	
}
