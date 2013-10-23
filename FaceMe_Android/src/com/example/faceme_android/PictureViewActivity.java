package com.example.faceme_android;

import java.io.File;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
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
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.picture_view, menu);
		return true;
	}

}
