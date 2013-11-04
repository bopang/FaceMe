package com.example.faceme_android;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;

public class ImageMergeTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_merge_test);
		
		Bitmap poster = Tools.getBitmapFromAsset(this,"iron_man_3_noFace.png");
		Bitmap face = Tools.getBitmapFromAsset(this, "face3.png");
		
		Bitmap result = Bitmap.createBitmap(poster.getWidth(), poster.getHeight(), Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(result);
	    
		canvas.drawBitmap(poster, 0, 0, null);
		canvas.drawBitmap(face, 0.2875f * poster.getWidth(), 0.1226f * poster.getHeight(), null);
		
		ImageView imview = (ImageView) findViewById(R.id.mergeTest);
		
		
		imview.setImageBitmap(result);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_merge_test, menu);
		
		
	    
		
		
		return true;
	}

}
