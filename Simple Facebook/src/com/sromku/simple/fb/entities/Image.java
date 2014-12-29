package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class Image {

	private static final String HEIGHT = "height";
	private static final String NULL = "null";
	private static final String SOURCE = "source";
	private static final String SRC = "src";
	private static final String WIDTH = "width";

	private Integer mHeight;
	private String mSource;
	private Integer mWidth;

	private Image(GraphObject graphObject) {
		mHeight = Utils.getPropertyInteger(graphObject, HEIGHT);
		mSource = Utils.getPropertyString(graphObject, SOURCE);
		if (mSource == null || NULL.equalsIgnoreCase(mSource)) {
			mSource = Utils.getPropertyString(graphObject, SRC);
		}
		mWidth = Utils.getPropertyInteger(graphObject, WIDTH);
	}

	public static Image create(GraphObject graphObject) {
		return new Image(graphObject);
	}

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
