package com.example.faceme_android;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

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
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class PosterSelectionActivity extends Activity {
	Context context;
	String posterUrl="https://facemegatech.appspot.com/_ah/api/posterendpoint/v1/poster/list";
	List<Poster>posters=new ArrayList<Poster>();
	
public PosterSelectionActivity() {
	// TODO Auto-generated constructor stub
}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.posterselection);
		context=this;
		ListView posterList=(ListView) findViewById(R.id.posterlistView);
		ArrayAdapter<Poster>posteradapter=new posterAdapter(posters);
		posterList.setAdapter(posteradapter);
		GetJsonData getData=new GetJsonData(posteradapter);
		getData.execute();
		chosePoster();
		
	}
	public void chosePoster(){
		ListView posterList=(ListView) findViewById(R.id.posterlistView);
		posterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked, int postion,
					long id) {
				// TODO Auto-generated method stub
				Poster currentPoster=posters.get(postion);
				String posterTitle=currentPoster.getPosterTitle();
				Bitmap posterBtm=currentPoster.getPosterPic();
				Intent intent;
				if(postion ==0)
					intent =new Intent(PosterSelectionActivity.this, CharacterSelectionActivityXiaofei.class);
				else
					intent =new Intent(PosterSelectionActivity.this, CharacterSelectionActivitySuperMan.class);
				
				intent.putExtra("title", posterTitle);
			    //intent.putExtra("posterPic", posterBtm);
				startActivity(intent);
			}
		});
	}
public class posterAdapter extends ArrayAdapter<Poster>{
	
	public posterAdapter(List<Poster>posters){
		super(PosterSelectionActivity.this,R.layout.poster_item,posters);
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View templateView;
		templateView=convertView;
		if(templateView==null){
			templateView=getLayoutInflater().inflate(R.layout.poster_item, parent, false);
		}
		Poster currentPoster=posters.get(position);
		TextView title=(TextView)templateView.findViewById(R.id.textView_posterTitle);
		TextView description=(TextView)templateView.findViewById(R.id.textView_posterDescription);
		title.setText(currentPoster.getPosterTitle());
		description.setText(currentPoster.getPosterDescription());
		ImageView posterImg=(ImageView)templateView.findViewById(R.id.imageView_poster);
		posterImg.setImageBitmap(currentPoster.getPosterPic());
		return templateView;
	}
	
	
	
	
}
public class GetJsonData extends AsyncTask<Void, Void, Void>{
    ProgressDialog dialog;
    ArrayAdapter<Poster>posteradapter;
    public GetJsonData( ArrayAdapter<Poster>posteraAdapter) {
    	
		posteradapter=posteraAdapter;
    	// TODO Auto-generated constructor stub
	}
	@Override
	protected Void doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
				 String name=item.getString("title");
				 String path=item.getString("urlpath");
				 Bitmap bmp=getImageBitmap("https://facemegatech.appspot.com"+path);
				 posters.add(new Poster(name, bmp));
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

	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		   posteradapter.notifyDataSetChanged();
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
	
}
	

}
