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

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

public class DataManager {
	HttpClient client;
	HttpGet httpGet;
	HttpResponse response;
	StatusLine statusline;
	int statusCode;
	InputStream inputStream;
	BufferedReader reader;
	StringBuilder builder;
	
	ApplicationData state;
	
	public DataManager(ApplicationData state){
		this.state = state;
	}
	
	public void loadPosterData(PosterEntity poster){
		AsyncTask<PosterEntity, Void, Void> loadPosterImageAndFaceCharacter = new AsyncTask<PosterEntity, Void, Void>()
				{
					ProgressDialog dialog;
					@Override
					protected Void doInBackground(PosterEntity... params) {
						// TODO Auto-generated method stub
						PosterEntity poster = params[0];
						poster.originalPoster = getImageBitmap(poster.getOriginalPosterKey());
						poster.nonfacePoster = getImageBitmap(poster.getNonfacePosterKey());
						
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
								 
								 //String path=item.getString("urlpath");
								 //Bitmap bmp=getImageBitmap("https://facemegatech.appspot.com"+path);
								 //posters.add(new Poster(name, bmp));
							}
							
							
							poster.faces = faces;
							
							state.currentPoster = poster;
							
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
