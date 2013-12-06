//
//  CharacterFace.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/26/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "CharacterFace.h"

@implementation CharacterFace
@synthesize key,imageKey,name,positionX,positionY,width,height,posterID,index,bmp;
-(id)initWithID:(NSString*)ID1 WithIM:(NSString*)imageKey1 withN:(NSString*)name1 withPX:(float)positionX1 withPY:(float)positionY1 withW:(float)width1 withH:(float)height1 withPID:(NSString*)posterID1 withI:(NSInteger*)index1{
    if (self=[super init]) {
        self.key=ID1;
        self.imageKey=imageKey1;
        self.name=name1;
        self.positionX=positionX1;
        self.positionY=positionY1;
        posterID=posterID1;
        index=index1;
        width=width1;
        height=height1;
    }
    return self;
}
@end
