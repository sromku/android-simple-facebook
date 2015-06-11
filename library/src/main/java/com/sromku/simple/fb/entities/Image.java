package com.sromku.simple.fb.entities;

import com.google.gson.annotations.SerializedName;

public class Image {

	private static final String HEIGHT = "height";
	private static final String NULL = "null";
	private static final String SOURCE = "source";
	private static final String SRC = "src";
	private static final String WIDTH = "width";

    @SerializedName(HEIGHT)
	private Integer mHeight;

    @SerializedName(SOURCE)
	private String mSource;

    @SerializedName(WIDTH)
	private Integer mWidth;

	public Integer getHeight() {
		return mHeight;
	}

	public String getSource() {
		return mSource;
	}

	public Integer getWidth() {
		return mWidth;
	}
}
