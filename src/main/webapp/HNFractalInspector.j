/*
 *	HNFractalInspector.j
 *  webapp
 *
 *  Created by tahiche on 05/06/11.
 *  Copyright 2011 280 North, Inc. All rights reserved.
 */

@import <AppKit/AppKit.j>
@import <Foundation/Foundation.j>
@import "HNPaneLayer.j"

var FractalInspectorSharedInstance = nil;

@implementation HNFractalInspector : CPWindowController
{    
    //  IBOutlet CPWindow theWindow;
    IBOutlet CPSlider pMinSlider;
    IBOutlet CPSlider pMaxSlider;
    IBOutlet CPSlider qMinSlider;
    IBOutlet CPSlider qMaxSlider;
    
    IBOutlet HNPaneLayer paneLayer;
}

+ (HNFractalInspector)sharedFractalInspector {
    if (!FractalInspectorSharedInstance) {
        FractalInspectorSharedInstance = [[HNFractalInspector alloc] initWithWindowCibName:@"HUDInspector" owner:self];
    }
    return FractalInspectorSharedInstance;
}

+ (void)inspectPaneLayer:(HNPaneLayer)aPaneLayer {
    var inspector = [self sharedFractalInspector];
    [inspector setPaneLayer:aPaneLayer];
    [inspector showWindow:self];
}

- (id)initWithWindowCibName:(CPString)cibName {
    self = [super initWithWindowCibName:cibName];
    if (self) {
        //[theWindow setDelegate:self];
    }
    return self;
}

- (void)windowWillClose:(id)sender {
    [self setPaneLayer:nil];
}

- (void)setPaneLayer:(HNPaneLayer)aPaneLayer {
    if (paneLayer == aPaneLayer)
        return;
    [[paneLayer pageView] setEditing:NO];
    paneLayer = aPaneLayer;
    
    var page = [paneLayer pageView];
    [page setEditing:YES];
    
    if (paneLayer) {
            CPLog.debug(paneLayer);
        var frame = [page convertRect:[page bounds] toView:nil];
        var windowSize = [[self window] frame].size;
        [[self window] setFrameOrigin:CGPointMake(CGRectGetMidX(frame) - windowSize.width/2., CGRectGetMidY(frame))];
    }
}

- (IBAction)scale:(id)sender {
    CPLog.debug(paneLayer);
    [[self paneLayer] setScale:[sender doubleValue]/100.0];
}

- (IBAction)rotate:(id)sender {
    [paneLayer setRotationRadians:PI/180. * [sender doubleValue]];
}

- (IBAction)updateParameters:(id)sender {
    CPLog.debug("parameter value: %@", [sender doubleValue]);
    var parameters = {"pMin":[pMinSlider doubleValue], 
        "pMax":[pMaxSlider doubleValue], 
        "qMin":[qMinSlider doubleValue], 
        "qMax":[qMaxSlider doubleValue]};
    CPLog.debug(parameters);
    var parametersJson = [CPString JSONFromObject:parameters];
    retrieveImageUrl(parametersJson);
}

@end