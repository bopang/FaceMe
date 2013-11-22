package com.example.faceme_android;

import android.graphics.Bitmap;


public class CharacterFaceEntity {
	private long key;
	
	private String imageKey;
	
	private String name;
	
	//image position
	private float positionX; //upper left point

	private float postionY;

	private float width; //image width and height

	private float height;

	private long posterID;

	private int index;

	public Bitmap bmp;
	
	public CharacterFaceEntity(long id, String imageKey, String name,
			float positionX, float postionY,float width, float height,
			long posterID, int index){
		this.key = id;
		this.imageKey = imageKey;
		this.name = name;
		this.positionX = positionX;
		this.postionY = postionY;
		this.width = width;
		this.height = height;
		this.posterID = posterID;
		this.index = index;
	}
	public String getImageKey() {
		return imageKey;
	}
	public void setImageKey(String imageKey) {
		this.imageKey = imageKey;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPositionX() {
		return positionX;
	}
	public void setPositionX(float positionX) {
		this.positionX = positionX;
	}
	public float getPostionY() {
		return postionY;
	}
	public void setPostionY(float postionY) {
		this.postionY = postionY;
	}
	public long getPostID() {
		return posterID;
	}
	public void setPostID(long postID) {
		this.posterID = postID;
	}
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public long getKey() {
		return key;
	}
}
