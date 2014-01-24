package com.sromku.simple.fb.entities;

import java.io.File;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.Privacy;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;

public class Photo implements Publishable {
    private static final String PICTURE = "picture";
    private static final String PLACE = "place";
    private static final String MESSAGE = "message";
    private static final String PRIVACY = "privacy";

    private String mId;
    private String mSource;

    private String mDescription = null;
    private String mPlaceId = null;
    private Parcelable mParcelable = null;
    private byte[] mBytes = null;
    private Privacy mPrivacy = null;

    private Photo() {
    }

    public Photo(Builder builder) {
	mDescription = builder.mDescription;
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
	return Permission.PUBLISH_STREAM;
    }

    public static Photo create(GraphObject graphObject) {
	Photo photo = new Photo();
	photo.mId = String.valueOf(graphObject.getProperty("id"));
	photo.mSource = String.valueOf(graphObject.getProperty("source"));
	return photo;
    }

    /**
     * Get id of the photo
     * 
     * @return
     */
    public String getId() {
	return mId;
    }

    /**
     * Get source of the photo
     * 
     * @return
     */
    public String getSource() {
	return mSource;
    }

    public Bundle getBundle() {
	Bundle bundle = new Bundle();

	// add description
	if (mDescription != null) {
	    bundle.putString(MESSAGE, mDescription);
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
	}

	if (mBytes != null) {
	    bundle.putByteArray(PICTURE, mBytes);
	}

	return bundle;
    }

    public static class Builder {
	private String mDescription = null;
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
	 * @param bitmap
	 */
	public Builder setImage(File file) {
	    try {
		mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
	    }
	    catch (FileNotFoundException e) {
		Logger.logError(Photo.class, "Failed to create photo from file", e);
	    }
	    return this;
	}

	/**
	 * Set photo to be published
	 * 
	 * @param bitmap
	 */
	public Builder setImage(byte[] bytes) {
	    mBytes = bytes;
	    return this;
	}

	/**
	 * Add description to the photo
	 * 
	 * @param description
	 *            The description of the photo
	 */
	public Builder setDescription(String description) {
	    mDescription = description;
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
	 * @see com.sromku.simple.fb.Privacy
	 */
	public Builder setPrivacy(Privacy privacy) {
	    mPrivacy = privacy;
	    return this;
	}

	public Photo build() {
	    return new Photo(this);
	}
    }

}
