//
//  IntroChildViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/15/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface IntroChildViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIImageView *backgroundImage;
@property (weak, nonatomic) IBOutlet UIImageView *iconImage;
@property (weak, nonatomic) IBOutlet UILabel *pageTitle;
@property (weak, nonatomic) IBOutlet UILabel *pageDetail;
@property (assign, nonatomic) NSInteger index;

@end
