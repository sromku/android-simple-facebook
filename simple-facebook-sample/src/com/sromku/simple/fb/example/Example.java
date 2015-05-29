package com.sromku.simple.fb.example;

import android.support.v4.app.Fragment;

public class Example {

	private final String mTitle;
	private final Class<? extends Fragment> mFragment;
	private final boolean mRequiresLogin;
	
	public Example(String title, Class<? extends Fragment> fragment, boolean requiresLogin) {
		mTitle = title;
		mFragment = fragment;
		mRequiresLogin = requiresLogin;
	}

	public String getTitle() {
		return mTitle;
	}

	public Class<? extends Fragment> getFragment() {
		return mFragment;
	}
	
	public boolean isRequireLogin() {
		return mRequiresLogin;
	}

}
