//
//  CharacterFace.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/26/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface CharacterFace : NSObject
@property NSString* key;
@property NSString*name;
@property NSString*imageKey;
@property float positionX;
@property float positionY;
@property float width;
@property float height;
@property NSInteger*index;
@property NSString*posterID;
@property UIImage*bmp;
@end
