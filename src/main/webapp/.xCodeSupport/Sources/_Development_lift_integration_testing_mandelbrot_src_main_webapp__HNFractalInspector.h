
@interface HNFractalInspector : NSWindowController
{
    IBOutlet NSSlider* pMinSlider;
    IBOutlet NSSlider* pMaxSlider;
    IBOutlet NSSlider* qMinSlider;
    IBOutlet NSSlider* qMaxSlider;
}
- (IBAction)updateParameters:(id)aSender;
@end