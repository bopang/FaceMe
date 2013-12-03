package com.gatech.faceme.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UserFaceEntity {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String imageKey;
	
	@Persistent
	private String userID;
	
	@Persistent
	private String posterKey;
	
	@Persistent
	private String characterKey;
	
	@Persistent
	private int rating;
	
	@Persistent
	private int ratingTimes;

	public UserFaceEntity(String imageKey, String userID,
			String posterKey, String characterKey, int rating, int ratingTimes) {
		super();
		this.imageKey = imageKey;
		this.userID = userID;
		this.posterKey = posterKey;
		this.characterKey = characterKey;
		this.rating = rating;
		this.ratingTimes = ratingTimes;
	}

	public Key getKey() {
		return key;
	}

	public String getImageKey() {
		return imageKey;
	}

	public String getUserID() {
		return userID;
	}

	public String getPosterKey() {
		return posterKey;
	}

	public String getCharacterKey() {
		return characterKey;
	}
	
	public int getRating(){
		return rating;
	}
	public int getRatingTimes(){
		return ratingTimes;
	}
}
