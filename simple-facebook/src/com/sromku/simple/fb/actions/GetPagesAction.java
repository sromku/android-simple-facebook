package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.entities.Page.Properties;
import com.sromku.simple.fb.utils.Utils;

public class GetPagesAction extends GetAction<List<Page>> {

	private Properties mProperties;

	public GetPagesAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setProperties(Properties properties) {
		mProperties = properties;
	}

	@Override
	protected Bundle getBundle() {
		if (mProperties != null) {
			return mProperties.getBundle();
		}
		return null;
	}

	@Override
	protected List<Page> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Page> pages = new ArrayList<Page>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Page page = Page.create(graphObject);
			pages.add(page);
		}
		return pages;
	}

}
