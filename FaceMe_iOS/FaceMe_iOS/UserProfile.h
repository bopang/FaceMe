//
//  UserProfile.h
//  FaceMe_iOS
//
//  Created by Shawn Wu on 12/6/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface UserProfile : NSObject
@property NSString*username;
@property NSString*gender;
@property NSString*school;
@property NSString*profilePicUrl;
@property NSString* password;
@property UIImage* faceBmp;
@end
