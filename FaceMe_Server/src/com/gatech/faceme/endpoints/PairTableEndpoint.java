package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.User;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "pairtableendpoint", description = 
		"This entity represents a pair table.", version = "v1")
public class PairTableEndpoint {
	
	@ApiMethod(httpMethod = "GET", name = "pairtable.list", path = "pairtable/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<PairTableEndpoint> listPairTable() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<PairTableEndpoint> result = new ArrayList<PairTableEndpoint>();
		try {
			Query query = mgr.newQuery(User.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((PairTableEndpoint) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}
	
	@ApiMethod(httpMethod = "GET", name = "pairtable.get", path = "pairtable/get/{id}")
	@SuppressWarnings({ "cast", "unchecked" })
	public PairTableEndpoint getUserByID(@Named("id") String id) {
		PersistenceManager mgr = getPersistenceManager();
		PairTableEndpoint pairtable = null;
		try {
			pairtable = mgr.getObjectById(PairTableEndpoint.class, id);
		} finally {
			mgr.close();
		}
		return pairtable;
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
}
