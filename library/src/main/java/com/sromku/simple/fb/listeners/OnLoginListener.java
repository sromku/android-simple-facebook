package com.sromku.simple.fb.listeners;

import com.facebook.login.LoginResult;

/**
 * On login/logout actions listener
 * 
 * @author sromku
 */
public interface OnLoginListener extends OnErrorListener {

    void onLogin(LoginResult loginResult);

    void onCancel();

}