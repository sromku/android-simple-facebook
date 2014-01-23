package com.sromku.simple.fb.entities;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import com.sromku.simple.fb.Privacy;
import com.sromku.simple.fb.utils.Logger;

import java.io.File;
import java.io.FileNotFoundException;

public class Video
{
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRIVACY = "privacy";

    private String mDescription = null;
    private String mTitle = null;
    private String mVideoFileName = null;
    private Privacy mPrivacy = null;

    private Parcelable mParcelable = null;
    private byte[] mBytes = null;

    public Video(File file)
    {
        try
        {
            mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            mVideoFileName = file.getName();
        }
        catch (FileNotFoundException e)
        {
            Logger.logError(Video.class, "Failed to create video from file", e);
        }
    }

    /**
     * @param videoFileName A valid video file name.<br/>
     *                      Have to be something with a valid extensions (for example: video.mp4)
     * @param bytes         The byte array of the video file
     */
    public Video(String videoFileName, byte[] bytes)
    {
        mVideoFileName = videoFileName;
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

    /**
     * Add privacy settings to the video
     *
     * @param privacy The privacy settings of the video
     * @see com.sromku.simple.fb.Privacy
     */
    public void addPrivacy(Privacy privacy)
    {
        mPrivacy = privacy;
    }

    public Bundle getBundle()
    {
        Bundle bundle = new Bundle();

        // add title
        if (mTitle != null)
        {
            bundle.putString(TITLE, mTitle);
        }

        // add description
        if (mDescription != null)
        {
            bundle.putString(DESCRIPTION, mDescription);
        }

        // add privacy
        if (mPrivacy != null)
        {
            bundle.putString(PRIVACY, mPrivacy.getJSONString());
        }

        // add video
        if (mParcelable != null)
        {
            bundle.putParcelable(mVideoFileName, mParcelable);
        }

        if (mBytes != null)
        {
            bundle.putByteArray(mVideoFileName, mBytes);
        }

        return bundle;
    }
}
