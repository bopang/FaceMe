package com.example.faceme_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends Activity{

	private Button btn_registration;
	private EditText et_name;
	private EditText et_password;
	private EditText et_gender;
	private EditText et_school;
	
	private String name, password, gender, school;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.registration);
		
		et_name = (EditText)findViewById(R.id.signup_name);
		et_password = (EditText)findViewById(R.id.signup_password);
		et_gender = (EditText)findViewById(R.id.signup_gender);
		et_school = (EditText)findViewById(R.id.signup_school);
		
		btn_registration = (Button)findViewById(R.id.btn_registration);
		btn_registration.setOnClickListener(onClickListener);
	}

	OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			
			name = et_name.getText().toString();
			password = et_password.getText().toString();
			gender = et_gender.getText().toString();
			school = et_school.getText().toString();
			
			final UserRegistrationTask mUserRegistrationTask = new UserRegistrationTask(name,password,gender,school);
			mUserRegistrationTask.execute();
			Toast.makeText(RegistrationActivity.this, "Congratulations! You have created a new account.", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
		}
		
	};
	
	public class UserRegistrationTask extends AsyncTask<Void, Void, Void>{
		String name;
		String password;
		String gender;
		String school;
		
		public UserRegistrationTask(String name, String password,
				String gender, String school) {
			this.name = name;
			this.password = password;
			this.gender = gender;
			this.school = school;
		}

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			this.createNewAccount(name, password, gender, school);
			return null;
		}

		private void createNewAccount(String name2, String password2,
				String gender2, String school2) {
			// TODO Auto-generated method stub
			final String userInsertUrl = "https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/insert";
			
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(userInsertUrl);

				JSONObject userEntity = new JSONObject();
				userEntity.put("userID", name);
				userEntity.put("password", password);
				userEntity.put("gender", gender);
				userEntity.put("school", school);
				
				
				System.out.println("JSON: "+userEntity.toString());
				
				StringEntity se = new StringEntity(userEntity.toString());  
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                postRequest.setEntity(se);
				
				HttpResponse response = httpClient.execute(postRequest);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String sResponse;
				while ((sResponse = reader.readLine()) != null) {
					System.out.println(sResponse);
				}
			} 
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		
	}
}
