package com.gatech.faceme.mediastore;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gatech.faceme.entity.OriginalPoster;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class ImageResource extends HttpServlet {
		  private BlobstoreService blobstoreService =
		    BlobstoreServiceFactory.getBlobstoreService();

		  public void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException {

		    BlobKey blobKey = new BlobKey(req.getParameter("key"));

		    resp.setContentType("image/png");
		    
		    blobstoreService.serve(blobKey, resp);
		  }
}
