//
//  ProfileViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/22/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "ProfileViewController.h"

@interface ProfileViewController ()

@end

@implementation ProfileViewController
@synthesize username,highSchool,hometown,university,gender,music,movies,birth,appDelegate;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    appDelegate=[UIApplication sharedApplication].delegate;
    self.username.text=appDelegate.currentUser.username;
    self.university.text=appDelegate.currentUser.school;
    self.gender.text=appDelegate.currentUser.gender;
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
@end
