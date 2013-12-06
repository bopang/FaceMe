//
//  CommentViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/21/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface CommentViewController : UIViewController<UITextFieldDelegate>

- (IBAction)submitComment:(id)sender;
- (IBAction)comment:(id)sender;
@property (weak, nonatomic) IBOutlet UIView *commentsView;

@property (weak, nonatomic) IBOutlet UITextField *myText;

@end
