package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * A Checkin represents a single visit to a location.
 *
 * // @see https://developers.facebook.com/docs/reference/api/checkin
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

    @SerializedName(ID)
    private String mId;

    @SerializedName(APPLICATION)
    private Application mApplication;

    @SerializedName(COMMENTS)
    private List<Comment> mComments;

    @SerializedName(CREATED_TIME)
    private Date mCreatedTime;

    @SerializedName(FROM)
    private User mFrom;

    @SerializedName(LIKES)
    private List<Like> mLikes;

    @SerializedName(MESSAGE)
    private String mMessage;

    @SerializedName(PLACE)
    private Place mPlace;

    @SerializedName(TAGS)
    private List<User> mTags;

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
    public Date getCreatedTime() {
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
