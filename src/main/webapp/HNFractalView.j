/*
 *	HNFractalView.j
 *  webapp
 *
 *  Created by tahiche on 07/06/11.
 *  Copyright 2011 280 North, Inc. All rights reserved.
 */

@import <AppKit/AppKit.j>
@import <Foundation/Foundation.j>

@implementation HNFractalView : CPView
{
    CPImageView imageView;
}

- (id)initWithFrame:(CGRect)frame {
    self = [super initWithFrame:frame];
    if (self) {
        // Initialization code here.
    }
    return self;
}

- (void)drawRect:(CPRect)aRect {
    // Drawing code here.
}

- (void)setRepresentedObject:(id)anObject {
    if (!imageView) {
        var frame = CGRectInset([self bounds], 5.0, 5.0);
        imageView = [[CPImageView alloc] initWithFrame:frame];
        
        [imageView setImageScaling:CPScaleProportionally];
        [imageView setAutoresizingMask:CPViewWidthSizable | CPViewHeightSizable];
        [self addSubview:imageView];
    }
    [imageView setImage:anObject];
}

- (void)setSelected:(BOOL)isSelected {
    [self setBackgroundColor:isSelected ? [CPColor grayColor] : nil];
}

@end