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
	private ArrayList<String> userFaces;

	public PairTableEntity(Key key, String activeUser,
			ArrayList<String> otherUsers, ArrayList<String> userFaces) {
		super();
		this.key = key;
		this.activeUser = activeUser;
		this.otherUsers = otherUsers;
		this.userFaces = userFaces;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public String getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(String activeUser) {
		this.activeUser = activeUser;
	}

	public ArrayList<String> getOtherUsers() {
		return otherUsers;
	}

	public void setOtherUsers(ArrayList<String> otherUsers) {
		this.otherUsers = otherUsers;
	}

	public ArrayList<String> getUserFaces() {
		return userFaces;
	}

	public void setUserFaces(ArrayList<String> userFaces) {
		this.userFaces = userFaces;
	}
	
	

}
