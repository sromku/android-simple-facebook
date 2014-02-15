package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class Place {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String LOCATION = "location";
    private static final String STREET = "street";
    private static final String CITY = "city";
    private static final String STATE = "state";
    private static final String COUNTRY = "country";
    private static final String ZIP = "zip";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";

    private String mId;
    private String mName;
    private String mStreet;
    private String mCity;
    private String mState;
    private String mCountry;
    private int mZip;
    private long mLatitude;
    private long mLongitude;

    private Place(GraphObject graphObject) {
	// id
	mId = Utils.getPropertyString(graphObject, ID);

	// name
	mName = Utils.getPropertyString(graphObject, NAME);

	// location
	GraphObject location = Utils.getPropertyGraphObject(graphObject, LOCATION);

	// street
	mStreet = Utils.getPropertyString(location, STREET);
	
	// city
	mCity = Utils.getPropertyString(location, CITY);
	
	// country
	mCountry = Utils.getPropertyString(location, COUNTRY);
	
	// zip
	mZip = Utils.getPropertyInteger(location, ZIP);
	
	// state
	mState = Utils.getPropertyString(location, STATE);
	
	// latitude
	mLatitude = Utils.getPropertyLong(location, LATITUDE);
	
	// longitude
	mLongitude = Utils.getPropertyLong(location, LONGITUDE);
    }
    
    public static Place create(GraphObject graphObject) {
	return new Place(graphObject);
    }

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

}
