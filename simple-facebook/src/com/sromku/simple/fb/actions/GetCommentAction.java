package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Comment;
import com.sromku.simple.fb.entities.Page.Properties;

public class GetCommentAction extends GetAction<Comment> {

	private Properties mProperties;

	public GetCommentAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setProperties(Properties properties) {
		mProperties = properties;
	}

	@Override
	protected String getGraphPath() {
		return getTarget();
	}

	@Override
	protected Bundle getBundle() {
		if (mProperties != null) {
			return mProperties.getBundle();
		}
		return null;
	}

	@Override
	protected Comment processResponse(Response response) {
		GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
		Comment comment = Comment.create(graphObject);
		return comment;
	}

}
