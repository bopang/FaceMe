//
//  ShareViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/14/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "ShareViewController.h"
#import <Social/Social.h>

@interface ShareViewController ()

@end

@implementation ShareViewController
@synthesize imageForPost;
@synthesize getImage;
@synthesize userface;

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

-(UIImage*)combineImage{
    AppDelegate* appDelegate = [UIApplication sharedApplication].delegate;
    
    UIGraphicsBeginImageContext(appDelegate.currentPoster.nonfacePoster.size);

    int posterWidth = appDelegate.currentPoster.nonfacePoster.size.width;
    int posterHeight = appDelegate.currentPoster.nonfacePoster.size.height;
    
    for (CharacterFace *chrarcterface in appDelegate.currentPoster.faces){
        [chrarcterface.bmp drawInRect:CGRectMake(chrarcterface.positionX * posterWidth, chrarcterface.positionY * posterHeight, chrarcterface.width * posterWidth, chrarcterface.height * posterHeight)];
    }
    
    CharacterFace* currentCharacter = appDelegate.chosenFace;
    [self.userface drawInRect:CGRectMake( currentCharacter.positionX * posterWidth, currentCharacter.positionY * posterHeight, currentCharacter.width * posterWidth, currentCharacter.height * posterHeight)];
    
    [appDelegate.currentPoster.nonfacePoster drawInRect:CGRectMake(0, 0, posterWidth, posterHeight)];
    
    UIImage *newImage = UIGraphicsGetImageFromCurrentImageContext();
    UIGraphicsEndImageContext();
    
    return newImage;
}


- (void)viewDidLoad
{
    [super viewDidLoad];
	// Do any additional setup after loading the view.
    AppDelegate* appDelegate=[UIApplication sharedApplication].delegate ;
    
    imageForPost.image = [self combineImage];
    NSLog(@"!!!!!!!!!!");
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)facebookIcon:(id)sender {
    if([SLComposeViewController isAvailableForServiceType:SLServiceTypeFacebook]) {
        SLComposeViewController *controller = [SLComposeViewController composeViewControllerForServiceType:SLServiceTypeFacebook];
        NSLog(@"facebook");
        [controller setInitialText:@"First post from my iPhone app"];
        [self presentViewController:controller animated:YES completion:Nil];
    }
    
}

- (IBAction)twitterIcon:(id)sender {
    if ([SLComposeViewController isAvailableForServiceType:SLServiceTypeTwitter])
    {
        SLComposeViewController *tweetSheet = [SLComposeViewController
                                               composeViewControllerForServiceType:SLServiceTypeTwitter];
        [tweetSheet setInitialText:@"Great fun to learn iOS programming at appcoda.com!"];
        [self presentViewController:tweetSheet animated:YES completion:nil];
    }
    
}

