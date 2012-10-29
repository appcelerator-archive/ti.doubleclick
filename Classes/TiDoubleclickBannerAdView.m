/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2012 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiDoubleclickBannerAdView.h"
#import "TiDoubleclickDFPAdRequest.h"
#import "TiApp.h"

@implementation TiDoubleclickBannerAdView

-(void)dealloc
{
	// Release objects and memory allocated by the view
    [self releaseAd];
    
	[super dealloc];
}

-(void)releaseAd
{
    if (_ad != nil) {
        _ad.delegate = nil;
        _ad.adSizeDelegate = nil;
        _ad.appEventDelegate = nil;
        
        [_ad removeFromSuperview];
        RELEASE_TO_NIL(_ad);
    }
}

-(void)requestAd
{
    // NOTE: Must run on UI thread
    ENSURE_UI_THREAD_0_ARGS
    
    // Release any existing ad
    [self releaseAd];
    
    // Build the adRequest
    TiDoubleclickDFPAdRequest* adRequest = [[[TiDoubleclickDFPAdRequest alloc] initWithProxy:self.proxy] autorelease];
    
    // Get ad-specific properties
    GADAdSize adSize = [TiDoubleclickDFPAdRequest setupAdSize:self.proxy];
    NSArray* validAdSizes = [TiDoubleclickDFPAdRequest setupValidAdSizes:self.proxy];
    NSString* adUnitId = [TiDoubleclickDFPAdRequest setupAdUnitId:self.proxy];
    
    _ad = [[DFPBannerView alloc] initWithAdSize:adSize];

    // Specify the ad's "unit identifier." This is your DFP ad unit ID.
    _ad.adUnitID = adUnitId;
    
    // Handle multiple ad size support
    if (validAdSizes != nil) {
        _ad.validAdSizes = validAdSizes;
        _ad.adSizeDelegate = self;
    }
    
    // Let the runtime know which UIViewController to restore after taking
    // the user wherever the ad goes and add it to the view hierarchy.
    _ad.rootViewController = [[TiApp app] controller];
    
    _ad.delegate = self;
    _ad.appEventDelegate = self;

    [self addSubview:_ad];
    [_ad loadRequest:[adRequest getRequestWithExtras]];
}

-(void)frameSizeChanged:(CGRect)frame bounds:(CGRect)bounds
{
    [self requestAd];
}

#pragma mark adSizeDelegate

// This method gets invoked when a DFPBannerView is about to change size.
// Only a multiple-sized DFPBannerView should implement this method.
-(void)adView:(GADBannerView *)view willChangeAdSizeTo:(GADAdSize)size
{
    if (!GADAdSizeEqualToSize(_ad.adSize, size)) {
        CGSize sizeOld = CGSizeFromGADAdSize(_ad.adSize);
        CGSize sizeNew = CGSizeFromGADAdSize(size);
        NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                               NUMINT(sizeOld.width), @"oldWidth",
                               NUMINT(sizeOld.height), @"oldHeight",
                               NUMINT(sizeNew.width), @"newWidth",
                               NUMINT(sizeNew.height), @"newHeight",
                               nil];
        [self.proxy fireEvent:@"willChangeAdSizeTo" withObject:event];
    }
}

#pragma mark appEventDelegate

// Called when a DFP creative invokes an app event. This method only
// needs to be implemented if your creative makes use of app events.
- (void)didReceiveAppEvent:(NSString *)name withInfo:(NSString *)info {
    NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                           info, name,
                           nil];
    [self.proxy fireEvent:@"appEvent" withObject:event];
}

#pragma mark bannerViewDelegate

- (void)adViewDidReceiveAd:(GADBannerView *)view
{
    [self.proxy fireEvent:@"didReceiveAd"];
}

- (void)adView:(GADBannerView *)view didFailToReceiveAdWithError:(GADRequestError *)error
{
    NSDictionary *event = [NSDictionary dictionaryWithObjectsAndKeys:
                           [error localizedDescription], @"error",
                           nil];
    [self.proxy fireEvent:@"didFailToReceiveAd" withObject:event];
}

- (void)adViewWillPresentScreen:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"willPresentScreen"];
}

- (void)adViewWillDismissScreen:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"willDismissScreen"];
}

- (void)adViewDidDismissScreen:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"didDismissScreen"];
}

- (void)adViewWillLeaveApplication:(GADBannerView *)adView
{
    [self.proxy fireEvent:@"willLeaveApplication"];
}

@end
