/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2012 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import "TiUIView.h"
#import "DFPBannerView.h"
#import "GADAppEventDelegate.h"
#import "GADAdSizeDelegate.h"
#import "TiDoubleclickDFPAdRequest.h"

@interface TiDoubleclickBannerAdView : TiUIView
    <GADAdSizeDelegate,GADBannerViewDelegate,GADAppEventDelegate> {
    DFPBannerView *_ad;
}

@end
