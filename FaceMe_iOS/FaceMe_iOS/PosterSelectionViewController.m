//
//  MovieSelectionViewController.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/2/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "PosterSelectionViewController.h"
#import"PosterCell.h"
@interface PosterSelectionViewController ()

@end

@implementation PosterSelectionViewController
@synthesize posterTable;
@synthesize theKey;
@synthesize posters;
@synthesize roundCorner;
@synthesize staticImageDictionary;
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
    roundCorner=[[ImageRoundCorner alloc]init];
    // UIEdgeInsets inset = UIEdgeInsetsMake(5, 0, 0, 0);
    //self.posterTable.contentInset = inset;
    NSURL *url = [NSURL URLWithString:@"https://facemegatech.appspot.com/_ah/api/posterendpoint/v1/poster/list"];
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
    posters=[[NSMutableArray alloc] init];
	// Do any additional setup after loading the view.
    // NSDictionary *items = [json objectForKey:@"items"];
    
    NSArray *items = [json objectForKey:@"items"];
    for (id item in items){
        PosterEntity *currentPoster=[[PosterEntity alloc]init];
        currentPoster.movieName =[item objectForKey:@"movieName"];
        NSLog(@"%@", currentPoster.movieName);
        currentPoster.thumbnailKey=[item objectForKey:@"thumbnailKey"];
        NSLog(@"%@", currentPoster.thumbnailKey);
        currentPoster.key=[[item objectForKey:@"key"] objectForKey:@"id"];
        NSLog(@"id= %@", currentPoster.key);
        
        currentPoster.posterName=[item objectForKey:@"posterName"];
        currentPoster.nonfacePosterKey=[item objectForKey:@"nonfacePosterKey"];
        currentPoster.originalPosterKey=[item objectForKey:@"originalPosterKey"];
        currentPoster.classification=[item objectForKey:@"classification"];
        [posters addObject:currentPoster];
        
        
    }
    AppDelegate*appDelegate=[UIApplication sharedApplication].delegate ;
    appDelegate.loadedPoster=posters;
    //    for (id key in json) {
    //            NSLog(@"%@", key);
    //        SinglePoster *currentPoster=[[SinglePoster alloc]init];
    //        NSDictionary *subDictionary = [json objectForKey:@"items"];
    //        currentPoster.posterTitle =[subDictionary objectForKey:@"posterName"];
    //        NSLog(@"%@", currentPoster.posterTitle );
    //        //NSString *image= [json objectForKey:@"urlpath"];
    //        // do stuff
    //        [posters addObject:currentPoster];
    
    //    }
    
    self.posterTable.separatorColor = [UIColor clearColor];
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
- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath
{
    static NSString *simpleTableIdentifier = @"SimpleTableCell";
    
    PosterCell *cell = (PosterCell *)[tableView dequeueReusableCellWithIdentifier:simpleTableIdentifier];
    
    cell.posterTitle.text = ((PosterEntity *)[posters objectAtIndex:indexPath.row]).movieName;
    NSLog(@"%ld",(long)indexPath.row);
    NSLog(@"%@",cell.posterTitle.text);
    NSString* posterImageString=((PosterEntity *)[posters objectAtIndex:indexPath.row]).thumbnailKey;
    NSMutableString *finalPosterImageString=[[NSMutableString alloc]initWithFormat:@"https://facemegatech.appspot.com/imageResource?key="];
    [finalPosterImageString appendString:posterImageString];
    cell.posterPicture.contentMode=UIViewContentModeCenter;
    cell.posterPicture.clipsToBounds=YES;
    [roundCorner roundCorner:cell.posterPicture atRadius:3];
    cell.posterPicture.image= [roundCorner scaleImage:[self imageNamedCached:finalPosterImageString] AtTheRatio:1];
    UIImage *background = [UIImage imageNamed:@"postCell.png"];
    
    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    //cellBackgroundView.image = background;
    cell.backgroundView = cellBackgroundView;
    
    
    //cell.posterTitle.text=@"1234";
    //cell.thumbnailImageView.image = [UIImage imageNamed:[thumbnails objectAtIndex:indexPath.row]];
    // cell.posterDetail.text = [prepTime objectAtIndex:indexPath.row];
    
    //    UIImage *background = [self cellBackgroundForRowAtIndexPath:indexPath];
    //
    //
    //    UIImageView *cellBackgroundView = [[UIImageView alloc] initWithImage:background];
    //    cellBackgroundView.image = background;
    //    cell.backgroundView = cellBackgroundView;
    
    return cell;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	
    return [posters count];
}

//-(void)viewWillAppear:(BOOL)animated{
//    [super viewWillAppear:animated];
//    [self.navigationController setNavigationBarHidden:YES animated:animated];
//}
//- (void) viewWillDisappear:(BOOL)animated
//{
//    [super viewWillDisappear:animated];
//    [self.navigationController setNavigationBarHidden:NO animated:animated];
//}

//-(void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
//    CharaterSelectionViewController*controller=[self.storyboard instantiateViewControllerWithIdentifier:@"character"];
//    controller.imageUrl=((SinglePoster *)[posters objectAtIndex:indexPath.row]).posterImage;
//    //NSLog(@"%@",controller.imageUrl);
//    [self.navigationController pushViewController:controller animated:YES];
//}


- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([segue.identifier isEqualToString:@"character"]) {
        NSIndexPath *indexPath = [self.posterTable indexPathForSelectedRow];
        NSLog(@"%ld",(long)indexPath.row);
        CharaterSelectionViewController*controller = segue.destinationViewController;
        controller.imageUrl = ((PosterEntity *)[posters objectAtIndex:indexPath.row]).thumbnailKey;
        self.theKey=[(PosterEntity*)[posters objectAtIndex:indexPath.row]key];
        NSLog(@"the key is %@",theKey);
        
        controller.getKey=self.theKey;
        AppDelegate*appDelegate=[UIApplication sharedApplication].delegate ;
        appDelegate.currentPoster= [posters objectAtIndex:indexPath.row];
    }
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
    
    return retImage;
}

@end
