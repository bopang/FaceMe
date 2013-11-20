package com.example.faceme_android;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.faceme_android.MultiTabActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button btn_signin=(Button)findViewById(R.id.button_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getBaseContext(), MultiTabActivity.class));
				//createNotification();
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
