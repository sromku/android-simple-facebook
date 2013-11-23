package com.sromku.simple.fb.entities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.sromku.simple.fb.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Video
{
	private static final String THUMBNAIL = "picture"; //TODO
	private static final String NAME = "name";
    private static final String DESCRIPTION = "description";

    private String mDescription = null;
    private String mTitle = null;
    private Bitmap mThumbnail = null; //TODO

	private Parcelable mParcelable = null;
	private byte[] mBytes = null;

	public Video(File file)
	{
		try
		{
			mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
		}
		catch (FileNotFoundException e)
		{
			Logger.logError(Video.class, "Failed to create video from file", e);
		}
	}

	public Video(byte[] bytes)
	{
		mBytes = bytes;
	}

	/**
	 * Add description to the video
	 * 
	 * @param description The description of the video
	 */
	public void addDescription(String description)
	{
		mDescription = description;
	}

    /**
     * Add title to the video
     *
     * @param title The title of the video
     */
    public void addTitle(String title)
    {
        mTitle = title;
    }

	public Bundle getBundle()
	{
		Bundle bundle = new Bundle();

        // add title
        if (mTitle != null)
        {
            bundle.putString(NAME, mTitle);
        }

		// add description
		if (mDescription != null)
		{
			bundle.putString(DESCRIPTION, mDescription);
		}

		// add thumbnail //TODO

		return bundle;
	}

}
