package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/group
 */
public class Group {

	private static final String COVER = "cover";
	private static final String DESCRIPTION = "description";
	private static final String ICON = "icon";
	private static final String ID = "id";
	private static final String LINK = "link";
	private static final String NAME = "name";
	private static final String OWNER = "owner";
	private static final String PRIVACY = "privacy";
	private static final String UPDATED_TIME = "updated_time";

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

	/**
	 * The URL for the group's cover photo.
	 */
	public String getCover() {
		return mCover;
	}

	/**
	 * A brief description of the group.
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * The URL for the group's icon.
	 */
	public String getIcon() {
		return mIcon;
	}

	/**
	 * The group Id.
	 */
	public String getId() {
		return mId;
	}

	/**
	 * The URL for the group's website.
	 */
	public String getLink() {
		return mLink;
	}

	/**
	 * The name of the group.
	 */
	public String getName() {
		return mName;
	}

	/**
	 * The profile that created this group.
	 */
	public User getOwner() {
		return mOwner;
	}

	/**
	 * The privacy setting of the group.
	 */
	public GroupPrivacy getPrivacy() {
		return mPrivacy;
	}

	/**
	 * The last time the group was updated.
	 */
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
