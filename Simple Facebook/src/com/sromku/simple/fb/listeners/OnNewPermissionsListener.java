package com.sromku.simple.fb.listeners;

import java.util.List;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.Permission.Type;

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
	public abstract void onSuccess(String accessToken, List<Permission> declinedPermissions);

    /**
     * If user pressed 'cancel' in PUBLISH permissions dialog
     * @param type 
     */
    public abstract void onNotAcceptingPermissions(Type type);
}