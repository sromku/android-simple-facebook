package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Score;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

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
	protected List<Score> processResponse(GraphResponse response) {
		List<Score> scores = Utils.typedListFromResponse(response);
		return scores;
	}

}
