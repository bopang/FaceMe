//
//  ImageRoundCorner.h
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/23/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <QuartzCore/QuartzCore.h>
@interface ImageRoundCorner : NSObject
-(void)roundCorner:(UIImageView*)image atRadius:(int)radius;
-(UIImage*)scaleImage:(UIImage*)originalImg AtTheRatio:(float)ratio;

@end
