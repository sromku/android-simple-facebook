package com.sromku.simple.fb.actions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnAppRequestsListener;
import com.sromku.simple.fb.utils.GraphPath;

public class GetAppRequestsAction extends GetAction<JSONArray> {

    private OnAppRequestsListener mOnAppRequestsListener;

    public GetAppRequestsAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setOnAppRequestsListener(OnAppRequestsListener onAppRequestsListener) {
	mOnAppRequestsListener = onAppRequestsListener;
    }

    @Override
    protected String getGraphPath() {
	return "me/" + GraphPath.APPREQUESTS;
    }

    @Override
    protected Bundle getBundle() {
	return null;
    }

    @Override
    protected OnActionListener<JSONArray> getActionListener() {
	return mOnAppRequestsListener;
    }

    @Override
    protected JSONArray processResponse(Response response) throws JSONException {
	GraphObject graphObject = response.getGraphObject();
	JSONObject graphResponse = graphObject.getInnerJSONObject();
	JSONArray result = graphResponse.getJSONArray("data");
	return result;
    }

}
