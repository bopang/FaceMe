//
//  MovieSelectionViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "CharaterSelectionViewController.h"
#import "PosterEntity.h"
#import "ImageRoundCorner.h"
#import "AppDelegate.h"
@interface PosterSelectionViewController : UIViewController
@property (weak, nonatomic) IBOutlet UITableView *posterTable;
@property NSMutableArray*posters;
@property(nonatomic) NSMutableDictionary*staticImageDictionary;
@property NSString*theKey;
@property ImageRoundCorner*roundCorner;
@end
