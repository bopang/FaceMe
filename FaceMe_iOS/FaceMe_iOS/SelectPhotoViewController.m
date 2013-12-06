//
//  SelectPhotoViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/17/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "SelectPhotoViewController.h"

@interface SelectPhotoViewController ()

@end

@implementation SelectPhotoViewController
@synthesize imageView,shareView;
- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)getPhoto:(id)sender {
    UIImagePickerController * picker = [[UIImagePickerController alloc] init];
    
    // Don't forget to add UIImagePickerControllerDelegate in your .h
     picker.delegate = self;
    
        picker.sourceType = UIImagePickerControllerSourceTypeSavedPhotosAlbum;
//    } else {
//        picker.sourceType = UIImagePickerControllerSourceTypeCamera;
//    }
//    
    [self presentViewController:picker animated:YES completion:nil];

}

- (IBAction)activiteCamera:(id)sender {
    UIImagePickerController * picker = [[UIImagePickerController alloc] init];
    picker.delegate = self;
    picker.sourceType = UIImagePickerControllerSourceTypeCamera;
    [self presentViewController:picker animated:YES completion:nil];
}

- (IBAction)cancelButton:(id)sender {
    CGRect currentFrame= self.view.frame;
    currentFrame.origin.y+=200;
    [UIView animateWithDuration:0.5 animations:^{
        self.view.frame=currentFrame;
    }];

    
}
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info {
    [self dismissViewControllerAnimated:YES completion:nil];
    UIImage * chosenImage = [info objectForKey:UIImagePickerControllerEditedImage];
    AppDelegate*appDelegate=[UIApplication sharedApplication].delegate ;
    appDelegate. getPhoto=chosenImage;
    // You have the image. You can use this to present the image in the next view like you require in `#3`.
   // self.imageView.image = chosenImage;
    shareView=[[self storyboard]instantiateViewControllerWithIdentifier:@"share"];
    //[self.parentViewController presentViewController:shareView animated:YES completion:Nil];
    [self.view addSubview:shareView.view];
}


@end
