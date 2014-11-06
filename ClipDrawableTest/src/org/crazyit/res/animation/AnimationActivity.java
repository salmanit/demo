package org.crazyit.res.animation;

import java.io.IOException;
import java.util.Locale;

import org.crazyit.res.R;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class AnimationActivity extends Activity {

	private WebView view = null;
	private static final String MAP_URL = "file:///android_asset/map_v3.html";  
	private String  address="上海市四川北路1666号";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.animationxml);
		
		view = (WebView) findViewById(R.id.webview01);
		view.getSettings().setJavaScriptEnabled(true);  
		view.setWebViewClient(new WebViewClient()
		{

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)
			{
				view.loadUrl(url);
				return true;
			}

			 @Override
			 public void onPageFinished(WebView view, String url) {
			// view.loadUrl(centerURL);
			 view.loadUrl("javascript:codeAddress('"+address+"')");
			 }
		});
		view.loadUrl(MAP_URL);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);  
	}

	public void showToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}




}
