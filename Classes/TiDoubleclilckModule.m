/**
 * Your Copyright Here
 *
 * Appcelerator Titanium is Copyright (c) 2009-2010 by Appcelerator, Inc.
 * and licensed under the Apache Public License (version 2)
 */
#import "TiDoubleclickModule.h"
#import "TiBase.h"
#import "TiHost.h"
#import "TiUtils.h"

@implementation TiDoubleclickModule

#pragma mark Internal

// this is generated for your module, please do not change it
-(id)moduleGUID
{
	return @"e35390af-1aa9-4416-ba22-88a3b1a50108";
}

// this is generated for your module, please do not change it
-(NSString*)moduleId
{
	return @"ti.doubleclick";
}

#pragma mark Lifecycle

- (void)startup {
	[super startup];
	
	NSLog(@"[INFO] %@ loaded",self);
}

- (void)shutdown:(id)sender {
	[super shutdown:sender];
}

#pragma mark Cleanup 

- (void)dealloc {
    [super dealloc];
}

#pragma mark Internal Memory Management

- (void)didReceiveMemoryWarning:(NSNotification*)notification {
	[super didReceiveMemoryWarning:notification];
}

@end
