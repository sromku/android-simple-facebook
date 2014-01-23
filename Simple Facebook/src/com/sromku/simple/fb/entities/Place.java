package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import com.facebook.model.GraphObject;

public class Place {
	private String mId;
	private String mName;
	private String mStreet;
	private String mCity;
	private String mCountry;
	private int mZip;
	private long mLatitude;
	private long mLongitude;

	public String getStreet() {
		return mStreet;
	}

	public void setStreet(String mStreet) {
		this.mStreet = mStreet;
	}

	public String getCity() {
		return mCity;
	}

	public void setCity(String mCity) {
		this.mCity = mCity;
	}

	public String getCountry() {
		return mCountry;
	}

	public void setCountry(String mCountry) {
		this.mCountry = mCountry;
	}

	public int getZip() {
		return mZip;
	}

	public void setZip(int mZip) {
		this.mZip = mZip;
	}

	public long getLatitude() {
		return mLatitude;
	}

	public void setLatitude(long mLatitude) {
		this.mLatitude = mLatitude;
	}

	public long getLongitude() {
		return mLongitude;
	}

	public void setLongitude(long mLongitude) {
		this.mLongitude = mLongitude;
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public String getName() {
		return mName;
	}

	public void setName(String mName) {
		this.mName = mName;
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
			
			mLatitude= jsonObject.optLong("latitude");
			mLongitude= jsonObject.optLong("longitude");
		}
	}
}
