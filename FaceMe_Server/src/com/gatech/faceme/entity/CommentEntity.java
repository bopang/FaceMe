package com.gatech.faceme.entity;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class CommentEntity {
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String comment;
	
	@Persistent
	private String pairTable;
	
	@Persistent
	private String user;
	
	@Persistent
	private String toComment;
	
	public CommentEntity(String comment, String pairTable, String user, String toComment){
		this.comment = comment;
		this.pairTable = pairTable;
		this.user = user;
		this.toComment = toComment;
	}
	
	public CommentEntity(String comment, String pairTable, String user){
		this.comment = comment;
		this.pairTable = pairTable;
		this.user = user;
		this.toComment = null;
	}
}
