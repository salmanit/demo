package org.crazyit.res.dialogfragment;


import org.crazyit.res.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class EditNameDialog extends DialogFragment implements
		OnEditorActionListener ,OnClickListener{

	private EditText mEditText;

	public EditNameDialog() {
		// Empty constructor required for DialogFragment
		//setStyle(STYLE_NO_TITLE, STYLE_NO_TITLE);
		setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) { 
		View view = inflater.inflate(R.layout.fragment_edit_name, container,false);
		mEditText = (EditText) view.findViewById(R.id.txt_your_name);
		//getDialog().setTitle("Hello");
		// Show soft keyboard automatically
		mEditText.requestFocus();
//		getDialog().getWindow().setSoftInputMode(
//				LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		mEditText.setOnEditorActionListener(this);
		view.findViewById(R.id.btnok).setOnClickListener(this);
		view.findViewById(R.id.btncancel).setOnClickListener(this);

		return view;
	}

//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		Dialog dialog=super.onCreateDialog(savedInstanceState);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		
//		return dialog;
//	}
	
	@Override
	public void onActivityCreated(Bundle arg0) {
		
		super.onActivityCreated(arg0);
		Dialog dialog=getDialog();
		Window window=dialog.getWindow();
		WindowManager.LayoutParams parama=window.getAttributes();
		parama.height=LayoutParams.WRAP_CONTENT;
		parama.width=getResources().getDisplayMetrics().widthPixels*4/5;
		window.setAttributes(parama);
		//dialog.onWindowAttributesChanged(parama);
	}
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			EditNameDialogListener activity = (EditNameDialogListener) getActivity();
			activity.onFinishEditDialog(mEditText.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}

	public interface EditNameDialogListener {
		void onFinishEditDialog(String inputText);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnok:
			EditNameDialogListener activity = (EditNameDialogListener) getActivity();
			activity.onFinishEditDialog(mEditText.getText().toString());
			break;
		case R.id.btncancel:
			
			break;
		default:
			break;
		}
		dismiss();
	}
	
	
}
