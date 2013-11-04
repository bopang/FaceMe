package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.ImageUploadURL;
import com.gatech.faceme.entity.OriginalPoster;
import com.gatech.faceme.entity.User;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

@Api(name = "originalposterendpoint", description = "This entity represents an original poster.", version = "v1")
public class OriginalPosterEndpoint {

	private BlobstoreService blobstoreService = 
		    BlobstoreServiceFactory.getBlobstoreService();

	@ApiMethod(httpMethod = "GET", name = "originalposter.list", path = "originalposter/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<OriginalPoster> listOriginalPoster() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<OriginalPoster> result = new ArrayList<OriginalPoster>();
		try {
			Query query = mgr.newQuery(OriginalPoster.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((OriginalPoster) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}

	@ApiMethod(httpMethod = "GET", name = "originalposter.get", path = "originalposter/get/{id}")
	@SuppressWarnings({ "cast", "unchecked" })
	public OriginalPoster getOriginalPosterByID(@Named("id") long key) {
		PersistenceManager mgr = getPersistenceManager();
		OriginalPoster originalposter = null;
		try {
			originalposter = mgr.getObjectById(OriginalPoster.class, key);
		} finally {
			mgr.close();
		}
		return originalposter;
	}
	@ApiMethod(httpMethod = "GET", name = "originalposter.url", path = "originalposter/url")
	@SuppressWarnings({ "cast", "unchecked" })
	public ImageUploadURL getUploadUrl() {
		return new ImageUploadURL(blobstoreService.createUploadUrl("/uploadImage"));
	}

	@ApiMethod(httpMethod = "POST", name = "originalposter.insert", path = "originalposter/insert")
	// curl -H 'Content-Type: application/json' -d '{ "userID": "Brandon",
	// "password": "111111" }'
	// http://localhost:8888/_ah/api/userendpoint/v1/user/insert
	public OriginalPoster insertOriginalPoster(OriginalPoster originalPoster) {
		//PersistenceManager pm = getPersistenceManager();
		//pm.makePersistent(originalPoster);
		//pm.close();
		
				
		return originalPoster;
	}

	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}

}
