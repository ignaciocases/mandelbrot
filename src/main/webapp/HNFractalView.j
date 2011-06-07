/*
 *	HNFractalView.j
 *  webapp
 *
 *  Created by tahiche on 07/06/11.
 *  Copyright 2011 280 North, Inc. All rights reserved.
 */

@import <AppKit/AppKit.j>
@import <Foundation/Foundation.j>
@import "HNPaneLayer.j"

@implementation HNFractalView : CPView
{
    IBOutlet CPImageView imageView @accessors;
    IBOutlet id delegate;
}

- (void)setRepresentedObject:(id)anObject {
    [imageView setImage:anObject];
    [imageView setHasShadow:YES];
}

- (void)setSelected:(BOOL)isSelected {

    [self setBackgroundColor:isSelected ? 
     [CPColor colorWithCalibratedRed:0.357 green:0.627 blue:0.851 alpha:1.000]                               : nil];
}

- (void)mouseDown:(CPEvent)anEvent {
    if ([anEvent clickCount] == 2) {
        CPLog.debug(@"%@");
    }
}

- (id)initWithCoder:(CPCoder)aCoder {
    self = [super initWithCoder:aCoder];
    
    if (self) {
        imageView = [aCoder decodeObjectForKey:@"HNFractalViewImage"];
    }
    return self;
}

- (void)encodeWithCoder:(CPCoder)aCoder {
    [super encodeWithCoder:aCoder];
    [aCoder encodeObject:imageView forKey:@"HNFractalViewImage"];
}

@end