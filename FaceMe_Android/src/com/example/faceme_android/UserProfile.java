package com.example.faceme_android;

import android.graphics.Bitmap;

public class UserProfile {
public String username;
public String gender;
public String school;
public String profilePicUrl;
public Bitmap faceBmp;

public String getProfilePicUrl() {
	return profilePicUrl;
}

public void setProfilePicUrl(String profilePicUrl) {
	this.profilePicUrl = profilePicUrl;
}

public UserProfile(String name, String Gender, String School,String ProfileUrl){
	username=name;
	gender=Gender;
	school=School;
	profilePicUrl=ProfileUrl;
}

public UserProfile(String name, String Gender, String School,String ProfileUrl, Bitmap bmp){
	username=name;
	gender=Gender;
	school=School;
	profilePicUrl=ProfileUrl;
	faceBmp = bmp;
}

public UserProfile(){
	username="";
	gender="";
	school="";
	profilePicUrl="";
}

public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
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

public Bitmap getFaceBmp(){
	return faceBmp;
}

public void setSchool(String school) {
	this.school = school;
}



}
