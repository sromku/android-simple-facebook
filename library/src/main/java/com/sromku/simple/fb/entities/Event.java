package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/graph-api/reference/event
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

    @SerializedName(DESCRIPTION)
    private String mDescription;

    @SerializedName(END_TIME)
    private Date mEndTime;

    @SerializedName(ID)
    private String mId;

    @SerializedName(LOCATION)
    private String mLocation;

    @SerializedName(NAME)
    private String mName;

    @SerializedName(OWNER)
    private User mOwner;

    /**
     * The URL of the event's picture (only returned if you explicitly include
     * picture in the fields param; example: ?fields=id,name,picture)
     */
    @SerializedName(PICTURE)
    private String mPicture;

    @SerializedName(PRIVACY)
    private EventPrivacy mPrivacy;

    @SerializedName(START_TIME)
    private Date mStartTime;

    @SerializedName(TICKET_URI)
    private String mTicketUri;

    @SerializedName(UPDATED_TIME)
    private Date mUpdatedTime;

    @SerializedName(VENUE)
    private Place mVenue;

    /**
     * The attendance options of the user. He can accept and <b>attend</b> the
     * event, or say <b>maybe</b>, or totally <b>decline</b> the invitation.
     *
     * <li>{@link #ATTENDING}</li> <li>{@link #MAYBE}</li> <li>{@link #DECLINED}
     * </li><br>
     *
     * @author sromku
     */
    public enum EventDecision {
        /**
         * Events that user decided to attend
         */
        ATTENDING("attending"),
        /**
         * Events that user said maybe
         */
        MAYBE("maybe"),
        /**
         * Events that user created
         */
        CREATED("created"),
        /**
         * Events that user not replied
         */
        NOT_REPLIED("not_replied"),
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

    public enum EventPrivacy {
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
    public Date getEndTime() {
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
    public Date getStartTime() {
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
    public Date getUpdatedTime() {
        return mUpdatedTime;
    }

    /**
     * The location of this event.
     */
    public Place getVenue() {
        return mVenue;
    }
}
