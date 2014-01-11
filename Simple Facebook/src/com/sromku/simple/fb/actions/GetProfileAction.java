package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphUser;
import com.sromku.simple.fb.Properties;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.listeners.OnProfileRequestListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class GetProfileAction extends AbstractAction {

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
    protected void executeImpl() {
	// if we are logged in
	if (sessionManager.isLogin()) {
	    Session session = sessionManager.getOpenSession();
	    Bundle bundle = null;
	    if (mProperties != null) {
		bundle = mProperties.getBundle();
	    }
	    Request request = new Request(session, "me", bundle, HttpMethod.GET, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    GraphUser graphUser = response.getGraphObjectAs(GraphUser.class);

		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(GetProfileAction.class, "failed to get profile", error.getException());

			// callback with 'exception'
			if (mOnProfileRequestListener != null) {
			    mOnProfileRequestListener.onException(error.getException());
			}
		    } else {
			// callback with 'complete'
			if (mOnProfileRequestListener != null) {
			    Profile profile = Profile.create(graphUser);
			    mOnProfileRequestListener.onComplete(profile);
			}
		    }

		}
	    });

	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();

	    // callback with 'thinking'
	    if (mOnProfileRequestListener != null) {
		mOnProfileRequestListener.onThinking();
	    }
	} else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(GetProfileAction.class, reason, null);

	    // callback with 'fail' due to not being loged
	    if (mOnProfileRequestListener != null) {
		mOnProfileRequestListener.onFail(reason);
	    }
	}
    }

}
