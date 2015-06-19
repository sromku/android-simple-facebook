package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/reference/api/group
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

    @SerializedName(COVER)
    private String mCover;

    @SerializedName(DESCRIPTION)
    private String mDescription;

    @SerializedName(ICON)
    private String mIcon;

    @SerializedName(ID)
    private String mId;

    @SerializedName(LINK)
    private String mLink;

    @SerializedName(NAME)
    private String mName;

    @SerializedName(OWNER)
    private User mOwner;

    @SerializedName(PRIVACY)
    private GroupPrivacy mPrivacy;

    @SerializedName(UPDATED_TIME)
    private Date mUpdatedTime;

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
    public Date getUpdatedTime() {
        return mUpdatedTime;
    }

    public enum GroupPrivacy {
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
