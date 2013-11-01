package com.gatech.faceme.mediastore;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gatech.faceme.entity.OriginalPoster;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class UploadImage extends HttpServlet{
	private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws
	IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		if (blobs.keySet().isEmpty()) {
			resp.sendRedirect("/?error=" + 
					URLEncoder.encode("No file uploaded", "UTF-8"));
			return;
		}
		Iterator<String> names = blobs.keySet().iterator();
		String blobName = names.next();
		BlobKey blobKey = blobs.get(blobName);
		
		resp.getOutputStream().write(blobKey.getKeyString().getBytes());
	}
}
