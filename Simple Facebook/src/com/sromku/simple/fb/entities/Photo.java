package com.sromku.simple.fb.entities;

import java.io.File;
import java.io.FileNotFoundException;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.sromku.simple.fb.Privacy;
import com.sromku.simple.fb.utils.Logger;

public class Photo
{
	private static final String PICTURE = "picture";
	private static final String PLACE = "place";
	private static final String MESSAGE = "message";
    private static final String PRIVACY = "privacy";

	private String mDescription = null;
	private String mPlaceId = null;

	private Parcelable mParcelable = null;
	private byte[] mBytes = null;
    private Privacy mPrivacy = null;

	public Photo(Bitmap bitmap)
	{
		mParcelable = bitmap;
	}

	public Photo(File file)
	{
		try
		{
			mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
		}
		catch (FileNotFoundException e)
		{
			Logger.logError(Photo.class, "Failed to create photo from file", e);
		}
	}

	public Photo(byte[] bytes)
	{
		mBytes = bytes;
	}

	/**
	 * Add description to the photo
	 * 
	 * @param description The description of the photo
	 */
	public void addDescription(String description)
	{
		mDescription = description;
	}

	/**
	 * Add place id of the photo
	 * 
	 * @param placeId The place id of the photo
	 */
	public void addPlace(String placeId)
	{
		mPlaceId = placeId;
	}

    /**
     * Add privacy setting to the photo
     *
     * @param privacy The privacy setting of the photo
     * @see com.sromku.simple.fb.Privacy
     */
    public void addPrivacy(Privacy privacy)
    {
        mPrivacy = privacy;
    }

	public Bundle getBundle()
	{
		Bundle bundle = new Bundle();

		// add description
		if (mDescription != null)
		{
			bundle.putString(MESSAGE, mDescription);
		}

		// add place
		if (mPlaceId != null)
		{
			bundle.putString(PLACE, mPlaceId);
		}

        // add privacy
        if (mPrivacy != null) {
            bundle.putString(PRIVACY, mPrivacy.getJSONString());
        }

		// add image
		if (mParcelable != null)
		{
			bundle.putParcelable(PICTURE, mParcelable);
		}

		if (mBytes != null)
		{
			bundle.putByteArray(PICTURE, mBytes);
		}

		return bundle;
	}

}