- (IBAction)uploadIcon:(id)sender {
    [self uploadUserFaceEntity: [self uploadImage:[self getUploadUrl]]];
    
}
-(NSString*)getUploadUrl
{
    NSURL *url = [NSURL URLWithString:@"https://facemegatech.appspot.com/_ah/api/originalposterendpoint/v1/originalposter/url"];
    NSMutableURLRequest *request = [NSMutableURLRequest requestWithURL:url];
    
    [request setHTTPMethod:@"GET"];
    //[request setValue:@"*/*" forHTTPHeaderField:@"Accept"];
    
    NSHTTPURLResponse *response = nil;
    NSError *error = nil;
    
    //Make the request
    NSData*responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    
    //Parse the data as a series of Note objects
    NSDictionary* json = [NSJSONSerialization JSONObjectWithData:responseData options:kNilOptions error:&error];
    NSString*jsonData=[json objectForKey:@"url"];
    NSLog(@"%@", jsonData);
    return jsonData;
}
-(NSString*)uploadImage:(NSString*)imgUrl
{
    NSMutableString *faceKey;
    NSData *imageData = UIImagePNGRepresentation(self.userface);
    //NSString *postLength = [NSString stringWithFormat:@"%lu", (unsigned long)[imageData length]];
    NSLog(@"size: %d", [imageData length]);
    // Init the URLRequest
    NSString *boundary = @"----------V2ymHFg022223333ss3ehbqgZCaKO6jy";
    
    // string constant for the post parameter 'file'. My server uses this name: `file`. Your's may differ
    NSString* FileParamConstant = @"file";
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    //    [request setCachePolicy:NSURLRequestReloadIgnoringLocalCacheData];
    //    [request setHTTPShouldHandleCookies:NO];
    //    [request setTimeoutInterval:30];
    [request setHTTPMethod:@"POST"];
    NSString *contentType = [NSString stringWithFormat:@"multipart/form-data; boundary=%@", boundary];
    [request setValue:contentType forHTTPHeaderField: @"Content-Type"];
    NSMutableData *body = [NSMutableData data];
    if (imageData) {
        [body appendData:[[NSString stringWithFormat:@"--%@\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
        [body appendData:[[NSString stringWithFormat:@"Content-Disposition: form-data; name=\"%@\"; filename=\"image.jpg\"\r\n", FileParamConstant] dataUsingEncoding:NSUTF8StringEncoding]];
        [body appendData:[@"Content-Type: image/jpeg\r\n\r\n" dataUsingEncoding:NSUTF8StringEncoding]];
        [body appendData:imageData];
        [body appendData:[[NSString stringWithFormat:@"\r\n"] dataUsingEncoding:NSUTF8StringEncoding]];
    }
    [body appendData:[[NSString stringWithFormat:@"--%@--\r\n", boundary] dataUsingEncoding:NSUTF8StringEncoding]];
    
    [request setHTTPBody:body];
    //[request setHTTPBody:imageData];
    NSString *postLength = [NSString stringWithFormat:@"%d", [body length]];
    [request setValue:postLength forHTTPHeaderField:@"Content-Length"];
    [request setURL:[NSURL URLWithString:[NSString stringWithString:imgUrl]]];
    NSURLConnection *connection = [[NSURLConnection alloc] initWithRequest:request delegate:self];
    
    if (connection) {
        // response data of the request
        NSHTTPURLResponse *response = nil;
        NSError *error = nil;
        NSData*responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];NSLog(@"%@",responseData);
        
        faceKey=[[NSString alloc]initWithData:responseData encoding:NSUTF8StringEncoding];
        NSLog(@"%@", faceKey);
        //NSLog(@"%@", responseData);
    }
    return faceKey;
    
}
-(void)uploadUserFaceEntity:(NSString*)facekey
{
    NSURL*postUrl=[[NSURL alloc]initWithString:@"https://facemegatech.appspot.com/_ah/api/userfaceendpoint/v1/userface/insert"];
    NSMutableURLRequest *request = [[NSMutableURLRequest alloc] init];
    [request setHTTPMethod:@"POST"];
    [request setURL:postUrl];
    NSDictionary*faceEntity=[NSDictionary dictionaryWithObject:facekey forKey:@"imageKey"];
    NSData* jsonData = [NSJSONSerialization dataWithJSONObject:faceEntity
                                                       options:NSJSONWritingPrettyPrinted error:nil];
    [request setValue:@"application/json" forHTTPHeaderField:@"Content-Type"];
    [request setHTTPBody:jsonData];
    NSURLConnection *connection = [[NSURLConnection alloc] initWithRequest:request delegate:self];
    NSHTTPURLResponse *response = nil;
    NSError *error = nil;
    NSData*responseData = [NSURLConnection sendSynchronousRequest:request returningResponse:&response error:&error];
    //NSLog(@"%@",responseData);
    NSString*data=[[NSString alloc]initWithData:responseData encoding:NSUTF8StringEncoding];
    NSLog(@"%@", data);
    
    
}
@end
