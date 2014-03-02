package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/graph-api/reference/event
 */
public class Event {

	private static final String DESCRIPTION = "description";
	private static final String END_TIME = "end_time";
	private static final String ID = "id";
	private static final String LOCATION = "location";
	private static final String NAME = "name";
	private static final String OWNER = "owner";
	private static final String PICTURE = "picture";
	private static final String PRIVACY = "privacy";
	private static final String START_TIME = "start_time";
	private static final String TICKET_URI = "ticket_uri";
	private static final String UPDATED_TIME = "updated_time";
	private static final String VENUE = "venue";

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
	private EventPrivacy mPrivacy;
	private Long mStartTime;
	private String mTicketUri;
	private Long mUpdatedTime;
	private Place mVenue;

	private Event(GraphObject graphObject) {
		// description
		mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);

		// end time
		mEndTime = Utils.getPropertyLong(graphObject, END_TIME);

		// id
		mId = Utils.getPropertyString(graphObject, ID);

		// location
		mLocation = Utils.getPropertyString(graphObject, LOCATION);

		// name
		mName = Utils.getPropertyString(graphObject, NAME);

		// owner
		mOwner = Utils.createUser(graphObject, OWNER);

		// picture
		mPicture = Utils.getPropertyString(graphObject, PICTURE);

		// private
		String privacy = Utils.getPropertyString(graphObject, PRIVACY);
		mPrivacy = EventPrivacy.fromValue(privacy);

		// start time
		mStartTime = Utils.getPropertyLong(graphObject, START_TIME);

		// ticket uri
		mTicketUri = Utils.getPropertyString(graphObject, TICKET_URI);

		// updated time
		mUpdatedTime = Utils.getPropertyLong(graphObject, UPDATED_TIME);

		// venue
		mVenue = Place.create(Utils.getPropertyGraphObject(graphObject, VENUE));
	}

	public static Event create(GraphObject graphObject) {
		return new Event(graphObject);
	}

	/**
	 * The attendance options of the user. He can accept and <b>attend</b> the
	 * event, or say <b>maybe</b>, or totally <b>decline</b> the invitation.
	 * 
	 * <li>{@link #ATTENDING}</li> <li>{@link #MAYBE}</li> <li>{@link #DECLINED}
	 * </li><br>
	 * 
	 * @author sromku
	 */
	public static enum EventDecision {
		/**
		 * Events that user decided to attend
		 */
		ATTENDING("attending"),
		/**
		 * Events that user said maybe
		 */
		MAYBE("maybe"),
		/**
		 * Events that user declined
		 */
		DECLINED("declined");

		private String graphNode;

		private EventDecision(String graphNode) {
			this.graphNode = graphNode;
		}

		public String getGraphNode() {
			return graphNode;
		}
	}

	public static enum EventPrivacy {
		OPEN("open"),
		SECRET("secret"),
		FRIENDS("friends"),
		CLOSED("closed");

		private String value;

		private EventPrivacy(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public static EventPrivacy fromValue(String value) {
			for (EventPrivacy privacyEnum : values()) {
				if (privacyEnum.value.equals(value)) {
					return privacyEnum;
				}
			}
			return null;
		}
	}

	/**
	 * The long-form description of the event.
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * The end time of the event, if one has been set.
	 */
	public Long getEndTime() {
		return mEndTime;
	}

	/**
	 * The event Id.
	 */
	public String getId() {
		return mId;
	}

	/**
	 * The location for this event.
	 */
	public String getLocation() {
		return mLocation;
	}

	/**
	 * The event title.
	 */
	public String getName() {
		return mName;
	}

	/**
	 * The profile that created the event.
	 */
	public User getOwner() {
		return mOwner;
	}

	/**
	 * The URL of the event's picture.
	 */
	public String getPicture() {
		return mPicture;
	}

	/**
	 * The visibility of this event.
	 */
	public EventPrivacy getPrivacy() {
		return mPrivacy;
	}

	/**
	 * The start time of the event, as you want it to be displayed on facebook.
	 */
	public Long getStartTime() {
		return mStartTime;
	}

	/**
	 * The URL to a location to buy tickets for this event (on Events for Pages
	 * only).
	 */
	public String getTicketUri() {
		return mTicketUri;
	}

	/**
	 * The last time the event was updated.
	 */
	public Long getUpdatedTime() {
		return mUpdatedTime;
	}

	/**
	 * The location of this event.
	 */
	public Place getVenue() {
		return mVenue;
	}
}
