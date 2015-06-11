package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

public class Place {

	private static final String ID = "id";
	private static final String NAME = "name";
//	private static final String LOCATION = "location";
	private static final String STREET = "street";
	private static final String CITY = "city";
	private static final String STATE = "state";
	private static final String COUNTRY = "country";
	private static final String ZIP = "zip";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";

    @SerializedName(ID)
	private String mId;

    @SerializedName(NAME)
	private String mName;

    @SerializedName(STREET)
	private String mStreet;

    @SerializedName(CITY)
	private String mCity;

    @SerializedName(STATE)
	private String mState;

    @SerializedName(COUNTRY)
	private String mCountry;

    @SerializedName(ZIP)
	private Integer mZip;

    @SerializedName(LATITUDE)
	private Double mLatitude;

    @SerializedName(LONGITUDE)
	private Double mLongitude;

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

	public Integer getZip() {
		return mZip;
	}

	public Double getLatitude() {
		return mLatitude;
	}

	public Double getLongitude() {
		return mLongitude;
	}

	public String getId() {
		return mId;
	}

	public String getName() {
		return mName;
	}

}
