package com.sromku.simple.fb.entities;

import java.util.List;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;
import com.sromku.simple.fb.utils.Utils.Converter;

/**
 * A Checkin represents a single visit to a location.
 * 
 * @see https://developers.facebook.com/docs/reference/api/checkin
 */
public class Checkin {

	private static final String ID = "id";
	private static final String APPLICATION = "application";
	private static final String COMMENTS = "comments";
	private static final String CREATED_TIME = "created_time";
	private static final String FROM = "from";
	private static final String LIKES = "likes";
	private static final String MESSAGE = "message";
	private static final String PLACE = "place";
	private static final String TAGS = "tags";

	private final GraphObject mGraphObject;
	private Application mApplication;
	private List<Comment> mComments;
	private Long mCreatedTime;
	private User mFrom;
	private String mId;
	private List<Like> mLikes;
	private String mMessage;
	private Place mPlace;
	private List<User> mTags;

	private Checkin(GraphObject graphObject) {
		mGraphObject = graphObject;

		if (mGraphObject != null) {

			// create application
			mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));

			// create comments
			mComments = Utils.createList(mGraphObject, COMMENTS, "data", new Converter<Comment>() {
				@Override
				public Comment convert(GraphObject graphObject) {
					return Comment.create(graphObject);
				}
			});

			// created time
			mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);

			// from
			mFrom = Utils.createUser(graphObject, FROM);

			// id
			mId = Utils.getPropertyString(graphObject, ID);

			// create likes
			mLikes = Utils.createList(mGraphObject, LIKES, "data", new Converter<Like>() {
				@Override
				public Like convert(GraphObject graphObject) {
					return Like.create(graphObject);
				}
			});

			// message
			mMessage = Utils.getPropertyString(graphObject, MESSAGE);

			// place
			mPlace = Place.create(Utils.getPropertyGraphObject(graphObject, PLACE));

			// create tags
			mTags = Utils.createList(mGraphObject, TAGS, "data", new Converter<User>() {
				@Override
				public User convert(GraphObject graphObject) {
					return Utils.createUser(graphObject);
				}
			});

		}
	}

	public static Checkin create(GraphObject graphObject) {
		return new Checkin(graphObject);
	}

	/**
	 * Information about the application that made the checkin.
	 */
	public Application getApplication() {
		return mApplication;
	}

	/**
	 * All of the comments on this checkin.
	 */
	public List<Comment> getComments() {
		return mComments;
	}

	/**
	 * The time the checkin was created.
	 */
	public Long getCreatedTime() {
		return mCreatedTime;
	}

	/**
	 * The ID and name of the user who made the checkin.
	 */
	public User getFrom() {
		return mFrom;
	}

	/**
	 * The checkin Id.
	 */
	public String getId() {
		return mId;
	}

	/**
	 * Users who like the checkin.
	 */
	public List<Like> getLikes() {
		return mLikes;
	}

	/**
	 * The message the user added to the checkin.
	 */
	public String getMessage() {
		return mMessage;
	}

	/**
	 * Information about the Facebook Page that represents the location of the
	 * checkin.
	 */
	public Place getPlace() {
		return mPlace;
	}

	/**
	 * The users the author tagged in the checkin.
	 */
	public List<User> getTags() {
		return mTags;
	}

}
