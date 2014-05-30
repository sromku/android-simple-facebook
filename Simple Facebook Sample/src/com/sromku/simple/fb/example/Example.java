package com.sromku.simple.fb.example;

import android.support.v4.app.Fragment;

public class Example {

	private final String mTitle;
	private final Class<? extends Fragment> mFragment;
	
	public Example(String title, Class<? extends Fragment> fragment) {
		mTitle = title;
		mFragment = fragment;
	}

	public String getTitle() {
		return mTitle;
	}

	public Class<? extends Fragment> getFragment() {
		return mFragment;
	}

}
