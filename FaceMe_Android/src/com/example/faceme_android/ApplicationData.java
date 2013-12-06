package com.example.faceme_android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.faceme_android.NewsEntity;

public class ApplicationData extends Application{
	public static final String posterUrl="https://facemegatech.appspot.com/_ah/api/posterendpoint/v1/poster/list";
	//public static String feedUrl="https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/get/Ziyi%20Jiang";
	public static String feedUrl;
	public static String newsFeedUrl = "https://facemegatech.appspot.com/_ah/api/newsendpoint/v1/news/list";
	public PosterEntity currentPoster = null;
	
	
	public List<PosterEntity> mPosters = null;
	public List<NewsEntity> mNews = null;
	
	public List<UserProfile> mRankingData = null;
	
	
	public Map<Long,PosterEntity> mPosterCache = null;
	public Map<Long, UserFaceEntity> mUserFaceCache = null;
	public Map<Long, CharacterFaceEntity> mCharacterFaceCache = null;
	
	public UserProfile mCurrentUser = null;
	public CharacterFaceEntity faceChosed = null;
	
	public boolean playWithNews = false;
	public NewsEntity currentNews = null;
	public List<Long> avaliableFaces = null;
	
	@Override 
	public void onCreate(){
		mPosters = new ArrayList<PosterEntity>();
		mNews = new ArrayList<NewsEntity>();
		mRankingData = new ArrayList<UserProfile>();
		
		//ArrayList<UserFaceEntity> userface = new ArrayList<UserFaceEntity>();
		//userface.add(new UserFaceEntity("Ziyi","Ziyi","Ziyi","Ziyi"));
		
//		mNews.add(new NewsEntity("","","","","","2013-10-20",userface,null));
		
		
		mPosterCache = new HashMap<Long, PosterEntity>();
		mUserFaceCache = new HashMap<Long, UserFaceEntity>();
		mCharacterFaceCache = new HashMap<Long, CharacterFaceEntity>();
		
		//mRankingData.add(mcurrentPoster);
		
		
	}
	
	public void loadUserInfo(){
		this.feedUrl = "https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/get/Ziyi%20Jiang";
		new GetUserInfoTask().execute();
	}
	
	public void loadUserInfo(String feedUrl){
		this.feedUrl = feedUrl; 
		new GetUserInfoTask().execute();
	}
	
	public void loadPostersData(ArrayAdapter adapter){
		new GetPostersDataTask (adapter).execute();
	}
	public void loadNewsFeed(ArrayAdapter adapter){
		new GetNewsFeedTask (adapter).execute();
	}
	
