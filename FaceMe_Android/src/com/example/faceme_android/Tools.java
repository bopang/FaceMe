package com.example.faceme_android;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



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

}
