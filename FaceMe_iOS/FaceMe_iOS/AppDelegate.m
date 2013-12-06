//
//  AppDelegate.m
//  FaceMe_iOS
//
//  Created by Bo Pang on 13-9-30.
//  Copyright (c) 2013年 Bo Pang. All rights reserved.
//

#import "AppDelegate.h"
#import "ViewController.h"
#import "MenuViewController.h"
#import "IntroductionViewController.h"
@interface AppDelegate ()
@property (strong, nonatomic) UITabBarController* tabController;
@property(strong,nonatomic) MenuViewController* mainViewController;
@property(strong,nonatomic) ViewController*loginViewController;
@property(strong, nonatomic) UIStoryboard* mainStoryboard;
@property(strong,nonatomic) IntroductionViewController*introViewController;

@end
@implementation AppDelegate
@synthesize tabController;
@synthesize mCharacterFaceCache,mPosterCache,mUserFaceCache;
@synthesize getPhoto;
@synthesize loginViewController;
@synthesize mainViewController;
@synthesize newsFeeds;
@synthesize chosenFace,currentPoster,currentUser,loadedPoster;
- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
    UIImage *navBackgroundImage = [UIImage imageNamed:@"navbar_bg"];
    [[UINavigationBar appearance] setBackgroundImage:navBackgroundImage forBarMetrics:UIBarMetricsDefault];
    [[UINavigationBar appearance] setTitleTextAttributes: [NSDictionary dictionaryWithObjectsAndKeys:
                                                           [UIColor colorWithRed:255.0/255.0 green:255.0/255.0 blue:255.0/255.0 alpha:1.0], NSForegroundColorAttributeName,
                                                           [UIFont fontWithName:@"HelveticaNeue" size:20.0], NSFontAttributeName, nil]];
    [[UINavigationBar appearance] setTintColor:[UIColor whiteColor]];
      //[[UITabBar appearance] setTintColor:[UIColor grayColor]];
  [[UINavigationBar appearance] setShadowImage:[UIImage imageNamed:@"shadow.png"]];
   
    
         // Override point for customization after application launch.
    [[UITabBar appearance]setBackgroundImage:navBackgroundImage];
    [[UITabBar appearance] setTintColor:[UIColor whiteColor]]; // for unselected items that are gray
   
    self.mainStoryboard = [UIStoryboard storyboardWithName:@"Main_iPhone" bundle:nil];
    self.mainViewController= [self.mainStoryboard instantiateViewControllerWithIdentifier:@"MenuViewController"];
    self.introViewController=[self.mainStoryboard instantiateViewControllerWithIdentifier:@"intro"];
    self.tabController=[self.mainStoryboard instantiateViewControllerWithIdentifier:@"TabBarController"];
     //self.window.rootViewController = self.introViewController;
     self.window.rootViewController = self.tabController;
    [self.window makeKeyAndVisible];
    
    if (FBSession.activeSession.state == FBSessionStateCreatedTokenLoaded) {
        // To-do, show logged in view
        NSLog(@"loged in");
       
    } else {
        // No, display the login page.
        NSLog(@"logging in");
        [self showLoginView];
        
    }
    
    
    // Change the appearance of other navigation button
   
    return YES;
}
							
- (void)applicationWillResignActive:(UIApplication *)application
{
    // Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
    // Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    // Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
    // If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    // Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    // Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
    [FBSession.activeSession handleDidBecomeActive];
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    // Called when the application is about to terminate. Save data if appropriate. See also applicationDidEnterBackground:.
}
- (void)showLoginView
{
    //UIViewController *topViewController = [self.navController topViewController];
    
    loginViewController =[self.mainStoryboard instantiateViewControllerWithIdentifier:@"login"];
 
    NSLog(@"login session");
    NSLog(@"%@", NSStringFromClass(loginViewController.class));
    self.window.rootViewController = self.introViewController;
    //[mainViewController presentViewController:loginViewController animated:YES completion:nil];
}
-(void)sessionStateChanged:(FBSession *)session
state:(FBSessionState) state
error:(NSError *)error
{
    switch (state) {
        case FBSessionStateOpen:

//            UIViewController*current=[topViewController presentedViewController];
//            NSLog(@"%@", NSStringFromClass(current.class));
//           if ([[topViewController presentedViewController] isKindOfClass:[ViewController class]]) {
            NSLog(@"navi arrives inside");
//               [topViewController dismissViewControllerAnimated:YES completion:nil];
            NSLog(@"%@", NSStringFromClass(self.loginViewController.class));
            
            self.window.rootViewController = self.tabController;
            break;
        case FBSessionStateClosed:
        case FBSessionStateClosedLoginFailed:
            // Once the user has logged in, we want them to
            // be looking at the root view.
            //[self.tabController popToRootViewControllerAnimated:NO];
            
            [FBSession.activeSession closeAndClearTokenInformation];
            
            //[self showLoginView];
            break;
        default:
            break;
    }
    
    if (error) {
        UIAlertView *alertView = [[UIAlertView alloc]
                                  initWithTitle:@"Error"
                                  message:error.localizedDescription
                                  delegate:nil
                                  cancelButtonTitle:@"OK"
                                  otherButtonTitles:nil];
        [alertView show];
    }
}

- (void)openSession
{
    [FBSession openActiveSessionWithReadPermissions:nil
                                       allowLoginUI:YES
                                  completionHandler:
     ^(FBSession *session,
       FBSessionState state, NSError *error) {
         [self sessionStateChanged:session state:state error:error];
     }];
}
- (BOOL)application:(UIApplication *)application
            openURL:(NSURL *)url
  sourceApplication:(NSString *)sourceApplication
         annotation:(id)annotation
{
    return [FBSession.activeSession handleOpenURL:url];
}
@end
