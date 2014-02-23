package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * Application request that is sent by one user to another.
 */
public class AppRequest {

	private static final String ID = "id";
	private static final String APPLICATION = "application";
	private static final String TO = "to";
	private static final String FROM = "from";
	private static final String DATA = "data";
	private static final String MESSAGE = "message";
	private static final String CREATED_TIME = "created_time";

	private final GraphObject mGraphObject;
	private String mRequestId;
	private Application mApplication;
	private User mTo;
	private User mFrom;
	private String mData;
	private String mMessage;
	private Long mCreatedTime;

	private AppRequest(GraphObject graphObject) {
		mGraphObject = graphObject;

		// request id
		mRequestId = Utils.getPropertyString(graphObject, ID);

		// create application
		mApplication = Application.create(Utils.getPropertyGraphObject(graphObject, APPLICATION));

		// to
		mTo = Utils.createUser(graphObject, TO);

		// from
		mFrom = Utils.createUser(graphObject, FROM);

		// data
		mData = Utils.getPropertyString(graphObject, DATA);

		// message
		mMessage = Utils.getPropertyString(graphObject, MESSAGE);

		// create time
		mCreatedTime = Utils.getPropertyLong(graphObject, CREATED_TIME);
	}

	public static AppRequest create(GraphObject graphObject) {
		return new AppRequest(graphObject);
	}

	public GraphObject getGraphObject() {
		return mGraphObject;
	}

	public String getRequestId() {
		return mRequestId;
	}

	/**
	 * The application used to send the request.
	 */
	public Application getApplication() {
		return mApplication;
	}

	/**
	 * The user who got the request.
	 */
	public User getTo() {
		return mTo;
	}

	/**
	 * The user who sent the request.
	 */
	public User getFrom() {
		return mFrom;
	}

	/**
	 * Optional data passed with the request for tracking purposes.
	 */
	public String getData() {
		return mData;
	}

	/**
	 * The message included with the request.
	 */
	public String getMessage() {
		return mMessage;
	}

	/**
	 * Timestamp that indicates when the request was sent.
	 */
	public Long getCreatedTime() {
		return mCreatedTime;
	}
}
