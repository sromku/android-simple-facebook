package com.sromku.simple.fb.actions;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.Response;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.listeners.OnProfileRequestListener;

public class GetProfileAction extends GetAction<Profile> {

    private OnProfileRequestListener mOnProfileRequestListener;
    private Properties mProperties;

    public GetProfileAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setProperties(Properties properties) {
	mProperties = properties;
    }

    public void setOnProfileRequestListener(OnProfileRequestListener onProfileRequestListener) {
	mOnProfileRequestListener = onProfileRequestListener;
    }

    @Override
    protected String getGraphPath() {
	return String.format("%s", "me");
    }

    @Override
    protected Bundle getBundle() {
	if (mProperties != null) {
	    return mProperties.getBundle();
	}
	return null;
    }

    @Override
    protected OnActionListener<Profile> getActionListener() {
	return mOnProfileRequestListener;
    }

    @Override
    protected Profile processResponse(Response response) throws JSONException {
	GraphUser graphUser = response.getGraphObjectAs(GraphUser.class);
	Profile profile = Profile.create(graphUser);
	return profile;
    }

}
