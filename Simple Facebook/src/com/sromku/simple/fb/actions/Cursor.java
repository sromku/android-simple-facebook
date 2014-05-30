package com.sromku.simple.fb.actions;

import com.facebook.Request;

public class Cursor<T> {

	private final GetAction<T> mGetAction;
	private Request mNextPage = null;
	private Request mPrevPage = null;
	private int mPageNum = 0;
	
	public Cursor(GetAction<T> getAction) {
		mGetAction = getAction;
	}
	
	public boolean hasNext() {
		return mNextPage != null ? true : false;
	}
	
	public boolean hasPrev() {
		return mPrevPage != null ? true : false;
	}
	
	public int getPageNum() {
		return mPageNum;
	}
	
	public void next() {
		mPageNum++;
		mGetAction.runRequest(mNextPage);
	}
	
	public void prev() {
		mPageNum--;
		mGetAction.runRequest(mPrevPage);
	}

	void setNextPage(Request requestNextPage) {
		mNextPage = requestNextPage;
	}

	void setPrevPage(Request requestPrevPage) {
		mPrevPage = requestPrevPage;
	}
}
