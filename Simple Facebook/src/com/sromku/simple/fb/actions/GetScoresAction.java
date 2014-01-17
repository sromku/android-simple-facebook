package com.sromku.simple.fb.actions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnScoresRequestListener;
import com.sromku.simple.fb.utils.GraphPath;

public class GetScoresAction extends GetAction<JSONArray> {

    private OnScoresRequestListener mOnScoresRequestListener;

    public GetScoresAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setOnScoresRequestListener(OnScoresRequestListener onScoresRequestListener) {
	mOnScoresRequestListener = onScoresRequestListener;
    }

    @Override
    protected String getGraphPath() {
	return configuration.getAppId() + "/" + GraphPath.SCORES;
    }

    @Override
    protected Bundle getBundle() {
	return null;
    }

    @Override
    protected OnActionListener<JSONArray> getActionListener() {
	return mOnScoresRequestListener;
    }

    @Override
    protected JSONArray processResponse(Response response) throws JSONException {
	GraphObject graphObject = response.getGraphObject();
	JSONObject graphResponse = graphObject.getInnerJSONObject();
	JSONArray result = graphResponse.getJSONArray("data");
	return result;
    }

}
