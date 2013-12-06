package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.endpoints.NewsEndpoint.News;
import com.gatech.faceme.entity.CharacterFaceEntity;
import com.gatech.faceme.entity.PairTableEntity;
import com.gatech.faceme.entity.PosterEntity;
import com.gatech.faceme.entity.User;
import com.gatech.faceme.entity.UserFaceEntity;
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
	
	@ApiMethod(httpMethod = "GET", name = "pairtable.id", path = "pairtable/list/{tableid}")
	@SuppressWarnings({ "cast", "unchecked" })
	public News idListPairTable(@Named("tableid") String tableID) {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		//News result;
		try {
			//Query query1 = mgr.newQuery(UserFaceEntity.class);
			Query query2 = mgr.newQuery(CharacterFaceEntity.class);
			//Query query3 =mgr.newQuery(PairTableEntity.class); 
			PairTableEntity pt = mgr.getObjectById(PairTableEntity.class, Long.parseLong(tableID));
			if(pt==null) return null;
			//for (PairTableEntity obj : (List<PairTableEntity>) query3.execute()) {
				if(pt.getUserFaces().size()==0) return null;
				UserFaceEntity userface = mgr.getObjectById(UserFaceEntity.class, 
						Long.parseLong(pt.getUserFaces().get(0)));
				
				String posterkey = userface.getPosterKey();
				PosterEntity posterEntity = mgr.getObjectById(PosterEntity.class, Long.parseLong(posterkey));
				Key posterKey = posterEntity.getKey();
				
				query2 = mgr.newQuery(CharacterFaceEntity.class);
				query2.setFilter("posterID == posterIDparam");
				query2.declareParameters(Key.class.getName() + " posterIDparam");
				
				ArrayList<UserFaceEntity> userfaces = new ArrayList<UserFaceEntity>();
				for (String uf: pt.getUserFaces()) {
					userfaces.add(mgr.getObjectById(UserFaceEntity.class, Long.parseLong(uf)));
				}
			
				ArrayList<CharacterFaceEntity> characters = new ArrayList<CharacterFaceEntity>();
				for (CharacterFaceEntity object : (List<CharacterFaceEntity>) query2.execute(posterKey)) {
					characters.add(object);
					
				}
				return new News(posterkey, posterEntity.getOriginalPosterKey(), posterEntity.getNonfacePosterKey(), posterEntity.getMovieName(),
						posterEntity.getPosterName(), userfaces, characters);
			//}
			
		} finally {
			//mgr.close();
		}
		
	}
	
	
	
