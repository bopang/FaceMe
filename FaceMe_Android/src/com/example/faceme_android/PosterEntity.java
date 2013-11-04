package com.example.faceme_android;

import android.graphics.Bitmap;



public class PosterEntity {

	private long key;
	private String originalPosterKey;
	private String thumbnailKey;
	private String nonfacePosterKey;
	private String movieName;
	private String classification;
	private String posterName;
	
	
	public Bitmap thumbnail;
	public Bitmap originalPoster;
	public Bitmap nonfacePoster;

	public PosterEntity(long key, String originalPosterKey,
			String thumbnailKey, String nonfacePosterKey, String movieName,
			String classification, String posterName) {
		super();
		this.key = key;
		this.originalPosterKey = originalPosterKey;
		this.thumbnailKey = thumbnailKey;
		this.nonfacePosterKey = nonfacePosterKey;
		this.movieName = movieName;
		this.classification = classification;
		this.posterName = posterName;
	}

	public long getKey() {
		return key;
	}


	public String getOriginalPosterKey() {
		return originalPosterKey;
	}


	public String getThumbnailKey() {
		return thumbnailKey;
	}


	public String getNonfacePosterKey() {
		return nonfacePosterKey;
	}


	public String getMovieName() {
		return movieName;
	}


	public String getClassification() {
		return classification;
	}

	public String getPosterName() {
		return posterName;
	}
}
