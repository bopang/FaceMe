package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.PairTableEntity;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Key;

@Api(name = "pairtableendpoint", description = 
		"This entity represents a pair table.", version = "v1")
public class PairTableEndpoint {
	
	@ApiMethod(httpMethod = "GET", name = "pairtable.list", path = "pairtable/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<PairTableEntity> listPairTable() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<PairTableEntity> result = new ArrayList<PairTableEntity>();
		try {
			Query query = mgr.newQuery(PairTableEntity.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((PairTableEntity) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}
	
	@ApiMethod(httpMethod = "GET", name = "pairtablenotification.get", 
			path = "pairtable/getallnotification/{userid}")
	@SuppressWarnings({ "cast", "unchecked" })
	public ArrayList<PairTableEntity> getNotNotifiedById(@Named("userid") String userID) {
		PersistenceManager mgr = getPersistenceManager();
		ArrayList<PairTableEntity> result = new ArrayList<PairTableEntity>();
		try {
//			Query query = mgr.newQuery("select from PairTableEntity " +
//										"where activeUser ==  mainuserid"+
//										"parameters String mainuserid "
//										);
			Query query = mgr.newQuery(PairTableEntity.class, 
					"(activeUser ==userID) && (ifNotified==false)");
		
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((PairTableEntity) obj));
			}
			
		} finally {
			mgr.close();
		}
		return result;
	}
	
	@ApiMethod(httpMethod = "GET", name = "pairtable.get", 
			path = "pairtable/getallpairtable/{userid}")
	@SuppressWarnings({ "cast", "unchecked" })
	public ArrayList<PairTableEntity> getallpairtableById(@Named("userid") String userID) {
		PersistenceManager mgr = getPersistenceManager();
		ArrayList<PairTableEntity> result = new ArrayList<PairTableEntity>();
		try {

			Query query = mgr.newQuery(PairTableEntity.class, 
					"activeUser ==userID");
			
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((PairTableEntity) obj));
			}
			
		} finally {
			mgr.close();
		}
		return result;
	}
	
	@ApiMethod(httpMethod = "GET", name = "twoparameters.get", 
			path = "pairtable/gettwoparameters/{userid}/{username}")
	@SuppressWarnings({ "cast", "unchecked" })
	public ArrayList<PairTableEntity> tryTwoParameters(
			@Named("userid") String userID, @Named("username") String userName) {
		PersistenceManager mgr = getPersistenceManager();
		ArrayList<PairTableEntity> result = new ArrayList<PairTableEntity>();
		try {

			Query query = mgr.newQuery(PairTableEntity.class, 
					"(activeUser ==userID) && (key ==userName)");
			
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((PairTableEntity) obj));
			}
			
		} finally {
			mgr.close();
		}
		return result;
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
}
