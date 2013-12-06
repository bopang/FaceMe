package com.faceme_android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.faceme_android.ApplicationData;
import com.example.faceme_android.R;
import com.example.faceme_android.RateAndCommentActivity;
import com.example.faceme_android.Tools;

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

	boolean hasNotification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		btn_signin = (Button) findViewById(R.id.btn_login);
		et_name = (EditText) this.findViewById(R.id.load_name);
		et_password = (EditText) this.findViewById(R.id.load_password);
		
		SharedPreferences preferences = getSharedPreferences("userInfo",
				MODE_PRIVATE);
		this.username = preferences.getString("username", "");
		this.password = preferences.getString("password", "");
		
		et_name.setText(username);
		et_password.setText(password);
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
			SharedPreferences sharedPreferences = getSharedPreferences(
					"userInfo", MODE_PRIVATE);
			Editor editor = sharedPreferences.edit();
			editor.putString("username", username);
			editor.putString("password", password);
			editor.commit();
			startActivity(new Intent(LoginActivity.this, MultiTabActivity.class));
			createNotification();
		}
	}
	
    public void createNotification() {
        // Prepare intent which is triggered if the
        // notification is selected
        Intent intent = new Intent(this, RateAndCommentActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Bitmap logoIcon=Tools.getBitmapFromAsset(this, "logo.png");
        
        new GetNotificationInfoTask().execute();
        if(hasNotification){
        	// Build notification
            NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
            .setContentTitle("You Got a Paired Photo! " )
            .setContentText("FaceMe app")
            .setSmallIcon(R.drawable.logo_launcher)
            .setContentIntent(pIntent)
            .addAction(R.drawable.ic_launcher, "Action Button", pIntent);
            
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            // hide the notification after its selected
            notificationManager.notify(0, noti.build());
        }

      }
    
    public class GetNotificationInfoTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet("https://facemegatech.appspot.com/_ah/api/notificationendpoint/v1/notification/getallnotification/"+username);
			try {
				
				HttpResponse response=client.execute(httpGet);
				StatusLine statusline=response.getStatusLine();
				int statusCode=statusline.getStatusCode();
				if(statusCode!=200){
					return null;
				}
				InputStream jsonStream =response.getEntity().getContent();
				BufferedReader reader=new BufferedReader(new InputStreamReader(jsonStream));
				StringBuilder builder=new StringBuilder();
				String line;
				while((line=reader.readLine())!=null){
					
					builder.append(line);
				}
				String jsonData=builder.toString();
				Log.i("JsonData", jsonData);
				JSONObject json= new JSONObject(jsonData);
				
				JSONArray items = json.getJSONArray("items");
				
				if (items.length() > 0) {
					hasNotification = true;
				}
				
				

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				hasNotification = false;
			}
			
			return null;
		}
    	
    }
}
