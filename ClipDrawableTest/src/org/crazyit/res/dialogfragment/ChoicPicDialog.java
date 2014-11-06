package org.crazyit.res.dialogfragment;


import org.crazyit.res.R;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.Toast;

public class ChoicPicDialog extends DialogFragment implements OnClickListener{

	public ChoicPicDialog(){
		setStyle(STYLE_NO_TITLE, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.choicpicdiaxml, container, false);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		getView().findViewById(R.id.btn1).setOnClickListener(this);
		getView().findViewById(R.id.btn2).setOnClickListener(this);
		getView().findViewById(R.id.btn3).setOnClickListener(this);
				Window window=getDialog().getWindow();
				// 设置显示动画,这个动画没效果�?��?�不知道为啥
		       // window.setWindowAnimations(R.style.mychoicpicdiastyle);
				//window.setWindowAnimations(R.anim.choicpic);
		        WindowManager.LayoutParams wl = window.getAttributes();
		        wl.x = 0;
		        wl.y = 0;
		        wl.gravity=Gravity.TOP;
		        // 以下这两句是为了保证按钮可以水平满屏
		        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		        // 设置显示位置
		        getDialog().onWindowAttributesChanged(wl);
		        // 设置点击外围解散
		        getDialog().setCanceledOnTouchOutside(true);
       
	}

	public void showToast(String str) {
		Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn1:
			showToast("camera");
			break;
		case R.id.btn2:
			showToast("gallery");
			break;
		case R.id.btn3:
			showToast("cancel");
			break;
		default:
			break;
		}
		dismiss();
	}
}
