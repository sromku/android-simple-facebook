package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Checkin;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetCheckinsAction extends GetAction<List<Checkin>> {

	public GetCheckinsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.CHECKINS);
	}

	@Override
	protected List<Checkin> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Checkin> checkins = new ArrayList<Checkin>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Checkin checkin = Checkin.create(graphObject);
			checkins.add(checkin);
		}
		return checkins;
	}

}
