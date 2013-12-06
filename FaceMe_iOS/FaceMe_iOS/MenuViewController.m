//
//  MenuViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "MenuViewController.h"
#import <FacebookSDK/FacebookSDK.h>
@interface MenuViewController ()

@end

@implementation MenuViewController
@synthesize responseData;
@synthesize newsFeeds;
@synthesize characterFaces;
@synthesize swipeTime;
@synthesize profileView;
@synthesize userName;
@synthesize newsFeed;
@synthesize gender;
@synthesize school;
@synthesize profilePicture;
@synthesize appDelegate;
@synthesize staticImageDictionary;
@synthesize tableview;
@synthesize intro;
@synthesize userFaces;
@synthesize loginViewController;
@synthesize imageWithCorner;
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
    //[self showTabBar];
    imageWithCorner=[[ImageRoundCorner alloc]init];
    [UIApplication sharedApplication].networkActivityIndicatorVisible=YES;
	// Do any additional setup after loading the view.
  
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:nil
                                                 name:@"FaceMe"
                                                 object:nil];
    
    
    NSString*photoUrl=@"https://facemegatech.appspot.com/_ah/api/newsendpoint/v1/news/list";
    //NSString*posterUrl=@"https://facemegatech.appspot.com/_ah/api/posterendpoint/v1/poster/list";
    //Parse the data as a series of Note objects
    appDelegate=[UIApplication sharedApplication].delegate;

    //NSLog(@"%@", json);
    swipeTime=NO;
    newsFeeds=[[NSMutableArray alloc]init];
    userName.text=appDelegate.currentUser.username;
    school.text=appDelegate.currentUser.school;
    gender.text=appDelegate.currentUser.gender;
    
    NSString*imageUrl=appDelegate.currentUser.profilePicUrl;
    NSData * imageData = [[NSData alloc] initWithContentsOfURL: [NSURL URLWithString: imageUrl]];
    profilePicture.image = [UIImage imageWithData: imageData];
    [imageWithCorner roundCorner:profilePicture atRadius:45];
    NSDictionary* jsonForNewFeeds = [self getData:photoUrl];
    NSLog(@"%@",jsonForNewFeeds);
       appDelegate.mCharacterFaceCache=[[NSMutableDictionary alloc]init];
    appDelegate.mUserFaceCache=[[NSMutableDictionary alloc]init];
    
    NSArray* newsfeeds = [jsonForNewFeeds objectForKey:@"items"];
    newsFeed=[[NewsFeedEntity alloc]init];
    newsFeed.userfaces=[[NSMutableArray alloc]init];
    newsFeed.characters=[[NSMutableArray alloc]init];
    for (id newfeed in newsfeeds ){
        
        NSString*posterKey=[newfeed objectForKey:@"posterKey"];
        NSString*orginalPosterImageKey=[newfeed objectForKey:@"originalPosterImageKey"];
        NSString*nonfacePosterImageKey=[newfeed objectForKey:@"nonfacePosterImageKey"];
        NSString*movieName=[newfeed objectForKey:@"movieName"];
        NSString*posterName=[newfeed objectForKey:@"posterName"];
        NSString*updateTime=@"";
        NSArray* userfaces = [newfeed objectForKey:@"userfaces"];
        UserFaceEntity*friendFeed=[[ UserFaceEntity alloc]init];
        for(id item in userfaces){
            
            friendFeed.userID=[item objectForKey:@"userID"];
            friendFeed.imageKey=[item objectForKey:@"imageKey"];
            friendFeed.posterKey=[item objectForKey:@"posterKey"];
            friendFeed.characterKey=[item objectForKey:@"characterKey"];
           
            friendFeed.ID=[[item objectForKey:@"key"]objectForKey:@"id"];
            NSLog(@"%@",friendFeed.ID);
            NSMutableString*faceString=[[NSMutableString alloc] initWithFormat: @"https://facemegatech.appspot.com/imageResource?key="];
            [faceString appendString:friendFeed.imageKey];
            NSURL*faceUrl=[[NSURL alloc]initWithString:faceString];
            friendFeed.userFaceBmp=[UIImage imageWithData:[[NSData alloc]initWithContentsOfURL:faceUrl]];
            [appDelegate.mUserFaceCache setObject:friendFeed forKey:friendFeed.ID];
            NSLog(@"%d",[appDelegate.mUserFaceCache count]);
            [newsFeed.userfaces addObject:friendFeed.ID ];
             NSLog(@"%@",friendFeed.ID);
            
            [newsFeed.userNames addObject:friendFeed.userID];
            
        }
        NSArray* characters = [newfeed objectForKey:@"characters"];
        for(id item1 in characters){
            CharacterFace*characterFace=[[CharacterFace alloc]init];
            characterFace.key=[[item1 objectForKey:@"key"]objectForKey:@"id"];
            characterFace.imageKey=[item1 objectForKey:@"imageKey"];
            characterFace.name=[item1 objectForKey:@"name"];
            characterFace.positionX=[[item1 objectForKey:@"positionX"]floatValue];
            characterFace.positionY=[[item1 objectForKey:@"postionY"]floatValue];
            NSLog(@"%f",characterFace.positionY);
            characterFace.width=[[item1 objectForKey:@"width"]floatValue];
            characterFace.height=[[item1 objectForKey:@"height"]floatValue];
            characterFace.index=[[item1 objectForKey:@"index"]integerValue];
            characterFace.posterID=posterKey;
            NSMutableString*faceString=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/imageResource?key="];
            [faceString appendString:characterFace.imageKey];
            NSURL*faceurl=[[NSURL alloc]initWithString:faceString];
            characterFace.bmp=[UIImage imageWithData:[[NSData alloc] initWithContentsOfURL:faceurl]];
            [appDelegate.mCharacterFaceCache setObject:characterFace forKey:characterFace.key];
            [newsFeed.characters addObject:characterFace.key];
        }
       
        newsFeed.posterKey=posterKey;
        newsFeed.orignalPosterImageKey=orginalPosterImageKey;
        newsFeed.nonfacePosterImagekey=nonfacePosterImageKey;
        newsFeed.movieName=movieName;
        newsFeed.posterName=posterName;
        newsFeed.updateDate=updateTime;
//        newsFeed.userfaces=userFaces;
//        newsFeed.characters=characterFaces;
        NSMutableString*newsFeedString=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/imageResource?key="];
        NSMutableString*newsFeedString1=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/imageResource?key="];
        [newsFeedString appendString:nonfacePosterImageKey];
        [newsFeedString1 appendString:orginalPosterImageKey];
        NSURL*newsNonfaceURL=[[NSURL alloc]initWithString:newsFeedString];
        NSURL*newsNonfaceURL1=[[NSURL alloc]initWithString:newsFeedString1];
        
        newsFeed.nonfacePosterBmp=[UIImage imageWithData:[[NSData alloc] initWithContentsOfURL:newsNonfaceURL]];
        newsFeed.orginalPosterBmp=[UIImage imageWithData:[[NSData alloc] initWithContentsOfURL:newsNonfaceURL1]];
        [newsFeeds addObject:newsFeed];
         NSLog(@"%@",[newsFeed.userfaces objectAtIndex:0]);
    }
    
    [self.tableview setSeparatorStyle:UITableViewCellSeparatorStyleSingleLineEtched];
    
    UISwipeGestureRecognizer *swipeLeft = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipe:)];
    [swipeLeft setDirection:UISwipeGestureRecognizerDirectionLeft];
    UISwipeGestureRecognizer *swipeRight = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(handleSwipe:)];
    [swipeRight setDirection:UISwipeGestureRecognizerDirectionRight];
    [self.view addGestureRecognizer:swipeRight];
    [self.view addGestureRecognizer:swipeLeft];
    self.profileView.layer.shadowOffset = CGSizeMake(0.0f, 1.0f);
    self.profileView.layer.shadowColor = [UIColor blackColor].CGColor;
   
    self.profileView.layer.shadowOpacity = 1.0f;
    self.profileView.layer.shadowRadius = 4.0f;
    
