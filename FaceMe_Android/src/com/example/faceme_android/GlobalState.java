package com.example.faceme_android;

import java.util.List;

import android.app.Application;

public class GlobalState extends Application{
	PosterEntity currentPoster;
	List<PosterEntity> loadedPOster;
}
