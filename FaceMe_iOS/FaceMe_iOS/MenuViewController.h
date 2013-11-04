//
//  MenuViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface MenuViewController : UIViewController
@property (weak, nonatomic) IBOutlet UIButton *playGameButton;
@property (weak, nonatomic) IBOutlet UIButton *rankingButton;
@property (weak, nonatomic) IBOutlet UIButton *browersingButton;
@property (weak, nonatomic) IBOutlet UIButton *profileButton;
@property (weak,nonatomic) NSData*responseData;
@property (weak, nonatomic) IBOutlet UILabel *userName;
@property (weak, nonatomic) IBOutlet UILabel *gender;
@property (weak, nonatomic) IBOutlet UILabel *school;
@property (weak, nonatomic) IBOutlet UIImageView *profilePicture;

@end
