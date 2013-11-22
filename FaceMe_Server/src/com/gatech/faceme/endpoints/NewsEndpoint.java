package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.Persistent;

import com.gatech.faceme.entity.CharacterFaceEntity;
import com.gatech.faceme.entity.PairTableEntity;
import com.gatech.faceme.entity.PosterEntity;
import com.gatech.faceme.entity.UserFaceEntity;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.appengine.api.datastore.Key;

@Api(name = "newsendpoint", description = "Used for getting news", version = "v1")
public class NewsEndpoint {

	@ApiMethod(httpMethod = "GET", name = "news.list", path = "news/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<News> listUserFace() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<News> result = new ArrayList<News>();
		try {
			Query query1 = mgr.newQuery(UserFaceEntity.class);
			Query query2 = mgr.newQuery(CharacterFaceEntity.class);
			Query query3 = mgr.newQuery(PosterEntity.class);
			Query query4 = mgr.newQuery(PairTableEntity.class);
			for (UserFaceEntity obj : (List<UserFaceEntity>) query1.execute()) {
				String posterkey = obj.getPosterKey();
				Key userKey = obj.getKey();
				PosterEntity posterEntity = mgr.getObjectById(PosterEntity.class, posterkey);
				Key posterid = posterEntity.getKey();
//				query3 = mgr.newQuery(PairTableEntity.class, 
//						"originalPosterKey ==posterkey");
				query2 = mgr.newQuery(CharacterFaceEntity.class, 
						"posterID==posterid");
				query4 = mgr.newQuery(PairTableEntity.class, 
						"activeUserFace==userid");
				ArrayList<UserFaceEntity> userfaces = new ArrayList<UserFaceEntity>();
				userfaces.add(obj);
				ArrayList<CharacterFaceEntity> characters = new ArrayList<CharacterFaceEntity>();
				for (CharacterFaceEntity object : (List<CharacterFaceEntity>) query2.execute()) {
					characters.add(object);
					
				}
				result.add(new News(posterkey, posterEntity.getOriginalPosterKey(),
						posterEntity.getNonfacePosterKey(), posterEntity.getMovieName(),
						posterEntity.getMovieName(), userfaces, characters));
			}
		} finally {
			mgr.close();
		}
		return result;
	}
	
	private PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public class News{
		
		private String posterKey;
		private String originalPosterImageKey;
		private String nonfacePosterImageKey;
		private String movieName;
		private String posterName;
		
		// to be done
		private String updateDate;
		
		private List<UserFaceEntity> userfaces;
		private List<CharacterFaceEntity> characters;

		
		public News(String posterKey, String originalPosterImageKey, String nonfacePosterImageKey,
				String movieName, String posterName, List<UserFaceEntity> userfaces
				, List<CharacterFaceEntity> characters){
			this.posterKey = posterKey;
			this.originalPosterImageKey = originalPosterImageKey;
			this.nonfacePosterImageKey = nonfacePosterImageKey;
			this.movieName = movieName;
			this.posterName = posterName;
//			this.updateDate = updateDate;
			this.userfaces = userfaces;
			this.characters = characters;
		}
	}
	
}
