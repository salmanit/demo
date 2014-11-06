package org.crazyit.res.dialogfragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.crazyit.res.R;
import org.crazyit.res.dialogfragment.AllDialogFragment.DialogClickListener;
import org.crazyit.res.dialogfragment.ChangeFileNameDialogFragment.EditNameDialogListener1;
import org.crazyit.res.dialogfragment.EditNameDialog.EditNameDialogListener;
import org.crazyit.res.fragment.Fragment1;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.view.menu.MenuPopupHelper;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.Toast;

public class FragmentDialogDemo extends ActionBarActivity implements
		EditNameDialogListener, EditNameDialogListener1, OnClickListener,
		DialogClickListener {

	private Button btndialog;
	private ImageView ivshow;
	// private ProgressDialog pDialog;
	String urlString = "http://www.android-doc.com/design/media/creative_vision_main.png";
	private EditText etname;
	private EditText etpsw;
	private AccountManager manager;
	private SearchView sv;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.framentdialogdemo);
		setOverflowShowingAlways();
		initUi();
		
		
		 
	}

	private void initUi() {
		findViewById(R.id.btndialog).setOnClickListener(this);
		findViewById(R.id.btndialog1).setOnClickListener(this);
		findViewById(R.id.btndown).setOnClickListener(this);
		findViewById(R.id.btnpic).setOnClickListener(this);
		findViewById(R.id.btn1).setOnClickListener(this);
		findViewById(R.id.btn2).setOnClickListener(this);
		findViewById(R.id.btnpopup).setOnClickListener(this);
		findViewById(R.id.btnreplace).setOnClickListener(this);
		findViewById(R.id.btnfragment).setOnClickListener(this);
		findViewById(R.id.btnsubmit).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		ivshow = (ImageView) findViewById(R.id.ivshow);
		// pDialog=new ProgressDialog(this);
		// pDialog.setTitle("涓嬭浇涓?...");
		// pDialog.setCanceledOnTouchOutside(true);
		// pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	}

	Bitmap downloadBitmap(String url) {
		final AndroidHttpClient client = AndroidHttpClient
				.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);

		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				// pDialog.dismiss();
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory
							.decodeStream(inputStream);
					// final Bitmap bitmap = BitmapFactory.decodeStream(new
					// FlushedInputStream(inputStream));
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
			// IllegalStateException
			getRequest.abort();
			Log.w("ImageDownloader", "Error while retrieving bitmap from "
					+ url + e.toString());
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}

	private void showEditDialog() {
		EditNameDialog editNameDialog = new EditNameDialog();
		editNameDialog.show(getSupportFragmentManager(), "fragment_edit_name");

	}

	@Override
	public void onFinishEditDialog(String inputText) {
		Toast.makeText(this, "Hi, " + inputText, Toast.LENGTH_SHORT).show();
	}

	public void showEditDialog1() {
		String tag = "test" + new Random().nextInt(100);
		ChangeFileNameDialogFragment.getInstance(tag).show(
				getSupportFragmentManager(), tag);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btndialog:
			showEditDialog();
//			 getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
//			 .add(android.R.id.content, new EditNameDialog()).commit();
			break;
		case R.id.btndialog1:
			showEditDialog1();
			break;
		case R.id.btndown:
			new BitmapDownloaderTask(ivshow).execute(urlString);
			// pDialog.show();
			break;
		case R.id.btnpic:
			showpicdia();
			break;
		case R.id.btn1:
			AllDialogFragment.getInstance(0).setListener(this)
					.show(getSupportFragmentManager(), "first");
			break;
		case R.id.btn2:
			AllDialogFragment.getInstance(1).setListener(this)
					.show(getSupportFragmentManager(), "second");
			break;
		case R.id.btnpopup:
			showPopup(v);
			break;
		case R.id.btnreplace:
			getSupportFragmentManager().beginTransaction().replace(R.id.testlayout, new Fragment1()).commit();
			break;
		case R.id.btnfragment:
			getSupportFragmentManager().beginTransaction().replace(R.id.myframlayout, new Fragment1()).commit();
			break;
		case R.id.btnsubmit:
			
			
			break;
		case R.id.button4:
			try {
				InputStream is = getAssets().open(".nomedia");
				File file4 = new File(Environment.getExternalStorageDirectory()
						.getAbsolutePath() + "/aaa", ".nomedia");
				FileOutputStream fos = new FileOutputStream(file4);
				byte[] buffer = new byte[1024];
				int length;
				int byteread = -1;
				while ((byteread = is.read(buffer)) != -1) {

					// 字节数 文件大小
					fos.write(buffer, 0, byteread);
				}
				fos.close();
				is.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	public void showPopup(View v) {
		PopupMenu popup = new PopupMenu(this, v);
		popup.inflate(R.menu.submenu);
		popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem arg0) {
				switch (arg0.getItemId()) {
				case R.id.action_save_menu:
					ShowToast("save");
					break;
				case R.id.action_cancel_menu:
					ShowToast("cancel");
					break;
				default:
					break;
				}
				return false;
			}
		});
		try {
			Field mpopup = popup.getClass().getDeclaredField("mPopup");
			mpopup.setAccessible(true);
			MenuPopupHelper mPopup = (MenuPopupHelper) mpopup.get(popup);
			mPopup.setForceShowIcon(true);
		} catch (Exception e) {
			System.out.println("exception");
		}
		popup.show();
	}

	private void showpicdia() {
		ChoicPicDialog cpd = new ChoicPicDialog();
		cpd.show(getSupportFragmentManager(), "choicpicdiafragment");
		cpd.setCancelable(true);

	}

	class BitmapDownloaderTask extends AsyncTask<String, Void, Bitmap> {
		private String url;
		private final WeakReference<ImageView> imageViewReference;

		public BitmapDownloaderTask(ImageView imageView) {
			imageViewReference = new WeakReference<ImageView>(imageView);
		}

		@Override
		// Actual download method, run in the task thread
		protected Bitmap doInBackground(String... params) {
			// params comes from the execute() call: params[0] is the url.
			return downloadBitmap(params[0]);
		}

		@Override
		// Once the image is downloaded, associates it to the imageView
		protected void onPostExecute(Bitmap bitmap) {
			// pDialog.dismiss();
			if (isCancelled()) {
				bitmap = null;
			}

			if (imageViewReference != null) {
				ImageView imageView = imageViewReference.get();
				if (imageView != null) {
					imageView.setImageBitmap(bitmap);
				}
			}
		}
	}

	/**
	 * a bug in the previous versions of BitmapFactory.decodeStream may prevent
	 * this code from working over a slow connection. Decode a new
	 * FlushedInputStream(inputStream) instead to fix the problem. Here is the
	 * implementation of this helper class:
	 * */
	class FlushedInputStream extends FilterInputStream {
		public FlushedInputStream(InputStream inputStream) {
			super(inputStream);
		}

		@Override
		public long skip(long n) throws IOException {

			long totalBytesSkipped = 0L;
			while (totalBytesSkipped < n) {
				long bytesSkipped = in.skip(n - totalBytesSkipped);
				if (bytesSkipped == 0L) {
					int byteread = read();
					if (byteread < 0) {
						break; // we reached EOF
					} else {
						bytesSkipped = 1; // we read one byte
					}
				}
				totalBytesSkipped += bytesSkipped;
			}
			return totalBytesSkipped;
		}

	}

	@Override
	public void okListner(int index) {
		switch (index) {
		case 0:
			ShowToast("first ok");
			break;
		case 1:
			ShowToast("second ok");
			break;
		default:
			break;
		}

	}

	@Override
	public void cancelListener(int index) {
		switch (index) {
		case 0:
			ShowToast("first cancel");
			break;
		case 1:
			ShowToast("second cancel");
			break;
		default:
			break;
		}
	}

	public void ShowToast(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	public void temp(View focusView, boolean show) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		if (show)
			imm.showSoftInput(focusView, InputMethodManager.SHOW_FORCED);
		else
			imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
	}

	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.testmenu1, menu);
		final SearchView searchView = (SearchView) menu.findItem(
				R.id.item_search).getActionView();
		searchView.setQueryHint("please input search words");
		searchView.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String arg0) {
				ShowToast("" + arg0);
				// 隐藏输入法
				temp(searchView, false);
				return true;
			}

			@Override
			public boolean onQueryTextChange(String arg0) {
				// ShowToast(""+arg0);
				return true;
			}
		});

		return super.onCreateOptionsMenu(menu);
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item_search:
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	private void setOverflowShowingAlways() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			menuKeyField.setAccessible(true);
			menuKeyField.setBoolean(config, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public boolean onMenuOpened(int featureId, Menu menu) {
		if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
			if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
				try {
					Method m = menu.getClass().getDeclaredMethod(
							"setOptionalIconsVisible", Boolean.TYPE);
					m.setAccessible(true);
					m.invoke(menu, true);
				} catch (Exception e) {
				}
			}
		}
		return super.onMenuOpened(featureId, menu);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	}
}
