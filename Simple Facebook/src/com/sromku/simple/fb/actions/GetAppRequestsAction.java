package com.sromku.simple.fb.actions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnAppRequestsListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class GetAppRequestsAction extends AbstractAction {

    private OnAppRequestsListener mOnAppRequestsListener;

    public GetAppRequestsAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setOnAppRequestsListener(OnAppRequestsListener onAppRequestsListener) {
	mOnAppRequestsListener = onAppRequestsListener;
    }

    @Override
    protected void executeImpl() {
	if (sessionManager.isLogin(true)) {
	    Session session = sessionManager.getActiveSession();
	    Bundle bundle = null;
	    Request request = new Request(session, "me/apprequests", bundle, HttpMethod.GET, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(GetAppRequestsAction.class, "failed to get app requests", error.getException());
			if (mOnAppRequestsListener != null) {
			    mOnAppRequestsListener.onException(error.getException());
			}
		    } else {
			GraphObject graphObject = response.getGraphObject();
			if (graphObject != null) {
			    JSONObject graphResponse = graphObject.getInnerJSONObject();
			    try {
				JSONArray result = graphResponse.getJSONArray("data");
				if (mOnAppRequestsListener != null) {
				    mOnAppRequestsListener.onComplete(result);
				}
			    } catch (JSONException e) {
				if (mOnAppRequestsListener != null) {
				    mOnAppRequestsListener.onException(e);
				}
			    }
			} else {
			    Logger.logError(GetAppRequestsAction.class, "The GraphObject in Response of getAppRequests has null value. Response=" + response.toString(), null);
			}
		    }
		}
	    });
	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();
	    if (mOnAppRequestsListener != null) {
		mOnAppRequestsListener.onThinking();
	    }
	} else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(GetAppRequestsAction.class, reason, null);
	    if (mOnAppRequestsListener != null) {
		mOnAppRequestsListener.onFail(reason);
	    }
	}
    }

}
