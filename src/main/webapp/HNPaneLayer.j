/*
 *	HNPaneLayer.j
 *  webapp
 *
 *  Created by tahiche on 05/06/11.
 *  Copyright 2011 280 North, Inc. All rights reserved.
 */

@import <Foundation/Foundation.j>
@import "HNPageView.j"

@implementation HNPaneLayer : CALayer
{
    float rotationRadians;
    float scale;
    
    CPImage image;
    CALayer imageLayer;
    
    HNPageView pageView;
}

- (id)initWithPageView:(HNPageView)aPageView
{
    self = [super init];
    if (self) 
	{
        pageView = aPageView;
        rotationRadians = 0.0;
        scale = 1.0;
        
        imageLayer = [CALayer layer];
        [imageLayer setDelegate:self];
        [self addSublayer:imageLayer];
    }
    
    return self;
}

- (HNPageView)pageView {
    return pageView;
}

- (void)setBounds:(CGRect)aRect {
    [super setBounds:aRect];
    [imageLayer setPosition:CGPointMake(CGRectGetMidX(aRect),
                                        CGRectGetMidY(aRect))];
}

- (void)setImage:(CPImage)anImage {
    if (image == anImage)
        return;
    image = anImage;

    if (image)
        [imageLayer setBounds:CGRectMake(0,0,
                                         [image size].width, 
                                         [image size].height)];
    [imageLayer setNeedsDisplay];
}

- (void)setRotationRadians:(float)radians {
    if (rotationRadians == radians)
        return;
    rotationRadians = radians;
    [imageLayer setAffineTransform:CGAffineTransformScale(CGAffineTransformMakeRotation(rotationRadians), scale, scale)];
}

- (void)setScale:(float)aScale {
    if (scale == aScale) 
        return;
    scale = aScale;
    [imageLayer setAffineTransform:CGAffineTransformScale(CGAffineTransformMakeRotation(rotationRadians), scale, scale)];    
}

- (void)drawInContext:(CGContext)aContext {
    CGContextSetFillColor(aContext, [CPColor grayColor]);
    CGContextFillRect(aContext, [self bounds]);
}

- (void)imageDidLoad:(CPImage)anImage {
    [imageLayer setNeedsDisplay];
}

- (void)drawLayer:(CALayer)aLayer inContext:(CGContext)aContext {
    var bounds = [aLayer bounds];
    if ([image loadStatus] != CPImageLoadStatusCompleted) {
        [image setDelegate:self];
    } else {
        CGContextDrawImage(aContext, bounds, image);
    }
}

@end