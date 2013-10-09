package com.gatech.faceme.entity;

public class User {
	private String userID;
	private String password;
	private String faceKey;
	private String gender;
	private String school;
	
	public User(String userID, String password, 
			String faceKey, String gender,String school){
		this.userID = userID;
		this.password = password;
		this.faceKey = faceKey;
		this.gender = gender;
		this.school = school;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFaceKey() {
		return faceKey;
	}

	public void setFaceKey(String faceKey) {
		this.faceKey = faceKey;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

}
