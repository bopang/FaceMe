//
//  CharaterSelectionViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "CharaterSelectionViewController.h"


@interface CharaterSelectionViewController ()

@end

@implementation CharaterSelectionViewController
@synthesize selectViewController;
@synthesize imageUrl;
@synthesize detailImage;
@synthesize  getKey;
@synthesize faceImages;
@synthesize imageTool;
@synthesize buttonView;
@synthesize faceImageViews;


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
    imageTool=[[ImageRoundCorner alloc]init];
    NSLog(@"%@",self.imageUrl);
	// Do any additional setup after loading the view.
    NSMutableString *finalPosterImageString=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/imageResource?key="];
    //[finalPosterImageString appendString:imageUrl];
    
    [imageTool roundCorner:self.detailImage atRadius:8];
    
    AppDelegate*appDelegate=[UIApplication sharedApplication].delegate ;
    
    if(appDelegate.currentPoster.orginalPoster == nil){
        NSData * imageData = [[NSData alloc] initWithContentsOfURL: [NSURL URLWithString:[NSString stringWithFormat:@"https://facemegatech.appspot.com/imageResource?key=%@",appDelegate.currentPoster.originalPosterKey]]];
        appDelegate.currentPoster.orginalPoster = [UIImage imageWithData:imageData];
    }
    
    if(appDelegate.currentPoster.nonfacePoster == nil){
        NSData * imageData = [[NSData alloc] initWithContentsOfURL: [NSURL URLWithString:[NSString stringWithFormat:@"https://facemegatech.appspot.com/imageResource?key=%@",appDelegate.currentPoster.nonfacePosterKey]]];
        appDelegate.currentPoster.nonfacePoster = [UIImage imageWithData:imageData];
    }
    
    self.detailImage.image =  appDelegate.currentPoster.orginalPoster;
    
    NSLog(@"movieName is %@",[[appDelegate currentPoster] movieName]);
    buttonView.layer.shadowOffset = CGSizeMake(0.0f, 1.0f);
    buttonView.layer.shadowColor = [UIColor blackColor].CGColor;
    
    buttonView.layer.shadowOpacity = 1.0f;
    buttonView.layer.shadowRadius = 4.0f;
    
    
    
    if([appDelegate currentPoster].faces == nil){
        NSMutableString *urlString= [[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/_ah/api/characterfaceendpoint/v1/characterfaceinposter/get/"];
        
        //    NSString*getKey=[NSString stringWithFormat:@"%lld", appDelegate.currentPoster.key];
        //    NSLog(@"the key is %lld",[[appDelegate currentPoster]key]);
        [urlString appendString:getKey];
        NSURL *url = [NSURL URLWithString:urlString];
        NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
        
        [request setHTTPMethod:@"GET"];
        //[request setValue:@"*/*" forHTTPHeaderField:@"Accept"];
        
        NSHTTPURLResponse *response = nil;
        NSError *error = nil;
        
        //Make the request
        NSData*responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
        
        //Parse the data as a series of Note objects
        NSDictionary* json = [NSJSONSerialization JSONObjectWithData:responseData options:kNilOptions error:&error];
        //NSLog(@"%@", json);
        
        // Do any additional setup after loading the view.
        // NSDictionary *items = [json objectForKey:@"items"];
        faceImages=[[NSMutableArray alloc] init];
        NSArray *items = [json objectForKey:@"items"];
        for (id item in items) {
            NSString *key =[[item objectForKey:@"key"]objectForKey:@"id"];
            
            CharacterFace*currentFace;
            //currentFace = [[appDelegate mCharacterFaceCache] objectForKey:key];
            //if(!currentFace){
            
            currentFace =[[CharacterFace alloc]init];
            currentFace.key=key;
            currentFace.imageKey=[item objectForKey:@"imageKey"];
            currentFace.name=[item objectForKey:@"name"];
            currentFace.positionX=[[item objectForKey:@"positionX"] floatValue];
            NSLog(@"positionX=%f",currentFace.positionX);
            currentFace.positionY=[[item objectForKey:@"postionY"] floatValue];
            NSLog(@"positionY=%f",currentFace.positionY);
            currentFace.width=[[item objectForKey:@"width"]floatValue];
            NSLog(@"width=%f",currentFace.width);
            currentFace.height=[[item objectForKey:@"height"]floatValue];
            NSLog(@"height=%f",currentFace.height);
            currentFace.index=[[item objectForKey:@"index"]integerValue];
            NSMutableString*imageFace=[[NSMutableString alloc]initWithFormat: @"https://facemegatech.appspot.com/imageResource?key="];
            
            [imageFace appendString:[currentFace imageKey]];
            currentFace.bmp=[UIImage imageWithData:[[NSData alloc]initWithContentsOfURL:[NSURL URLWithString:imageFace]]];
            //}
            [faceImages addObject:currentFace];
            NSLog(@"%d",[faceImages count]);
        }
        [appDelegate currentPoster].faces = faceImages;
    }
    
    faceImageViews = [[NSMutableArray alloc] init];
    for (int i=0; i<[faceImages count];i++) {
        UIImageView*faceImgView=[[UIImageView alloc]initWithImage:[imageTool scaleImage:[[[appDelegate currentPoster].faces objectAtIndex:i]bmp] AtTheRatio:0.65]];
        faceImgView.contentMode = UIViewContentModeCenter;
        faceImgView.clipsToBounds=YES;
        CGRect currentFrame=faceImgView.frame;
        
        currentFrame.size.width=100;
        currentFrame.size.height=100;
        currentFrame.origin.x=((320/[[appDelegate currentPoster].faces count]-currentFrame.size.width)/2)+i*(320/[[appDelegate currentPoster].faces count]);
        currentFrame.origin.y=410;
        
        faceImgView.frame=currentFrame;
        [imageTool roundCorner:faceImgView atRadius:faceImgView.frame.size.width/2];
        [self.view addSubview:faceImgView];
        UITapGestureRecognizer* singleTap=[[UITapGestureRecognizer alloc]init];
        [singleTap addTarget:self action:@selector(chooseCharacter:)];
        faceImgView.userInteractionEnabled=YES;
        [faceImgView addGestureRecognizer:singleTap];
        [faceImageViews addObject:faceImgView];
    }
    
    
    
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (void)chooseCharacter:(UIGestureRecognizer*)gesture {
    //    selectViewController=[[self storyboard]instantiateViewControllerWithIdentifier:@"select"];
    //    [selectViewController.view setFrame:CGRectMake(0, 480, 320, 320)];
    //
    //    selectViewController.view.layer.shadowOffset = CGSizeMake(0.0f, 1.0f);
    //    selectViewController.view.layer.shadowColor = [UIColor blackColor].CGColor;
    //
    //    selectViewController.view.layer.shadowOpacity = 1.0f;
    //    selectViewController.view.layer.shadowRadius = 4.0f;
    //    [self.view addSubview:selectViewController.view];
    //
    //    [UIView animateWithDuration:0.5 animations:^{
    //        [selectViewController.view setTransform:CGAffineTransformMakeTranslation(0, -120)];
    //    }];
    
    UIView *theSuperview = self.view; // whatever view contains your image views
    CGPoint touchPointInSuperview = [gesture locationInView:theSuperview];
    UIView *touchedView = [theSuperview hitTest:touchPointInSuperview withEvent:nil];
    if([touchedView isKindOfClass:[UIImageView class]])
    {
        for(int i=0; i<faceImageViews.count; i++){
            if(touchedView == [faceImageViews objectAtIndex:i]){
                AppDelegate* appDelegate=[UIApplication sharedApplication].delegate ;
                appDelegate.chosenFace = [faceImages objectAtIndex:i];
                break;
            }
        }
        UIViewController* cameraViewController = [[self storyboard] instantiateViewControllerWithIdentifier:@"CameraController"];
        [self.navigationController pushViewController:cameraViewController animated:true];
        
    }
    
}
@end
