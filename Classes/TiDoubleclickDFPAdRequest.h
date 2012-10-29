/**
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2012 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 */
#import <Foundation/Foundation.h>
#import "TiProxy.h"
#import "DFPExtras.h"
#import "GADRequest.h"
#import "GADAdSize.h"

@interface TiDoubleclickDFPAdRequest : NSObject {
@private
    GADRequest* _request;
    NSMutableDictionary* _extras;
    BOOL extrasSet;
}

-(id)initWithProxy:(TiProxy*)proxy;
-(void)setupTesting:(TiProxy*)proxy;
+(NSString*)setupAdUnitId:(TiProxy*)proxy;
+(GADAdSize)setupAdSize:(TiProxy*)proxy;
+(NSArray*)setupValidAdSizes:(TiProxy*)proxy;
-(void)setupCustomTargeting:(TiProxy*)proxy;
-(void)setupLocationTargeting:(TiProxy*)proxy;
-(void)setupAdColors:(TiProxy*)proxy;
-(GADRequest*)getRequestWithExtras;

@end
