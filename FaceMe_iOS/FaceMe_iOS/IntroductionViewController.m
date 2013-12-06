//
//  IntroductionViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/15/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "IntroductionViewController.h"

@interface IntroductionViewController ()
@property NSUInteger viewIndex;
@end

@implementation IntroductionViewController

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
    self.viewIndex=0;
    self.mainPageView = [[UIPageViewController alloc] initWithTransitionStyle:UIPageViewControllerTransitionStyleScroll navigationOrientation:UIPageViewControllerNavigationOrientationHorizontal options:nil];
    
    self.mainPageView.dataSource = self;
    [[self.mainPageView view] setFrame:[[self view] bounds]];
    
    IntroChildViewController *initialViewController = [self viewControllerAtIndex:0];
    
    NSArray *viewControllers = [NSArray arrayWithObject:initialViewController];
    
    [self.mainPageView setViewControllers:viewControllers direction:UIPageViewControllerNavigationDirectionForward animated:YES completion:nil];

    [self addChildViewController:self.mainPageView];
    [[self view] addSubview:[self.mainPageView view]];
    [self.mainPageView didMoveToParentViewController:self];

}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
- (UIViewController *)pageViewController:(UIPageViewController *)pageViewController viewControllerBeforeViewController:(IntroChildViewController *)viewController {
    
    
    NSInteger index= viewController.index;
    
    if (index == 0) {
        return nil;
    }
    
    index--;
    
    return [self viewControllerAtIndex:index];
    
}

- (UIViewController *)pageViewController:(UIPageViewController *)pageViewController viewControllerAfterViewController:(IntroChildViewController *)viewController {
    
    
    NSInteger index= viewController.index;
    index++;
       if (index == 4) {
        
        return nil;
    }
    
    
    
    return [self viewControllerAtIndex:index];
    
}

- (UIViewController *)viewControllerAtIndex:(NSUInteger*)index {
    
    IntroChildViewController *childViewController = [self.storyboard instantiateViewControllerWithIdentifier:@"child"];
    
//    IntroChildViewController*childViewController=[[IntroChildViewController alloc]initWithNibName:nil bundle:nil];
      childViewController.index = index;
    //NSLog(@"%d",childViewController.index);
  
    return childViewController;
    
}
//- (NSInteger)presentationCountForPageViewController:(UIPageViewController *)pageViewController {
//    // The number of items reflected in the page indicator.
//    return 3;
//}
//
//- (NSInteger)presentationIndexForPageViewController:(UIPageViewController *)pageViewController {
//    // The selected item reflected in the page indicator.
//    return 0;
//}
@end
