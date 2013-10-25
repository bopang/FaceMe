package com.example.faceme_android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CharacterSelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charaterselection);
		Intent intent= getIntent();
		String postertitle=intent.getStringExtra("title");
		TextView posterTitle=(TextView) findViewById(R.id.received_postertitle);
		posterTitle.setText(postertitle);
		Bitmap btm=intent.getParcelableExtra("posterPic");
		ImageView posterImg=(ImageView) findViewById(R.id.received_posterPic);
		posterImg.setImageBitmap(btm);
	}

}
