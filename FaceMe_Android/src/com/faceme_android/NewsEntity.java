package com.faceme_android;

import java.util.List;

import android.graphics.Bitmap;

import com.example.faceme_android.*;

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
	
//	public String getUserNames(){
//		return userfaces.get(0).getUserID();
//	}
	
}
