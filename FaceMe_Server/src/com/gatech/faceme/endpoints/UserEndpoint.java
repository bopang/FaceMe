package com.gatech.faceme.endpoints;

import java.util.*;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.User;
import com.gatech.faceme.mediastore.*;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.response.UnauthorizedException;

@Api(name = "userendpoint", description = "This entity represents a user.", version = "v1")
public class UserEndpoint {

	@ApiMethod(httpMethod = "GET", name = "user.list", path = "user/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<User> listUser() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<User> result = new ArrayList<User>();
		try {
			Query query = mgr.newQuery(User.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((User) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}

	@ApiMethod(httpMethod = "GET", name = "user.get", path = "user/get/{id}")
	@SuppressWarnings({ "cast", "unchecked" })
	public User getUserByID(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		User user = null;
		try {
			user = mgr.getObjectById(User.class, id);
		} finally {
			mgr.close();
		}
		return user;
	}

	@ApiMethod(httpMethod = "GET", name = "image.list", path = "image/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<User> listImage() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<User> result = new ArrayList<User>();
		try {
			Query query = mgr.newQuery(User.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((User) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}

	@ApiMethod(httpMethod = "POST", name = "user.insert", path = "user/insert")
	//curl -H 'Content-Type: application/json' -d '{ "userID": "Ziji Jiang","password": "111111","faceKey": "https://1.gravatar.com/avatar/e62128516f78f7f2c25e3b48d7e6e69b?d=https%3A%2F%2Fidenticons.github.com%2F6322625007f47a9f44efdc898e835a99.png&r=x&s=400","gender": "Male","school": "Gatech"}' https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/insert
	public User insertUser(User user) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(user);
		pm.close();
		return user;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
