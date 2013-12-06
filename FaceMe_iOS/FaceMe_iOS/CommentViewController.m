//
//  CommentViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/21/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "CommentViewController.h"

@interface CommentViewController ()

@end

@implementation CommentViewController
@synthesize myText;
@synthesize commentsView;
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
  // [myText becomeFirstResponder];
    commentsView.layer.shadowOffset = CGSizeMake(0.0f, 1.0f);
    commentsView.layer.shadowColor = [UIColor blackColor].CGColor;
    
    commentsView.layer.shadowOpacity = 1.0f;
    commentsView.layer.shadowRadius = 4.0f;


}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}



- (IBAction)submitComment:(id)sender {
    
    
    }


- (BOOL)textFieldShouldReturn:(UITextField *)textField {
    
    // do whatever you have to do
   

    [myText resignFirstResponder];
    return YES;
}
@end
