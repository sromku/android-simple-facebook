package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import com.facebook.model.GraphObject;

public class Work
{
	public static final String NAME = "name";
	public static final String EMPLOYER = "employer";
	public static final String LOCATION = "location";
	public static final String POSITION = "position";
	public static final String DESCRIPTION = "description";
	public static final String START_DATE = "start_date";
	public static final String END_DATE = "end_date";

	private String mEmployer;
	private Location mLocation;
	private String mPosition;
	private String mDescription;
	private String mStartDate;
	private String mEndDate;

	private Work(GraphObject graphObject)
	{
		/*
		 * employer
		 */
		mEmployer = getName(graphObject, EMPLOYER);

		/*
		 * location
		 */
		if (graphObject != null)
		{
			GraphObject graphObjectLocation = graphObject.getPropertyAs(LOCATION, GraphObject.class);
			if (graphObjectLocation != null)
			{
				mLocation = Location.create(graphObjectLocation);
			}
		}

		/*
		 * position
		 */
		mPosition = getName(graphObject, POSITION);

		/*
		 * description
		 */
		Object property = graphObject.getProperty(DESCRIPTION);
		mDescription = String.valueOf(property);

		/*
		 * start date
		 */
		property = graphObject.getProperty(START_DATE);
		mStartDate = String.valueOf(property);

		/*
		 * end date
		 */
		property = graphObject.getProperty(END_DATE);
		mEndDate = String.valueOf(property);
	}

	private static String getName(GraphObject graphObject, String property)
	{
		JSONObject jsonObject = (JSONObject)graphObject.getProperty(property);
		if (jsonObject != null)
		{
			String name = jsonObject.optString(NAME);
			return name;
		}
		return null;
	}

	public static Work create(GraphObject graphObject)
	{
		return new Work(graphObject);
	}

	public String getEmployer()
	{
		return mEmployer;
	}

	public Location getLocation()
	{
		return mLocation;
	}

	public String getPosition()
	{
		return mPosition;
	}

	public String getDescription()
	{
		return mDescription;
	}

	public String getStartDate()
	{
		return mStartDate;
	}

	public String getEndDate()
	{
		return mEndDate;
	}
}
