//
//  ViewController.h
//  FaceMe_iOS
//
//  Created by Bo Pang on 13-9-30.
//  Copyright (c) 2013年 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
@interface MainViewController : UIViewController
- (IBAction)signIn_btn:(id)sender;
@property (weak, nonatomic) IBOutlet UIView *mainView;
- (IBAction)facebookLogin:(id)sender;
- (IBAction)facemeLogin:(id)sender;


@end
