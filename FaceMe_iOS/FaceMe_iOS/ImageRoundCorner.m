//
//  ImageRoundCorner.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/23/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "ImageRoundCorner.h"

@implementation ImageRoundCorner
-(void)roundCorner:(UIImageView*)currentImage atRadius:(int)radius{
    UIImageView * roundedView = currentImage;
    // Get the Layer of any view
    //    CALayer * l = [roundedView layer];
    //    [l setMasksToBounds:YES];
    //    [l setCornerRadius:10.0];
    
    // You can even add a border
    //    [l setBorderWidth:4.0];
    //    [l setBorderColor:[[UIColor blueColor] CGColor]];
    roundedView.layer.cornerRadius = radius;
    roundedView.clipsToBounds = YES;
    //return roundedView;
}

-(UIImage*)scaleImage:(UIImage*)originalImg AtTheRatio:(float)ratio{
    
    float width=originalImg.size.width*ratio;
    float height=originalImg.size.width*ratio;
    CGRect rect = CGRectMake(0,0,width,height);
    UIGraphicsBeginImageContext(rect.size);
    [originalImg drawInRect:rect];
    UIImage *img = UIGraphicsGetImageFromCurrentImageContext();
    
    UIGraphicsEndImageContext();
    
    return img;
}
@end
