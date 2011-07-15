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

- (void)dealloc {
	RELEASE_TO_NIL(attributes);
	RELEASE_TO_NIL(adController);
	RELEASE_TO_NIL(adView);
    
	[super dealloc];
}


- (void)initializeState {
	[super initializeState];

    NSString* adSize = [self.proxy valueForKey:@"adSize"];

    adController = [[GADAdViewController alloc] initWithDelegate:self];

    if ([adSize isEqualToString:@"180x150"]) {
        NSLog(@"[INFO] Found Size.");
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
        
	adView = adController.view;
            
	attributes = [NSDictionary dictionaryWithObjectsAndKeys:[self.proxy valueForKey:@"keywords"], kGADDoubleClickKeyname, nil];
    
	[adController loadGoogleAd:attributes];
    
	NSLog(@"[DEBUG] Created a DoubleClick AD. Setting size to: %@", adSize);
}

- (void)loadSucceeded:(GADAdViewController*)adController withResults:(NSDictionary*)results {    
    adView.frame = [super bounds];
    
	[super addSubview:adView];
    
    NSLog(@"[DEBUG] We received a DoubleClick AD.");
}

- (void)loadFailed:(GADAdViewController*)adController withError:(NSError*)error {
    NSLog(@"[ERROR] There was an error loading the DoubleClick AD.");
    NSLog(@"[ERROR] ERROR: %@", error);
}

- (UIViewController *)viewControllerForModalPresentation:(GADAdViewController*)adController {    
    NSLog(@"[INFO] Opening DoubleClick AD Modal.");
	return controller;
}

- (GADAdClickAction)adController:(GADAdViewController*)adController actionModelForAdClickWithResults:(NSDictionary*)results {    
    if ([self.proxy _hasListeners:@"openurl"]) {
        [self.proxy fireEvent:@"openurl" withObject:results];
    }
    
    return GAD_ACTION_DISPLAY_NONE;
}

@end
