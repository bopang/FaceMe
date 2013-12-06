//
//  NewFeedCell.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/8/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CommentViewController.h"
@interface NewFeedCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *uploadedImage;
@property (weak, nonatomic) IBOutlet UILabel *username;
@property CommentViewController*commentView;
@property(strong, nonatomic) UIStoryboard* mainStoryboard;
- (IBAction)playGame:(id)sender;
- (IBAction)rate:(id)sender;
- (IBAction)comment:(id)sender;
@end
