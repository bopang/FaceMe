//
//  NewFeedCell.m
//  FaceMe_iOS
//
//  Created by ShawnWu on 11/8/13.
//  Copyright (c) 2013 Bo Pang. All rights reserved.
//

#import "NewFeedCell.h"

@implementation NewFeedCell
@synthesize username;
@synthesize uploadedImage;
@synthesize commentView;
@synthesize mainStoryboard;
- (id)initWithStyle:(UITableViewCellStyle)style reuseIdentifier:(NSString *)reuseIdentifier
{
    self = [super initWithStyle:style reuseIdentifier:reuseIdentifier];
    if (self) {
        // Initialization code
    }
    return self;
}

- (void)setSelected:(BOOL)selected animated:(BOOL)animated
{
    [super setSelected:selected animated:animated];

    // Configure the view for the selected state
}
- (IBAction)playGame:(id)sender {
    
}

- (IBAction)rate:(id)sender {
}

- (IBAction)comment:(id)sender {
    self.mainStoryboard=[UIStoryboard storyboardWithName:@"Main_iPhone" bundle:nil];
    commentView=[[self mainStoryboard]instantiateViewControllerWithIdentifier:@"comment"];
    
}

@end
