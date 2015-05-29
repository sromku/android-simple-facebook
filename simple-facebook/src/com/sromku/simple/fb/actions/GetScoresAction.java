package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Score;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetScoresAction extends GetAction<List<Score>> {

	public GetScoresAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", configuration.getAppId(), GraphPath.SCORES);
	}

	@Override
	protected Bundle getBundle() {
		return null;
	}

	@Override
	protected List<Score> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Score> scores = new ArrayList<Score>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Score score = Score.create(graphObject);
			scores.add(score);
		}
		return scores;
	}

}
