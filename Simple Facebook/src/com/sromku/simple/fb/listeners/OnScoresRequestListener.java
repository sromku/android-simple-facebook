package com.sromku.simple.fb.listeners;

import org.json.JSONArray;

/**
 * On get scores requests listener
 * 
 * @author koraybalci
 * 
 */
public interface OnScoresRequestListener extends OnActionListener {
    void onComplete(JSONArray result);
}
