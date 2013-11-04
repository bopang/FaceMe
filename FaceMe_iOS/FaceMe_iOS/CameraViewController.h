//
//  CameraViewController.h
//  FaceMe_iOS
//
//  Created by Bo Pang on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <opencv2/highgui/cap_ios.h>



@interface CameraViewController : UIViewController<CvVideoCameraDelegate>

@property (weak, nonatomic) IBOutlet UIButton *snapButton;

@property (weak, nonatomic) IBOutlet UIImageView *posterView;

@property (nonatomic, retain) CvVideoCamera* videoCamera;

- (IBAction)takePhoto:(id)sender;

@end
