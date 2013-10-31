package com.example.faceme_android;

import android.graphics.Bitmap;

public class UserComment {
	public String name;
	public String commentText;
	public Bitmap bmp;
	
	
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getComments() {
		return commentText;
	}


	public void setComments(String comments) {
		commentText = comments;
	}


	public Bitmap getBmp() {
		return bmp;
	}


	public void setBmp(Bitmap bmp) {
		this.bmp = bmp;
	}


	public UserComment(String name, String comments, Bitmap bmp) {
		super();
		this.name = name;
		commentText = comments;
		this.bmp = bmp;
	}
	
	
	
}
