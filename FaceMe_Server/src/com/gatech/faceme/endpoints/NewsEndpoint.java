package com.gatech.faceme.endpoints;

import javax.jdo.annotations.Persistent;

import com.google.api.server.spi.config.Api;
import com.google.appengine.api.datastore.Key;

@Api(name = "newsendpoint", description = "Used for getting news", version = "v1")
public class NewsEndpoint {

	
	
	public class News{
		
		//get from user face table;
		private String imageKey;
		private String userID;
		private String posterKey;
		private String characterKey;
		
		//get from characterFace table	
		private String name;
		//image position
		private float positionX; //upper left point
		private float positionY;
		private float width; //image width and height
		private float height;
		private int index;
		
		
		//get from poster table;
		private String originalPosterKey;
		private String thumbnailKey;
		private String nonfacePosterKey;
		private String movieName;
		
	}
}
