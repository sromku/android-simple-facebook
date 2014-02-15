package com.sromku.simple.fb.actions;

import org.json.JSONException;

import android.os.Bundle;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public abstract class GetAction<T> extends AbstractAction {

    private String mTarget = "me"; // default
    private OnActionListener<T> mOnActionListener = null;
    
    public GetAction(SessionManager sessionManager) {
	super(sessionManager);
    }
    
    public void setTarget(String target) {
	mTarget = target;
    }
    
    public void setActionListener(OnActionListener<T> actionListener) {
	mOnActionListener = actionListener;
    }

    @Override
    protected void executeImpl() {
	final OnActionListener<T> actionListener = getActionListener();
	if (sessionManager.isLogin(true)) {
	    Session session = sessionManager.getActiveSession();
	    Request request = new Request(session, getGraphPath(), getBundle(), HttpMethod.GET, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(GetAction.class, "Failed to get what you have requested", error.getException());
			if (actionListener != null) {
			    actionListener.onException(error.getException());
			}
		    }
		    else {
			if (response.getGraphObject() == null) {
			    Logger.logError(GetAction.class, "The response GraphObject has null value. Response=" + response.toString(), null);
			}
			else {
			    if (actionListener != null) {
				try {
				    T result = processResponse(response);
				    actionListener.onComplete(result);
				}
				catch (JSONException e) {
				    actionListener.onException(e);
				}
			    }
			}
		    }
		}
	    });
	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();
	    if (actionListener != null) {
		actionListener.onThinking();
	    }
	}
	else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(getClass(), reason, null);
	    if (actionListener != null) {
		actionListener.onFail(reason);
	    }
	}
    }
    
    protected String getTarget() {
	return mTarget;
    }

    protected abstract String getGraphPath();

    protected abstract Bundle getBundle();

    protected OnActionListener<T> getActionListener() {
	return mOnActionListener;
    }

    protected abstract T processResponse(Response response) throws JSONException;

}
