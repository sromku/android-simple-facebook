package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetFriendsAction extends GetAction<List<Profile>> {

	private Properties mProperties;

	public GetFriendsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setProperties(Properties properties) {
		mProperties = properties;
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.FRIENDS);
	}

	@Override
	protected Bundle getBundle() {
		if (mProperties != null) {
			return mProperties.getBundle();
		}
		return null;
	}

	@Override
	protected List<Profile> processResponse(Response response) {
		List<GraphUser> graphUsers = Utils.typedListFromResponse(response, GraphUser.class);
		List<Profile> profiles = new ArrayList<Profile>(graphUsers.size());
		for (GraphUser graphUser : graphUsers) {
			profiles.add(Profile.create(graphUser));
		}
		return profiles;
	}

}
