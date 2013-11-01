package com.gatech.faceme.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;

public class ImageUploadData {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private String imageName;

	@Persistent
	private byte[] imageData;

	public ImageUploadData(String imageName, byte[] imageData) {
		super();
		this.imageName = imageName;
		this.imageData = imageData;
	}

}

