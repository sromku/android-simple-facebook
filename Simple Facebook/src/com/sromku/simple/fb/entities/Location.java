package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;

public class Location {
    // will be populated for Profile
    private String mId;
    private String mName;

    // will be populated for Page
    private String mCountry;
    private String mCity;
    private String mZip;
    private String mState;
    private String mStreet;
    private Float mLatitude;
    private Float mLongitude;

    /**
     * @return the id
     */
    public String getId() {
	return mId;
    }

    /**
     * @return the name
     */
    public String getName() {
	return mName;
    }

    /**
     * @return the country
     */
    public String getCountry() {
	return mCountry;
    }

    /**
     * @return the city
     */
    public String getCity() {
	return mCity;
    }

    /**
     * @return the zip
     */
    public String getZip() {
	return mZip;
    }

    /**
     * @return the state
     */
    public String getState() {
	return mState;
    }

    /**
     * @return the street
     */
    public String getStreet() {
	return mStreet;
    }

    /**
     * @return the latitude
     */
    public Float getLatitude() {
	return mLatitude;
    }

    /**
     * @return the longitude
     */
    public Float getLongitude() {
	return mLongitude;
    }

    public static Location create(GraphObject graphObject) {
	Location location = new Location();
	location.mId = String.valueOf(graphObject.getProperty("id"));
	location.mName = String.valueOf(graphObject.getProperty("name"));
	location.mCountry = String.valueOf(graphObject.getProperty("coutry"));
	location.mCity = String.valueOf(graphObject.getProperty("city"));
	location.mZip = String.valueOf(graphObject.getProperty("zip"));
	location.mState = String.valueOf(graphObject.getProperty("state"));
	location.mStreet = String.valueOf(graphObject.getProperty("street"));
	location.mLatitude = Float.valueOf(String.valueOf(graphObject.getProperty("latitude")));
	location.mLongitude = Float.valueOf(String.valueOf(graphObject.getProperty("longitude")));
	return location;
    }
}
