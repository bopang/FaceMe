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
        //[self.spinner startAnimating];
        AppDelegate*appDelegate=[UIApplication sharedApplication].delegate ;
        [appDelegate openSession];
    }
    
}
@end
