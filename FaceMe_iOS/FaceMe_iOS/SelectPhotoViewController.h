//
//  SelectPhotoViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/17/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "AppDelegate.h"
#import "ShareViewController.h"
@interface SelectPhotoViewController : UIViewController<UIImagePickerControllerDelegate,UINavigationControllerDelegate>
- (IBAction)getPhoto:(id)sender;
- (IBAction)activiteCamera:(id)sender;
- (IBAction)cancelButton:(id)sender;
@property(retain) UIImageView*imageView;
@property ShareViewController*shareView;
@end
