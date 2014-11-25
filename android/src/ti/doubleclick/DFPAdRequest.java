/**
 * Ti.DoubleClick Module
 * Copyright (c) 2010-2013 by Appcelerator, Inc. All Rights Reserved.
 * Please see the LICENSE included with this distribution for details.
 */

package ti.doubleclick;

import org.appcelerator.kroll.KrollDict;
import org.appcelerator.titanium.util.TiConvert;

import android.util.Log;
import android.location.Location;

import com.google.ads.*;
import com.google.ads.doubleclick.*;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class DFPAdRequest {

    private static final String LCAT = "DoubleClick Module";

	private AdRequest _request = new AdRequest();
	private DfpExtras _extras = new DfpExtras();
	private Boolean extrasSet = false;

	public DFPAdRequest() {
	}

	public DFPAdRequest(KrollDict props)
	{
	    super();

        setupTesting(props);
        setupCustomTargeting(props);
        setupLocationTargeting(props);
        setupAdColors(props);
	}

    public AdRequest getRequestWithExtras() {
        // Return a request object ready for setting in DoubleClick. To do this we take the extras object and add
        // it to the request object.
        if (!extrasSet) {
            _request.setNetworkExtras(_extras);
            extrasSet = true;
        }

        return _request;
    }

	private static AdSize convertValidAdSize(Object arg)
	{
	    if (arg instanceof HashMap) {
	        HashMap map = (HashMap)arg;
	        if (map.containsKey("width") && (map.containsKey("height"))) {
	            return new AdSize(TiConvert.toInt(map, "width"), TiConvert.toInt(map, "height"));
	        }
	    }

	    throw new IllegalArgumentException("adSize dictionary must contain 'width' and 'height' properties");
	}

	public void setupTesting(KrollDict props)
	{
	    Boolean test = props.optBoolean("testing", false);
	    if (test) {
	        Log.d(LCAT,"[DEBUG] >>> TESTING <<<");
	        _request.addTestDevice(AdRequest.TEST_EMULATOR);
	    }
	}

	public static String setupAdUnitId(KrollDict props)
	{
	    String adUnitID = props.optString("adUnitId", "");
	    if (adUnitID.length() > 0) {
	        return adUnitID;
	    }

	    throw new IllegalArgumentException("adUnitID is required");
	}

	public static AdSize setupAdSize(KrollDict props)
	{
	    Object obj = props.get("adSize");
	    if (obj != null) {
	        if (obj instanceof HashMap) {
	            return convertValidAdSize(obj);
	        }
	    }

	    Log.i(LCAT, "[INFO] adSize not specified -- using default banner size");

	    return AdSize.BANNER;
	}

	public static AdSize[] setupValidAdSizes(KrollDict props)
	{
	    Object obj = props.get("validAdSizes");
	    if (obj != null) {
	        if (obj.getClass().isArray()) {
	            Object[] arr = (Object[])obj;
	            AdSize[] validAdSizes = new AdSize[arr.length];
	            for (int i=0; i<arr.length; i++) {
	                validAdSizes[i] = convertValidAdSize(arr[i]);
	            }
	            return validAdSizes;
	        } else {
	            throw new IllegalArgumentException("adSize must be an array of dictionary entries that contain 'width' and 'height' properties");
	        }
	    }

	    return null;
    }

    public void setupCustomTargeting(KrollDict props)
    {
        Object obj = props.get("customTargeting");
        if (obj != null) {
            if (obj instanceof HashMap) {
                HashMap<String, Object> map = (HashMap<String, Object>)obj;
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    _extras.addExtra(entry.getKey(), TiConvert.toString(entry.getValue(),""));
                }
            } else {
                throw new IllegalArgumentException("customTargeting must be a dictionary of key-value pairs");
            }
        }
    }

    public void setupLocationTargeting(KrollDict props)
    {
        Object obj = props.get("location");
        if (obj != null) {
            if (obj instanceof HashMap) {
                HashMap map = (HashMap)obj;
                Location location = new Location("");
                location.setLongitude(TiConvert.toFloat(map, "longitude"));
                location.setLatitude(TiConvert.toFloat(map, "latitude"));
                location.setAccuracy(TiConvert.toInt(map, "accuracy"));
                _request.setLocation(location);
            } else {
                throw new IllegalArgumentException("location must be a dictionary containing 'longitude', 'latitude', and 'accuracy' properties");
            }
        }
    }

    public void setupAdColors(KrollDict props)
    {
        Object obj = props.get("adColors");
        if (obj != null) {
            if (obj instanceof HashMap) {
                HashMap<String, String> map = (HashMap<String, String>)obj;
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    _extras.addExtra(entry.getKey(), convertColor(TiConvert.toString(entry.getValue())));
                }
            } else {
                throw new IllegalArgumentException("adColors must be a dictionary of color properties");
            }
        }
    }

    private String convertColor(String color)
    {
        color = color.replace("#", "");
		if (color.equals("white")) {
			color = "FFFFFF";
	    } else	if (color.equals("red")) {
			color = "FF0000";
		} else	if (color.equals("blue")) {
			color = "0000FF";
	    } else 	if (color.equals("green")) {
			color = "008000";
		} else	if (color.equals("yellow")) {
			color = "FFFF00";
		} else	if (color.equals("black")) {
			color = "000000";
		}

		return color;
    }
}