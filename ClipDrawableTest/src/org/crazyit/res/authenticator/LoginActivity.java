package org.crazyit.res.authenticator;

import org.crazyit.res.R;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class LoginActivity extends AccountAuthenticatorActivity implements
		OnClickListener {

	private EditText etname;
	private EditText etpsw;
	private AccountManager manager;

	@Override
	protected void onCreate(Bundle icicle) {

		super.onCreate(icicle);
		setContentView(R.layout.framentdialogdemo);
		findViewById(R.id.btnsubmit).setOnClickListener(this);
		etname = (EditText) findViewById(R.id.etname);
		etpsw = (EditText) findViewById(R.id.etpsw);
		update();
	}

	public void update(){
		manager = AccountManager.get(this);
		Account[] accounts = manager.getAccountsByType("org.crazyit.res");
		
		for (Account account : accounts) {
			String name = account.name;
			String type = account.type;
			String psw=manager.getPassword(account);
			System.out.println("name=" + name + " type=" + type+" psw="+psw);
			if(TextUtils.equals(type, "org.crazyit.res")){
				etname.setText(name);
				etpsw.setText(psw);
			}
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnsubmit:
			
			manager = AccountManager.get(this);
			Account[] accounts = manager.getAccounts();
			for (Account account : accounts) {
				String name = account.name;
				String type = account.type;
				System.out.println("name=" + name + " type=" + type);
			}

			Account myAccount = new Account(etname.getText().toString(),
					"org.crazyit.res");
			Bundle bundle = new Bundle();
			bundle.putString(AccountManager.KEY_USERDATA, "ÄÐ");

			if (manager.addAccountExplicitly(myAccount, "123456", bundle)) {
				System.out.println("add success");

			}
			break;

		default:
			break;
		}

	}

}
