package com.sromku.simple.fb.listeners;

import com.facebook.login.LoginResult;

/**
 * On permission listener - If the App must request a specific permission (only
 * to obtain the new Access Token)
 * 
 * @author Gryzor
 * 
 */
public abstract class OnNewPermissionsListener implements OnThinkingListetener {
	
    /**
     * If the permission was granted, this callback is invoked.
     */
	public abstract void onSuccess(LoginResult loginResult);

}