/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2012 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.doubleclick;

import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.KrollDict;
import org.appcelerator.kroll.KrollProxy;
import org.appcelerator.titanium.TiApplication;
import org.appcelerator.kroll.common.TiMessenger;

import android.util.Log;
import android.app.Activity;
import java.util.HashMap;

import com.google.ads.*;
import com.google.ads.doubleclick.*;

@Kroll.proxy(creatableInModule = DoubleclickModule.class)
public class InterstitialAdProxy extends KrollProxy
    implements AdListener {

    private static final String LCAT = "DoubleClick Module";
	private DfpInterstitialAd _ad = null;

    public InterstitialAdProxy()
    {
    }

    private void __requestAd()
    {
        // NOTE: Must run on UI thread
        KrollDict props = getProperties();

        // It does not appear that the Doubleclick for Publishers SDK
        // supports the ability to add targeting information. If it is
        // added in the future then it may be as simple as using the constructor
        // that accepts the properties of the proxy.
        DFPAdRequest adRequest = new DFPAdRequest();
        adRequest.setupTesting(props);

        String adUnitId = DFPAdRequest.setupAdUnitId(props);
        _ad = new DfpInterstitialAd(TiApplication.getInstance().getCurrentActivity(), adUnitId);

        _ad.setAdListener(this);
        _ad.loadAd(adRequest.getRequestWithExtras());
    }

    private void __presentAd()
    {
        // NOTE: Must run on UI thread
        _ad.show();
    }

	@Override
	public void handleCreationDict(KrollDict props)
	{
		super.handleCreationDict(props);
        // Interstitials MUST be created on the UI thread or else an
        // exception will occur when the interstitial is displayed. The
        // interstitial MUST be created on the UI thread.
        requestAdOnUIThread();
	}

    @Kroll.method
	public void presentAd()
	{
        // Interstitials MUST be displayed on the UI thread or else an
        // exception will occur. The interstitial is created on the UI thread.
        presentAdOnUIThread();
	}

    @Kroll.method
    public boolean isReady()
    {
        return _ad.isReady();
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

    public void presentAdOnUIThread() {
        if (!TiApplication.isUIThread()) {
            TiMessenger.postOnMain(new Runnable() {
                public void run() {
                    __presentAd();
                }
            });
        } else {
            __presentAd();
        }
    }

    // Delegates

    public void onReceiveAd(Ad ad)
    {
        fireEvent("didReceiveAd", new HashMap());

        KrollDict props = getProperties();
        Boolean autoPresent = props.optBoolean("presentAd", true);
        if (autoPresent) {
            presentAdOnUIThread();
        }
    }

    public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode error)
    {
        HashMap<String, String> event = new HashMap<String, String>();
        event.put("error", error.toString());

        fireEvent("didFailToReceiveAd", event);
    }

    public void onPresentScreen(Ad ad)
    {
        fireEvent("willPresentScreen", new HashMap());
    }

    public void onDismissScreen(Ad ad)
    {
        fireEvent("willDismissScreen", new HashMap());
    }

    public void onLeaveApplication(Ad ad)
    {
        fireEvent("willLeaveApplication", new HashMap());
    }
}