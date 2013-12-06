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
@synthesize username,highSchool,hometown,university,gender,music,movies,birth;
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
    NSURL*url=[[NSURL alloc]initWithString:@"https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/get/Ziyi%20Jiang"];
    NSMutableURLRequest*request=[NSMutableURLRequest requestWithURL:url];
    [request setHTTPMethod:@"GET"];
    NSHTTPURLResponse *response = nil;
    NSError *error = nil;
    NSData*responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSDictionary* json = [NSJSONSerialization JSONObjectWithData:responseData options:kNilOptions error:&error];
    self.username.text=[json objectForKey:@"userID"];
    self.university.text=[json objectForKey:@"school"];
    self.gender.text=[json objectForKey:@"gender"];

    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
@end
