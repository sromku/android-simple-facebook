package com.sromku.simple.fb.actions;

import java.util.Iterator;
import java.util.Map.Entry;

import android.os.Bundle;

import com.facebook.model.GraphObject;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.FacebookDialog.OpenGraphActionDialogFeature;
import com.facebook.widget.FacebookDialog.PendingCall;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Story;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.utils.Logger;

public class PublishStoryDialogAction extends AbstractAction {

	private OnPublishListener mOnPublishListener;
	private Story mStory;

	public PublishStoryDialogAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setOnPublishListener(OnPublishListener onPublishListener) {
		mOnPublishListener = onPublishListener;
	}
	
	public void setStory(Story story) {
		mStory = story;
	}

	@Override
	protected void executeImpl() {
		if (FacebookDialog.canPresentOpenGraphActionDialog(sessionManager.getActivity(), OpenGraphActionDialogFeature.OG_ACTION_DIALOG)) {
			
			FacebookDialog shareDialog = null;
			
			/*
			 * Publishing open graph can be in 2 ways: 1. Publish actions on
			 * app-owned objects Means, you as developer of the app define Open
			 * Graph Object on your server with <meta> tags or you have
			 * published object to facebook server. This is predefined Object
			 * and user can't change it. 2. Publish actions on user-owned
			 * objects You didn't add anything to server, but you give the user
			 * the option to define Object Graph properties from the app and
			 * publish it.
			 */

			String objectId = mStory.getStoryObject().getId();
			String objectUrl = mStory.getStoryObject().getHostedUrl();
			final boolean predefineObject;
			if (objectId != null || objectUrl != null) {
				predefineObject = true;
			} else {
				predefineObject = false;
			}

			// set the option 1
			if (predefineObject) {
				OpenGraphAction action = OpenGraphAction.Factory.createForPost(mStory.getPath());
				action.setProperty(mStory.getStoryObject().getNoun(), objectId != null ? objectId : objectUrl);
				Iterator<String> actionProperties = mStory.getStoryAction().getParams().keySet().iterator();
				while (actionProperties.hasNext()) {
					String property = actionProperties.next();
					action.setProperty(property, mStory.getStoryAction().getParams().get(property));
				}
				
				// set share dialog
				shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(sessionManager.getActivity(), action, mStory.getStoryObject().getNoun()).build();
				
			} else {
				// set the option 2
				OpenGraphObject object = OpenGraphObject.Factory.createForPost(mStory.getObjectType());
				Iterator<String> objectProperties = mStory.getStoryObject().getObjectProperties().keySet().iterator();
				while (objectProperties.hasNext()) {
					String property = objectProperties.next();
					object.setProperty(property, mStory.getStoryObject().getObjectProperties().get(property));
				}

				// set custom object properties
				GraphObject data = mStory.getStoryObject().getData();
				if (data != null) {
					for (Entry<String, Object> property : data.asMap().entrySet()) {
						object.getData().setProperty(property.getKey(), property.getValue());
					}
				}

				OpenGraphAction action = OpenGraphAction.Factory.createForPost(mStory.getPath());
				action.setProperty(mStory.getStoryObject().getNoun(), object);
				
				// set custom action properties
				Iterator<String> actionProperties = mStory.getStoryAction().getParams().keySet().iterator();
				while (actionProperties.hasNext()) {
					String property = actionProperties.next();
					action.setProperty(property, mStory.getStoryAction().getParams().get(property));
				}
						
				shareDialog = new FacebookDialog.OpenGraphActionDialogBuilder(sessionManager.getActivity(), action, mStory.getStoryObject().getNoun()).build();
			}

			PendingCall pendingCall = shareDialog.present();
			sessionManager.trackFacebookDialogPendingCall(pendingCall, new FacebookDialog.Callback() {

				@Override
				public void onError(PendingCall pendingCall, Exception error, Bundle data) {
					sessionManager.untrackPendingCall();
					Logger.logError(PublishStoryDialogAction.class, "Failed to share by using native dialog", error);
					if ("".equals(error.getMessage())) {
						Logger.logError(PublishStoryDialogAction.class, "Make sure to have 'app_id' meta data value in your manifest", error);
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
			mOnPublishListener.onFail("Open graph sharing dialog isn't supported");
		}
	}

}
