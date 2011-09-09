Titanium.DoubleClick = Ti.DoubleClick = require('ti.doubleclick');

var win = Ti.UI.createWindow({ backgroundColor: '#fff' });

var adView = Ti.DoubleClick.createView({
		width: 300, height: 250,
		adSize: '300x250',
		keywords:'mo.appcelerator.app/test;site=appcelerator;' +
				'sect=test;' +
				'sub2=loading;' +
				'sub3=000000;' +
				'mdeck=1;' +
				'type=ipad;!c=app;!c=ipad;!c=appcelerator;!c=test;' +
				'size=180x150;' +
				'pos=2',
		trackImpression: true
});

win.add(adView);

win.open();