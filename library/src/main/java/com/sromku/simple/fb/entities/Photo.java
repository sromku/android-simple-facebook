package com.sromku.simple.fb.entities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.List;

/**
 * @author sromku
 * // @see https://developers.facebook.com/docs/graph-api/reference/photo
 */
public class Photo implements Publishable {

    private static final String ID = "id";
    private static final String ALBUM = "album";
    private static final String BACKDATED_TIME = "backdated_time";
    private static final String BACKDATED_TIME_GRANULARITY = "backdate_time_granularity";
    private static final String CREATED_TIME = "created_time";
    private static final String FROM = "from";
    private static final String HEIGHT = "height";
    private static final String ICON = "icon";
    private static final String IMAGES = "images";
    private static final String LINK = "link";
    private static final String PAGE_STORY_ID = "page_story_id";
    private static final String PICTURE = "picture";
    private static final String PLACE = "place";
    private static final String SOURCE = "source";
    private static final String UPDATED_TIME = "updated_time";
    private static final String WIDTH = "width";
    private static final String NAME = "name";
    private static final String MESSAGE = "message"; // same as NAME
    private static final String PRIVACY = "privacy";

    @SerializedName(ID)
    private String mId;

    @SerializedName(ALBUM)
    private Album mAlbum;

    @SerializedName(BACKDATED_TIME)
    private Date mBackDatetime;

    @SerializedName(BACKDATED_TIME_GRANULARITY)
    private BackDatetimeGranularity mBackDatetimeGranularity;

    @SerializedName(CREATED_TIME)
    private Date mCreatedTime;

    @SerializedName(FROM)
    private User mFrom;

    @SerializedName(HEIGHT)
    private Integer mHeight;

    @SerializedName(ICON)
    private String mIcon;

    @SerializedName(IMAGES)
    private List<Image> mImages;

    @SerializedName(LINK)
    private String mLink;

    @SerializedName(NAME)
    private String mName;

    @SerializedName(PAGE_STORY_ID)
    private String mPageStoryId;

    @SerializedName(PICTURE)
    private String mPicture;

    @SerializedName(SOURCE)
    private String mSource;

    @SerializedName(UPDATED_TIME)
    private Date mUpdatedTime;

    @SerializedName(WIDTH)
    private Integer mWidth;

    @SerializedName(PLACE)
    private Place mPlace;

    private String mPlaceId = null;
    private Parcelable mParcelable = null;
    private byte[] mBytes = null;
    private Privacy mPrivacy = null;

    private Photo(Builder builder) {
        mName = builder.mName;
        mPlaceId = builder.mPlaceId;
        mParcelable = builder.mParcelable;
        mBytes = builder.mBytes;
        mPrivacy = builder.mPrivacy;
    }

    @Override
    public String getPath() {
        return GraphPath.PHOTOS;
    }

    @Override
    public Permission getPermission() {
        return Permission.PUBLISH_ACTION;
    }

    /**
     * Get id of the photo
     *
     * @return
     */
    public String getId() {
        return mId;
    }

    public Album getAlbum() {
        return mAlbum;
    }

    public Date getBackDateTime() {
        return mBackDatetime;
    }

    public BackDatetimeGranularity getBackDatetimeGranularity() {
        return mBackDatetimeGranularity;
    }

    public Date getCreatedTime() {
        return mCreatedTime;
    }

    public User getFrom() {
        return mFrom;
    }

    public Integer getHeight() {
        return mHeight;
    }

    public String getIcon() {
        return mIcon;
    }

    public List<Image> getImages() {
        return mImages;
    }

    public String getLink() {
        return mLink;
    }

    public String getName() {
        return mName;
    }

    public String getPageStoryId() {
        return mPageStoryId;
    }

    public String getPicture() {
        return mPicture;
    }

    public Place getPlace() {
        return mPlace;
    }

    public String getSource() {
        return mSource;
    }

    public Date getUpdatedTime() {
        return mUpdatedTime;
    }

    public Integer getWidth() {
        return mWidth;
    }

    /**
     * Is used for publishing action
     */
    public Parcelable getParcelable() {
        return mParcelable;
    }

    /**
     * Is used for publishing action
     */
    public String getPlaceId() {
        return mPlaceId;
    }

    public Bundle getBundle() {
        Bundle bundle = new Bundle();

        // add description
        if (mName != null) {
            bundle.putString(MESSAGE, mName);
        }

        // add place
        if (mPlaceId != null) {
            bundle.putString(PLACE, mPlaceId);
        }

        // add privacy
        if (mPrivacy != null) {
            bundle.putString(PRIVACY, mPrivacy.getJSONString());
        }

        // add image
        if (mParcelable != null) {
            bundle.putParcelable(PICTURE, mParcelable);
        } else if (mBytes != null) {
            bundle.putByteArray(PICTURE, mBytes);
        }

        return bundle;
    }

    public enum BackDatetimeGranularity {
        YEAR("year"),
        MONTH("month"),
        DAY("day"),
        HOUR("hour"),
        MIN("min"),
        NONE("none");

        private String mValue;

        private BackDatetimeGranularity(String value) {
            mValue = value;
        }

        public String getValue() {
            return mValue;
        }

        public static BackDatetimeGranularity fromValue(String value) {
            for (BackDatetimeGranularity granularityEnum : values()) {
                if (granularityEnum.mValue.equals(value)) {
                    return granularityEnum;
                }
            }
            return BackDatetimeGranularity.NONE;
        }
    }

    /**
     * Builder for preparing the Photo object to be published.
     */
    public static class Builder {
        private String mName = null;
        private String mPlaceId = null;

        private Parcelable mParcelable = null;
        private byte[] mBytes = null;
        private Privacy mPrivacy = null;

        public Builder() {
        }

        /**
         * Set photo to be published
         *
         * @param bitmap
         */
        public Builder setImage(Bitmap bitmap) {
            mParcelable = bitmap;
            return this;
        }

        /**
         * Set photo to be published
         *
         * @param file
         */
        public Builder setImage(File file) {
            try {
                mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            } catch (FileNotFoundException e) {
                Logger.logError(Photo.class, "Failed to create photo from file", e);
            }
            return this;
        }

        /**
         * Set photo to be published
         *
         * @param bytes
         */
        public Builder setImage(byte[] bytes) {
            mBytes = bytes;
            return this;
        }

        /**
         * Add name/description to the photo
         *
         * @param name
         *            The name/description of the photo
         */
        public Builder setName(String name) {
            mName = name;
            return this;
        }

        /**
         * Add place id of the photo
         *
         * @param placeId
         *            The place id of the photo
         */
        public Builder setPlace(String placeId) {
            mPlaceId = placeId;
            return this;
        }

        /**
         * Add privacy setting to the photo
         *
         * @param privacy
         *            The privacy setting of the photo
         * @see com.sromku.simple.fb.entities.Privacy
         */
        public Builder setPrivacy(Privacy privacy) {
            mPrivacy = privacy;
            return this;
        }

        public Photo build() {
            return new Photo(this);
        }
    }

    @Override
    public String toString() {
        return mSource;
    }
}
