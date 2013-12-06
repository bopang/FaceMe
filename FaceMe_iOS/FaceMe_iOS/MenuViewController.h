//
//  MenuViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "NewFeedCell.h"
#import "NewsFeedEntity.h"
#import "ViewController.h"
#import "CharacterFace.h"
#import "IntroductionViewController.h"
#import "ImageRoundCorner.h"
#import "UserFaceEntity.h"
#import <FacebookSDK/FacebookSDK.h>
#import "AppDelegate.h"
@interface MenuViewController : UIViewController<UITableViewDelegate,UITableViewDataSource>
@property (weak, nonatomic) IBOutlet UIView *profileView;
@property ViewController*loginViewController;
@property (weak,nonatomic) NSData*responseData;
@property (weak, nonatomic) IBOutlet UILabel *userName;
@property (weak, nonatomic) IBOutlet UILabel *gender;
@property (weak, nonatomic) IBOutlet UILabel *school;
@property (weak, nonatomic) IBOutlet UIImageView *profilePicture;
- (IBAction)showProfileButton:(UIButton *)sender;
@property  AppDelegate*appDelegate;
@property NewsFeedEntity*newsFeed;
@property NSMutableDictionary*staticImageDictionary;
@property IntroductionViewController*intro;
@property ImageRoundCorner*imageWithCorner;
@property NSMutableArray*characterFaces;
@property BOOL swipeTime;
@property NSMutableArray*newsFeeds;
@property NSMutableArray*userFaces;
@property NSMutableArray*characters;
@property (weak, nonatomic) IBOutlet UITableView *tableview;
- (IBAction)logout:(id)sender;
@end
