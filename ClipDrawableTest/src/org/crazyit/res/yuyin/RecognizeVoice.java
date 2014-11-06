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

	
	// ������д����
	private SpeechRecognizer mIat;
	// ������дUI
	private RecognizerDialog iatDialog;
	// ��д�������
	private EditText mResultText;
	private Toast mToast;
	private int ret;
	
	// �����ϳɶ���
	private SpeechSynthesizer mTts;
	private String mEngineType;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recognizevoicexml);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);	
		initview();
		
				// ��ʼ��ʶ�����
				mIat = SpeechRecognizer.createRecognizer(this, mInitListener);
				// ��ʼ����дDialog,���ֻʹ����UI��д����,���贴��SpeechRecognizer
				iatDialog = new RecognizerDialog(this,mInitListener);
				// ��ʼ���ϳɶ���
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
	 * ���ڻ�������
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
	
			// ��ʼ��д
		case R.id.iat_recognize:
			mResultText.setText(null);// �����ʾ����
			// ���ò���
			setParam();
			boolean isShowDialog = true;
			if (isShowDialog) {
				// ��ʾ��д�Ի���
				iatDialog.setListener(recognizerDialogListener);
				iatDialog.show();
				showTip("�뿪ʼ˵��");
			} else {
				// ����ʾ��д�Ի���
				ret = mIat.startListening(recognizerListener);
				if(ret != ErrorCode.SUCCESS){
					showTip("��дʧ��,�����룺" + ret);
				}else {
					showTip("�뿪ʼ˵��");
				}
			}
			break;
			// ֹͣ��д
		case R.id.iat_stop: 
			mIat.stopListening();
			showTip("ֹͣ��д");
			break;
			// ȡ����д
		case R.id.iat_cancel: 
			mIat.cancel();
			showTip("ȡ����д");
			break;
	
			
			// ��ʼ�ϳ�
					case R.id.tts_play:
						String text = ((EditText) findViewById(R.id.iat_text)).getText().toString();
						// ���ò���
						setParam();
						int code = mTts.startSpeaking(text, mTtsListener);
						if (code != ErrorCode.SUCCESS) {
							if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
								//δ��װ����ת����ʾ��װҳ��
							//	mInstaller.install();
							}else {
								showTip("�����ϳ�ʧ��,������: " + code);	
							}
						}
						break;
					// ȡ���ϳ�
					case R.id.tts_cancel:
						mTts.stopSpeaking();
						break;
					// ��ͣ����
					case R.id.tts_pause:
						mTts.pauseSpeaking();
						break;
					// ��������
					case R.id.tts_resume:
						mTts.resumeSpeaking();
						break;	
			
			
			}
	}

	/**
	 * ��дUI������
	 */
	private RecognizerDialogListener recognizerDialogListener=new RecognizerDialogListener(){
		public void onResult(RecognizerResult results, boolean isLast) {
			String text = JsonParser.parseIatResult(results.getResultString());
			mResultText.append(text);
			mResultText.setSelection(mResultText.length());
		}

		/**
		 * ʶ��ص�����.
		 */
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}
		
	};
	
	/**
	 * ��д��������
	 */
	private RecognizerListener recognizerListener=new RecognizerListener(){

		@Override
		public void onBeginOfSpeech() {	
			showTip("��ʼ˵��");
		}


		@Override
		public void onError(SpeechError error) {
			showTip(error.getPlainDescription(true));
		}

		@Override
		public void onEndOfSpeech() {
			showTip("����˵��");
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
				//TODO ���Ľ��
			}
		}

		@Override
		public void onVolumeChanged(int volume) {
			showTip("��ǰ����˵����������С��" + volume);
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
		 * ��ʼ����������
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
				// ��������
				mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
				// ������������
				mIat.setParameter(SpeechConstant.ACCENT,"zh_cn");
			
			// ��������ǰ�˵�
			mIat.setParameter(SpeechConstant.VAD_BOS, "4000");
			// ����������˵�
			mIat.setParameter(SpeechConstant.VAD_EOS, "1000");
			// ���ñ�����,1��ʾ�б��
			mIat.setParameter(SpeechConstant.ASR_PTT, "1");
			// ������Ƶ����·��
			mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, "/sdcard/iflytek/wavaudio.pcm");
			
			
			
			
			//���úϳ�
//			if(mEngineType.equals(SpeechConstant.TYPE_CLOUD))
//			{
				mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
//			}else {
//				mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
//			}
			
			//���÷�����
			mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");
			
			//��������
			mTts.setParameter(SpeechConstant.SPEED,"70");

			//��������
			mTts.setParameter(SpeechConstant.PITCH,"70");

			//��������
			mTts.setParameter(SpeechConstant.VOLUME,"70");
			
			//���ò�������Ƶ������
			mTts.setParameter(SpeechConstant.STREAM_TYPE,"3");	
			
			
			
			
		}	
		
		/**
		 * �ϳɻص�������
		 */
		private SynthesizerListener mTtsListener = new SynthesizerListener() {
			@Override
			public void onSpeakBegin() {
				showTip("��ʼ����");
			}

			@Override
			public void onSpeakPaused() {
				showTip("��ͣ����");
			}

			@Override
			public void onSpeakResumed() {
				showTip("��������");
			}

			@Override
			public void onBufferProgress(int percent, int beginPos, int endPos,
					String info) {
				mPercentForBuffering = percent;
				mToast.setText("�������Ϊ"+ mPercentForPlaying);
				
				mToast.show();
			}

			@Override
			public void onSpeakProgress(int percent, int beginPos, int endPos) {
				mPercentForPlaying = percent;
				showTip("����Ϊ"+ mPercentForPlaying);
			}

			@Override
			public void onCompleted(SpeechError error) {
				if(error == null)
				{
					showTip("�������");
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
			// �˳�ʱ�ͷ�����
			mIat.cancel();
			mIat.destroy();
			
			mTts.stopSpeaking();
			// �˳�ʱ�ͷ�����
			mTts.destroy();
		}
		
		@Override
		protected void onResume() {
			SpeechUtility.getUtility().checkServiceInstalled();
			super.onResume();
		}
		
}
