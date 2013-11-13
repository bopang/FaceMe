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

public class CharacterSelectionActivityXiaofei extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charaterselection);
		//Intent intent= getIntent();
		//String postertitle=intent.getStringExtra("title");
		//TextView posterTitle=(TextView) findViewById(R.id.received_postertitle);
		//posterTitle.setText(postertitle);
		//Bitmap btm=intent.getParcelableExtra("posterPic");
		ImageView posterImg=(ImageView) findViewById(R.id.received_posterPic);
		posterImg.setImageBitmap(Tools.getBitmapFromAsset(getBaseContext(), "iron_man_3_FaceXF.jpg"));
	
		ViewGroup characterLayout = (ViewGroup) findViewById(R.id.characterSelectionLayout);
		
		for(int i=2; i<3; i++){
			ImageButton characterButton = new ImageButton(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, 120);
			lp.setMargins(30, 0, 30, 0);
			
			characterButton.setLayoutParams(lp);
			Bitmap bmp = Tools.getBitmapFromAsset(getBaseContext(), "face"+i+".png");
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

}
