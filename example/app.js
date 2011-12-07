var DoubleClick = require('ti.doubleclick');
var u = Ti.Android != undefined ? 'dp' : 0;

var win = Ti.UI.createWindow({ backgroundColor: '#fff' });

var adView = DoubleClick.createView({
    width: 120 + u, height: 60 + u,
    adSize: '120x60',
    keywords: 'mo.nbc.app/portal_loading;site=nbc;' +
        'sect=portal;' +
        'sub=loading;' +
        'mdeck=1;' +
        'type=iphone;' +
        '!c=app;!c=iphone;!c=nbc;!c=portal;' +
        'size=120x60;' +
        'pos=1',
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