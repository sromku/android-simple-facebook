package com.sromku.simple.fb.listeners;

/**
 * On authorization request listener
 */
public interface OnAuthorizationDeviceListener extends OnThinkingListetener {

    void onComplete(String accessToken);
}
