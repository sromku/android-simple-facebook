package com.sromku.simple.fb.listeners;

import android.app.Activity;

/**
 * On login/logout actions listener
 * 
 * @author sromku
 */
public interface OnLoginListener extends OnActionListener {
    /**
     * If user performed {@link FacebookTools#login(Activity)} action, this
     * callback method will be invoked
     */
    void onLogin();

    /**
     * If user pressed 'cancel' in READ (First) permissions dialog
     */
    void onNotAcceptingPermissions();
}