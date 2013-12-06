//
//  ProfileViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/22/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
@interface ProfileViewController : UITableViewController
@property (weak, nonatomic) IBOutlet UILabel *username;
@property (weak, nonatomic) IBOutlet UILabel *gender;
@property (weak, nonatomic) IBOutlet UILabel *birth;
@property (weak, nonatomic) IBOutlet UILabel *university;
@property (weak, nonatomic) IBOutlet UILabel *movies;
@property (weak, nonatomic) IBOutlet UILabel *music;
@property (weak, nonatomic) IBOutlet UILabel *hometown;
@property (weak, nonatomic) IBOutlet UILabel *highSchool;
@property AppDelegate*appDelegate;
@end
