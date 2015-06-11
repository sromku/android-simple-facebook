package com.sromku.simple.fb.entities;

import java.util.Locale;

public class Location extends IdName {

	@Override
	public String toString() {
		return String.format(Locale.US, "id=%s,name=%s", mId, mName);
	}
}
