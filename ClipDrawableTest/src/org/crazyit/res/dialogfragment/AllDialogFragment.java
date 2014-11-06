package org.crazyit.res.dialogfragment;


import org.crazyit.res.R;

import android.R.integer;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class AllDialogFragment extends DialogFragment {

	public static AllDialogFragment getInstance(int index){
		AllDialogFragment fragment=new AllDialogFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("index", index);
		fragment.setArguments(bundle);
		return fragment;
	}
	
	public AllDialogFragment(){
		setStyle(STYLE_NO_TITLE, STYLE_NO_TITLE);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return super.onCreateView(inflater, container, savedInstanceState);
	}
	private int index;
	private View view;
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		index=getArguments().getInt("index");
		view=LayoutInflater.from(getActivity()).inflate(R.layout.dialognomal, null);
		TextView tvTitle=(TextView) view.findViewById(R.id.tvtitle);
		TextView tvMessage=(TextView) view.findViewById(R.id.tvmessage);
		Button btnOk=(Button) view.findViewById(R.id.btnok);
		Button btnCancel=(Button) view.findViewById(R.id.btncancel);
		Dialog dialog=new AlertDialog.Builder(getActivity()).setView(view).create();
		if(listener!=null){
			btnOk.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					listener.okListner(index);
					dismiss();
				}
			});
			btnCancel.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					listener.cancelListener(index);
					dismiss();
				}
			});
		}
		switch (index) {
		case 0:
			tvTitle.setText("first title");
			tvTitle.setVisibility(View.VISIBLE);
			break;
		case 1:
			tvMessage.setText("second message");
			tvMessage.setVisibility(View.VISIBLE);
			break;
		
		case 2:
			
			break;
		default:
			
			break;
		}
		//dialog.setInverseBackgroundForced(true);
		return dialog;
	}
	
	private DialogClickListener listener;
	public AllDialogFragment setListener(DialogClickListener listener) {
		this.listener = listener;
		return this;
	}
	public interface DialogClickListener{
		void okListner(int index);
		void cancelListener(int index);
	}
}
