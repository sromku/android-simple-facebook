package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Location {

    private static final String ID = "id";
    private static final String NAME = "name";

    @SerializedName(ID)
	private String mId;

    @SerializedName(NAME)
	private String mName;

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

//	public static Location create(GraphObject graphObject) {
//		Location location = new Location();
//		if (graphObject == null) {
//			return location;
//		}
//
//		location.mId = String.valueOf(graphObject.getProperty("id"));
//		location.mName = String.valueOf(graphObject.getProperty("name"));
//		return location;
//	}
	
	@Override
	public String toString() {
		return String.format(Locale.US, "id=%s,name=%s", mId, mName);
	}
}
