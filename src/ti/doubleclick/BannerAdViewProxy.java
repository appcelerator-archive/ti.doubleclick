/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2012 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.doubleclick;

import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

@Kroll.proxy(creatableInModule = DoubleclickModule.class)
public class BannerAdViewProxy extends TiViewProxy {

	@Override
	public TiUIView createView(Activity activity) {
		return new BannerAdView(this);
	}
}