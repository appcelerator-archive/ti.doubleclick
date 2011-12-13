var DoubleClick = require('ti.doubleclick');
var u = Ti.Android != undefined ? 'dp' : 0;

var win = Ti.UI.createWindow({ backgroundColor: '#fff' });

var adView = DoubleClick.createView({
    width: 120 + u, height: 60 + u,
    adSize: '120x60',
    keywords: '<< YOUR KEYWORWDS HERE >>',
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