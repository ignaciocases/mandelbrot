/*
 * AppController.j
 * Mandelbrot
 *
 * Created by Ignacio Cases on May 26, 2011.
 * Copyright 2011, HnLab All rights reserved.
 */

@import <Foundation/CPObject.j>
@import "HNFractalInspector.j"
@import "HNPageView.j"

@implementation AppController : CPObject
{
    CPWindow    theWindow; //this "outlet" is connected automatically by the Cib
    IBOutlet HNPageView pageView @accessors;

}

- (void)applicationDidFinishLaunching:(CPNotification)aNotification
{
    AppController.instance = self;
    CPLogRegister(CPLogPopup);

    // GUI settings
    var contentView = [theWindow contentView];
    [contentView setBackgroundColor:[CPColor blackColor]];

    //var fractalInspector = [[HNFractalInspector alloc] init];
    
}

- (void)updateWithImageUrl:(CPString)imageUrl {
//    image1 = [[CPImage alloc] initWithContentsOfFile:imageUrl size:CGSizeMake(1024, 1024)];
//    CPLog.debug(imageUrl);
//    [imageView setImage:image1];
}

- (void)awakeFromCib
{
    // In this case, we want the window from Cib to become our full browser window
    [theWindow setFullPlatformWindow:YES];
    
}

@end

function imageCallback(imageUrl) {
    [AppController.instance updateWithImageUrl:imageUrl];
}