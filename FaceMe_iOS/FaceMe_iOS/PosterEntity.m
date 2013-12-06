//
//  PosterEntity.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/26/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "PosterEntity.h"

@implementation PosterEntity
@synthesize key,orginalPoster,originalPosterKey,thumbnail,thumbnailKey,movieName,nonfacePoster,nonfacePosterKey,posterName,classification,faces;
-(id)initWithKey:(NSString*) key1 AndOP:(NSString*)orginalPosterKey1 AndTK:(NSString*)thumbnailKey1 AndNFK:(NSString*)nonfaceKey1 AndM:(NSString*)movieName1 AndC:(NSString*)classification1 AndPN:(NSString*)posterName1{
    if (self=[super init]) {
        self.key=key1;
        self.originalPosterKey=orginalPosterKey1;
        self.thumbnailKey=thumbnailKey1;
        self.nonfacePosterKey=nonfaceKey1;
        self.movieName=movieName1;
        self.classification=classification1;
        self.posterName=posterName1;
        
    }
    return self;
}
//-(id)init:{
//    return self;
//}
@end
