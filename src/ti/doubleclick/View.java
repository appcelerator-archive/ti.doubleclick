/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.doubleclick;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.util.TiConvert;
import org.appcelerator.titanium.view.TiUIView;

import com.google.ads.AdViewListener;
import com.google.ads.DoubleClickSpec.SizeProfile;
import com.google.ads.GoogleAdView;

public class View extends TiUIView {

	public View(TiViewProxy proxy) {
		super(proxy);
		_ad = createAd();
		setNativeView(_ad);
	}

	private GoogleAdView _ad;

	private GoogleAdView createAd() {

		final GoogleAdView adView = new GoogleAdView(proxy.getContext());
		// adView.setVisibility(android.view.View.GONE);

		KrollDict props = proxy.getProperties();
		String keywords = props.getString("keywords");
		String[] splitKeywords = keywords.split(";");
		String keyName = splitKeywords[0];
		String adSize = props.optString("adSize", "320x50");

		TiDoubleClickSpec spec = new TiDoubleClickSpec(keyName);

		String[] splitSize = adSize.split("x");
		spec.setSizeProfile(SizeProfile.XL);
		spec.setCustomSize(TiConvert.toInt(splitSize[0]), TiConvert.toInt(splitSize[1]));

		spec.addParameter("csit", props.optBoolean("trackImpression", false) ? "1" : "0");

		for (int i = 1; i < splitKeywords.length; i++) {
			String[] pair = splitKeywords[i].split("=");
			spec.addParameter(pair[0], pair[1]);
		}

		spec.setColorBackground("000000");
		spec.addParameter("c", "i");

		adView.setAdViewListener(new AdViewListener() {

			private boolean isAdLoadSuccessful;

			@Override
			public void onStartFetchAd() {
			}

			@Override
			public void onAdFetchFailure() {
				isAdLoadSuccessful = false;
				proxy.fireEvent("onadfail", new KrollDict());
			}

			@Override
			public void onFinishFetchAd() {
				if (isAdLoadSuccessful) {
					// adView.setVisibility(android.view.View.VISIBLE);
					proxy.fireEvent("onadload", new KrollDict());
				}
			}

			@Override
			public void onClickAd() {
				proxy.fireEvent("onadclick", new KrollDict());
			}

		});

		adView.showAds(spec);

		return adView;
	}

}
