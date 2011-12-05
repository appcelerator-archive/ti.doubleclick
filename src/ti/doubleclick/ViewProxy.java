/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.doubleclick;

import org.appcelerator.kroll.annotations.Kroll;

import org.appcelerator.titanium.TiContext;
import org.appcelerator.titanium.proxy.TiViewProxy;
import org.appcelerator.titanium.view.TiUIView;

import android.app.Activity;

// This proxy can be created by calling Doubleclick.createExample({message: "hello world"})
@Kroll.proxy(creatableInModule = DoubleclickModule.class)
public class ViewProxy extends TiViewProxy {
	public ViewProxy(TiContext tiContext) {
		super(tiContext);
	}

	@Override
	public TiUIView createView(Activity activity) {
		return new View(this);
	}
}