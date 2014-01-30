package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/group
 */
public class Group {

    public static final String COVER = "cover";
    public static final String DESCRIPTION = "description";
    public static final String ICON = "icon";
    public static final String ID = "id";
    public static final String LINK = "link";
    public static final String NAME = "name";
    public static final String OWNER = "owner";
    public static final String PRIVACY = "privacy";
    public static final String UPDATED_TIME = "updated_time";

    private String mCover;
    private String mDescription;
    private String mIcon;
    private String mId;
    private String mLink;
    private String mName;
    private User mOwner;
    private GroupPrivacy mPrivacy;
    private Long mUpdatedTime;

    private Group(GraphObject graphObject) {

	// cover
	mCover = Utils.getPropertyInsideProperty(graphObject, COVER, "source");

	// description
	mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);

	// icon
	mIcon = Utils.getPropertyString(graphObject, ICON);

	// id
	mId = Utils.getPropertyString(graphObject, ID);

	// link
	mLink = Utils.getPropertyString(graphObject, LINK);

	// name
	mName = Utils.getPropertyString(graphObject, NAME);

	// user
	mOwner = Utils.createUser(graphObject, OWNER);

	// privacy
	String privacy = Utils.getPropertyString(graphObject, PRIVACY);
	mPrivacy = GroupPrivacy.fromValue(privacy);

	// updated time
	mUpdatedTime = Utils.getPropertyLong(graphObject, UPDATED_TIME);
    }

    public static Group create(GraphObject graphObject) {
	return new Group(graphObject);
    }

    public String getCover() {
	return mCover;
    }

    public String getDescription() {
	return mDescription;
    }

    public String getIcon() {
	return mIcon;
    }
    
    public String getId() {
	return mId;
    }

    public String getLink() {
	return mLink;
    }

    public String getName() {
	return mName;
    }

    public User getOwner() {
	return mOwner;
    }

    public GroupPrivacy getPrivacy() {
	return mPrivacy;
    }

    public Long getUpdatedTime() {
	return mUpdatedTime;
    }

    public static enum GroupPrivacy {
	OPEN("OPEN"),
	CLOSED("CLOSED"),
	SECRET("SECRET");

	private String value;

	private GroupPrivacy(String value) {
	    this.value = value;
	}

	public String getValue() {
	    return value;
	}

	public static GroupPrivacy fromValue(String value) {
	    for (GroupPrivacy groupPrivacy : values()) {
		if (groupPrivacy.value.equals(value)) {
		    return groupPrivacy;
		}
	    }
	    return null;
	}
    }
}
