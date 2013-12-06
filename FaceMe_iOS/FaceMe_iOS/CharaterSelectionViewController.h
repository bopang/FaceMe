//
//  CharaterSelectionViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SelectPhotoViewController.h"
#import "AppDelegate.h"
#import "CharacterFace.h"
#import "ImageRoundCorner.h"
@interface CharaterSelectionViewController : UIViewController
@property (retain) NSString*imageUrl;
@property(strong)NSMutableArray*faceImages;
@property (nonatomic, retain) IBOutlet UIImageView *detailImage;
@property SelectPhotoViewController*selectViewController;
@property NSString* getKey;
@property ImageRoundCorner*imageTool;
@property (weak, nonatomic) IBOutlet UIView *buttonView;

@end
