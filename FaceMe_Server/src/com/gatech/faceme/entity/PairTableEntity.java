package com.gatech.faceme.entity;

import java.util.ArrayList;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class PairTableEntity {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private Key activeUser;
	
	@Persistent
	private ArrayList<Key> faceList;
	
	@Persistent
	private Key activeUserFace;
	
	@Persistent
	private ArrayList<Key> otherUserFace;
	
	
	@Persistent
	private boolean ifNotified;
	
	public PairTableEntity(Key activeUser, ArrayList<Key> faceList,
				Key activeUserFace, ArrayList<Key> otherUserFace,
				boolean ifNotified){
		this.activeUser = activeUser;
		this.setFaceList(faceList);
		this.setActiveUserFace(activeUserFace);
		this.setOtherUserFace(otherUserFace);
		this.ifNotified = ifNotified;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public ArrayList<Key> getFaceList() {
		return faceList;
	}

	public void setFaceList(ArrayList<Key> faceList) {
		this.faceList = faceList;
	}

	public Key getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(Key activeUser) {
		this.activeUser = activeUser;
	}
	public boolean getIfNotified() {
		return ifNotified;
	}

	public void setIfNotified(boolean ifNotified) {
		this.ifNotified = ifNotified;
	}

	public Key getActiveUserFace() {
		return activeUserFace;
	}

	public void setActiveUserFace(Key activeUserFace) {
		this.activeUserFace = activeUserFace;
	}

	public ArrayList<Key> getOtherUserFace() {
		return otherUserFace;
	}

	public void setOtherUserFace(ArrayList<Key> otherUserFace) {
		this.otherUserFace = otherUserFace;
	}
	
}
