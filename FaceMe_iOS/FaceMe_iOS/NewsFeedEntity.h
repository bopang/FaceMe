//
//  NewsFeedEntity.h
//  FaceMe_iOS
//
//  Created by imac on 12/1/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NewsFeedEntity : NSObject

@property NSString*posterKey;

@property NSMutableArray* userNames;

@property NSString*orignalPosterImageKey;
@property NSString*nonfacePosterImagekey;
@property NSString*movieName;
@property NSString*posterName;
@property NSMutableArray* userfaces;
@property NSMutableArray*characters;
@property NSString*updateDate;
@property UIImage* orginalPosterBmp;
@property UIImage* nonfacePosterBmp;
@property UIImage*cosplayBmp;
@end
