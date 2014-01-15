package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

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
import com.sromku.simple.fb.listeners.OnFriendsRequestListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

public class GetFriendsAction extends AbstractAction {

    private OnFriendsRequestListener mOnFriendsRequestListener;
    private Properties mProperties;

    public GetFriendsAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setProperties(Properties properties) {
	mProperties = properties;
    }

    public void setOnFriendsRequestListener(OnFriendsRequestListener onFriendsRequestListener) {
	mOnFriendsRequestListener = onFriendsRequestListener;
    }

    @Override
    protected void executeImpl() {
	if (sessionManager.isLogin(true)) {
	    Session session = sessionManager.getActiveSession();
	    Bundle bundle = null;
	    if (mProperties != null) {
		bundle = mProperties.getBundle();
	    }
	    Request request = new Request(session, "me/" + GraphPath.FRIENDS, bundle, HttpMethod.GET, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    List<GraphUser> graphUsers = Utils.typedListFromResponse(response, GraphUser.class);
		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(GetFriendsAction.class, "failed to get friends", error.getException());
			if (mOnFriendsRequestListener != null) {
			    mOnFriendsRequestListener.onException(error.getException());
			}
		    } else {
			if (mOnFriendsRequestListener != null) {
			    List<Profile> friends = new ArrayList<Profile>(graphUsers.size());
			    for (GraphUser graphUser : graphUsers) {
				friends.add(Profile.create(graphUser));
			    }
			    mOnFriendsRequestListener.onComplete(friends);
			}
		    }

		}
	    });
	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();
	    if (mOnFriendsRequestListener != null) {
		mOnFriendsRequestListener.onThinking();
	    }
	} else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(GetFriendsAction.class, reason, null);
	    if (mOnFriendsRequestListener != null) {
		mOnFriendsRequestListener.onFail(reason);
	    }
	}
    }

}