//    [[UITabBar appearance] setBarTintColor:[UIColor colorWithRed:77.0/255.0f green:104.0/255.0f blue:159.0/255.0f alpha:1.0]];
    self.navigationController.navigationBar.layer.shadowOffset = CGSizeMake(0.0f, 1.0f);
    self.navigationController.navigationBar.layer.shadowColor = [UIColor blackColor].CGColor;
    
    self.navigationController.navigationBar.layer.shadowOpacity = 1.0f;
    self.navigationController.navigationBar.layer.shadowRadius = 4.0f;

}
- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}


-(UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath{
   
    static NSString *NewFeedIdentifier = @"NewFeedTableCell";
    
     NewFeedCell*cell = (NewFeedCell *)[tableView dequeueReusableCellWithIdentifier:NewFeedIdentifier];
    
    NewsFeedEntity * news = [[self newsFeeds]objectAtIndex:indexPath.row];
    
    if(news.userfaces.count == 1){
        NSString* username = [news.userNames objectAtIndex:0];
        cell.username.text = [NSString stringWithFormat:@"%@ upload a new photo",username];
    }
    else{
        NSMutableString* text = [[NSMutableString alloc] init];
        [text appendString:[news.userNames objectAtIndex:0]];
        [text appendString:@"takes a new photo with"];
        for (int i=1; i<news.userNames.count; i++){
            [text appendString:[news.userNames objectAtIndex:i]];
            [text appendString:@" "];
        }
        cell.username.text = text;
    }
    //cell.uploadedImage.contentMode = UIViewContentModeCenter;
    cell.uploadedImage.clipsToBounds = YES;
    //cell.uploadedImage.image=[imageWithCorner scaleImage:[self combineImage:[[self newsFeeds]objectAtIndex:indexPath.row]]AtTheRatio:0.4];
    if(news.cosplayBmp == nil){
        news.cosplayBmp = [self combineImage:news];
    }
    cell.uploadedImage.image = news.cosplayBmp;
    
    [imageWithCorner roundCorner:cell.uploadedImage atRadius:3];
    UIImage *background = [UIImage imageNamed:@"cell_weibo1.png"];
    
    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    cellBackgroundView.image = background;
    cell.backgroundView = cellBackgroundView;
    return cell;
}
- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	NSLog(@"newsFeeds are");
    NSLog(@"%d",[newsFeeds count]);
    return [ newsFeeds count];
}

