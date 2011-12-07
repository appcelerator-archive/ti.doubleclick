var DoubleClick = require('ti.doubleclick');
var u = Ti.Android != undefined ? 'dp' : 0;

var win = Ti.UI.createWindow({ backgroundColor: '#fff' });

var adView = DoubleClick.createView({
    width: 300 + u, height: 250 + u,
    adSize: '300x250',
    keywords: 'mo.appcelerator.app/test;site=appcelerator;' +
        'sect=test;' +
        'sub2=loading;' +
        'sub3=000000;' +
        'mdeck=1;' +
        'type=ipad;' +
        '!c=app;!c=ipad;!c=appcelerator;!c=test;' +
        'size=180x150;' +
        'pos=2',
    trackImpression: true
});
adView.addEventListener('onadload', function (evt) {
    alert('onadload!');
});
adView.addEventListener('onadfail', function (evt) {
    alert('onadfail!');
});
adView.addEventListener('onadclick', function (evt) {
    alert('onadclick!');
});
win.add(adView);

win.open();