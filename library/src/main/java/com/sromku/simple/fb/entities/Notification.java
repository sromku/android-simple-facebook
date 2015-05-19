package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Notification {

	private static final String ID = "id";
	private static final String FROM = "from";
	private static final String TO = "to";
	private static final String CREATED_TIME = "created_time";
	private static final String UPDATED_TIME = "updated_time";
	private static final String TITLE = "title";
	private static final String LINK = "link";
	private static final String APPLICATION = "application";
	private static final String UNREAD = "unread";
//	private static final String OBJECT = "object";

    @SerializedName(ID)
	private String mId;

    @SerializedName(FROM)
	private String mFromId;

    @SerializedName(FROM)
	private String mFromName;

    @SerializedName(TO)
	private User mTo;

    @SerializedName(CREATED_TIME)
	private Date mCreatedTime;

    @SerializedName(UPDATED_TIME)
	private Date mUpdatedTime;

    @SerializedName(TITLE)
	private String mTitle;

    @SerializedName(LINK)
	private String mLink;

    @SerializedName(APPLICATION)
	private Application mApplication;

    @SerializedName(UNREAD)
	private Boolean mIsUnread;

//	private GraphObject mObject;

//	private Notification(GraphObject graphObject) {
//
//		// id
//		mId = Utils.getPropertyString(graphObject, ID);
//
//		// from id, name
//		User userFrom = Utils.createUser(graphObject, FROM);
//		mFromId = userFrom.getId();
//		mFromName = userFrom.getName();
//
//		// to id, name
//		mTo = Utils.createUser(graphObject, TO);
//
//		// created time
//		mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);
//
//		// updated time
//		mUpdatedTime = Utils.getPropertyLong(graphObject, UPDATED_TIME);
//
//		// title
//		mTitle = Utils.getPropertyString(graphObject, TITLE);
//
//		// link
//		mLink = Utils.getPropertyString(graphObject, LINK);
//
//		// application
//		mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));
//
//		// unread
//		Integer unread = Utils.getPropertyInteger(graphObject, UNREAD);
//		mIsUnread = unread == 1 ? true : false;
//
//		// object
//		mObject = Utils.getPropertyGraphObject(graphObject, OBJECT);
//	}
//
//	public static Notification create(GraphObject graphObject) {
//		return new Notification(graphObject);
//	}

	/**
	 * The notification's id.
	 */
	public String getId() {
		return mId;
	}

	/**
	 * The entity (user, page, app, etc.) that 'sent', or is the source of the
	 * notification.
	 */
	public String getFromId() {
		return mFromId;
	}

	/**
	 * The entity (user, page, app, etc.) that 'sent', or is the source of the
	 * notification.
	 */
	public String getFromName() {
		return mFromName;
	}

	/**
	 * The entity that received the notification.
	 */
	public User getTo() {
		return mTo;
	}

	/**
	 * When the notification was created.
	 */
	public Date getCreatedTime() {
		return mCreatedTime;
	}

	/**
	 * When the notification was last updated.
	 */
	public Date getUpdatedTime() {
		return mUpdatedTime;
	}

	/**
	 * The message text in the notification.
	 */
	public String getTitle() {
		return mTitle;
	}

	/**
	 * The URL that clicking on the notification would take someone.
	 */
	public String getLink() {
		return mLink;
	}

	/**
	 * The app responsible for generating the notification. Some of the core
	 * Facebook features have their own app that shows up here, such as likes
	 * when someone likes another person's content.
	 */
	public Application getApplication() {
		return mApplication;
	}

	/**
	 * Indicates that the notification is unread. Note that 'read' notifications
	 * will not be accessible.
	 */
	public Boolean getIsUnread() {
		return mIsUnread;
	}

	/**
	 * The object (this can be a post, a photo, a comment, etc.) that was the
	 * subject of the notification.
	 */
//	public GraphObject getObject() {
//		return mObject;
//	}

}
