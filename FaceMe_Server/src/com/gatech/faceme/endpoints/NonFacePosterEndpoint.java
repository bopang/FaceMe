package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.NonFacePoster;
import com.gatech.faceme.entity.User;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "nonfaceposterendpoint", description = "This entity represents "
		+ "a poster with no face.", version = "v1")
public class NonFacePosterEndpoint {
	@ApiMethod(httpMethod = "GET", name = "nonfaceposter.list", path = "nonfaceposter/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<NonFacePoster> listNonFacePoster() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<NonFacePoster> result = new ArrayList<NonFacePoster>();
		try {
			Query query = mgr.newQuery(NonFacePoster.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((NonFacePoster) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}

	@ApiMethod(httpMethod = "GET", name = "nonfaceposter.get", path = "nonfaceposter/get/{id}")
	@SuppressWarnings({ "cast", "unchecked" })
	public NonFacePoster getNonFacePosterByID(@Named("id") long id) {
		PersistenceManager mgr = getPersistenceManager();
		NonFacePoster nonfaceposter = null;
		try {
			nonfaceposter = mgr.getObjectById(NonFacePoster.class, id);
		} finally {
			mgr.close();
		}
		return nonfaceposter;
	}

	@ApiMethod(httpMethod = "GET", name = "image.list", path = "image/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<NonFacePoster> listImage() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<NonFacePoster> result = new ArrayList<NonFacePoster>();
		try {
			Query query = mgr.newQuery(NonFacePoster.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((NonFacePoster) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}

	@ApiMethod(httpMethod = "POST", name = "nonfaceposter.insert", path = "nonfaceposter/insert")
	//curl -H 'Content-Type: application/json' -d '{ "userID": "Brandon","password": "111111" }' http://localhost:8888/_ah/api/userendpoint/v1/user/insert
	public NonFacePoster insertNonFacePoster(NonFacePoster nonfaceposter) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(nonfaceposter);
		pm.close();
		return nonfaceposter;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
