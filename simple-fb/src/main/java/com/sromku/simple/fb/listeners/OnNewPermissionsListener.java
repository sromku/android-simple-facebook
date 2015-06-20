package com.sromku.simple.fb.listeners;

import com.sromku.simple.fb.Permission;

import java.util.List;

/**
 * On permission listener - If the App must request a specific permission (only
 * to obtain the new Access Token)
 *
 * @author Gryzor
 *
 */
public abstract class OnNewPermissionsListener implements OnErrorListener {

    /**
     * If the permission was granted, this callback is invoked.
     */
    public abstract void onSuccess(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions);

    public abstract void onCancel();
}