/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2012 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.doubleclick;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.TiMessenger;

import android.util.Log;
import java.util.HashMap;

import com.google.ads.*;
import com.google.ads.doubleclick.*;

public class BannerAdView extends TiUIView
    implements AdListener, AppEventListener {

    private static final String LCAT = "DoubleClick Module";
    private DfpAdView _ad = null;

	public BannerAdView(TiViewProxy proxy) {
		super(proxy);
	}

    private void __requestAd()
    {
        // NOTE: Must run on UI thread
        KrollDict props = proxy.getProperties();

        // Build the adRequest
        DFPAdRequest adRequest = new DFPAdRequest(props);

        // Get ad-specific properties
        AdSize adSize = DFPAdRequest.setupAdSize(props);
        AdSize[] validAdSizes = DFPAdRequest.setupValidAdSizes(props);
        String adUnitId = DFPAdRequest.setupAdUnitId(props);

        if (validAdSizes == null) {
            _ad = new DfpAdView(TiApplication.getInstance().getCurrentActivity(), adSize, adUnitId);
        } else {
            _ad = new DfpAdView(TiApplication.getInstance().getCurrentActivity(), validAdSizes, adUnitId);
        }

        _ad.setAdListener(this);
        _ad.setAppEventListener(this);
        _ad.loadAd(adRequest.getRequestWithExtras());

        setNativeView(_ad);
    }

	@Override
	public void processProperties(KrollDict props)
	{
		super.processProperties(props);
        requestAdOnUIThread();
    }

	// UI Thread Management

    public void requestAdOnUIThread() {
        if (!TiApplication.isUIThread()) {
            TiMessenger.postOnMain(new Runnable() {
                public void run() {
                    __requestAd();
                }
            });
        } else {
            __requestAd();
        }
    }

    // Delegates

    public void onAppEvent(Ad ad, String name, String info)
    {
        HashMap<String, String> event = new HashMap<String, String>();
        event.put(name, info);

        proxy.fireEvent("appEvent", event);
    }

    public void onReceiveAd(Ad ad)
    {
        proxy.fireEvent("didReceiveAd", new HashMap());
    }

    public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error)
    {
        HashMap<String, String> event = new HashMap<String, String>();
        event.put("error", error.toString());

        proxy.fireEvent("didFailToReceiveAd", event);
    }

    public void onPresentScreen(Ad ad)
    {
        proxy.fireEvent("willPresentScreen", new HashMap());
    }

    public void onDismissScreen(Ad ad)
    {
        proxy.fireEvent("willDismissScreen", new HashMap());
    }

    public void onLeaveApplication(Ad ad)
    {
        proxy.fireEvent("willLeaveApplication", new HashMap());
    }
}