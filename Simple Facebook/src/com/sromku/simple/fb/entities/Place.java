package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import com.facebook.model.GraphObject;

public class Place {

    private String mId;
    private String mName;
    private String mStreet;
    private String mCity;
    private String mState;
    private String mCountry;
    private int mZip;
    private long mLatitude;
    private long mLongitude;

    public String getStreet() {
	return mStreet;
    }

    public String getCity() {
	return mCity;
    }

    /**
     * @return the state
     */
    public String getState() {
	return mState;
    }

    public String getCountry() {
	return mCountry;
    }

    public int getZip() {
	return mZip;
    }

    public long getLatitude() {
	return mLatitude;
    }

    public long getLongitude() {
	return mLongitude;
    }

    public String getId() {
	return mId;
    }

    public String getName() {
	return mName;
    }

    public static Place create(GraphObject graphObject) {
	return new Place(graphObject);
    }

    private Place(GraphObject graphObject) {
	mId = String.valueOf(graphObject.getProperty("id"));
	mName = String.valueOf(graphObject.getProperty("name"));

	JSONObject jsonObject = (JSONObject) graphObject.getProperty("location");
	if (jsonObject != null) {
	    mStreet = jsonObject.optString("street");
	    mCity = jsonObject.optString("city");
	    mCountry = jsonObject.optString("country");
	    mZip = jsonObject.optInt("zip");
	    mState = jsonObject.optString("state");
	    mLatitude = jsonObject.optLong("latitude");
	    mLongitude = jsonObject.optLong("longitude");
	}
    }
}
