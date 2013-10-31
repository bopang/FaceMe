package com.example.faceme_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CharacterSelectionActivitySuperMan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charaterselection);
		//Intent intent= getIntent();
		//String postertitle=intent.getStringExtra("title");
		TextView posterTitle=(TextView) findViewById(R.id.received_postertitle);
		posterTitle.setText("Man of Steel");
		//Bitmap btm=intent.getParcelableExtra("posterPic");
		ImageView posterImg=(ImageView) findViewById(R.id.received_posterPic);
		posterImg.setImageBitmap(Tools.getBitmapFromAsset(getBaseContext(), "superman_Face.jpg"));
	
		ViewGroup characterLayout = (ViewGroup) findViewById(R.id.characterSelectionLayout);
		
		
			ImageButton characterButton = new ImageButton(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, 120);
			lp.setMargins(30, 0, 30, 0);
			
		characterButton.setLayoutParams(lp);
		Bitmap bmp = Tools.getBitmapFromAsset(getBaseContext(), "SupermanFace.png");
		characterButton.setImageBitmap(bmp);
		characterButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//startActivity(new Intent(getBaseContext(),PosterSelectionActivity.class));
						startActivity(new Intent(getBaseContext(),CameraActivitySuperMan.class));
					}
				});
			
		characterLayout.addView(characterButton);
		
	}

}
