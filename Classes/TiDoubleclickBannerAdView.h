/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
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
