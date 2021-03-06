package com.example.faceme_android.backgroundservice;

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

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.faceme_android.R;
import com.example.faceme_android.RateAndCommentActivity;
import com.example.faceme_android.Tools;

public class PairingService extends Service {

	String feedUrl = "https://facemegatech.appspot.com/_ah/api/pairtableendpoint/v1/pairtable/getallnotification/Ziyi%20Jiang";

	private boolean started;

	private boolean threadDisable;

	private ServiceBinder serviceBinder = new ServiceBinder();

	public class ServiceBinder extends Binder implements IPairingService {

		@Override
		public boolean isStarted() {
			return started;
		}

		@Override
		public void start() {
			started = true;
			Log.d("paring.service", "paring service started.");
		}

		@Override
		public void stop() {
			started = false;
			Log.d("paring.service", "paring service stopped.");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return serviceBinder;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		Thread thread = new Thread() {
			@Override
			public void run() {
				while (!threadDisable) {
					try {
						if (started) {
							Log.d("paring.service", "Getting paring message...");
							getPairingInBackground();
						}
						Thread.sleep(1000 * 9);
					} catch (InterruptedException e) {
					}
				}
			}

			private void getPairingInBackground() {

				HttpClient client = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(feedUrl);

				try {

					HttpResponse response = client.execute(httpGet);
					StatusLine statusline = response.getStatusLine();
					int statusCode = statusline.getStatusCode();
					if (statusCode != 200) {
						return;
					}
					InputStream jsonStream = response.getEntity().getContent();
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(jsonStream));
					StringBuilder builder = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {

						builder.append(line);
					}
					String jsonData = builder.toString();

					Log.i("JsonData", jsonData);

					JSONObject json = new JSONObject(jsonData);
					JSONArray items = json.getJSONArray("items");

					for (int i = 0; i < items.length(); i++) {
						JSONObject item = items.getJSONObject(i);

						if (item.length() > 0) {
							Log.d("paring.service", "Successfully pairing!");
							System.out.println("Successfully pairing!");
							createNotification();
						} else {
							Log.d("paring.service",
									"Failed to get any pairing...");
							System.out.println("Failed to get any pairing...");
						}

					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}

				return;
			}

		};

		thread.start();

		Log.d("paring.service", "paring service created.");
	}

	protected void createNotification() {

		// Prepare intent which is triggered if the
		// notification is selected
		Intent intent = new Intent(this, RateAndCommentActivity.class);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		Bitmap logoIcon = Tools.getBitmapFromAsset(this, "logo.png");

		// Build notification

		NotificationCompat.Builder noti = new NotificationCompat.Builder(this)
				.setContentTitle("You Got a Paired Photo! ")
				.setContentText("FaceMe app")
				.setSmallIcon(R.drawable.logo_launcher)
				.setContentIntent(pIntent)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.addAction(R.drawable.ic_launcher, "Action Button", pIntent);

		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// hide the notification after its selected
		notificationManager.notify(0, noti.build());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		threadDisable = true;
		Log.d("paring.service", "paring service shutdown.");
	}
}
