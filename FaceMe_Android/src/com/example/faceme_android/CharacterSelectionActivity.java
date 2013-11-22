package com.example.faceme_android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CharacterSelectionActivity extends Activity {
	ApplicationData state;
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.charaterselection);
		//Intent intent= getIntent();
		//String postertitle=intent.getStringExtra("title");
		TextView posterTitle=(TextView) findViewById(R.id.received_postertitle);

		context = this;
		state =(ApplicationData) getApplicationContext();
		posterTitle.setText(state.currentPoster.getPosterName());
		loadPosterData(state.currentPoster);
		
	}
	
	public void setImageViews(){
		
		ImageView posterImg=(ImageView) findViewById(R.id.received_posterPic);
		posterImg.setImageBitmap(state.currentPoster.originalPoster);
	
		ViewGroup characterLayout = (ViewGroup) findViewById(R.id.characterSelectionLayout);
		
		for(int i=0; i<state.currentPoster.faces.size(); i++){
			ImageButton characterButton = new ImageButton(this);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(120, 120);
			lp.setMargins(30, 0, 30, 0);
			
			characterButton.setLayoutParams(lp);
			characterButton.setTag(state.currentPoster.faces.get(i));
			Bitmap faceBmp = state.currentPoster.faces.get(i).bmp;
			Bitmap bmp = Bitmap.createScaledBitmap(faceBmp, 120*faceBmp.getWidth()/faceBmp.getHeight(), 120, false);
			characterButton.setImageBitmap(bmp);
			characterButton.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						//startActivity(new Intent(getBaseContext(),PosterSelectionActivity.class));
						
						state.faceChosed = (CharacterFaceEntity) v.getTag();
						startActivity(new Intent(getBaseContext(),CameraActivity.class));
					}
				});
			
			characterLayout.addView(characterButton);
		}
	}
	
	public void loadPosterData(PosterEntity poster){
		AsyncTask<PosterEntity, Void, Void> loadPosterImageAndFaceCharacter = new AsyncTask<PosterEntity, Void, Void>()
				{
					ProgressDialog dialog;
					@Override
					protected Void doInBackground(PosterEntity... params) {
						// TODO Auto-generated method stub
						PosterEntity poster = params[0];
						
						poster.nonfacePoster = getImageBitmap("https://facemegatech.appspot.com/imageResource?key=" + poster.getNonfacePosterKey());
						poster.originalPoster = getImageBitmap("https://facemegatech.appspot.com/imageResource?key=" + poster.getOriginalPosterKey());


						HttpClient client=new DefaultHttpClient();
						HttpGet httpGet=new HttpGet("https://facemegatech.appspot.com/_ah/api/characterfaceendpoint/v1/characterfaceinposter/get/"+poster.getKey());
						try {
							
							HttpResponse response=client.execute(httpGet);
							StatusLine statusline=response.getStatusLine();
							int statusCode=statusline.getStatusCode();
							if(statusCode!=200){
								return null;
							}
							InputStream jsonStream =response.getEntity().getContent();
							BufferedReader reader=new BufferedReader(new InputStreamReader(jsonStream));
							StringBuilder builder=new StringBuilder();
							String line;
							while((line=reader.readLine())!=null){
								
								builder.append(line);
							}
							String jsonData=builder.toString();
							Log.i("JsonData", jsonData);
							JSONObject json= new JSONObject(jsonData);
							ArrayList<CharacterFaceEntity> faces = new ArrayList<CharacterFaceEntity>();
							
							JSONArray  items=json.getJSONArray("items");
							
							
							for(int i=0; i<items.length();i++){
								JSONObject item=items.getJSONObject(i);
								 long id = item.getJSONObject("key").getLong("id");
								 String imageKey=item.getString("imageKey");
								 String name = item.getString("name");
								 float positionX = (float) item.getDouble("positionX");
								 float positionY =  (float) item.getDouble("postionY")	;
								 float width =  (float) item.getDouble("width");
								 float height =  (float) item.getDouble("height");
								 int index = item.getInt("index");
								 CharacterFaceEntity face = new CharacterFaceEntity(id, imageKey, name,
										 positionX, positionY, width, height,
											poster.getKey(), index);
								 face.bmp = getImageBitmap("https://facemegatech.appspot.com/imageResource?key=" + imageKey);

								 faces.add(face);

							}
		
							poster.faces = faces;							
							
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
						return null;
					}
					@Override
					protected void onPostExecute(Void result) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						setImageViews();
						super.onPostExecute(result);
						super.onPostExecute(result);
					}

					@Override
					protected void onPreExecute() {
						// TODO Auto-generated method stub
						dialog=new ProgressDialog(context);
						dialog.setTitle("loading");
						dialog.show();
						super.onPreExecute();
						super.onPreExecute();
					}
					
				};
				
				loadPosterImageAndFaceCharacter.execute(poster);
	}
	public Bitmap getImageBitmap(String url) { 
        Bitmap bm = null; 
        try { 
            URL aURL = new URL(url); 
            URLConnection conn = aURL.openConnection(); 
            conn.connect(); 
            InputStream is = conn.getInputStream(); 
            BufferedInputStream bis = new BufferedInputStream(is); 
            bm = BitmapFactory.decodeStream(bis); 
            bis.close(); 
            is.close(); 
       } catch (IOException e) { 
    	   e.printStackTrace();
       } 
       return bm; 
    }
}
