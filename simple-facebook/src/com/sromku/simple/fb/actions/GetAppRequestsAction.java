package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.AppRequest;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetAppRequestsAction extends GetAction<List<AppRequest>> {

	public GetAppRequestsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.APPREQUESTS);
	}

	@Override
	protected List<AppRequest> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<AppRequest> appRequests = new ArrayList<AppRequest>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			AppRequest graphRequest = AppRequest.create(graphObject);
			appRequests.add(graphRequest);
		}
		return appRequests;
	}

}
