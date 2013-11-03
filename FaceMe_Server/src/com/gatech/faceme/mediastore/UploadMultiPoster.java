/* Copyright (c) 2009 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.gatech.faceme.mediastore;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gatech.faceme.entity.CharacterFaceEntity;
import com.gatech.faceme.entity.NonFacePoster;
import com.gatech.faceme.entity.PosterEntity;
import com.google.appengine.api.blobstore.BlobInfo;
import com.google.appengine.api.blobstore.BlobInfoFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class UploadMultiPoster extends HttpServlet {

	private BlobstoreService blobstoreService = BlobstoreServiceFactory
			.getBlobstoreService();

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
		if (blobs.keySet().isEmpty()) {
			resp.sendRedirect("/?error="
					+ URLEncoder.encode("No file uploaded", "UTF-8"));
			return;
		}
		
		if (user != null){
			
			Iterator<String> names = blobs.keySet().iterator();
			
			String blobName = names.next();
			BlobKey originalPosterBlobKey = blobs.get(blobName);
			String originalPosterKey = originalPosterBlobKey.toString();
			
			blobName = names.next();
			BlobKey thumbNailBlobKey = blobs.get(blobName);
			String thumbNailKey = thumbNailBlobKey.toString();
			
			blobName = names.next();
			BlobKey nonFacePosterBlobKey = blobs.get(blobName);
			String nonFacePosterKey = nonFacePosterBlobKey.toString();
			
			BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
			BlobInfo originalPosterBlobInfo = blobInfoFactory
					.loadBlobInfo(originalPosterBlobKey);

		
			Date creationDate = originalPosterBlobInfo.getCreation();
			String posterName = req.getParameter("postername");
			String movieName = req.getParameter("moviename");
			String classification = req.getParameter("classification");
			

			try {
				PosterEntity posterEntity = new PosterEntity(originalPosterKey,
						thumbNailKey, nonFacePosterKey, movieName, classification,
						creationDate, posterName);
				
				PMF.get().getPersistenceManager().makePersistent(posterEntity);
				
				String numOfFaces = req.getParameter("numoffaces");
				int numberoffaces = Integer.parseInt(numOfFaces);
				String faceKey, faceName, posterKey;
				float positionX, positionY, width, height;
				int index;
				
				for (int i = 1; i <= numberoffaces; i++) {
					blobName = names.next();
					faceKey = blobs.get(blobName).toString();
					faceName = req.getParameter("faceName"+i);
					positionX = Float.parseFloat(req.getParameter("positionX"+i));
					positionY = Float.parseFloat(req.getParameter("positionY"+i));
					width = Float.parseFloat(req.getParameter("width"+i));
					height = Float.parseFloat(req.getParameter("height"+i));
					index = Integer.parseInt(req.getParameter("index"+i));
					CharacterFaceEntity characterFaceEntity = new CharacterFaceEntity(faceKey, faceName, positionX, positionY, width, height, originalPosterKey, index);
					PMF.get().getPersistenceManager().makePersistent(characterFaceEntity);
				}
				
				
				resp.sendRedirect("/");
			} catch (Exception e) {
				blobstoreService.delete(originalPosterBlobKey);
				blobstoreService.delete(thumbNailBlobKey);
				blobstoreService.delete(nonFacePosterBlobKey);
				resp.sendRedirect("/?error="
						+ URLEncoder.encode(
								"Object save failed: " + e.getMessage(), "UTF-8"));
			}
		}else if (user == null) {
			resp.sendRedirect("/?error="
					+ URLEncoder.encode("Must be logged in to upload", "UTF-8"));
			return;
		}


	}
}
