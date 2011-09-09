# Ti.DoubleClick.View

## Description

A DoubleClick View is created by the method [Ti.DoubleClick.createView][]. It provides access to Google/DoubleClick advertising services
(DoubleClick and AdSense).

## Properties

### Ti.DoubleClick.View.adSize[string]

The size of the ad to display; can be any of the following: 180x150, 88x31, 728x90, 300x250, or 300x50.

### Ti.DoubleClick.View.keywords[object]

The kGADDoubleClickKeyname based on your DFP configuration. For example: "mo.appcelerator.app/test;site=appcelerator;sect=test;sub2=loading;sub3=000000;mdeck=1;type=ipad;!c=app;!c=ipad;!c=appcelerator;!c=test;size=180x150;pos=2".

### Ti.DoubleClick.View.trackImpression[boolean] *optional*

Allows toggling of impression tracking. Defaults to false.

### Ti.DoubleClick.View.width[double]

The width of the container your ad will be placed in.

### Ti.DoubleClick.View.height[double]

The height of the container your ad will be placed in.

## Events

### onadclick

Fired when the ad is clicked. Returns 'clickURL'[string], which represents the clickthrough URL.

### onadload

Fired when the ad is loaded. Returns 'html'[string], which represents HTML data of loaded ad WebView.

[Ti.DoubleClick.createView]: index.html