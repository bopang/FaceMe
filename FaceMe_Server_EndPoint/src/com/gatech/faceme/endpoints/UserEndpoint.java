package com.gatech.faceme.endpoints;

import java.util.*;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.demos.mediastore.*;

@Api(
	     name = "userendpoint",
	     description = "This entity represents a user.", 
	     version = "v1")

public class UserEndpoint {

	@ApiMethod(
		      httpMethod = "GET",
		      name = "user.list", 
		      path = "user/list")
    @SuppressWarnings({ "cast", "unchecked" })
	public ArrayList<User> listUser(){
		User a = new User();
		a.setUserID("A");
		User b = new User();
		b.setUserID("B");
		ArrayList<User> users = new ArrayList<User>();
		users.add(a);
		users.add(b);
		return users;
	}
	
	@ApiMethod(
		      httpMethod = "GET",
		      name = "image.list", 
		      path = "image/list")
  @SuppressWarnings({ "cast", "unchecked" })
	public List<MediaObject> listImage(){
		PersistenceManager mgr = PMF.get().getPersistenceManager();
	    List<MediaObject> result = new ArrayList<MediaObject>();
	    try{
	      Query query = mgr.newQuery(MediaObject.class);
	      for (Object obj : (List<Object>) query.execute()) {
	        result.add(((MediaObject) obj));
	      }
	    } finally {
	      mgr.close();
	    }
	    return result;
	}

	
	
	@ApiMethod(
		      httpMethod = "POST",
		      name = "user.add", 
		      path = "user/add/{id}")
	 public User insertUser(User user) throws Exception {
//	    PersistenceManager mgr = PMF.get().getPersistenceManager();
//	    try {
//	      mgr.makePersistent(user);
//	      addToSearchIndex(user);
//	    } finally {
//	      mgr.close();
//	    }
	    return user;
	  }
	
	
	
}
