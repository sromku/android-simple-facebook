package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/event
 */
public class Event {

    public static final String DESCRIPTION = "description";
    public static final String END_TIME = "end_time";
    public static final String ID = "id";
    public static final String LOCATION = "location";
    public static final String NAME = "name";
    public static final String OWNER = "owner";
    public static final String PICTURE = "picture";
    public static final String PRIVACY = "privacy";
    public static final String START_TIME = "start_time";
    public static final String TICKET_URI = "ticket_uri";
    public static final String UPDATED_TIME = "updated_time";
    public static final String VENUE = "venue";

    private String mDescription;
    private Long mEndTime;
    private String mId;
    private String mLocation;
    private String mName;
    private User mOwner;

    /**
     * The URL of the event's picture (only returned if you explicitly include
     * picture in the fields param; example: ?fields=id,name,picture)
     */
    private String mPicture;
    private EventPrivacyType mPrivacy;
    private Long mStartTime;
    private String mTicketUri;
    private Long mUpdatedTime;
    private Place mVenue;

    private Event(GraphObject graphObject) {
	// description
	mDescription = String.valueOf(String.valueOf(graphObject.getProperty(DESCRIPTION)));

	// end time
	mEndTime = Long.valueOf(String.valueOf(graphObject.getProperty(END_TIME)));

	// id
	mId = String.valueOf(String.valueOf(graphObject.getProperty(ID)));

	// location
	mLocation = String.valueOf(String.valueOf(graphObject.getProperty(LOCATION)));

	// name
	mName = String.valueOf(String.valueOf(graphObject.getProperty(NAME)));

	// owner
	mOwner = Utils.createUser(graphObject, OWNER);

	// picture
	mPicture = String.valueOf(String.valueOf(graphObject.getProperty(PICTURE)));

	// private
	String privacy = String.valueOf(String.valueOf(graphObject.getProperty(PRIVACY)));
	mPrivacy = EventPrivacyType.fromValue(privacy);

	// start time
	mStartTime = Long.valueOf(String.valueOf(graphObject.getProperty(START_TIME)));

	// ticket uri
	mTicketUri = String.valueOf(String.valueOf(graphObject.getProperty(TICKET_URI)));

	// updated time
	mUpdatedTime = Long.valueOf(String.valueOf(graphObject.getProperty(UPDATED_TIME)));

	// venue
	mVenue = Place.create(graphObject.getPropertyAs(VENUE, GraphObject.class));
    }

    public static Event create(GraphObject graphObject) {
	return new Event(graphObject);
    }

    public static enum EventPrivacyType {
	OPEN("open"),
	SECRET("secret"),
	FRIENDS("friends"),
	CLOSED("closed");

	private String value;

	private EventPrivacyType(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}

	public static EventPrivacyType fromValue(String value) {
	    for (EventPrivacyType privacyEnum : values()) {
		if (privacyEnum.value.equals(value)) {
		    return privacyEnum;
		}
	    }
	    return null;
	}
    }

    public String getDescription() {
	return mDescription;
    }

    public Long getEndTime() {
	return mEndTime;
    }

    public String getId() {
	return mId;
    }

    public String getLocation() {
	return mLocation;
    }

    public String getName() {
	return mName;
    }

    public User getOwner() {
	return mOwner;
    }

    public String getPicture() {
	return mPicture;
    }

    public EventPrivacyType getPrivacy() {
	return mPrivacy;
    }
    
    public Long getStartTime() {
	return mStartTime;
    }
    
    public String getTicketUri() {
	return mTicketUri;
    }

    public Long getUpdatedTime() {
	return mUpdatedTime;
    }

    public Place getVenue() {
	return mVenue;
    }
}
