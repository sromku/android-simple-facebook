package com.sromku.simple.fb.entities;

import java.io.File;
import java.io.FileNotFoundException;

import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;

/**
 * <ul>
 * <li>3g2 (Mobile Video)</li>
 * <li>3gp (Mobile Video)</li>
 * <li>3gpp (Mobile Video)</li>
 * <li>asf (Windows Media Video)</li>
 * <li>avi (AVI Video)</li>
 * <li>dat (MPEG Video)</li>
 * <li>divx (DIVX Video)</li>
 * <li>dv (DV Video)</li>
 * <li>f4v (Flash Video)</li>
 * <li>flv (Flash Video)</li>
 * <li>m2ts (M2TS Video)</li>
 * <li>m4v (MPEG-4 Video)</li>
 * <li>mkv (Matroska Format)</li>
 * <li>mod (MOD Video)</li>
 * <li>mov (QuickTime Movie)</li>
 * <li>mp4 (MPEG-4 Video)</li>
 * <li>mpe (MPEG Video)</li>
 * <li>mpeg (MPEG Video)</li>
 * <li>mpeg4 (MPEG-4 Video)</li>
 * <li>mpg (MPEG Video)</li>
 * <li>mts (AVCHD Video)</li>
 * <li>nsv (Nullsoft Video)</li>
 * <li>ogm (Ogg Media Format)</li>
 * <li>ogv (Ogg Video Format)</li>
 * <li>qt (QuickTime Movie)</li>
 * <li>tod (TOD Video)</li>
 * <li>ts (MPEG Transport Stream)</li>
 * <li>vob (DVD Video)</li>
 * <li>wmv (Windows Media Video)</li>
 * </ul>
 * The aspect ratio of the video must be between 9x16 and 16x9, and the video
 * cannot exceed 1024MB or 20 minutes in length. <br>
 * <br>
 * 
 * @author sromku
 * @see https://developers.facebook.com/docs/reference/api/video
 */
public class Video implements Publishable {
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String PRIVACY = "privacy";

    private String mDescription = null;
    private String mTitle = null;
    private String mFileName = null;
    private Privacy mPrivacy = null;

    private Parcelable mParcelable = null;
    private byte[] mBytes = null;

    public Video(Builder builder) {
	mDescription = builder.mDescription;
	mTitle = builder.mTitle;
	mFileName = builder.mFileName;
	mPrivacy = builder.mPrivacy;
	mParcelable = builder.mParcelable;
	mBytes = builder.mBytes;
    }

    @Override
    public String getPath() {
	return GraphPath.VIDEOS;
    }

    @Override
    public Permission getPermission() {
	return Permission.PUBLISH_STREAM;
    }

    public Bundle getBundle() {
	Bundle bundle = new Bundle();

	// add title
	if (mTitle != null) {
	    bundle.putString(TITLE, mTitle);
	}

	// add description
	if (mDescription != null) {
	    bundle.putString(DESCRIPTION, mDescription);
	}

	// add privacy
	if (mPrivacy != null) {
	    bundle.putString(PRIVACY, mPrivacy.getJSONString());
	}

	// add video
	if (mParcelable != null) {
	    bundle.putParcelable(mFileName, mParcelable);
	}
	else if (mBytes != null) {
	    bundle.putByteArray(mFileName, mBytes);
	}

	return bundle;
    }

    public static class Builder {

	private String mDescription = null;
	private String mTitle = null;
	private String mFileName = null;
	private Privacy mPrivacy = null;

	private Parcelable mParcelable = null;
	private byte[] mBytes = null;

	public Builder() {
	}

	/**
	 * Set video to be published.<br>
	 * <br>
	 * The aspect ratio of the video must be between 9x16 and 16x9, and the
	 * video cannot exceed 1024MB or 20 minutes in length.
	 * 
	 * @param file
	 */
	public Builder setVideo(File file) {
	    try {
		mParcelable = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
		mFileName = file.getName();
	    }
	    catch (FileNotFoundException e) {
		Logger.logError(Photo.class, "Failed to create photo from file", e);
	    }
	    return this;
	}

	/**
	 * Set video to be published.<br>
	 * <br>
	 * The aspect ratio of the video must be between 9x16 and 16x9, and the
	 * video cannot exceed 1024MB or 20 minutes in length.
	 * 
	 * @param bytes
	 */
	public Builder setVideo(String fileName, byte[] bytes) {
	    mBytes = bytes;
	    mFileName = fileName;
	    return this;
	}

	/**
	 * Set description of the video.
	 * 
	 * @param description
	 *            The description of the video
	 */
	public Builder setDescription(String description) {
	    mDescription = description;
	    return this;
	}

	/**
	 * Set title of the video.
	 * 
	 * @param title
	 *            The title of the video
	 */
	public Builder setTitle(String title) {
	    mTitle = title;
	    return this;
	}

	/**
	 * Set privacy settings of the video.
	 * 
	 * @param privacy
	 *            The privacy settings of the video
	 * @see com.sromku.simple.fb.entities.Privacy
	 */
	public Builder setPrivacy(Privacy privacy) {
	    mPrivacy = privacy;
	    return this;
	}

	public Video build() {
	    return new Video(this);
	}
    }

}
