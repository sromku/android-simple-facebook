package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class PublishFeedDialogAction extends AbstractAction {

    private OnPublishListener mOnPublishListener;
    private Feed mFeed;

    public PublishFeedDialogAction(SessionManager sessionManager) {
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
	if (sessionManager.isLogin()) {
	    WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(sessionManager.getActivity(), Session.getActiveSession(), mFeed.getBundle())).setOnCompleteListener(new OnCompleteListener() {
		@Override
		public void onComplete(Bundle values, FacebookException error) {
		    if (error == null) {
			// When the story is posted, echo the
			// success
			// and the post Id.
			final String postId = values.getString("post_id");
			if (postId != null) {
			    mOnPublishListener.onComplete(postId);
			} else {
			    mOnPublishListener.onFail("Canceled by user");
			}
		    } else if (error instanceof FacebookOperationCanceledException) {
			// User clicked the "x" button
			mOnPublishListener.onFail("Canceled by user");
		    } else {
			mOnPublishListener.onException(error);
		    }
		}

	    }).build();

	    // show the dialog
	    feedDialog.show();
	} else {
	    // callback with 'fail' due to not being loged
	    if (mOnPublishListener != null) {
		String reason = Errors.getError(ErrorMsg.LOGIN);
		Logger.logError(PublishFeedDialogAction.class, reason, null);

		mOnPublishListener.onFail(reason);
	    }
	}
    }

}
