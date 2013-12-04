package com.faceme_android;

import com.example.faceme_android.ApplicationData;
import com.example.faceme_android.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/***
 * 
 * Login
 * 
 * @author leonhardt
 * 
 */
public class LoginActivity extends Activity {

	private ApplicationData mApplicationData;
	private String username;
	private String password, url_password;
	private Button btn_signin;
	private EditText et_name;
	private EditText et_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btn_signin = (Button) findViewById(R.id.btn_login);
		et_name = (EditText) this.findViewById(R.id.load_name);
		et_password = (EditText) this.findViewById(R.id.load_password);
		et_name.setText("Brandon");
		et_password.setText("111111");
		btn_signin.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				verification();
			}
		});
	}

	public void readNamePass() {
		username = et_name.getText().toString();
		password = et_password.getText().toString();
		
		String user_url_path = "https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/get/"
				+ username;
		
		mApplicationData = (ApplicationData) getApplicationContext();
		mApplicationData.loadUserInfo(user_url_path.replace(" ", "%20"));
		
		while(mApplicationData.mCurrentUser==null){
			;
		}
		url_password = mApplicationData.mCurrentUser.getPassword();
		System.out.println("Password: " + password + "||"+url_password);
	}

	public void verification() {
		readNamePass();
		if(!url_password.equals(password)){
			Toast.makeText(LoginActivity.this, "Login failed." + username + "password incorrect", Toast.LENGTH_SHORT).show();
			et_password.setText("");
		}else{
			startActivity(new Intent(LoginActivity.this, MultiTabActivity.class));
		}
	}
}
