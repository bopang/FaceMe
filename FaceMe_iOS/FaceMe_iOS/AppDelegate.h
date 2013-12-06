//
//  AppDelegate.h
//  FaceMe_iOS
//
//  Created by Bo Pang on 13-9-30.
//  Copyright (c) 2013年 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <FacebookSDK/FacebookSDK.h>
#import "PosterEntity.h"
#import "ProfileViewController.h"
#import "CharacterFace.h"
#import "NewsFeedEntity.h"
@interface AppDelegate : UIResponder <UIApplicationDelegate>
@property PosterEntity*currentPoster;
@property NSMutableArray*loadedPoster;
@property ProfileViewController* currentUser;
@property CharacterFace*chosenFace;
@property (strong, nonatomic) UIWindow *window;
@property UIImage*getPhoto;
@property NSMutableArray*newsFeeds;
@property NSMutableDictionary*mPosterCache;
@property NSMutableDictionary*mCharacterFaceCache;
@property NSMutableDictionary*mUserFaceCache;
- (void)openSession;
@end
