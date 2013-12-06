//
//  IntroChildViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/15/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "IntroChildViewController.h"
#import "MainViewController.h"
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
    backgroundImage.clipsToBounds=YES;
    if (self.index<3) {
        iconImage.image=[self GetIconImage:self.index];
    backgroundImage.image= [self GetImage:self.index];
    self.pageTitle.text=[self getLabel:self.index].text;
        self.pageDetail.text=[self getContent:self.index];
    
    }
	// Do any additional setup after loading the view.
   }
-(void)viewDidAppear:(BOOL)animated{
    
    if (self.index==3) {
        //[btn_1 setHidden:NO];
        //[btn_2 setHidden:NO];
        MainViewController*loginViewController=[self.storyboard instantiateViewControllerWithIdentifier:@"login"];
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
-(UIImage*)GetIconImage:(NSUInteger)imageIndex{
    // NSLog(@"%lu",imageIndex);
    //NSLog(@"12345");
    NSMutableArray*imageLibrary=[[NSMutableArray alloc]init];
    UIImage*addImage= [UIImage imageNamed:@"icon3.png"];
    [imageLibrary addObject:addImage];
    UIImage*addImage1= [UIImage imageNamed:@"icon1.png"];
    [imageLibrary addObject:addImage1];
    UIImage*addImage2= [UIImage imageNamed:@"icon2.png"];
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
-(NSString*)getContent:(NSUInteger)labelIndex{
    NSMutableArray*labelLibrary=[[NSMutableArray alloc]init];
    NSString*string=@"The users will make new friends";
    [labelLibrary addObject:string];
    NSString*string1=@"The users will find people who share the same interests with them ";
    [labelLibrary addObject:string1];
    NSString*string2=@"The users will have more fun in their daily life";
    [labelLibrary addObject:string2];
    NSString*currentLabel=[labelLibrary objectAtIndex:labelIndex];
    return currentLabel;
}

@end
