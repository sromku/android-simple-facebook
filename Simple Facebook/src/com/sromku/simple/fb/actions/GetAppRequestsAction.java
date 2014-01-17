package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.AppRequest;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnAppRequestsListener;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetAppRequestsAction extends GetAction<List<AppRequest>> {

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
	Bundle bundle = new Bundle();
	bundle.putString("date_format", "U");
	return bundle;
    }

    @Override
    protected OnActionListener<List<AppRequest>> getActionListener() {
	return mOnAppRequestsListener;
    }

    @Override
    protected List<AppRequest> processResponse(Response response) throws JSONException {
	List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
	List<AppRequest> appRequests = new ArrayList<AppRequest>(graphObjects.size());
	for (GraphObject graphObject : graphObjects) {
	    AppRequest graphRequest = AppRequest.create(graphObject);
	    appRequests.add(graphRequest);
	}
	return appRequests;
    }

}
