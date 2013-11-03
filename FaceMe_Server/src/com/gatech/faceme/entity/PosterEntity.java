package com.gatech.faceme.entity;

import java.util.Date;

import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

public class PosterEntity {

	@PrimaryKey
	private Key key;
	
	@Persistent
	private String originalPosterKey;
	
	@Persistent
	private String thumbnailKey;
	
	@Persistent 
	private String nonfacePosterKey;

	@Persistent
	private String movieName;

	@Persistent
	private String classification;
	
	@Persistent
	private Date creationDate;
	
	@Persistent
	private String posterName;

	
	public PosterEntity(Key key, String originalPosterKey, String thumbnailKey,
			String nonfacePosterKey, String movieName, String classification,
			Date creationDate, String posterName) {
		super();
		this.key = key;
		this.originalPosterKey = originalPosterKey;
		this.thumbnailKey = thumbnailKey;
		this.nonfacePosterKey = nonfacePosterKey;
		this.movieName = movieName;
		this.classification = classification;
		this.creationDate = creationDate;
		this.posterName = posterName;
	}


	public Key getKey() {
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


	public Date getCreationDate() {
		return creationDate;
	}


	public String getPosterName() {
		return posterName;
	}
	

	
	
}
