package com.example.faceme_android;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class PictureViewActivity extends Activity {

	public PictureViewActivity(){

	}
	GlobalState state;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		state = (GlobalState) getApplicationContext();
		setContentView(R.layout.activity_picture_view);
		ImageView imview = (ImageView) findViewById(R.id.imageView);

		Bitmap tmp = Tools.getBitmapFromPath(Environment.getExternalStorageDirectory().getPath() +"/CosplayTmp.png");


		GlobalState state = (GlobalState) getApplicationContext();
		context = this;
		Bitmap nonfacePoster = state.currentPoster.nonfacePoster;
		//Tools.getBitmapFromPath(Environment.getExternalStorageDirectory().getPath() +"/CosplayTmp.png"));

		CharacterFace facechoosed = state.faceChosed;


		Bitmap userFaceBmp = Bitmap.createScaledBitmap(tmp, (int)(nonfacePoster.getWidth() * facechoosed.getWidth()), (int)(nonfacePoster.getHeight() * facechoosed.getHeight()), false);
		Bitmap result = Bitmap.createBitmap(nonfacePoster.getWidth(), nonfacePoster.getHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(result);

		for(CharacterFace face : state.currentPoster.faces){
			if(face != facechoosed){
				canvas.drawBitmap(face.bmp, face.getPositionX() * nonfacePoster.getWidth(), face.getPostionY() * nonfacePoster.getHeight(), null);
			}
		}

		canvas.drawBitmap(userFaceBmp, facechoosed.getPositionX() * nonfacePoster.getWidth(), facechoosed.getPostionY() * nonfacePoster.getHeight(), null);
		canvas.drawBitmap(nonfacePoster, 0, 0, null);

		imview.setImageBitmap(result);
		try {
			String filename = Environment.getExternalStorageDirectory().getPath() +"/CosplayTmp.png";
			FileOutputStream out = new FileOutputStream(filename);
			result.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		final UploadUserFaceTask uploadTask = new UploadUserFaceTask(userFaceBmp);
		
		Button btn_share = (Button)findViewById(R.id.button_Share);
		Button btn_upload = (Button)findViewById(R.id.button_Upload);

		btn_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("image/png");
				File f = new File(Environment.getExternalStorageDirectory().getPath()+"/CosplayTmp.png");
				Uri uri = Uri.fromFile(f); intent.putExtra(Intent.EXTRA_STREAM, uri); 

				intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
				intent.putExtra(Intent.EXTRA_TEXT,
						"FaceMe is awesome! You should also try this!");
				startActivity(Intent.createChooser(intent, getTitle()));

			}
		});

		Button btn_home = (Button) findViewById(R.id.button_home);
		btn_home.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(getBaseContext(),MenuActivity.class));
			}
		});

		btn_upload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				uploadTask.execute();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_view, menu);
		return true;
	}
	public void createNotification() {
		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this, RateAndCommentActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		Bitmap logoIcon=Tools.getBitmapFromAsset(this, "logo.png");

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
	public class UploadUserFaceTask extends AsyncTask<Void, Void, Void>{
		ProgressDialog dialog;
		ArrayAdapter<PosterEntity>posteradapter;
		final String BlobUploadURLAPI = "https://facemegatech.appspot.com/_ah/api/originalposterendpoint/v1/originalposter/url";
		Bitmap imageBmp;
		public UploadUserFaceTask(Bitmap imageBmp) {
			this.imageBmp = imageBmp;
		}
		
		public String getUploadURL(){
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(BlobUploadURLAPI);
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
				System.out.println("JsonData" + jsonData);
				JSONObject json= new JSONObject(jsonData);

				return json.getString("url");
			}catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		public String uploadImage(String uploadUrl){
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(uploadUrl);
				MultipartEntity reqEntityBuilder = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
				ByteArrayOutputStream stream = new ByteArrayOutputStream();
				imageBmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
				byte[] data = stream.toByteArray();
				//TODO: Format the upload image Name;
				String uploadFileName = state.currentUser.username + "_" + state.currentPoster.getPosterName() + ".png";
				ByteArrayBody bab = new ByteArrayBody(data, uploadFileName);
				reqEntityBuilder.addPart("uploadFileName", bab);
				postRequest.setEntity(reqEntityBuilder);       
				HttpResponse response = httpClient.execute(postRequest);
				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				String sResponse = reader.readLine() ;
				return sResponse;
			} 
			catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		
		public void uploadUserFaceEntity(String faceKey){
			String insertUserFaceUrl = "https://facemegatech.appspot.com/_ah/api/userfaceendpoint/v1/userface/insert";
			try {
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost postRequest = new HttpPost(insertUserFaceUrl);

				JSONObject faceEntity = new JSONObject();
				faceEntity.put("imageKey", faceKey );
				faceEntity.put("characterKey", ""+state.faceChosed.getKey());
				faceEntity.put("posterKey", ""+state.currentPoster.getKey());
				faceEntity.put("userID", state.currentUser.getUsername());
				
				StringEntity se = new StringEntity(faceEntity.toString());  
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
		
		@Override
		protected Void doInBackground(Void... params) {
				String uploadUrl = this.getUploadURL();
				String imageBlobKey = this.uploadImage(uploadUrl);
				this.uploadUserFaceEntity(imageBlobKey);
				return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			dialog.dismiss();
			AlertDialog notification= new AlertDialog.Builder(context).create();
			notification.setMessage("Upload Successful!");
			notification.setButton("OK", new DialogInterface.OnClickListener() {
				 public void onClick(DialogInterface dialog, int which) {
                   dialog.dismiss();
                }
            });
			notification.show();
			
			super.onPostExecute(result);
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog=new ProgressDialog(context);
			dialog.setTitle("Uploading");
			dialog.show();
			super.onPreExecute();
			super.onPreExecute();
		}
	}
}
