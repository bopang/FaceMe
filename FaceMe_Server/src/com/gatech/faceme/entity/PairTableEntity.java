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
	private String activeUser;
	
	@Persistent
	private ArrayList<String> otherUsers;
	
	@Persistent
	private String activeUserFace;
	
	@Persistent
	private ArrayList<String> otherUserFace;
	
	
	@Persistent
	private boolean ifNotified;
	
	public PairTableEntity(String activeUser, ArrayList<String> otherUsers,
			String activeUserFace, ArrayList<String> otherUserFace,
				boolean ifNotified){
		this.activeUser = activeUser;
		this.setotherUsers(otherUsers);
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

	public ArrayList<String> getotherUsers() {
		return otherUsers;
	}

	public void setotherUsers(ArrayList<String> otherUsers) {
		this.otherUsers = otherUsers;
	}

	public String getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}
	public boolean getIfNotified() {
		return ifNotified;
	}

	public void setIfNotified(boolean ifNotified) {
		this.ifNotified = ifNotified;
	}

	public String getActiveUserFace() {
		return activeUserFace;
	}

	public void setActiveUserFace(String activeUserFace) {
		this.activeUserFace = activeUserFace;
	}

	public ArrayList<String> getOtherUserFace() {
		return otherUserFace;
	}

	public void setOtherUserFace(ArrayList<String> otherUserFace) {
		this.otherUserFace = otherUserFace;
	}
	
}
