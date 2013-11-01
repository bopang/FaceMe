package com.gatech.faceme.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

/**
 * Defines an entity representing the original poster This class is used as both
 * the definition of original poster in the datastore, as well as the format of
 * the original poster sent over the wire.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class OriginalPoster {
	
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private BlobKey blob;

	@Persistent
	private String moviename;

	@Persistent
	private String classification;

	@Persistent
	private User owner;
	
	@Persistent
	private Date creation;

	@PrimaryKey
	@Persistent
	private String filename;

	@Persistent
	private long size;

	@Persistent
	private String contentType;

	@Persistent
	private String title;

	@Persistent
	private String description;

	@Persistent
	private boolean isPublic;

	private static final List<String> IMAGE_TYPES = Arrays.asList("image/png",
			"image/jpeg", "image/tiff", "image/gif", "image/bmp");

	public OriginalPoster(User owner, BlobKey blob, String moviename,
			String classification, Date creationTime, String contentType,
			String filename, long size, String title, String description,
			boolean isPublic) {

		this.owner = owner;
		this.blob = blob;
		this.moviename = moviename;
		this.classification = classification;
		this.creation = creationTime;
		this.contentType = contentType;
		this.filename = filename;
		this.size = size;
		this.title = title;
		this.description = description;
		this.isPublic = isPublic;

	}


	public User getOwner() {
		return owner;
	}

	public Key getKey() {
		return key;
	}

	public String moviename() {
		return moviename;
	}
	
	public String classification() {
		return classification;
	}
	
	public Date getCreationTime() {
		return creation;
	}

	public boolean isPublic() {
		return isPublic;
	}

	public String getDescription() {
		return description;
	}

	public String getTitle() {
		return title;
	}

	public String getFilename() {
		return filename;
	}

	public long getSize() {
		return size;
	}

	public String getContentType() {
		if (contentType == null) {
			return "text/plain";
		}
		return contentType;
	}

	public String getURLPath() {
		String key = blob.getKeyString();
		return "/resource?key=" + key;
	}

	public String getDisplayURL() {
		String key = blob.getKeyString();
		return "/display?key=" + key;
	}

	public boolean isImage() {
		return IMAGE_TYPES.contains(getContentType());
	}
}
