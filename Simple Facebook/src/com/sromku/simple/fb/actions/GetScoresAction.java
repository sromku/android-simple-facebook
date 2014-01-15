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
import com.sromku.simple.fb.listeners.OnScoresRequestListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Logger;

public class GetScoresAction extends AbstractAction {

    private OnScoresRequestListener mOnScoresRequestListener;

    public GetScoresAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setOnScoresRequestListener(OnScoresRequestListener onScoresRequestListener) {
	mOnScoresRequestListener = onScoresRequestListener;
    }

    @Override
    protected void executeImpl() {
	if (sessionManager.isLogin(true)) {
	    Session session = sessionManager.getActiveSession();
	    Bundle bundle = null;
	    Request request = new Request(session, configuration.getAppId() + "/" + GraphPath.SCORES, bundle, HttpMethod.GET, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(GetScoresAction.class, "failed to get scores", error.getException());
			if (mOnScoresRequestListener != null) {
			    mOnScoresRequestListener.onException(error.getException());
			}
		    } else {
			GraphObject graphObject = response.getGraphObject();
			if (graphObject != null) {
			    JSONObject graphResponse = graphObject.getInnerJSONObject();
			    try {
				JSONArray result = graphResponse.getJSONArray("data");
				if (mOnScoresRequestListener != null) {
				    mOnScoresRequestListener.onComplete(result);
				}
			    } catch (JSONException e) {
				if (mOnScoresRequestListener != null) {
				    mOnScoresRequestListener.onException(e);
				}
			    }
			} else {
			    Logger.logError(GetScoresAction.class, "The GraphObject in Response of getScores has null value. Response=" + response.toString(), null);
			}
		    }
		}
	    });
	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();
	    if (mOnScoresRequestListener != null) {
		mOnScoresRequestListener.onThinking();
	    }
	} else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(GetScoresAction.class, reason, null);
	    if (mOnScoresRequestListener != null) {
		mOnScoresRequestListener.onFail(reason);
	    }
	}
    }

}
