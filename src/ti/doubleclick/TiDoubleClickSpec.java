/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2011 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */
package ti.doubleclick;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.google.ads.DoubleClickSpec;

class TiDoubleClickSpec extends DoubleClickSpec {
	private List<Parameter> additionalParameters;

	/**
	 * @param keyname
	 *            Pass through constructor to superclass.
	 */
	public TiDoubleClickSpec(String keyname) {
		super(keyname);
		additionalParameters = new ArrayList<Parameter>();
	}

	// Use this method to pass any additional targeting parameters
	public void addParameter(String name, String value) {
		additionalParameters.add(new Parameter(name, value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.ads.DoubleClickSpec#generateParameters(android.content.Context)
	 */
	@Override
	public List<Parameter> generateParameters(Context context) {
		List<Parameter> result = new ArrayList<Parameter>(super.generateParameters(context));

		for (Parameter p : additionalParameters) {
			result.add(p);
		}

		return result;
	}

}