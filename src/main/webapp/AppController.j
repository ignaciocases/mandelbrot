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
@import "HNFractalView.j"

@implementation AppController : CPObject
{
    CPWindow    theWindow; //this "outlet" is connected automatically by the Cib
    IBOutlet HNPageView pageView @accessors;
    IBOutlet CPCollectionView fractalCollectionView;
    CPArray images;
}

- (void)applicationDidFinishLaunching:(CPNotification)aNotification
{
    AppController.instance = self;
    //CPLogRegister(CPLogPopup);

    // GUI settings
    var contentView = [theWindow contentView];
    [contentView setBackgroundColor:[CPColor blackColor]];

    var mainBundle = [CPBundle mainBundle];
    var mandelbrotProxyPath = [mainBundle pathForResource:@"FNYZQ5DQQWVUH2I3.png"];
    var mandelbrotProxyImage = [[CPImage alloc] initWithContentsOfFile:mandelbrotProxyPath
                                                                  size:CGSizeMake(512, 512)];

    [CPMenu setMenuBarVisible:NO];
}

- (void)awakeFromCib
{
    // In this case, we want the window from Cib to become our full browser window
    [theWindow setFullPlatformWindow:YES];

    
    [fractalCollectionView setAutoresizingMask:CPViewWidthSizable];
    [fractalCollectionView setMinItemSize:CGSizeMake(100, 100)];
    [fractalCollectionView setMaxItemSize:CGSizeMake(100, 100)];
    [fractalCollectionView setDelegate:self];
    
    images = [[[CPImage alloc]
               initWithContentsOfFile:"Resources/sample4.jpg"
               size:CGSizeMake(512.0, 512.0)],
              [[CPImage alloc]
               initWithContentsOfFile:"Resources/sample5.jpg"
               size:CGSizeMake(512.0, 512.0)]];

    [fractalCollectionView setContent:images];
}

@end
