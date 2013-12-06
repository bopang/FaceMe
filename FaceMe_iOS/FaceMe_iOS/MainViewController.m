//
//  ViewController.m
//  FaceMe_iOS
//
//  Created by Bo Pang on 13-9-30.
//  Copyright (c) 2013年 Bo Pang. All rights reserved.
//

#import "MainViewController.h"

@interface MainViewController ()

@end

@implementation MainViewController
@synthesize mainView;

- (void)viewDidLoad
{
    [super viewDidLoad];
   	// Do any additional setup after loading the view, typically from a nib.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)signIn_btn:(id)sender {
    
    {
        CGRect newFrame = self.mainView.frame;
        newFrame.origin.y -= 250;    // shift right by 500pts
        [UIView animateWithDuration:0.5
                         animations:^{
                             self.mainView.frame = newFrame;
                         }
                         completion:^(BOOL finished){}];
        
        
        
        //[self.spinner startAnimating];
        
    }
    
}
- (IBAction)facebookLogin:(id)sender {
    AppDelegate*appDelegate=[UIApplication sharedApplication].delegate ;
    [appDelegate openSession];
}

- (IBAction)facemeLogin:(id)sender {
}
@end
