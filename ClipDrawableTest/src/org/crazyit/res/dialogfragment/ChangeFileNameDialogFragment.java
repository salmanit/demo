package org.crazyit.res.dialogfragment;




import org.crazyit.res.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.WindowManager.LayoutParams;
public class ChangeFileNameDialogFragment extends DialogFragment implements
		OnEditorActionListener ,OnClickListener{

	public static ChangeFileNameDialogFragment getInstance(String oldname) {
		ChangeFileNameDialogFragment fragment = new ChangeFileNameDialogFragment();
		Bundle bundle = new Bundle();
		bundle.putString("oldname", oldname);
		fragment.setArguments(bundle);
		return fragment;
	}

	private EditText mEditText;

	public ChangeFileNameDialogFragment() {
		// Empty constructor required for DialogFragment
	}

//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.changefilename, container);
//		mEditText = (EditText) view.findViewById(R.id.et_name);
//		// Show soft keyboard automatically
//		mEditText.requestFocus();
//		getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//		mEditText.setOnEditorActionListener(this);
//		return view;
//	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.changefilename, null);
		mEditText = (EditText) view.findViewById(R.id.et_name);
		// Show soft keyboard automatically
		mEditText.requestFocus();
		if(getArguments()!=null){
			String oldname=getArguments().getString("oldname");
			mEditText.setText(oldname);
			if(!TextUtils.isEmpty(oldname))
				mEditText.setSelection(oldname.length());
		}
		//getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);//ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö¸ï¿½ë¡£ï¿½ï¿½ï¿½ï¿?
		//getActivity().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);//ï¿½ï¿½ï¿½ï¿½ï¿½Ð?
		//((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(mEditText, 0); //ï¿½ï¿½Ð§
		mEditText.setOnEditorActionListener(this);
		view.findViewById(R.id.btnok).setOnClickListener(this);
		view.findViewById(R.id.btncancel).setOnClickListener(this);
		
		Dialog dialog=new AlertDialog.Builder(getActivity()).setView(view).create();
		return dialog;
	}
	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		//getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		getDialog().setCancelable(false);
		getDialog().setCanceledOnTouchOutside(false);
	}
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		if (EditorInfo.IME_ACTION_DONE == actionId) {
			EditNameDialogListener1 activity = (EditNameDialogListener1) getActivity();
			activity.onFinishEditDialog(mEditText.getText().toString());
			this.dismiss();
			return true;
		}
		return false;
	}

	public interface EditNameDialogListener1 {
		void onFinishEditDialog(String inputText);
	}

	@Override
	public void onClick(View v) {
		getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		switch (v.getId()) {
		case R.id.btnok:
			if(TextUtils.isEmpty(mEditText.getText().toString().trim())){
				return;
			}
			if(getArguments().getString("oldname").equals(mEditText.getText().toString().trim())){
				return;
			}
			FragmentDialogDemo activity = (FragmentDialogDemo) getActivity();
			activity.onFinishEditDialog(mEditText.getText().toString().trim());
			dismiss();
			break;
		case R.id.btncancel:
			getActivity().finish();
			//dismiss();
			break;
		default:
			break;
		}
		
		//((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS); 
	}

}
