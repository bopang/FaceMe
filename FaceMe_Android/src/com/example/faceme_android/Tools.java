package com.example.faceme_android;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.faceme_android.NewsEntity;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;



public class Tools {

	public static Bitmap getBitmapFromAsset(Context context, String strName) {
	    AssetManager assetManager = context.getAssets();

	    InputStream istr;
	    Bitmap bitmap = null;
	    try {
	        istr = assetManager.open(strName);
	        bitmap = BitmapFactory.decodeStream(istr);
	    } catch (IOException e) {
	        return null;
	    }

	    return bitmap;
	}
	
	public static Bitmap getBitmapFromPath(String fileName){
	    Bitmap bitmap = null;
	    File tmpFile = new File(fileName);
			bitmap = BitmapFactory.decodeFile(tmpFile.getAbsolutePath());

	    return bitmap;
	}

	
	public static Bitmap getImageBitmapFromBlobKey (String blobkey) { 
		String url = "https://facemegatech.appspot.com/imageResource?key=" + blobkey;
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
	
	public static Bitmap synthesisPoster(NewsEntity news, ApplicationData data ){
		Bitmap nonfacePoster = news.nonfacePosterBmp;

		Bitmap result = Bitmap.createBitmap(nonfacePoster.getWidth(), nonfacePoster.getHeight(), Bitmap.Config.ARGB_8888);
		
		Canvas canvas = new Canvas(result);

		for(long faceKey : news.characters){
			CharacterFaceEntity face = data.mCharacterFaceCache.get(faceKey);
			canvas.drawBitmap(face.bmp, face.getPositionX() * nonfacePoster.getWidth(), face.getPostionY() * nonfacePoster.getHeight(), null);
		}
		
		for(long userFaceKey : news.userfaces){
			UserFaceEntity userFace = data.mUserFaceCache.get(userFaceKey);
			CharacterFaceEntity face = data.mCharacterFaceCache.get(userFace.getCharacterKey());
			Bitmap userFaceBmp = Bitmap.createScaledBitmap(userFace.userFaceBmp, (int)(nonfacePoster.getWidth() * face.getWidth()), (int)(nonfacePoster.getHeight() * face.getHeight()), false);
			canvas.drawBitmap(userFaceBmp, face.getPositionX() * nonfacePoster.getWidth(), face.getPostionY() * nonfacePoster.getHeight(), null);
		}

		canvas.drawBitmap(nonfacePoster, 0, 0, null);
		
		return result;
	}
}
