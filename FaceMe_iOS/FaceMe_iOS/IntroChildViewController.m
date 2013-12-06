//
//  IntroChildViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/15/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "IntroChildViewController.h"
#import "ViewController.h"
@interface IntroChildViewController ()

@end

@implementation IntroChildViewController

@synthesize iconImage;
@synthesize index;
@synthesize backgroundImage;
@synthesize pageTitle;
@synthesize pageDetail;

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
    if (self.index<3) {
    backgroundImage.image= [self GetImage:self.index];
    self.pageTitle.text=[self getLabel:self.index].text;
    self.pageDetail.text=[NSString stringWithFormat:@"%d", self.index];
        
    }
	// Do any additional setup after loading the view.
   }
-(void)viewDidAppear:(BOOL)animated{
    
    if (self.index==3) {
        //[btn_1 setHidden:NO];
        //[btn_2 setHidden:NO];
        ViewController*loginViewController=[self.storyboard instantiateViewControllerWithIdentifier:@"login"];
        [self presentViewController:loginViewController animated:YES completion:nil];
    }
   

}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}
-(UIImage*)GetImage:(NSUInteger)imageIndex{
   // NSLog(@"%lu",imageIndex);
    //NSLog(@"12345");
    NSMutableArray*imageLibrary=[[NSMutableArray alloc]init];
    UIImage*addImage= [UIImage imageNamed:@"gatech1.jpg"];
    [imageLibrary addObject:addImage];
    UIImage*addImage1= [UIImage imageNamed:@"gatech8.jpg"];
    [imageLibrary addObject:addImage1];
    UIImage*addImage2= [UIImage imageNamed:@"gatech9.jpg"];
    [imageLibrary addObject:addImage2];
    //NSLog(@"%lx",[imageLibrary count]);
    UIImage*currentImage=[imageLibrary objectAtIndex:imageIndex];
    return currentImage;
}
-(UILabel*)getLabel:(NSUInteger)labelIndex{
    NSMutableArray*labelLibrary=[[NSMutableArray alloc]init];
    UILabel*addLabel=[[UILabel alloc]init];
    addLabel.text=@"Meet Friends";
    [labelLibrary addObject:addLabel];
    UILabel*addLabel1=[[UILabel alloc]init];
    addLabel1.text=@"Share Interests";
     [labelLibrary addObject:addLabel1];
    UILabel*addLabel2=[[UILabel alloc]init];
    addLabel2.text=@"Have Fun";
     [labelLibrary addObject:addLabel2];
   
    UILabel*currentLabel=[labelLibrary objectAtIndex:labelIndex];
    return currentLabel;
}

@end
