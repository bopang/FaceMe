//
//  PosterEntity.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/26/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface PosterEntity : NSObject
@property NSString* key;
@property NSString*originalPosterKey;
@property NSString*thumbnailKey;
@property NSString*nonfacePosterKey;
@property NSString*movieName;
@property NSString*posterName;
@property NSString*classification;
@property UIImage* thumbnail;
@property UIImage* orginalPoster;
@property UIImage* nonfacePoster;
@property NSArray*faces;
@end
