package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;

public class Location
{
	private String mId;
	private String mName;

	/**
	 * @return the id
	 */
	public String getId()
	{
		return mId;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		mId = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		mName = name;
	}

	public static Location create(GraphObject graphObject)
	{
		Location location = new Location();
		String id = String.valueOf(graphObject.getProperty("id"));
		String name = String.valueOf(graphObject.getProperty("name"));
		location.setId(id);
		location.setName(name);
		return location;
	}
}
