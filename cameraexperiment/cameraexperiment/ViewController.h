//
//  ViewController.h
//  cameraexperiment
//
//  Created by Bo Pang on 12/3/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "GLESImageView.h"
#import "VideoSource.h"

@interface ViewController : UIViewController<VideoSourceDelegate,UIActionSheetDelegate>

@property (nonatomic, strong) GLESImageView *imageView;
@property (nonatomic, strong) UIImage *posterImage;
@property (nonatomic, strong) UIImage *posterNFImage;

@property float face_x;
@property float face_y;
@property float face_height;
@property float face_width;
@property int face_winHeight;
@property int face_winWidth;
@property int face_winX;
@property int face_winY;
@property bool transposed;


@property cv::Mat mat_poster;
@property cv::Mat mat_posterNF;
@property cv::Mat mat_PosterForDisplay;
@property cv::Mat mat_posterNFForDisplay;


@end
