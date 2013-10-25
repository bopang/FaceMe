package com.example.faceme_android;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PictureViewActivity extends Activity {

	public PictureViewActivity(){
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_view);
		ImageView imview = (ImageView) findViewById(R.id.imageView);
		imview.setImageBitmap(Tools.getBitmapFromPath(Environment.getExternalStorageDirectory().getPath() +"/CosplayTmp.png"));
		
		Button btn_share = (Button)findViewById(R.id.button_Share);
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
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_view, menu);
		return true;
	}

}