-(NSDictionary*)getData:(NSString*)urlInput
{
    NSURL *newUrl = [NSURL URLWithString:urlInput];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:newUrl];
    
    [request setHTTPMethod:@"GET"];
    //[request setValue:@"*/*" forHTTPHeaderField:@"Accept"];
    
    NSHTTPURLResponse *response = nil;
    NSError *error = nil;
    //Make the request
    NSData*retrivedData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    NSDictionary*currentJson=[NSJSONSerialization JSONObjectWithData:retrivedData options:kNilOptions error:&error];
    return currentJson;

}
- (UIImage*)imageNamedCached:(NSString*)imageNamed
{
    
    if (staticImageDictionary == nil){
        staticImageDictionary = [NSMutableDictionary new];}
    UIImage* retImage = [staticImageDictionary objectForKey:imageNamed];
    if (retImage == nil)
    {
        retImage = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:imageNamed]]];
        
        [staticImageDictionary setObject:retImage forKey:imageNamed];
        
    }
       // NSLog(@"rounded corner");
    return retImage;
}

- (IBAction)logout:(id)sender {
     [FBSession.activeSession closeAndClearTokenInformation];
    intro=[[self storyboard]instantiateViewControllerWithIdentifier:@"intro"];
    [self presentViewController:intro animated:YES completion:nil];
    IntroductionViewController*intro1=[self.storyboard instantiateViewControllerWithIdentifier:@"intro"];
    [self presentViewController:intro1 animated:YES completion:nil];
}
- (void)handleSwipe:(UISwipeGestureRecognizer *)swipe {
    NSLog(@"get gesture");
    if (swipeTime==NO&&swipe.direction==UISwipeGestureRecognizerDirectionLeft) {
        NSLog(@"Left Swipe");
        CGRect newFrame = self.profileView.frame;
        newFrame.origin.x -= 300;    // shift right by 500pts
        [UIView animateWithDuration:0.5
                         animations:^{
                             self.profileView.frame = newFrame;
                         }
                         completion:^(BOOL finished){swipeTime=YES;}];

    }
    if (swipeTime==YES&&swipe.direction==UISwipeGestureRecognizerDirectionRight) {
        CGRect newFrame = self.profileView.frame;
        newFrame.origin.x += 300;    // shift right by 500pts
        [UIView animateWithDuration:0.5
                         animations:^{
                             self.profileView.frame = newFrame;
                         }
                         completion:^(BOOL finished){swipeTime=NO;}];

    }
        
    }