//	@ApiMethod(httpMethod = "GET", name = "pairtablenotification.get", 
//			path = "pairtable/getallnotification/{userid}")
//	@SuppressWarnings({ "cast", "unchecked" })
//	public ArrayList<PairTableEntity> getNotNotifiedById(@Named("userid") String userID) {
//		PersistenceManager mgr = getPersistenceManager();
//		ArrayList<PairTableEntity> result = new ArrayList<PairTableEntity>();
//		try {
////			Query query = mgr.newQuery("select from PairTableEntity " +
////										"where activeUser ==  mainuserid"+
////										"parameters String mainuserid "
////										);
//			Query query = mgr.newQuery(PairTableEntity.class, 
//					"(activeUser ==userID) && (ifNotified==false)");
//		
//			for (Object obj : (List<Object>) query.execute()) {
//				result.add(((PairTableEntity) obj));
//				((PairTableEntity) obj).setIfNotified(true);
//			}
//			
//		} finally {
//			mgr.close();
//		}
//		return result;
//	}
//	
//	@ApiMethod(httpMethod = "GET", name = "pairtable.get", 
//			path = "pairtable/getallpairtable/{userid}")
//	@SuppressWarnings({ "cast", "unchecked" })
//	public ArrayList<PairTableEntity> getallpairtableById(@Named("userid") String userID) {
//		PersistenceManager mgr = getPersistenceManager();
//		ArrayList<PairTableEntity> result = new ArrayList<PairTableEntity>();
//		try {
//
//			Query query = mgr.newQuery(PairTableEntity.class, 
//					"activeUser ==userID");
//			
//			for (Object obj : (List<Object>) query.execute()) {
//				result.add(((PairTableEntity) obj));
//			}
//			
//		} finally {
//			mgr.close();
//		}
//		return result;
//	}
//	
//	@ApiMethod(httpMethod = "GET", name = "twoparameters.get", 
//			path = "pairtable/gettwoparameters/{userid}/{username}")
//	@SuppressWarnings({ "cast", "unchecked" })
//	public ArrayList<PairTableEntity> tryTwoParameters(
//			@Named("userid") String userID, @Named("username") String userName) {
//		PersistenceManager mgr = getPersistenceManager();
//		ArrayList<PairTableEntity> result = new ArrayList<PairTableEntity>();
//		try {
//			Query query = mgr.newQuery(PairTableEntity.class, 
//					"(activeUser ==userID) && (key ==userName)");
//			
//			for (Object obj : (List<Object>) query.execute()) {
//				result.add(((PairTableEntity) obj));
//			}
//			
//		} finally {
//			mgr.close();
//		}
//		return result;
//	}
	
	@ApiMethod(httpMethod = "POST", name = "paritable.insert",
			path = "pairtable/insert")
	//curl -H 'Content-Type: application/json' -d '{ "activeUser": activeUserkey,"otherUsers": [{userKey1},{userKey2}],
	//"activeUserFace": "activeUserFaceKey", "otherUserFace":[{keyFaceKey1},{keyFaceKey2}], "ifNotified": false}' 
	// http://localhost:8888/_ah/api/pairtableendpoint/v1/pairtable/insert
	public PairTableEntity addPairTable(PairTableEntity pairTable) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(pairTable);
		pm.close();
		return pairTable;
	}
	
	private static PersistenceManager getPersistenceManager() {
		return PMF.get().getPersistenceManager();
	}
	public class News{

		public String posterKey;
		public String originalPosterImageKey;
		public String nonfacePosterImageKey;
		public String movieName;
		public String posterName;
		
		// to be done
		public String updateDate;
		public List<UserFaceEntity> userfaces;
		public List<CharacterFaceEntity> characters;

		
		public News(String posterKey, String originalPosterImageKey, String nonfacePosterImageKey,
				String movieName, String posterName, List<UserFaceEntity> userfaces
				, List<CharacterFaceEntity> characters){
			this.setPosterKey(posterKey);
			this.setOriginalPosterImageKey(originalPosterImageKey);
			this.setNonfacePosterImageKey(nonfacePosterImageKey);
			this.setMovieName(movieName);
			this.setPosterName(posterName);
//			this.updateDate = updateDate;
			this.userfaces = userfaces;
			this.characters = characters;
		}


		public String getPosterKey() {
			return posterKey;
		}


		public void setPosterKey(String posterKey) {
			this.posterKey = posterKey;
		}


		public String getOriginalPosterImageKey() {
			return originalPosterImageKey;
		}


		public void setOriginalPosterImageKey(String originalPosterImageKey) {
			this.originalPosterImageKey = originalPosterImageKey;
		}


		public String getNonfacePosterImageKey() {
			return nonfacePosterImageKey;
		}


		public void setNonfacePosterImageKey(String nonfacePosterImageKey) {
			this.nonfacePosterImageKey = nonfacePosterImageKey;
		}


		public String getMovieName() {
			return movieName;
		}


		public void setMovieName(String movieName) {
			this.movieName = movieName;
		}


		public String getPosterName() {
			return posterName;
		}


		public void setPosterName(String posterName) {
			this.posterName = posterName;
		}


		public String getUpdateDate() {
			return updateDate;
		}


		public void setUpdateDate(String updateDate) {
			this.updateDate = updateDate;
		}
	}
	
}
