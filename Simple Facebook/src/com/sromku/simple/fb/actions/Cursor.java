package com.sromku.simple.fb.actions;

import com.facebook.Request;

public class Cursor<T> {

	private final GetAction<T> mGetAction;
	private Request mNextPage = null;
	private Request mPrevPage = null;
	
	public Cursor(GetAction<T> getAction) {
		mGetAction = getAction;
	}
	
	public boolean hasNext() {
		return mNextPage != null ? true : false;
	}
	
	public boolean hasPrev() {
		return mPrevPage != null ? true : false;
	}
	
	public void next() {
		mGetAction.runRequest(mNextPage);
	}
	
	public void prev() {
		mGetAction.runRequest(mPrevPage);
	}

	void setNextPage(Request requestNextPage) {
		mNextPage = requestNextPage;
	}

	void setPrevPage(Request requestPrevPage) {
		mPrevPage = requestPrevPage;
	}
}
