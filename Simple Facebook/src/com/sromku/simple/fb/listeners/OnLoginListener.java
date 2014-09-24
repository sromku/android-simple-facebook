package com.sromku.simple.fb.listeners;

import android.app.Activity;

import com.sromku.simple.fb.Permission;

/**
 * On login/logout actions listener
 * 
 * @author sromku
 */
public interface OnLoginListener extends OnThinkingListetener {
	/**
	 * If user performed {@link FacebookTools#login(Activity)} action, this
	 * callback method will be invoked
	 */
	void onLogin();

	/**
	 * If user pressed 'cancel' in one of the permissions dialog (READ or
	 * PUBLISH)
	 */
	void onNotAcceptingPermissions(Permission.Type type);
}