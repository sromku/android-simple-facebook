package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * Album entity.
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/album
 */
public class Album {

	private static final String ID = "id";
	private static final String FROM = "from";
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private static final String LOCATION = "location";
	private static final String LINK = "link";
	private static final String COUNT = "count";
	private static final String PRIVACY = "privacy";
	private static final String COVER_PHOTO = "cover_photo";
	private static final String TYPE = "type";
	private static final String CREATED_TIME = "created_time";
	private static final String UPDATED_TIME = "updated_time";
	private static final String CAN_UPLOAD = "can_upload";

	private final GraphObject mGraphObject;
	private String mId = null;
	private User mFrom = null;
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

	private Album(GraphObject graphObject) {
		mGraphObject = graphObject;
		if (graphObject == null) {
			return;
		}

		// id
		mId = Utils.getPropertyString(graphObject, ID);

		// from
		mFrom = Utils.createUser(graphObject, FROM);

		// name
		mName = Utils.getPropertyString(graphObject, NAME);

		// description
		mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);

		// location
		mLocation = Utils.getPropertyString(graphObject, LOCATION);

		// link
		mLink = Utils.getPropertyString(graphObject, LINK);

		// count
		mCount = Utils.getPropertyInteger(graphObject, COUNT);

		// privacy
		mPrivacy = Utils.getPropertyString(graphObject, PRIVACY);

		// cover photo
		mCoverPhotoId = Utils.getPropertyString(graphObject, COVER_PHOTO);

		// type
		mType = Utils.getPropertyString(graphObject, TYPE);

		// created time
		mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);

		// updated time
		mUpdatedTime = Utils.getPropertyLong(graphObject, UPDATED_TIME);

		// can upload
		mCanUpload = Utils.getPropertyBoolean(graphObject, CAN_UPLOAD);
	}

	/**
	 * Create new album based on {@link GraphObject} instance.
	 * 
	 * @param graphObject
	 *            The {@link GraphObject} instance
	 * @return {@link Album}
	 */
	public static Album create(GraphObject graphObject) {
		return new Album(graphObject);
	}

	/**
	 * Return the graph object.
	 */
	public GraphObject getGraphObject() {
		return mGraphObject;
	}

	/**
	 * The album id.
	 * 
	 * @return The album id
	 */
	public String getId() {
		return mId;
	}

	/**
	 * The user who created this album.
	 * 
	 * @return The user who created this album
	 */
	public User getFrom() {
		return mFrom;
	}

	/**
	 * The title of the album.
	 * 
	 * @return The title of the album
	 */
	public String getName() {
		return mName;
	}

	/**
	 * The description of the album.
	 * 
	 * @return The description of the album
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * The location of the album.
	 * 
	 * @return The location of the album
	 */
	public String getLocation() {
		return mLocation;
	}

	/**
	 * A link to this album on Facebook.
	 * 
	 * @return A link to this album on Facebook
	 */
	public String getLink() {
		return mLink;
	}

	/**
	 * The number of photos in this album.
	 * 
	 * @return The number of photos in this album
	 */
	public Integer getCount() {
		return mCount;
	}

	/**
	 * The privacy settings for the album.
	 * 
	 * @return The privacy settings for the album
	 */
	public String getPrivacy() {
		return mPrivacy;
	}

	/**
	 * The album cover photo id.
	 * 
	 * @return The album cover photo id
	 */
	public String getCoverPhotoId() {
		return mCoverPhotoId;
	}

	/**
	 * The type of the album.
	 * 
	 * @return The type of the album
	 */
	public String getType() {
		return mType;
	}

	/**
	 * The time the photo album was initially created.
	 * 
	 * @return The time the photo album was initially created
	 */
	public long getCreatedTime() {
		return mCreatedTime;
	}

	/**
	 * The last time the photo album was updated.
	 * 
	 * @return The last time the photo album was updated
	 */
	public long getUpdatedTime() {
		return mUpdatedTime;
	}

	/**
	 * Determines whether the user can upload to the album and returns true if
	 * the user owns the album, the album is not full, and the app can add
	 * photos to the album. <br>
	 * <br>
	 * <b>Important</b> The privacy setting of the app should be at minimum as
	 * the privacy setting of the album ({@link #getPrivacy()}.
	 * 
	 * @return <code>True</code> if user can upload to this album
	 */
	public boolean canUpload() {
		return mCanUpload;
	}

}
