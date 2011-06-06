
@interface HNFractalInspector : NSWindowController
{
    IBOutlet NSSlider* pMinSlider;
    IBOutlet NSSlider* pMaxSlider;
    IBOutlet NSSlider* qMinSlider;
    IBOutlet NSSlider* qMaxSlider;
}
- (IBAction)scale:(id)aSender;
- (IBAction)rotate:(id)aSender;
- (IBAction)updateParameters:(id)aSender;
@end