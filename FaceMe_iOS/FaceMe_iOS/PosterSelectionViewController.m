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
@synthesize posterDetails;
@synthesize posterImages;
@synthesize posterTitles;

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
    NSLog(@"%@", json);
    posterTitles=[NSMutableArray array];
    posterImages=[NSMutableArray array];
	// Do any additional setup after loading the view.
    for (id key in json) {
        NSString* title = [json objectForKey:@"posterName"];
        [posterTitles addObject:title];
        //NSString *image= [json objectForKey:@"urlpath"];
        // do stuff
        
    }
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
    
    //cell.posterTitle.text = [posterTitles objectAtIndex:indexPath.row];
    cell.posterTitle.text=@"1234";
    //cell.thumbnailImageView.image = [UIImage imageNamed:[thumbnails objectAtIndex:indexPath.row]];
   // cell.posterDetail.text = [prepTime objectAtIndex:indexPath.row];
    
   
    return cell;
}


- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section
{
	
    return 1;
}

@end
