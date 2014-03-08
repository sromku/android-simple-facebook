package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Group;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetGroupsAction extends GetAction<List<Group>> {

	public GetGroupsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.GROUPS;
	}

	@Override
	protected List<Group> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Group> groups = new ArrayList<Group>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Group group = Group.create(graphObject);
			groups.add(group);
		}
		return groups;
	}

}
