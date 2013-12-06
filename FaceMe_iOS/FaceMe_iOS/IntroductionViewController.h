//
//  IntroductionViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/15/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "IntroChildViewController.h"
#import "MainViewController.h"
@interface IntroductionViewController : UIViewController<UIPageViewControllerDataSource,UIPageViewControllerDelegate>
@property (strong,nonatomic) UIPageViewController*mainPageView;

@end
