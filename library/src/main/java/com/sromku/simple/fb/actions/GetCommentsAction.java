package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Comment;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetCommentsAction extends GetAction<List<Comment>> {

	public GetCommentsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.COMMENTS;
	}

	@Override
	protected List<Comment> processResponse(GraphResponse response) {
		List<Comment> comments = Utils.typedListFromResponse(response);
		return comments;
	}

}
