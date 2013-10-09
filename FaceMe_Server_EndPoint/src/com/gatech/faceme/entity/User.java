package com.gatech.faceme.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

//curl -H 'Content-Type: application/json' -d '{ "userID": "Brandon", "password": "111111" }' http://localhost:8888/_ah/api/userendpoint/v1/user/add

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class User {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String userID;
	@Persistent
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

	public User() {
		// TODO Auto-generated constructor stub
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
