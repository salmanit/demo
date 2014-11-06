package org.crazyit.res.sms;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.widget.EditText;

public class SmsContent extends ContentObserver {

	 public static final String SMS_URI_INBOX = "content://sms/inbox";
	 private Activity activity = null;
	 private String smsContent = "";
	 private EditText verifyText = null;
	 public SmsContent(Activity activity, Handler handler, EditText verifyText) {
	  super(handler);
	  this.activity = activity;
	  this.verifyText = verifyText;
	 }
	 @Override
	 public void onChange(boolean selfChange) {
	  super.onChange(selfChange);
	  ((SMSAUTOreadActivity)activity).test();
	  Cursor cursor = null;// 光标
	  // 读取收件箱中指定号码的短信
//	  cursor = activity.managedQuery(Uri.parse(SMS_URI_INBOX), new String[] { "_id", "address", "body", "read" }, "address=? and read=?",
//	                                 new String[] { "106900977086", "0" }, "date desc");
	  /**因为我们获取短信的号码可能有变化，所以我是对短信的内容进行过滤的，我们的有下边4个字母 czmm*/
	  cursor=activity.getContentResolver().query(Uri.parse(SMS_URI_INBOX), new String[] { "_id", "address", "body", "read" }, "read=? and body like '%czmm%'",
             new String[] {  "0" }, "date desc");  
	  if (cursor != null) {// 如果短信为未读模式
	 
	   System.out.println("cursor=="+cursor.getCount());
	   if (cursor.moveToFirst()) {
	    String smsbody = cursor.getString(cursor.getColumnIndex("body"));
	    System.out.println("smsbody=======================" + smsbody);
	    String regEx = "[^0-9]";//匹配不是数字的，注意只是一个字符哦
	    Pattern p = Pattern.compile(regEx);
	    Matcher m = p.matcher(smsbody.toString());
	    smsContent = m.replaceAll("").trim().toString();
	    verifyText.setText(smsContent);
	   }
	  }else{
		  System.out.println("cursor is null");
	  }
	 }
	}
