package com.gatech.faceme.endpoints;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.gatech.faceme.entity.CommentEntity;
import com.gatech.faceme.entity.PairTableEntity;
import com.gatech.faceme.entity.User;
import com.gatech.faceme.mediastore.PMF;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;

@Api(name = "commentendpoint", description = "This entity represents a comment.", version = "v1")
public class CommentEndpoint {
	
	@ApiMethod(httpMethod = "GET", name = "comment.list", path = "comment/get/list")
	@SuppressWarnings({ "cast", "unchecked" })
	public List<CommentEntity> listComment() {
		PersistenceManager mgr = PMF.get().getPersistenceManager();
		List<CommentEntity> result = new ArrayList<CommentEntity>();
		try {
			Query query = mgr.newQuery(CommentEntity.class);
			for (Object obj : (List<Object>) query.execute()) {
				result.add(((CommentEntity) obj));
			}
		} finally {
			mgr.close();
		}
		return result;
	}
	
//	@ApiMethod(httpMethod = "GET", name = "commentbyuserid.get", 
//			path = "comment/get/{userid}")
//	@SuppressWarnings({ "cast", "unchecked" })
//	public ArrayList<CommentEntity> getCommentByUserId(@Named("userid") String userID) {
//		PersistenceManager mgr = getPersistenceManager();
//		ArrayList<CommentEntity> result = new ArrayList<CommentEntity>();
//		try {
//			Query query = mgr.newQuery(CommentEntity.class, 
//					"user ==userID");
//		
//			for (Object obj : (List<Object>) query.execute()) {
//				result.add(((CommentEntity) obj));
//				((PairTableEntity) obj).setIfNotified(true);
//			}
//			
//		} finally {
//			mgr.close();
//		}
//		return result;
//	}
//	
//	@ApiMethod(httpMethod = "GET", name = "commentbyUserIDAndToComment.get", 
//			path = "comment/get/{userid}/{tocomment}")
//	@SuppressWarnings({ "cast", "unchecked" })
//	public ArrayList<CommentEntity> getCommentByToUserIdAndToComment(@Named("userid") String userID, @Named("tocomment") String tocomment) {
//		PersistenceManager mgr = getPersistenceManager();
//		ArrayList<CommentEntity> result = new ArrayList<CommentEntity>();
//		try {
//			Query query = mgr.newQuery(CommentEntity.class, 
//					"(user ==userID) && (toComment ==tocomment) ");
//		
//			for (Object obj : (List<Object>) query.execute()) {
//				result.add(((CommentEntity) obj));
//				((PairTableEntity) obj).setIfNotified(true);
//			}
//			
//		} finally {
//			mgr.close();
//		}
//		return result;
//	}
	
	@ApiMethod(httpMethod = "POST", name = "comment.insert",
			path = "comment/insert")
	//curl -H 'Content-Type: application/json' -d '{"comment": comment , "pairtable": pairtable, "user": user}' http://localhost:8888/_ah/api/pairtableendpoint/v1/pairtable/insert
	public CommentEntity addComment(CommentEntity pairTable) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(pairTable);
		pm.close();
		return pairTable;
	}
	
	@ApiMethod(httpMethod = "POST", name = "commentToComment.insert",
			path = "commentToComment/insert")
	//curl -H 'Content-Type: application/json' -d '{"comment": comment , "pairtable": pairtable, "user": user, "toComment": toComment}' http://localhost:8888/_ah/api/pairtableendpoint/v1/pairtable/insert
	public CommentEntity addCommentToComment(CommentEntity comment) {
		PersistenceManager pm = getPersistenceManager();
		pm.makePersistent(comment);
		pm.close();
		return comment;
	}

	private PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}
}
