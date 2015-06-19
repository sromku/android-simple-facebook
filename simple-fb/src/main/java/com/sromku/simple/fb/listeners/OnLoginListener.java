package com.sromku.simple.fb.listeners;

import com.sromku.simple.fb.Permission;

import java.util.List;

/**
 * On login/logout actions listener
 *
 * @author sromku
 */
public interface OnLoginListener extends OnErrorListener {

    void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions);

    void onCancel();

}