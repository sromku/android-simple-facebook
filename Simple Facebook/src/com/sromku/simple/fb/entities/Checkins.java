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
    private Application mApplication;
    private List<Comment> mComments;
    private Long mCreatedTime;
    private User mFrom;
    private String mId;
    private List<Like> mLikes;
    private String mMessage;
    private Place mPlace;
    private List<User> mTags;

    private Checkins(GraphObject graphObject) {
	mGraphObject = graphObject;

	if (mGraphObject != null) {

	    // create application
	    mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));

	    // create comments
	    Utils.createList(mGraphObject, COMMENTS, new Converter<Comment>() {
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
	    Utils.createList(mGraphObject, LIKES, new Converter<Like>() {
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
	    Utils.createList(mGraphObject, TAGS, new Converter<User>() {
		@Override
		public User convert(GraphObject graphObject) {
		    return Utils.createUser(graphObject);
		}
	    });

	}
    }

    public static Checkins create(GraphObject graphObject) {
	return new Checkins(graphObject);
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
     * Information about the Facebook Page that represents the location of the checkin.
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
