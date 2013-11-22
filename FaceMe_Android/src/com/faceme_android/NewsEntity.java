package com.faceme_android;

import java.util.List;

import com.example.faceme_android.*;

public class NewsEntity {
	public String posterKey;
	public String originalPosterImageKey;
	public String nonfacePosterImageKey;
	public String movieName;
	public String posterName;
	public String updateDate;
	
	public List<UserFaceEntity> userfaces;
	public List<CharacterFaceEntity> characters;
	
	
	
	public NewsEntity(String posterKey, String originalPosterImageKey,
			String nonfacePosterImageKey, String movieName, String posterName,
			String updateDate, List<UserFaceEntity> userfaces,
			List<CharacterFaceEntity> characters) {
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
	
	public String getUserNames(){
		return userfaces.get(0).getUserID();
	}
	
}
