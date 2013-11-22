package com.example.faceme_android;

public class UserFaceEntity {
	private String imageKey;
	private String userID;
	private String posterKey;
	private String characterKey;
	
	
	
	public UserFaceEntity(String imageKey, String userID, String posterKey,
			String characterKey) {
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
	public String getCharacterKey() {
		return characterKey;
	}
	public void setCharacterKey(String characterKey) {
		this.characterKey = characterKey;
	}
	
	
}
