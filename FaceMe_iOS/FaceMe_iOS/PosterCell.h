//
//  PosterCell.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/3/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface PosterCell : UITableViewCell
@property (weak, nonatomic) IBOutlet UIImageView *posterPicture;
@property (weak, nonatomic) IBOutlet UILabel *posterTitle;
@property (weak, nonatomic) IBOutlet UILabel *posterDetail;

@end
