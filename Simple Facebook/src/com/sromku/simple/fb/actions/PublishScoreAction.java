package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Score;
import com.sromku.simple.fb.listeners.OnPostScoreListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class PublishScoreAction extends AbstractAction {

    private OnPostScoreListener mOnPostScoreListener;
    private Score mScore;

    public PublishScoreAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setScore(Score score) {
	mScore = score;
    }

    public void setOnPostScoreListener(OnPostScoreListener onPostScoreListener) {
	mOnPostScoreListener = onPostScoreListener;
    }

    @Override
    protected void executeImpl() {
	if (sessionManager.isLogin()) {
	    // if we defined the publish permission
	    if (configuration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue())) {
		// callback with 'thinking'
		if (mOnPostScoreListener != null) {
		    mOnPostScoreListener.onThinking();
		}

		/*
		 * Check if session to facebook has 'publish_action' permission.
		 * If not, we will return fail, client app may try to ask for
		 * permission later (not to annoy users).
		 */
		if (!sessionManager.getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue())) {
		    String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(configuration.getPublishPermissions()));
		    Logger.logError(PublishScoreAction.class, reason, null);

		    // callback with 'fail' due to not being logged in
		    if (mOnPostScoreListener != null) {
			mOnPostScoreListener.onFail(reason);
		    }
		    return;
		}
	    } else {
		String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH);
		Logger.logError(PublishScoreAction.class, reason, null);

		// callback with 'fail' due to not being logged in
		if (mOnPostScoreListener != null) {
		    mOnPostScoreListener.onFail(reason);
		}
		return;
	    }

	    Bundle param = new Bundle();
	    param.putInt("score", mScore.getScore());
	    Request request = new Request(sessionManager.getOpenSession(), "me/scores", param, HttpMethod.POST, new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
		    FacebookRequestError error = response.getError();
		    if (error != null) {
			// log
			Logger.logError(PublishScoreAction.class, "Failed to publish score", error.getException());

			// callback with 'exception'
			if (mOnPostScoreListener != null) {
			    mOnPostScoreListener.onException(error.getException());
			}
		    } else {
			// callback with 'complete'
			if (mOnPostScoreListener != null) {
			    mOnPostScoreListener.onComplete();
			}
		    }
		}
	    });

	    RequestAsyncTask task = new RequestAsyncTask(request);
	    task.execute();
	} else {
	    String reason = Errors.getError(ErrorMsg.LOGIN);
	    Logger.logError(PublishScoreAction.class, reason, null);

	    // callback with 'fail' due to not being logged in
	    if (mOnPostScoreListener != null) {
		mOnPostScoreListener.onFail(reason);
	    }
	}
    }

}