	private Bitmap getImageBitmap(String url) { 
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
	
	
	public class GetUserInfoTask extends AsyncTask<Void, Void, Void>{
	    public GetUserInfoTask() {

		}
		@Override
		protected Void doInBackground(Void... params) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated method stub
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(feedUrl);
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
				
				String name = json.getString("userID");
				String gender = json.getString("gender");
				String school = json.getString("school");
				String picUrl=json.getString("faceKey");
				String password = json.getString("password");
				Bitmap bmp=getImageBitmap(picUrl);
				
				mCurrentUser = new UserProfile(name,gender,school,picUrl,bmp,password);
				System.out.println("CurrentUser: " + mCurrentUser);
				System.out.println("Current pwd:" + mCurrentUser.getPassword());
				Log.i("UserInfoLoaded","UserInfoLoaded");
				mRankingData.add(mCurrentUser);
				

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
	}
	
	public class GetPostersDataTask extends AsyncTask<Void, Void, Void>{
		ArrayAdapter mAdapter;
		public GetPostersDataTask(ArrayAdapter adapter) {
			mAdapter = adapter;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.i("Loading Completed", "Loading Completed");
			mAdapter.notifyDataSetChanged();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(posterUrl);
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
				JSONArray  items=json.getJSONArray("items");
				for(int i=0; i<items.length();i++){
					JSONObject item=items.getJSONObject(i);
					long key = item.getJSONObject("key").getLong("id");
					String movieName=item.getString("movieName");
					String posterName = item.getString("posterName");
					String classification = item.getString("classification");
					String thumbnailKey = item.getString("thumbnailKey")	;
					String originalPosterKey = item.getString("originalPosterKey");
					String nonfacePosterKey = item.getString("nonfacePosterKey");
					PosterEntity poster = new PosterEntity(key, originalPosterKey,thumbnailKey,nonfacePosterKey,movieName,
							classification, posterName);
					poster.thumbnail = getImageBitmap("https://facemegatech.appspot.com/imageResource?key=" + thumbnailKey);

					mPosters.add(poster);

				}
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
	}
	
	public class GetNewsFeedTask extends AsyncTask<Void, Void, Void>{
		ArrayAdapter mAdapter;
		public GetNewsFeedTask(ArrayAdapter adapter) {
			mAdapter = adapter;
		}
		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			Log.i("Loading Completed", "Loading Completed");
			mAdapter.notifyDataSetChanged();
		}
		
		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			mNews.clear();
			HttpClient client=new DefaultHttpClient();
			HttpGet httpGet=new HttpGet(newsFeedUrl);
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
				JSONArray  items=json.getJSONArray("items");
				
				for(int i=0; i<items.length();i++){
					JSONObject item=items.getJSONObject(i);
					
					
					String posterKey = item.getString("posterKey");
					String originalPosterImageKey = item.getString("originalPosterImageKey");
					String nonfacePosterImageKey = item.getString("nonfacePosterImageKey");
					String movieName = item.getString("movieName");
					String posterName = item.getString("posterName");
					String updateDate = "";
					
					List<Long> userfaces = new ArrayList<Long>();
					List<Long> characters = new ArrayList<Long>();
					
					JSONArray userfacesJson = item.getJSONArray("userfaces");
					
					for(int j=0; j<userfacesJson.length(); j++){
						JSONObject userfaceJson = userfacesJson.getJSONObject(j);
						long id = userfaceJson.getJSONObject("key").getLong("id");
						if(mUserFaceCache.containsKey(id)){
							userfaces.add(id);
							continue;
						}
						
						String imageKey = userfaceJson.getString("imageKey");
						String userName = userfaceJson.getString("userID");
						String posterID = userfaceJson.getString("posterKey");
						long characterID = userfaceJson.getLong("characterKey");
						
						UserFaceEntity userface = new UserFaceEntity(imageKey, userName, posterID, characterID);
						userface.userFaceBmp = Tools.getImageBitmapFromBlobKey(imageKey);
						
						mUserFaceCache.put(id, userface);
						userfaces.add(id);
						
					}
					
					JSONArray characterfacesJson = item.getJSONArray("characters");
					
					for(int j=0; j<characterfacesJson.length(); j++){
						JSONObject characterfaceJson = characterfacesJson.getJSONObject(j);
						 long id = characterfaceJson.getJSONObject("key").getLong("id");
						 if(mCharacterFaceCache.containsKey(id)){
							 characters.add(id);
							 continue;
						 }
						 
						 String imageKey=characterfaceJson.getString("imageKey");
						 String name = characterfaceJson.getString("name");
						 float positionX = (float) characterfaceJson.getDouble("positionX");
						 float positionY =  (float) characterfaceJson.getDouble("postionY")	;
						 float width =  (float) characterfaceJson.getDouble("width");
						 float height =  (float) characterfaceJson.getDouble("height");
						 int index = characterfaceJson.getInt("index");
						 CharacterFaceEntity characterface = new CharacterFaceEntity(id, imageKey, name,
								 positionX, positionY, width, height,
								 Long.parseLong(posterKey), index);
						 characterface.bmp = Tools.getImageBitmapFromBlobKey(imageKey);
						 
						 mCharacterFaceCache.put(id, characterface);
						 characters.add(id);
					}
					
					NewsEntity news = new NewsEntity(posterKey, originalPosterImageKey,
							nonfacePosterImageKey, movieName, posterName,
							updateDate, userfaces, characters);
					
					news.nonfacePosterBmp = Tools.getImageBitmapFromBlobKey(nonfacePosterImageKey);
					news.originalPosterBmp = Tools.getImageBitmapFromBlobKey(originalPosterImageKey);
					
					mNews.add(news);
				
				}
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
	}
	
}
