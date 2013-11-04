package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.CharacterFaceEntity;
import com.gatech.faceme.entity.PairTableEntity;
import com.gatech.faceme.entity.User;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "characterfaceendpoint", description = 
			"This entity represents a character face.", version = "v1")	
public class CharacterFaceEndpoint {
	@ApiMethod(httpMethod = "GET", name = "characterface.list", path = "characterface/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<CharacterFaceEntity> listCharacterFace() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<CharacterFaceEntity> result = new ArrayList<CharacterFaceEntity>();
		try {
			Query query = mgr.newQuery(CharacterFaceEntity.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((CharacterFaceEntity) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}
	
	@ApiMethod(httpMethod = "GET", name = "characterface.get", path = "characterface/get/{id}")
	@SuppressWarnings({ "cast", "unchecked" })
	public CharacterFaceEntity getCharacterFaceByID(@Named("id") long id) {
		PersistenceManager mgr = getPersistenceManager();
		CharacterFaceEntity characterface = null;
		try {
			characterface = mgr.getObjectById(CharacterFaceEntity.class, id);
	
		} finally {
			mgr.close();
		}
		return characterface;
	}
	
	@ApiMethod(httpMethod = "GET", name = "characterfaceInPoster.get", path = "characterfaceinposter/get/{id}")
	@SuppressWarnings({ "cast", "unchecked" })
	public ArrayList<CharacterFaceEntity> getCharacterFaceInPoster(@Named("id") long posterid) {
		PersistenceManager mgr = getPersistenceManager();
		ArrayList<CharacterFaceEntity> characterface = null;
		try {
			Query query = mgr.newQuery(PairTableEntity.class, 
					"posterID ==posterid");
			for (Object obj : (List<Object>) query.execute()) {
				characterface.add(((CharacterFaceEntity) obj));
			}
	
		} finally {
			mgr.close();
		}
		return characterface;
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}


}
