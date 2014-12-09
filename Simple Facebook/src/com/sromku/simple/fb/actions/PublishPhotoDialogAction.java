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

	public PublishPhotoDialogAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setPhotos(List<Photo> photos) {
		mPhotos = photos;
	}

	public void setPlace(String place) {
		mPlace = place;
	}

	public void setOnPublishListener(OnPublishListener onPublishListener) {
		mOnPublishListener = onPublishListener;
	}

	@Override
	protected void executeImpl() {
		if (FacebookDialog.canPresentShareDialog(sessionManager.getActivity(), ShareDialogFeature.PHOTOS)) {
			FacebookDialog shareDialog = new FacebookDialog.PhotoShareDialogBuilder(sessionManager.getActivity())
				.addPhotos(Utils.extractBitmaps(mPhotos))
				.setPlace(mPlace)
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
					mOnPublishListener.onFail("Have you added com.facebook.NativeAppCallContentProvider to your manifest? " + error.getMessage());
				}

				@Override
				public void onComplete(PendingCall pendingCall, Bundle data) {
					sessionManager.untrackPendingCall();
					boolean didComplete = FacebookDialog.getNativeDialogDidComplete(data);
					String postId = FacebookDialog.getNativeDialogPostId(data);
					String completeGesture = FacebookDialog.getNativeDialogCompletionGesture(data);
					if (completeGesture != null) {
						if (completeGesture.equals("post")) {
							mOnPublishListener.onComplete(postId != null ? postId : "no postId return");
						} else {
							mOnPublishListener.onFail("Canceled by user");
						}
					} else if (didComplete) {
						mOnPublishListener.onComplete(postId != null ? postId : "published successfully. (post id is not availaible if you are not logged in)");
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
