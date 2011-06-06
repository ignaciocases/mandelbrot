/*
 *	HNPageView.j
 *  webapp
 *
 *  Created by tahiche on 05/06/11.
 *  Copyright 2011 280 North, Inc. All rights reserved.
 */

@import <AppKit/AppKit.j>
@import <Foundation/Foundation.j>
@import "HNPaneLayer.j"
@import "HNFractalInspector.j"

@implementation HNPageView : CPView
{
    CALayer rootLayer;
    CALayer borderLayer;
    HNPaneLayer paneLayer;
}

- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
        rootLayer = [CALayer layer];
        [self setWantsLayer:YES];
        [self setLayer:rootLayer];
        [rootLayer setBackgroundColor:[CPColor whiteColor]];
        
        paneLayer = [[HNPaneLayer alloc] initWithPageView:self];
        
        // load a proxy image to show something
        // when the application starts
        var mainBundle = [CPBundle mainBundle];
        var mandelbrotProxyPath = [mainBundle pathForResource:@"FNYZQ5DQQWVUH2I3.png"];
        var mandelbrotProxyImage = [[CPImage alloc] initWithContentsOfFile:mandelbrotProxyPath
                                                                      size:CGSizeMake(512, 512)];
        
        [paneLayer setBounds:CGRectMake(0,0,512,512)];
        [paneLayer setAnchorPoint:CGPointMakeZero()];
        [paneLayer setPosition:CGPointMake(0,0)];
        [paneLayer setImage:mandelbrotProxyImage];
                
        [rootLayer addSublayer:paneLayer];
        
        borderLayer = [CALayer layer];
        [borderLayer setAnchorPoint:CGPointMakeZero()];
        [borderLayer setBounds:[paneLayer bounds]];
        [borderLayer setDelegate:self];

        [rootLayer addSublayer:borderLayer];
        
        [paneLayer setNeedsDisplay];
        [rootLayer setNeedsDisplay];
    }
    return self;
}

- (void)drawRect:(CPRect)aRect {
    // Drawing code here.
}

- (void)drawLayer:(CALayer)aLayer inContext:(CGContext)aContext {
    CGContextSetFillColor(aContext, [CPColor grayColor]);

    var bounds = [aLayer bounds],
        width = CGRectGetWidth(bounds),
        height = CGRectGetHeight(bounds);
    
    CGContextFillRect(aContext, CGRectMake(0, 0, width, 40));
    CGContextFillRect(aContext, CGRectMake(0, 40, 
                                           40, height - 2 * 40));
    CGContextFillRect(aContext, CGRectMake(width - 40, 40, 
                                           40, height - 2 * 40));
    CGContextFillRect(aContext, CGRectMake(0, height - 40, 
                                           width, 40));
}

- (void)setEditing:(BOOL)isEditing {
    [borderLayer setOpacity:isEditing ? 0.5 : 1.0];
}

- (void)mouseDown:(CPEvent)anEvent {
    if ([anEvent clickCount] == 2) {
        [HNFractalInspector inspectPaneLayer:paneLayer];
    }
}

@end