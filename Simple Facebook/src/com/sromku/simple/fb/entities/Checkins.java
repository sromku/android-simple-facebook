package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class Checkins {
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String APPLICATION = "application";
	public static final String COMMENTS = "comments";
	public static final String CREATED_TIME = "created_time";
	public static final String FROM = "from";	
	public static final String LIKES = "likes";
	public static final String MESSAGE = "message";
	public static final String PLACE = "place";
	public static final String TAGS = "tags";
	public static final String TYPE = "type";

	private final GraphObject mGraphObject;
	private String mId = null;
	private String mFromId = null;
	private String mMessage = null;
	private String mAplicationId = null;
	private Place mPlace = null;
	private String mCreatedTime;
	
	private Checkins(GraphObject graphObject)
	{
		mGraphObject = graphObject;
		
		if (mGraphObject != null)
		{
			// id
			mId = String.valueOf(mGraphObject.getProperty("id"));
			
			// from
			JSONObject jsonObject = (JSONObject)mGraphObject.getProperty("from");
			mFromId = String.valueOf(jsonObject.optString("id"));
			
			// message
			mMessage = String.valueOf(mGraphObject.getProperty("message"));
			
			// aplication
			jsonObject = (JSONObject)mGraphObject.getProperty("application");
			mAplicationId = String.valueOf(jsonObject.optString("id"));
			
			/*
			 * place
			 */			
			GraphObject graphObjectPlace = graphObject.getPropertyAs("place", 
					GraphObject.class);
			if (graphObjectPlace != null)
			{
				mPlace = Place.create(graphObjectPlace);
			}
			
			// created time
			mCreatedTime = String.valueOf(mGraphObject.getProperty("created_time"));
		}
	}
	
	public static Checkins create(GraphObject graphObject)
	{
		return new Checkins(graphObject);
	}

	public String getId() {
		return mId;
	}

	public void setId(String mId) {
		this.mId = mId;
	}

	public Place getPlace() {
		return mPlace;
	}

	public void setPlace(Place mPlace) {
		this.mPlace = mPlace;
	}

	public String getFromId() {
		return mFromId;
	}

	public void setFromId(String mFromId) {
		this.mFromId = mFromId;
	}

	public String getMessage() {
		return mMessage;
	}

	public void setMessage(String mMessage) {
		this.mMessage = mMessage;
	}

	public String getAplicationId() {
		return mAplicationId;
	}

	public void setAplicationId(String mAplicationId) {
		this.mAplicationId = mAplicationId;
	}

	public String getCreatedTime() {
		return mCreatedTime;
	}

	public void setCreatedTime(String mCreatedTime) {
		this.mCreatedTime = mCreatedTime;
	}
	
}
