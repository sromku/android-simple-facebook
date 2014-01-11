package com.sromku.simple.fb.listeners;

import org.json.JSONArray;

/**
 * On get app requests listener
 * 
 * @author koraybalci
 * 
 */
public interface OnAppRequestsListener extends OnActionListener {
    void onComplete(JSONArray result);
}
