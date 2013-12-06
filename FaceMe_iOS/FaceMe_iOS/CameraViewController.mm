//
//  ViewController.m
//  cameraexperiment
//
//  Created by Bo Pang on 12/3/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "CameraViewController.h"
#import "UIImage2OpenCV.h"
#import "AppDelegate.h"

@interface CameraViewController ()
{
#if TARGET_IPHONE_SIMULATOR
    DummyVideoSource * videoSource;
#else
    VideoSource * videoSource;
#endif
    
    cv::Mat outputFrame;
}
@property (weak, nonatomic) IBOutlet UISlider *zoomSlider;
- (IBAction)sliderChanged:(id)sender;

@property (weak, nonatomic) IBOutlet UIView *containerView;
@property (weak, nonatomic) IBOutlet UIButton *screenShotButton;

@end

@implementation CameraViewController
@synthesize imageView;
@synthesize posterImage;
@synthesize posterNFImage;
@synthesize face_x;
@synthesize face_y;
@synthesize face_height;
@synthesize face_width;
@synthesize face_winWidth;
@synthesize face_winHeight;
@synthesize face_winX;
@synthesize face_winY;
@synthesize transposed;

@synthesize mat_poster;
@synthesize mat_posterNF;
@synthesize mat_PosterForDisplay;
@synthesize mat_posterNFForDisplay;

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    // Init the default view (video view layer)
    self.imageView = [[GLESImageView alloc] initWithFrame:self.containerView.bounds];
    [self.imageView setAutoresizingMask:(UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight)];
    [self.containerView addSubview:self.imageView];
    
    // Init poster Image
    
    AppDelegate* appDelegate=[UIApplication sharedApplication].delegate ;
//    self.posterImage = [UIImage imageNamed:@"image1.jpg"];
//    self.posterNFImage = [UIImage imageNamed:@"image2.png"];
    self.posterImage = [[appDelegate currentPoster] orginalPoster];
    self.posterNFImage = [[appDelegate currentPoster] nonfacePoster];
    
    self.mat_poster = [self.posterImage toMat];
    self.mat_posterNF = [self.posterNFImage toMat];
    
    
    self.face_x = [[appDelegate chosenFace] positionX];//0.31318f;
    self.face_y = [[appDelegate chosenFace] positionY];
    self.face_width = [[appDelegate chosenFace] width];
    self.face_height = [[appDelegate chosenFace] height];
    
    [self initMatData];
    
    // Init video source:
#if TARGET_IPHONE_SIMULATOR
    videoSource = [[DummyVideoSource alloc] initWithFrameSize:CGSizeMake(640, 480)];
#else
    videoSource = [[VideoSource alloc] init];
#endif
    
    videoSource.delegate = self;
    
}

- (void) viewWillAppear:(BOOL)animated
{
    [super viewWillAppear:animated];
    
    [videoSource startRunning];
    
    //toggleCameraButton.enabled = [videoSource hasMultipleCameras];
    //captureReferenceFrameButton.enabled = self.currentSample.isReferenceFrameRequired;
    //clearReferenceFrameButton.enabled   = self.currentSample.isReferenceFrameRequired;
    
}

- (void) viewDidDisappear:(BOOL)animated
{
    [super viewDidDisappear:animated];
    [videoSource stopRunning];
}


//- (IBAction)toggleCameraPressed:(id)sender
//{
//    [videoSource toggleCamera];
//}



- (void)viewDidUnload
{
//    [self setToggleCameraButton:nil];
    [self setContainerView:nil];
    [super viewDidUnload];
}

