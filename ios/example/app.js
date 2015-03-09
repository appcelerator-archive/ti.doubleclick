var DoubleClick = require('ti.doubleclick');
var u = Ti.Android ? 'dp' : 0;

var win = Ti.UI.createWindow({
	backgroundColor:'white'
});

/*
 We'll make two ads. This first one doesn't care about where the user is located.
 */
var ad;
win.add(ad = DoubleClick.createBannerAdView({
	top:0, left:0,
	width:320 + u, height:50 + u,
	adUnitId:'<< YOUR AD UNIT ID HERE >>', // in the format ca-app-pub-XXXXXXXXXXXXXXXX/NNNNNNNNNN
	adSize:{ width:320, height:50 },
	// If set up to server multiple ad sizes, specify the valid adsizes here
	//validAdSizes: [
	//	{ width: 320, height: 50 },
	//	{ width: 250, height: 250 },
	//	{ width: 120, height: 20 }
	//],
	testing:true,
	customTargeting:{
		dateOfBirth:new Date(1985, 10, 1, 12, 1, 1),
		gender:'male',
		keywords:'test'
	}
}));
ad.addEventListener('didReceiveAd', function ()
{
	Ti.API.warn('Received Ad');
});
ad.addEventListener('didFailToReceiveAd', function (e)
{
	Ti.API.error('ERROR: ' + e.error);
	alert('Failed to receive ad!');
});
ad.addEventListener('willPresentScreen', function ()
{
	Ti.API.info('Presenting screen!');
});
ad.addEventListener('willDismissScreen', function ()
{
	Ti.API.info('Dismissing screen!');
});
ad.addEventListener('didDismissScreen', function ()
{
	Ti.API.info('Dismissed screen!');
});
ad.addEventListener('willLeaveApplication', function ()
{
	Ti.API.info('Leaving the app!');
});
ad.addEventListener('appEvent', function (e)
{
	Ti.API.info('Received app event: ' + JSON.stringify(e));
});
ad.addEventListener('willChangeAdSizeTo', function (e)
{
	Ti.API.info('WillChangeAdSizeTo: ' + JSON.stringify(e));
});

/*
 And we'll try to get the user's location for this second ad!
 
 Note: Starting with iOS 8, you must add the NSLocationAlwaysUsageDescription
 or the NSLocationWhenInUseUsageDescription key to your tiapp.xml in order
 to access location services:
 
    <ios>
        <plist>
            <dict>
				<key>NSLocationAlwaysUsageDescription</key>
				<string>To show you local ads, of course!</string>
				...
				...
			</dict>
		</plist>
	</ios>
 */

Ti.Geolocation.accuracy = Ti.Geolocation.ACCURACY_BEST;
Ti.Geolocation.distanceFilter = 0;
Ti.Geolocation.getCurrentPosition(function reportPosition(e)
{
	if (!e.success || e.error) {
		alert('Failed to receive location.');
	} else {
		win.add(DoubleClick.createBannerAdView({
			top:100, left:0,
			width:320 + u, height:50 + u,
			adUnitId: '<< YOUR AD UNIT ID HERE >>', // in the format ca-app-pub-XXXXXXXXXXXXXXXX/NNNNNNNNNN
			adSize:{ width:320, height:50 },
			testing:true,
			location:e.coords
		}));
	}
});

win.add(Ti.UI.createLabel({
	text:'Loading the ads now! ' +
		'Note that there may be a several minute delay ' +
		'if you have not viewed an ad in over 24 hours.',
	bottom:40,
	height:Ti.UI.SIZE || 'auto', width:Ti.UI.SIZE || 'auto'
}));

win.open();

/*
 Create an interstitial ad
 */

var iad = DoubleClick.createInterstitialAd({
	adUnitId: '<< YOUR AD UNIT ID HERE >>', // in the format ca-app-pub-XXXXXXXXXXXXXXXX/NNNNNNNNNN
	testing:true
	// Interstitial ad is automatically displayed unless you set presentAd to false.
	//, presentAd: false
});
iad.addEventListener('didReceiveAd', function ()
{
	Ti.API.warn('Received Interstitial Ad');

	// If you set presentAd to false in createInterstitialAd, then you need to call presentAd yourself.
	//Ti.API.warn('Presenting interstitial Ad');
	//iad.presentAd();
});
iad.addEventListener('didFailToReceiveAd', function (e)
{
	Ti.API.error('ERROR: ' + e.error);
	alert('Failed to receive ad!');
});
iad.addEventListener('willPresentScreen', function ()
{
	Ti.API.info('Presenting screen!');
});
iad.addEventListener('willDismissScreen', function ()
{
	Ti.API.info('Dismissing screen!');
});
iad.addEventListener('didDismissScreen', function ()
{
	Ti.API.info('Dismissed screen!');
});
iad.addEventListener('willLeaveApplication', function ()
{
	Ti.API.info('Leaving the app!');
});


