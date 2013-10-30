package com.sromku.simple.fb.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.facebook.model.GraphObject;

public class Education
{
	public static final String SCHOOL = "school";
	public static final String DEGREE = "degree";
	public static final String YEAR = "year";
	public static final String CONCENTRATION = "concentration";
	public static final String TYPE = "type";
	public static final String NAME = "name";

	private String mSchool;
	private String mDegree;
	private String mYear;
	private List<String> mConcentration = new ArrayList<String>();
	private String mType;

	private Education(GraphObject graphObject)
	{
		/*
		 * school
		 */
		JSONObject jsonObject = (JSONObject)graphObject.getProperty(SCHOOL);
		if (jsonObject != null)
		{
			mSchool = jsonObject.optString(NAME);
		}

		/*
		 * degree
		 */
		jsonObject = (JSONObject)graphObject.getProperty(DEGREE);
		if (jsonObject != null)
		{
			mDegree = jsonObject.optString(NAME);
		}

		/*
		 * degree
		 */
		jsonObject = (JSONObject)graphObject.getProperty(YEAR);
		if (jsonObject != null)
		{
			mYear = jsonObject.optString(NAME);
		}

		/*
		 * concentration
		 */
		JSONArray jsonArray = (JSONArray)graphObject.getProperty(CONCENTRATION);
		if (jsonArray != null)
		{
			for (int i = 0; i < jsonArray.length(); i++)
			{
				jsonObject = jsonArray.optJSONObject(i);
				if (jsonObject != null)
				{
					String concentration = jsonObject.optString(NAME);
					mConcentration.add(concentration);
				}
			}
		}

		/*
		 * type
		 */
		String type = String.valueOf(graphObject.getProperty(TYPE));
		mType = type;
	}

	public static Education create(GraphObject graphObject)
	{
		return new Education(graphObject);
	}

	public String getSchool()
	{
		return mSchool;
	}

	public String getDegree()
	{
		return mDegree;
	}

	public String getYear()
	{
		return mYear;
	}

	public List<String> getConcentrations()
	{
		return mConcentration;
	}

	public String getType()
	{
		return mType;
	}
}
