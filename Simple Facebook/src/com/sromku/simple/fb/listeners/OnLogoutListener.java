package com.sromku.simple.fb.listeners;

public interface OnLogoutListener extends OnThinkingListetener {
    /**
     * If user performed {@link FacebookTools#logout()} action, this callback
     * method will be invoked
     */
    void onLogout();
}