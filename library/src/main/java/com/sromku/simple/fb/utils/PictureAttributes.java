package com.sromku.simple.fb.utils;

public class PictureAttributes extends Attributes {
	private final static String HEIGHT = "height";
	private final static String WIDTH = "width";
	private final static String TYPE = "type";

	PictureAttributes() {
	}

	public void setHeight(int pixels) {
		attributes.put(HEIGHT, String.valueOf(pixels));
	}

	public void setWidth(int pixels) {
		attributes.put(WIDTH, String.valueOf(pixels));
	}

	public void setType(PictureType type) {
		attributes.put(TYPE, type.getValue());
	}

	public static enum PictureType {
		SMALL("small"),
		NORMAL("normal"),
		LARGE("large"),
		SQUARE("square");

		private String mValue;

		private PictureType(String value) {
			mValue = value;
		}

		public String getValue() {
			return mValue;
		}
	}
}
