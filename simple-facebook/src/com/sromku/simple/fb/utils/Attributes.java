package com.sromku.simple.fb.utils;

import java.util.HashMap;
import java.util.Map;

public abstract class Attributes {
	protected Map<String, String> attributes = new HashMap<String, String>();

	public Map<String, String> getAttributes() {
		return attributes;
	}

	/**
	 * Create picture attributes
	 * 
	 * @return {@link PictureAttributes}
	 */
	public static PictureAttributes createPictureAttributes() {
		return new PictureAttributes();
	}
}
