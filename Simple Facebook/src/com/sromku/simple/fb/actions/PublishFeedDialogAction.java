package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Session;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.FacebookDialog.ShareDialogFeature;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.entities.Feed.Builder.Parameters;
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
		if (sessionManager.isLogin(true)) {
			if (FacebookDialog.canPresentShareDialog(sessionManager.getActivity(), ShareDialogFeature.SHARE_DIALOG)) {
				FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(sessionManager.getActivity())
						.setCaption(mFeed.getBundle().getString(Parameters.CAPTION))
						.setDescription(mFeed.getBundle().getString(Parameters.DESCRIPTION))
						.setName(mFeed.getBundle().getString(Parameters.NAME))
						.setPicture(mFeed.getBundle().getString(Parameters.PICTURE))
						.setLink(mFeed.getBundle().getString(Parameters.LINK))
						.build();
				PendingCall pendingCall = shareDialog.present();
				sessionManager.trackFacebookDialogPendingCall(pendingCall, new FacebookDialog.Callback() {

					@Override
					public void onError(PendingCall pendingCall, Exception error, Bundle data) {
						sessionManager.untrackPendingCall();
						Logger.logError(PublishFeedDialogAction.class, "Failed to share by using native dialog", error);
						if ("".equals(error.getMessage())) {
							Logger.logError(PublishFeedDialogAction.class, "Make sure to have 'app_id' meta data value in your manifest", error);
						}
						shareWithWebDialog();
					}

					@Override
					public void onComplete(PendingCall pendingCall, Bundle data) {
						sessionManager.untrackPendingCall();
						boolean didComplete = FacebookDialog.getNativeDialogDidComplete(data);
						String postId = FacebookDialog.getNativeDialogPostId(data);
						if (didComplete && postId != null) {
							mOnPublishListener.onComplete(postId);
						}
						else {
							mOnPublishListener.onFail("Canceled by user");
						}
					}
				});
			}
			else {
				shareWithWebDialog();
			}
		}
		else {
			if (mOnPublishListener != null) {
				String reason = Errors.getError(ErrorMsg.LOGIN);
				Logger.logError(PublishFeedDialogAction.class, reason, null);
				mOnPublishListener.onFail(reason);
			}
		}
	}

	private void shareWithWebDialog() {
		WebDialog feedDialog = (new WebDialog.FeedDialogBuilder(sessionManager.getActivity(), Session.getActiveSession(), mFeed.getBundle())).setOnCompleteListener(new OnCompleteListener() {
			@Override
			public void onComplete(Bundle values, FacebookException error) {
				if (error == null) {
					final String postId = values.getString("post_id");
					if (postId != null) {
						mOnPublishListener.onComplete(postId);
					}
					else {
						mOnPublishListener.onFail("Canceled by user");
					}
				}
				else if (error instanceof FacebookOperationCanceledException) {
					// User clicked the "x" button
					mOnPublishListener.onFail("Canceled by user");
				}
				else {
					mOnPublishListener.onException(error);
				}
			}

		}).build();
		feedDialog.show();
	}
}
