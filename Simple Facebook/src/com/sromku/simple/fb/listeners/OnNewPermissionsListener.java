package com.sromku.simple.fb.listeners;

import com.sromku.simple.fb.Permission.Type;

/**
 * On permission listener - If the App must request a specific permission (only
 * to obtain the new Access Token)
 * 
 * @author Gryzor
 * 
 */
public interface OnNewPermissionsListener extends OnThinkingListetener {
    /**
     * If the permission was granted, this callback is invoked.
     */
    void onSuccess(String accessToken);

    /**
     * If user pressed 'cancel' in PUBLISH permissions dialog
     * @param type 
     */
    void onNotAcceptingPermissions(Type type);
}