package com.sromku.simple.fb.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permissions;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.listeners.OnReopenSessionListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class PublishFeedAction extends AbstractAction {

    private OnPublishListener mOnPublishListener;
    private Feed mFeed;

    public PublishFeedAction(SessionManager sessionManager) {
	super(sessionManager);
    }

    public void setFeed(Feed feed) {
	mFeed = feed;
    }

    public void setOnPublishListener(OnPublishListener onPublishListener) {
	mOnPublishListener = onPublishListener;
    }

    @Override
    protected void executeImpl() {
	// if we are logged in
	if (sessionManager.isLogin()) {
	    // if we defined the publish permission
	    if (configuration.getPublishPermissions().contains(Permissions.PUBLISH_ACTION.getValue())) {
		// callback with 'thinking'
		if (mOnPublishListener != null) {
		    mOnPublishListener.onThinking();
		}

		/*
		 * Check if session to facebook has 'publish_action' permission.
		 * If not, we will ask user for this permission.
		 */
		if (!sessionManager.getOpenSessionPermissions().contains(Permissions.PUBLISH_ACTION.getValue())) {
		    sessionManager.getSessionStatusCallback().setOnReopenSessionListener(new OnReopenSessionListener() {
			@Override
			public void onSuccess() {
			    publishImpl(mFeed, mOnPublishListener);
			}

			@Override
			public void onNotAcceptingPermissions() {
			    // this fail can happen when user doesn't accept the
			    // publish permissions
			    String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(configuration.getPublishPermissions()));
			    Logger.logError(PublishFeedAction.class, reason, null);
			    if (mOnPublishListener != null) {
				mOnPublishListener.onFail(reason);
			    }
			}
		    });

		    // extend publish permissions automatically
		    sessionManager.extendPublishPermissions();
		} else {
		    publishImpl(mFeed, mOnPublishListener);
		}
	    } else {
		String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH, Permissions.PUBLISH_ACTION.getValue());
		Logger.logError(PublishFeedAction.class, reason, null);

		// callback with 'fail' due to insufficient permissions
		if (mOnPublishListener != null) {
		    mOnPublishListener.onFail(reason);
		}
	    }
	} else {
	    // callback with 'fail' due to not being loged
	    if (mOnPublishListener != null) {
		String reason = Errors.getError(ErrorMsg.LOGIN);
		Logger.logError(PublishFeedAction.class, reason, null);

		mOnPublishListener.onFail(reason);
	    }
	}
    }

    private void publishImpl(Feed feed, final OnPublishListener onPublishListener) {
	Session session = sessionManager.getOpenSession();
	Request request = new Request(session, "me/feed", feed.getBundle(), HttpMethod.POST, new Request.Callback() {
	    @Override
	    public void onCompleted(Response response) {
		GraphObject graphObject = response.getGraphObject();
		if (graphObject != null) {
		    JSONObject graphResponse = graphObject.getInnerJSONObject();
		    String postId = null;
		    try {
			postId = graphResponse.getString("id");
		    } catch (JSONException e) {
			Logger.logError(PublishFeedAction.class, "JSON error", e);
		    }

		    FacebookRequestError error = response.getError();
		    if (error != null) {
			Logger.logError(PublishFeedAction.class, "Failed to publish", error.getException());

			// callback with 'exception'
			if (onPublishListener != null) {
			    onPublishListener.onException(error.getException());
			}
		    } else {
			// callback with 'complete'
			if (onPublishListener != null) {
			    onPublishListener.onComplete(postId);
			}
		    }
		} else {
		    Logger.logError(PublishFeedAction.class, "The GraphObject in Response of publish action has null value. Response=" + response.toString(), null);

		    if (onPublishListener != null) {
			onPublishListener.onComplete("0");
		    }
		}
	    }
	});

	RequestAsyncTask task = new RequestAsyncTask(request);
	task.execute();
    }

}
