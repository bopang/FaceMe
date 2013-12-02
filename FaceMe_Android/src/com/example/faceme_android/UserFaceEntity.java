package com.example.faceme_android;

import android.graphics.Bitmap;

public class UserFaceEntity {
	public long id;
	private String imageKey;
	private String userID;
	private String posterKey;
	private long characterKey;
	
	public Bitmap userFaceBmp;
	
	
	
	public UserFaceEntity(String imageKey, String userID, String posterKey,
			long characterKey) {
		this.imageKey = imageKey;
		this.userID = userID;
		this.posterKey = posterKey;
		this.characterKey = characterKey;
	}
	
	
	public String getImageKey() {
		return imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getPosterKey() {
		return posterKey;
	}
	public void setPosterKey(String posterKey) {
		this.posterKey = posterKey;
	}
	public long getCharacterKey() {
		return characterKey;
	}
	public void setCharacterKey(long characterKey) {
		this.characterKey = characterKey;
	}
	
	
}
