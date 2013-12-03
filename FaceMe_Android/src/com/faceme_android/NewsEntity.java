package com.faceme_android;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

import com.example.faceme_android.ApplicationData;

public class NewsEntity {
	public String posterKey;
	public String originalPosterImageKey;
	public String nonfacePosterImageKey;
	public String movieName;
	public String posterName;
	public String updateDate;
	
	public List<Long> userfaces;
	public List<Long> characters;
	
	public Bitmap originalPosterBmp;
	public Bitmap nonfacePosterBmp;
	
	public Bitmap cosplayBmp;
	public Bitmap newsBmp;
	
	
	public NewsEntity(String posterKey, String originalPosterImageKey,
			String nonfacePosterImageKey, String movieName, String posterName,
			String updateDate, List<Long> userfaces,
			List<Long> characters) {
		super();
		this.posterKey = posterKey;
		this.originalPosterImageKey = originalPosterImageKey;
		this.nonfacePosterImageKey = nonfacePosterImageKey;
		this.movieName = movieName;
		this.posterName = posterName;
		this.updateDate = updateDate;
		this.userfaces = userfaces;
		this.characters = characters;
	}
	
	public List<Long> getAvaliableFaces(ApplicationData data){
		List<Long> result = new ArrayList<Long>();
		for(Long face : characters){
			result.add(face);
		}
		for(Long userface : userfaces){
			long key = data.mCharacterFaceCache.get(data.mUserFaceCache.get(userface).getCharacterKey()).getKey();
			result.remove(new Long(key));
		}
		return result;
	}
	
//	public String getUserNames(){
//		return userfaces.get(0).getUserID();
//	}
	
}
