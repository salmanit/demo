package org.crazyit.res.yuyin;

import org.crazyit.res.R;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RecognizeVoice extends Activity implements OnClickListener{

	
	// 语音听写对象
	private SpeechRecognizer mIat;
	// 语音听写UI
	private RecognizerDialog iatDialog;
	// 听写结果内容
	private EditText mResultText;
	private Toast mToast;
	private int ret;
	
	// 语音合成对象
	private SpeechSynthesizer mTts;
	private String mEngineType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recognizevoicexml);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);	
		initview();
		
				// 初始化识别对象
				mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
				// 初始化听写Dialog,如果只使用有UI听写功能,无需创建SpeechRecognizer
				iatDialog = new RecognizerDialog(this,mInitListener);
				// 初始化合成对象
				mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);
				
				
	}
	
	
	public void initview(){
		mResultText = ((EditText)findViewById(R.id.iat_text));		
		findViewById(R.id.iat_recognize).setOnClickListener(this);
		findViewById(R.id.iat_recognize).setEnabled(false);		
		findViewById(R.id.iat_stop).setOnClickListener(this);
		findViewById(R.id.iat_cancel).setOnClickListener(this);
		
		findViewById(R.id.tts_play).setOnClickListener(this);
		findViewById(R.id.tts_play).setEnabled(false);
		
		findViewById(R.id.tts_cancel).setOnClickListener(this);
		findViewById(R.id.tts_pause).setOnClickListener(this);
		findViewById(R.id.tts_resume).setOnClickListener(this);
		
		
		mEngineType = SpeechConstant.TYPE_CLOUD;
	}
	/**
	 * 初期化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
			Log.d("", "InitListener init() code = " + code);
			if (code == ErrorCode.SUCCESS) {
				((Button)findViewById(R.id.tts_play)).setEnabled(true);
			}			
		}
	};
	
	
	
	
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
	
			// 开始听写
		case R.id.iat_recognize:
			mResultText.setText(null);// 清空显示内容
			// 设置参数
			setParam();
			boolean isShowDialog = true;
			if (isShowDialog) {
				// 显示听写对话框
				iatDialog.setListener(recognizerDialogListener);
				iatDialog.show();
				showTip("请开始说话");
			} else {
				// 不显示听写对话框
				ret = mIat.startListening(recognizerListener);
				if(ret != ErrorCode.SUCCESS){
					showTip("听写失败,错误码：" + ret);
				}else {
					showTip("请开始说话");
				}
			}
			break;
			// 停止听写
		case R.id.iat_stop: 
			mIat.stopListening();
			showTip("停止听写");
			break;
			// 取消听写
		case R.id.iat_cancel: 
			mIat.cancel();
			showTip("取消听写");
			break;
	
			
			// 开始合成
					case R.id.tts_play:
						String text = ((EditText) findViewById(R.id.iat_text)).getText().toString();
						// 设置参数
						setParam();
						int code = mTts.startSpeaking(text, mTtsListener);
						if (code != ErrorCode.SUCCESS) {
							if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
								//未安装则跳转到提示安装页面
							//	mInstaller.install();
							}else {
								showTip("语音合成失败,错误码: " + code);	
							}
						}
						break;
					// 取消合成
					case R.id.tts_cancel:
						mTts.stopSpeaking();
						break;
					// 暂停播放
					case R.id.tts_pause:
						mTts.pauseSpeaking();
						break;
					// 继续播放
					case R.id.tts_resume:
						mTts.resumeSpeaking();
						break;	
			
			
			}
	}

	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener(){
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = JsonParser.parseIatResult(results.getResultString());
			mResultText.append(text);
			mResultText.setSelection(mResultText.length());
		}

		/**
		 * 识别回调错误.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}
		
	};
	
	/**
	 * 听写监听器。
	 */
	private RecognizerListener recognizerListener=new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {	
			showTip("开始说话");
		}


		@Override
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			showTip("结束说话");
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, String msg) {

		}

		@Override
		public void onResult(RecognizerResult results, boolean isLast) {		
			String text = JsonParser.parseIatResult(results.getResultString());
			mResultText.append(text);
			mResultText.setSelection(mResultText.length());
			if(isLast) {
				//TODO 最后的结果
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			showTip("当前正在说话，音量大小：" + volume);
		}

	};
	
	private void showTip(final String str)
	{
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				mToast.setText(str);
				mToast.show();
			}
		});
	}		
		
		/**
		 * 初始化监听器。
		 */
		private InitListener mInitListener = new InitListener() {

			@Override
			public void onInit(int code) {
				showTip("code=="+code);
				if (code == ErrorCode.SUCCESS) {
					findViewById(R.id.iat_recognize).setEnabled(true);
				}
			}
		};
		protected int mPercentForBuffering;
		protected int mPercentForPlaying;

		public void setParam(){
				//mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
				// 设置语言
				mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
				// 设置语言区域
				mIat.setParameter(SpeechConstant.ACCENT,"zh_cn");
			
			// 设置语音前端点
			mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
			// 设置语音后端点
			mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
			// 设置标点符号,1表示有标点
			mIat.setParameter(SpeechConstant.ASR_PTT, "1");
			// 设置音频保存路径
			mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, "/sdcard/iflytek/wavaudio.pcm");
			
			
			
			
			//设置合成
//			if(mEngineType.equals(SpeechConstant.TYPE_CLOUD))
//			{
				mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//			}else {
//				mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
//			}
			
			//设置发音人
			mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
			
			//设置语速
			mTts.setParameter(SpeechConstant.SPEED,"70");

			//设置音调
			mTts.setParameter(SpeechConstant.PITCH,"70");

			//设置音量
			mTts.setParameter(SpeechConstant.VOLUME,"70");
			
			//设置播放器音频流类型
			mTts.setParameter(SpeechConstant.STREAM_TYPE,"3");	
			
			
			
			
		}	
		
		/**
		 * 合成回调监听。
		 */
		private SynthesizerListener mTtsListener = new SynthesizerListener() {
			@Override
			public void onSpeakBegin() {
				showTip("开始播放");
			}

			@Override
			public void onSpeakPaused() {
				showTip("暂停播放");
			}

			@Override
			public void onSpeakResumed() {
				showTip("继续播放");
			}

			@Override
			public void onBufferProgress(int percent, int beginPos, int endPos,
					String info) {
				mPercentForBuffering = percent;
				mToast.setText("缓冲进度为"+ mPercentForPlaying);
				
				mToast.show();
			}

			@Override
			public void onSpeakProgress(int percent, int beginPos, int endPos) {
				mPercentForPlaying = percent;
				showTip("进度为"+ mPercentForPlaying);
			}

			@Override
			public void onCompleted(SpeechError error) {
				if(error == null)
				{
					showTip("播放完成");
				}
				else if(error != null)
				{
					showTip(error.getPlainDescription(true));
				}
			}
		};		
	
		@Override
		protected void onDestroy() {
			super.onDestroy();
			// 退出时释放连接
			mIat.cancel();
			mIat.destroy();
			
			mTts.stopSpeaking();
			// 退出时释放连接
			mTts.destroy();
		}
		
		@Override
		protected void onResume() {
			SpeechUtility.getUtility().checkServiceInstalled();
			super.onResume();
		}
		
}
