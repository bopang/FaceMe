//
//  LoginViewController.h
//  FaceMe_iOS
//
//  Created by Shawn Wu on 12/5/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
#import "MenuViewController.h"

@interface LoginViewController : UIViewController<UITextFieldDelegate>
@property (weak, nonatomic) IBOutlet UITextField *usernameText;
@property (weak, nonatomic) IBOutlet UITextField *passwordText;
- (IBAction)login:(id)sender;
- (IBAction)cancel:(id)sender;
@property AppDelegate*appDelegate;
@end
