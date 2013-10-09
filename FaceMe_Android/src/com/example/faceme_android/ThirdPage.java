package com.example.faceme_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ThirdPage extends Activity{

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.thirdpage);
	        
	        Button btn_play=(Button)findViewById(R.id.Button04);
	        
	        btn_play.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					startActivity(new Intent(getBaseContext(), CameraActivity.class));
				}
			});
	 }
	 
}
