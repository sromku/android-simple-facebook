package com.sromku.simple.fb.entities;

import java.util.Locale;

import com.facebook.model.GraphObject;

public class Location {
	private String mId;
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

	public static Location create(GraphObject graphObject) {
		Location location = new Location();
		if (graphObject == null) {
			return location;
		}

		location.mId = String.valueOf(graphObject.getProperty("id"));
		location.mName = String.valueOf(graphObject.getProperty("name"));
		return location;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.US, "id=%s,name=%s", mId, mName);
	}
}
