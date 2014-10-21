package com.sromku.simple.fb.actions;

import java.util.List;

import android.os.Bundle;

import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.facebook.widget.FacebookDialog.ShareDialogFeature;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

public class PublishPhotoDialogAction extends AbstractAction {

	private OnPublishListener mOnPublishListener;
	private List<Photo> mPhotos;
	private String mPlace;
	private List<String> mTaggedFriends;

	public PublishPhotoDialogAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setPhotos(List<Photo> photos) {
		mPhotos = photos;
	}

	public void setPlace(String place) {
		mPlace = place;
	}

	public void setTaggedFriends(List<String> profileIds) {
		mTaggedFriends = profileIds;
	}

	public void setOnPublishListener(OnPublishListener onPublishListener) {
		mOnPublishListener = onPublishListener;
	}

	@Override
	protected void executeImpl() {
		if (FacebookDialog.canPresentShareDialog(sessionManager.getActivity(), ShareDialogFeature.PHOTOS)) {
			FacebookDialog shareDialog = new FacebookDialog.PhotoShareDialogBuilder(sessionManager.getActivity())
				.addPhotos(Utils.extractBitmaps(mPhotos))
				.build();
			PendingCall pendingCall = shareDialog.present();
			sessionManager.trackFacebookDialogPendingCall(pendingCall, new FacebookDialog.Callback() {

				@Override
				public void onError(PendingCall pendingCall, Exception error, Bundle data) {
					sessionManager.untrackPendingCall();
					Logger.logError(PublishPhotoDialogAction.class, "Failed to share by using native dialog", error);
					if ("".equals(error.getMessage())) {
						Logger.logError(PublishPhotoDialogAction.class, "Make sure to have 'app_id' meta data value in your manifest", error);
					}
					// TODO  callback - fail
				}

				@Override
				public void onComplete(PendingCall pendingCall, Bundle data) {
					sessionManager.untrackPendingCall();
					// redundant check, since
					// boolean didComplete =
					// FacebookDialog.getNativeDialogDidComplete(data);
					String postId = FacebookDialog.getNativeDialogPostId(data);
					String completeGesture = FacebookDialog.getNativeDialogCompletionGesture(data);

					// didComplete is meaningless when completeGesture can
					// determine the result
					if (completeGesture != null) {
						if (completeGesture.equals("post")) {
							mOnPublishListener.onComplete(postId != null ? postId : "no postId return");
						} else {
							mOnPublishListener.onFail("Canceled by user");
						}
					} else {
						mOnPublishListener.onFail("Canceled by user");
					}

				}
			});
		} else {
			mOnPublishListener.onFail("Photos sharing dialog isn't supported");
		}
	}

}
