package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import com.facebook.model.GraphObject;

/**
 * Album entity
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/album/
 */
public class Album
{
	private final GraphObject mGraphObject;
	private String mId = null;
	private String mFromId = null;
	private String mName = null;
	private String mDescription = null;
	private String mLocation = null;
	private String mLink = null;
	private Integer mCount = null;
	private String mPrivacy = null;
	private String mCoverPhotoId = null;
	private String mType = null;
	private long mCreatedTime;
	private long mUpdatedTime;
	private boolean mCanUpload;

	private Album(GraphObject graphObject)
	{
		mGraphObject = graphObject;

		// id
		mId = String.valueOf(mGraphObject.getProperty("id"));

		// from
		JSONObject jsonObject = (JSONObject)mGraphObject.getProperty("from");
		mFromId = String.valueOf(jsonObject.optString("id"));

		// name
		mName = String.valueOf(mGraphObject.getProperty("name"));

		// description
		mDescription = String.valueOf(mGraphObject.getProperty("description"));

		// location
		mLocation = String.valueOf(mGraphObject.getProperty("location"));

		// link
		mLink = String.valueOf(mGraphObject.getProperty("link"));

		// count
		if (mGraphObject.getProperty("count") != null)
		{
			mCount = Integer.valueOf(String.valueOf(mGraphObject.getProperty("count")));
		}

		// privacy
		mPrivacy = String.valueOf(mGraphObject.getProperty("privacy"));

		// cover photo
		mCoverPhotoId = String.valueOf(mGraphObject.getProperty("cover_photo"));

		// type
		mType = String.valueOf(mGraphObject.getProperty("type"));

		// created time
		mCreatedTime = Long.valueOf(String.valueOf(mGraphObject.getProperty("created_time")));

		// updated time
		mUpdatedTime = Long.valueOf(String.valueOf(mGraphObject.getProperty("updated_time")));

		// can upload
		mCanUpload = Boolean.valueOf(String.valueOf(mGraphObject.getProperty("can_upload")));
	}

	/**
	 * Create new album based on {@link GraphObject} instance.
	 * 
	 * @param graphObject The {@link GraphObject} instance
	 * @return {@link Album}
	 */
	public static Album create(GraphObject graphObject)
	{
		return new Album(graphObject);
	}

	/**
	 * Return the graph object
	 * 
	 * @return
	 */
	public GraphObject getGraphObject()
	{
		return mGraphObject;
	}

	/**
	 * The album id<br>
	 * 
	 * @return The album id
	 */
	public String getId()
	{
		return mId;
	}

	/**
	 * The profile id that created this album
	 * 
	 * @return The profile id that created this album
	 */
	public String getFromId()
	{
		return mFromId;
	}

	/**
	 * The title of the album
	 * 
	 * @return The title of the album
	 */
	public String getName()
	{
		return mName;
	}

	/**
	 * The description of the album
	 * 
	 * @return The description of the album
	 */
	public String getDescription()
	{
		return mDescription;
	}

	/**
	 * The location of the album
	 * 
	 * @return The location of the album
	 */
	public String getLocation()
	{
		return mLocation;
	}

	/**
	 * A link to this album on Facebook
	 * 
	 * @return A link to this album on Facebook
	 */
	public String getLink()
	{
		return mLink;
	}

	/**
	 * The number of photos in this album
	 * 
	 * @return The number of photos in this album
	 */
	public Integer getCount()
	{
		return mCount;
	}

	/**
	 * The privacy settings for the album
	 * 
	 * @return The privacy settings for the album
	 */
	public String getPrivacy()
	{
		return mPrivacy;
	}

	/**
	 * The album cover photo id
	 * 
	 * @return The album cover photo id
	 */
	public String getCoverPhotoId()
	{
		return mCoverPhotoId;
	}

	/**
	 * The type of the album
	 * 
	 * @return The type of the album
	 */
	public String getType()
	{
		return mType;
	}

	/**
	 * The time the photo album was initially created
	 * 
	 * @return The time the photo album was initially created
	 */
	public long getCreatedTime()
	{
		return mCreatedTime;
	}

	/**
	 * The last time the photo album was updated
	 * 
	 * @return The last time the photo album was updated
	 */
	public long getUpdatedTime()
	{
		return mUpdatedTime;
	}

	/**
	 * Determines whether the user can upload to the album and returns true if the user owns the album, the
	 * album is not full, and the app can add photos to the album. <br>
	 * <br>
	 * <b>Important</b> The privacy setting of the app should be at minimum as the privacy setting of the
	 * album ({@link #getPrivacy()}.
	 * 
	 * @return <code>True</code> if user can upload to this album
	 */
	public boolean canUpload()
	{
		return mCanUpload;
	}

}