#pragma mark - InitMatData
- (void) initMatData
{
    
    
    int imageWidth = mat_poster.cols;
    int imageHeight = mat_poster.rows;
    cv::Mat m1;
    if(imageWidth < imageHeight){
        mat_poster = mat_poster.t();
        cv::flip(mat_poster, mat_poster, 0);
        
        float tmp = face_x;
        face_x = face_y;
        face_y = 1-tmp;
        
        tmp = face_width;
        face_width = face_height;
        face_height = tmp;
        transposed = true;
    }
    imageWidth = mat_poster.cols;
    imageHeight = mat_poster.rows;
    
    float scale;
    int border_width = 0;
    int border_height = 0;
    float width = self.imageView.frame.size.height;
    float height = self.imageView.frame.size.width;
    
    if(imageWidth / width > imageHeight / height){
        scale = width /  imageWidth;
        imageWidth = width;
        imageHeight = (int) (imageHeight * scale);
        border_height = (int) (height - imageHeight)/2;
    }
    else{
        scale = height / imageHeight;
        imageWidth = (int) (imageWidth * scale);
        imageHeight = height;
        border_width = (int) (width - imageWidth)/2;
    }
    cv::resize(mat_poster, mat_poster, cv::Size(imageWidth,imageHeight));
    //mat_poster.resize(scale);
    cv::copyMakeBorder(mat_poster, mat_PosterForDisplay, border_height, border_height, border_width, border_width, cv::BORDER_CONSTANT);
    cv::copyMakeBorder(mat_posterNF, mat_posterNFForDisplay, border_width, border_width, border_height, border_height, cv::BORDER_CONSTANT);
    
    
    
    face_winWidth = (int) (imageWidth * face_width);
    face_winHeight = (int) (imageHeight * face_height);
    
    face_winY = (int) (imageHeight * face_y) + border_height;
    if(transposed)
        face_winY = face_winY - face_winHeight;
    face_winX = (int) (imageWidth * face_x) + border_width;
    
    //cv::Mat mat_win = mat_posterNFForDisplay(cv::Rect(face_winY, face_winY + face_winHeight, face_winX, face_winX+face_winHeight));
    
//    mPosterCopy = new Mat();
//    mPosterNFCopy = new Mat();
//    
//    mResizedPosterNF.copyTo(mPosterNFCopy);
//    
//    if(faceWinHeight > 0)
//        rWinPosterNF = mPosterNFCopy.submat(faceWinY, faceWinY + faceWinHeight, faceWinX, faceWinX + faceWinWidth);
//    else
//        rWinPosterNF = mPosterNFCopy.submat(faceWinY + faceWinHeight, faceWinY, faceWinX, faceWinX + faceWinWidth);
//    
//    ones = Mat.ones(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1);
//    Core.multiply(ones, new Scalar(255), ones);
//    allChannels2 = new ArrayList<Mat>();
//    allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
//    allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
//    allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
//    allChannels2.add(Mat.zeros(rWinPosterNF.rows(), rWinPosterNF.cols(), CvType.CV_32FC1));
    
    
}

#pragma mark - VideoSourceDelegate

- (void) frameCaptured:(cv::Mat) frame
{
    bool isMainQueue = dispatch_get_current_queue() == dispatch_get_main_queue();
    
    if (isMainQueue)
    {
        //[self.currentSample processFrame:frame into:outputFrame];
//        [imageView drawFrame:frame];
//        [imageView drawFrame:mat_poster];
        int zoomWidth, zoomHeight;
        zoomWidth = (int) (self.zoomSlider.value * (mat_PosterForDisplay.cols - face_winWidth) + face_winWidth);
        zoomHeight = zoomWidth * mat_PosterForDisplay.rows / mat_PosterForDisplay.cols;
        int dx = (mat_PosterForDisplay.cols - zoomWidth) / 2;
        int dy = (mat_PosterForDisplay.rows - zoomHeight) / 2;
        
        cv::Mat winFrame = frame(cv::Rect(dx, dy,zoomWidth, zoomHeight));
        
        
        cv::Mat outFrame = mat_PosterForDisplay.clone();
//        cv::Mat mat_win = outFrame(cv::Rect(face_winX, face_winY,face_winWidth, face_winHeight));
        cv::Mat mat_win;
        cv::resize(winFrame, mat_win, cv::Size(face_winWidth, face_winHeight));
        cv::Mat mat_camera;
        cv::resize(frame, mat_camera, mat_win.size());
        cv::addWeighted(mat_camera, 0.6, mat_win, 0.4, 0.0, mat_win);
        [imageView drawFrame:outFrame];
    }
    else
    {
        dispatch_sync( dispatch_get_main_queue(),
                      ^{
                          //[self.currentSample processFrame:frame into:outputFrame];
                          //[imageView drawFrame:frame];
                          
                          cv::Mat outFrame = mat_PosterForDisplay.clone();
                          cv::Mat mat_win = outFrame(cv::Rect(face_winX, face_winY,face_winWidth, face_winHeight));

                          int zoomWidth, zoomHeight;
                          if(mat_win.rows < mat_win.cols){
                              zoomHeight = (int) (self.zoomSlider.value * (frame.rows - face_winHeight) + face_winHeight);
                              zoomWidth = zoomHeight * face_winWidth / face_winHeight;
                          }
                          else{
                              zoomWidth = (int) (self.zoomSlider.value * (frame.cols - face_winWidth) + face_winWidth);
                              zoomHeight = zoomWidth * face_winHeight / face_winWidth;
                          }
                          int dx = (frame.cols - zoomWidth) / 2;
                          int dy = (frame.rows - zoomHeight) / 2;
                          
                          cv::Mat winFrame = frame(cv::Rect(dx, dy,zoomWidth, zoomHeight));
                          
                          
                                                    //cv::Mat mat_win;
                          //cv::resize(winFrame, mat_win, cv::Size(face_winWidth, face_winHeight));
                          cv::Mat mat_camera;
                          cv::resize(winFrame, mat_camera, mat_win.size());
                          //cv::resize(frame, mat_camera, mat_win.size());
                          cv::addWeighted(mat_camera, 0.6, mat_win, 0.4, 0.0, mat_win);
                          [imageView drawFrame:outFrame];

                      }
                      );
    }
}
- (IBAction)sliderChanged:(id)sender {
    
}
@end

