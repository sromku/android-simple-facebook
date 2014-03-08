package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Video;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetVideosAction extends GetAction<List<Video>> {

	public GetVideosAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.VIDEOS;
	}

	@Override
	protected List<Video> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Video> videos = new ArrayList<Video>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Video video = Video.create(graphObject);
			videos.add(video);
		}
		return videos;
	}

}
