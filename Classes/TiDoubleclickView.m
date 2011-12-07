/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */

#import "TiDoubleclickView.h"
#import "TiApp.h"
#import "TiUtils.h"

@implementation TiDoubleclickView

- (void)dealloc 
{    
	[super dealloc];
}


- (void)initializeState 
{
	[super initializeState];
    
    NSString* adSize = [self.proxy valueForKey:@"adSize"];
    
    adController = [[GADAdViewController alloc] initWithDelegate:self];
    
    if ([adSize isEqualToString:@"180x150"]) {
        adController.adSize = kGADAdSize180x150;
    }
    else if ([adSize isEqualToString:@"88x31"]) {
        adController.adSize = kGADAdSize88x31;
    }
    else if ([adSize isEqualToString:@"728x90"]) {
        adController.adSize = kGADAdSize728x90;
    }
    else if ([adSize isEqualToString:@"300x250"]) {
        adController.adSize = kGADAdSize300x250;
    }
    else if ([adSize isEqualToString:@"300x50"]) {
        adController.adSize = kGADAdSize300x50;
    }
	else if ([adSize isEqualToString:@"120x60"]) {
		adController.adSize = kGADAdSize120x60;
	}
    
	adView = adController.view;
    
	attributes = [NSDictionary dictionaryWithObjectsAndKeys:
                  [self.proxy valueForKey:@"keywords"], kGADDoubleClickKeyname,
                  ([TiUtils boolValue:[self.proxy valueForKey:@"trackImpression"]]) ? @"1" : @"0", kGADDoubleClickImpressionTracking,
                  @"000000", kGADDoubleClickBackgroundColor,
                  @"i", kGADDoubleClickClickableArea, 
                  nil];
    
	[adController loadGoogleAd:attributes];
    
	// NSLog(@"[DEBUG] Created a DoubleClick AD. Setting size to: %@", adSize);
}

- (void)loadSucceeded:(GADAdViewController*)adController withResults:(NSDictionary*)results 
{    
    adView.frame = [super bounds];
    
	[super addSubview:adView];
    
    if ([self.proxy _hasListeners:@"onadload"]) {
        [self.proxy fireEvent:@"onadload" withObject:results];
    }
    
    // NSLog(@"[DEBUG] We received a DoubleClick AD. Set size to: %@", [self.proxy valueForKey:@"adSize"]);
    // NSLog(@"[DEBUG] Response: %@", results);
}

- (void)loadFailed:(GADAdViewController*)adController withError:(NSError*)error 
{
    
    if ([self.proxy _hasListeners:@"onadfail"]) {
        [self.proxy fireEvent:@"onadfail" withObject:[NSDictionary dictionaryWithObjectsAndKeys:error, @"error", nil]];
    }
    // NSLog(@"[ERROR] There was an error loading the DoubleClick AD.");
    // NSLog(@"[ERROR] ERROR: %@", error);
}

- (UIViewController *)viewControllerForModalPresentation:(GADAdViewController*)adController 
{    
    // NSLog(@"[INFO] Opening DoubleClick AD Modal.");
	return controller;
}

- (GADAdClickAction)adControllerActionModelForAdClick:(GADAdViewController *)adController 
{
    return GAD_ACTION_DISPLAY_NONE; // prevent additional click through 
}

- (GADAdClickAction)adController:(GADAdViewController*)adController actionModelForAdClickWithResults:(NSDictionary*)results {    
    if ([self.proxy _hasListeners:@"onadclick"]) {
        [self.proxy fireEvent:@"onadclick" withObject:results];
    }
    
    return GAD_ACTION_DISPLAY_NONE;
}

@end