-(UIImage*)combineImage:(NewsFeedEntity*)currentNewsFeed{
    
    UIGraphicsBeginImageContext(currentNewsFeed.nonfacePosterBmp.size);
    
    
    int posterWidth = currentNewsFeed.nonfacePosterBmp.size.width;
    int posterHeight = currentNewsFeed.nonfacePosterBmp.size.height;
    
    for (NSString *chrarcterface in currentNewsFeed.characters){
        CharacterFace *currentCharacter =[[appDelegate mCharacterFaceCache]objectForKey:chrarcterface];
        [currentCharacter.bmp drawInRect:CGRectMake(currentCharacter.positionX * posterWidth, currentCharacter.positionY * posterHeight, currentCharacter.width * posterWidth, currentCharacter.height * posterHeight)];
    }
    
   for (int i=0; i< [currentNewsFeed.userfaces count];i++) {
 
       UserFaceEntity* currentUserEntity= [[appDelegate mUserFaceCache]objectForKey:[currentNewsFeed.userfaces objectAtIndex:i]] ;
       CharacterFace*currentCharacter=[[appDelegate mCharacterFaceCache]objectForKey:currentUserEntity.characterKey];
       
      [currentUserEntity.userFaceBmp drawInRect:CGRectMake( currentCharacter.positionX * posterWidth, currentCharacter.positionY * posterHeight, currentCharacter.width * posterWidth, currentCharacter.height * posterHeight)];
   }

    [currentNewsFeed.nonfacePosterBmp drawInRect:CGRectMake(0, 0, currentNewsFeed.nonfacePosterBmp.size.width,currentNewsFeed.nonfacePosterBmp.size.height)];
    
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return newImage;
}
- (IBAction)showProfileButton:(UIButton *)sender {
    if (swipeTime==NO) {
        NSLog(@"Left Swipe");
        CGRect newFrame = self.profileView.frame;
        newFrame.origin.x -= 300;    // shift right by 500pts
        [UIView animateWithDuration:1
                         animations:^{
                             self.profileView.frame = newFrame;
                         }
                         completion:^(BOOL finished){swipeTime=YES;}];
        
    }
    if (swipeTime==YES) {
        CGRect newFrame = self.profileView.frame;
        newFrame.origin.x += 300;    // shift right by 500pts
        [UIView animateWithDuration:1
                         animations:^{
                             self.profileView.frame = newFrame;
                         }
                         completion:^(BOOL finished){swipeTime=NO;}];
        
    }
}

-(void)viewDidAppear:(BOOL)animated{
    NSMutableString*urlString=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/_ah/api/notificationendpoint/v1/notification/getallnotification/"];
    [urlString appendFormat:appDelegate.currentUser.username];
    NSDictionary*jsonData=[self getData:urlString];
    NSArray*json=[jsonData objectForKey:@"items"];
    if (json!=nil) {
        [[NSNotificationCenter defaultCenter] postNotificationName:@"FaceMe"
                                                            object:nil
                                                          ];
    }
   
}
@end
