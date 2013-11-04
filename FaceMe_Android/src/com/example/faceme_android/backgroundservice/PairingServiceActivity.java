package com.example.faceme_android.backgroundservice;
import com.example.faceme_android.*;
import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;

public class PairingServiceActivity extends Activity {

	private IPairingService paringService;
	
	private ServiceConnection serviceConnection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {		
			paringService = (IPairingService) service;
			Log.d("paring.service", "starting service...");
			paringService.start();	
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {		
			paringService = null;
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pairingservice);
		
		this.bindService(new Intent("com.example.faceme_android.backgroundservice.PairingService"),
				this.serviceConnection, BIND_AUTO_CREATE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
