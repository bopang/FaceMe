//
//  ShareViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/14/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <Social/Social.h>
#import "AppDelegate.h"
@interface ShareViewController : UIViewController
- (IBAction)facebookIcon:(id)sender;
- (IBAction)twitterIcon:(id)sender;
- (IBAction)uploadIcon:(id)sender;
@property (weak, nonatomic) IBOutlet UIImageView *imageForPost;
@property UIImage* getImage;
@property UIImage* userface;


@end
