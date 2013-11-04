//
//  CameraViewController.h
//  FaceMe_iOS
//


#import <UIKit/UIKit.h>


@interface CameraViewController : UIViewController

#import <opencv2/highgui/cap_ios.h>



@interface CameraViewController : UIViewController<CvVideoCameraDelegate>

@property (weak, nonatomic) IBOutlet UIButton *snapButton;

@property (weak, nonatomic) IBOutlet UIImageView *posterView;

@property (nonatomic, retain) CvVideoCamera* videoCamera;

- (IBAction)takePhoto:(id)sender;
@end
