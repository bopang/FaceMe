//
//  MovieSelectionViewController.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PosterSelectionViewController : UIViewController
@property (weak, nonatomic) IBOutlet UITableView *posterTable;

@property (weak,nonatomic)NSMutableArray*posterTitles;
@property(weak,nonatomic)NSMutableArray*posterDetails;
@property (weak, nonatomic)NSMutableArray*posterImages;
@end
