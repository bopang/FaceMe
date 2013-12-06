//
//  LoginViewController.m
//  FaceMe_iOS
//
//  Created by Shawn Wu on 12/5/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "LoginViewController.h"

@interface LoginViewController ()

@end

@implementation LoginViewController
@synthesize usernameText;
@synthesize passwordText;
@synthesize appDelegate;
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
    
    usernameText.delegate=self;
    passwordText.delegate=self;
    appDelegate=[UIApplication sharedApplication].delegate;
    appDelegate.currentUser=[[UserProfile alloc]init];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    
    // do whatever you have to do
    [usernameText resignFirstResponder];
    [passwordText resignFirstResponder];
    return YES;
}
- (void)authenticateHandler {
    
    BOOL isUserAuthenticated =[passwordText.text isEqualToString:[appDelegate currentUser].password];
    
    [[NSUserDefaults standardUserDefaults] setBool:isUserAuthenticated forKey:@"userLoggedIn"];
    [[NSUserDefaults standardUserDefaults] synchronize];
    
    if( isUserAuthenticated ){
        //[self.presentingViewController dismissViewControllerAnimated:YES completion:nil];
        //MenuViewController*menu=[self.storyboard instantiateViewControllerWithIdentifier:@"MenuViewController"];
        UITabBarController*tabBar=[self.storyboard instantiateViewControllerWithIdentifier:@"TabBarController"];
        [self presentViewController:tabBar animated:YES completion:nil];
    }else{
      
    }
}

- (IBAction)login:(id)sender {
    NSMutableString*urlString=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/_ah/api/userendpoint/v1/user/get/"];
    [urlString appendString:usernameText.text];
    NSURL*url=[[NSURL alloc]initWithString:urlString];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    [request setHTTPMethod:@"GET"];
    //[request setValue:@"*/*" forHTTPHeaderField:@"Accept"];
    
    NSHTTPURLResponse *response = nil;
    NSError *error = nil;
    //Make the request
    NSData*retrivedData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSDictionary*currentJson=[NSJSONSerialization JSONObjectWithData:retrivedData options:kNilOptions error:&error];
    appDelegate.currentUser.username=[currentJson objectForKey:@"userID"];
    appDelegate.currentUser.gender=[currentJson objectForKey:@"gender"];
    appDelegate.currentUser.school=[currentJson objectForKey:@"school"];
    appDelegate.currentUser.profilePicUrl=[currentJson objectForKey:@"faceKey"];
    appDelegate.currentUser.password=[currentJson objectForKey:@"password"];
    [self authenticateHandler];
}

- (IBAction)cancel:(id)sender {
    
}
@end
