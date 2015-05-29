package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Like;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetLikesAction extends GetAction<List<Like>> {

	public GetLikesAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.LIKES;
	}

	@Override
	protected List<Like> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Like> likes = new ArrayList<Like>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Like like = Like.create(graphObject);
			likes.add(like);
		}
		return likes;
	}
}
