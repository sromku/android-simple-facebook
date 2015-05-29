package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Story.StoryObject;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetStoryObjectsAction extends GetAction<List<StoryObject>> {

	private String mObjectName;
	
	public GetStoryObjectsAction(SessionManager sessionManager) {
		super(sessionManager);
	}
	
	public void setObjectName(String objectName) {
		mObjectName = objectName;
	}

	@Override
	protected String getGraphPath() {
		String namespace = configuration.getNamespace();
		return getTarget() + "/" + GraphPath.OBJECTS + "/" + namespace  + ":" + mObjectName;
	}

	@Override
	protected List<StoryObject> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<StoryObject> storyObjects = new ArrayList<StoryObject>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			StoryObject storyObject = StoryObject.create(graphObject);
			storyObjects.add(storyObject);
		}
		return storyObjects;
	}

}
