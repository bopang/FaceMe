package com.gatech.faceme.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class NotificationEntity {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String pairKey;
	
	@Persistent
	private String userName;

	public NotificationEntity(Key key, String pairKey, String userName) {
		super();
		this.key = key;
		this.pairKey = pairKey;
		this.userName = userName;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getPairKey() {
		return pairKey;
	}

	public void setPairKey(String pairKey) {
		this.pairKey = pairKey;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	
	
}
