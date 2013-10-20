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
 * Defines an entity representing the poster on which all characters' faces have
 * been removed This class is used as both the definition of a non-face poster
 * in the datastore, as well as the format of the non-face poster sent over the
 * wire.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class NonFacePoster {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private BlobKey blob;

	@Persistent
	private User owner;

	/**
	 * Key of the original poster this non-face poster belongs to
	 */
	@Persistent
	private Key originalposterkey;

	/**
	 * Key of the original character faces
	 */
	@Persistent
	private ArrayList<Key> originalfacekey;

	@Persistent
	private Date creation;

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

	public NonFacePoster(User owner, BlobKey blob, Key originalposterkey,
			ArrayList<Key> originalfacekey, Date creationTime,
			String contentType, String filename, long size, String title,
			String description, boolean isPublic) {

		this.owner = owner;
		this.blob = blob;
		this.originalposterkey = originalposterkey;
		this.originalfacekey = originalfacekey;
		this.creation = creationTime;
		this.contentType = contentType;
		this.filename = filename;
		this.size = size;
		this.title = title;
		this.description = description;
		this.isPublic = isPublic;

	}

	public Key getOriginalposterkey() {
		return originalposterkey;
	}

	public ArrayList<Key> getOriginalfacekey() {
		return originalfacekey;
	}

	public User getOwner() {
		return owner;
	}

	public Key getKey() {
		return key;
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
