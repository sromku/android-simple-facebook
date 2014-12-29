package com.sromku.simple.fb.actions;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetPhotoAction extends GetAction<Photo> {

	public GetPhotoAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget();
	}

	@Override
	protected Photo processResponse(Response response) {
		GraphObject graphObject = response.getGraphObjectAs(GraphObject.class);
		Photo photo = Photo.create(graphObject);
		return photo;
	}

}
