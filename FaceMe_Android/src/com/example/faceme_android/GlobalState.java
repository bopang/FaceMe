package com.example.faceme_android;

import java.util.List;

import android.app.Application;

public class GlobalState extends Application{
	public PosterEntity currentPoster;
	public List<PosterEntity> loadedPoster;
	public UserProfile currentUser;
	public DataManager dm;
	public CharacterFace faceChosed;
	
	public void initDataManager(){
		dm = new DataManager(this);
	}
}
