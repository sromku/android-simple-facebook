package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.widget.ShareDialog;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Story;
import com.sromku.simple.fb.listeners.OnPublishListener;

import org.json.JSONObject;

import java.util.Iterator;

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

        /*
         * Publishing open graph can be in 2 ways:
         *
         * 1. Publish actions on app-owned objects Means, you as developer of the app define Open
         * Graph Object on your server with <meta> tags or you have
         * published object to facebook server. This is predefined Object
         * and user can't change it.
         *
         * 2. Publish actions on user-owned objects You didn't add anything to server,
         * but you give the user the option to define Object Graph properties from the app and
         * publish it.
         *
         */

        ShareDialog shareDialog = new ShareDialog(sessionManager.getActivity());
        ShareOpenGraphContent.Builder builder = new ShareOpenGraphContent.Builder();

        // build the content
        String objectId = mStory.getStoryObject().getId();
        String objectUrl = mStory.getStoryObject().getHostedUrl();
        final boolean predefineObject;
        if (objectId != null || objectUrl != null) {
            predefineObject = true;
        } else {
            predefineObject = false;
        }

        String objectType = mStory.getObjectType();
        String storyPath = mStory.getPath();

        // set the option 1
        if (predefineObject) {

            // action
            ShareOpenGraphAction.Builder actionBuilder = new ShareOpenGraphAction.Builder();

            actionBuilder.putString(objectType, objectId != null ? objectId : objectUrl);
            Iterator<String> actionProperties = mStory.getStoryAction().getParams().keySet().iterator();
            while (actionProperties.hasNext()) {
                String property = actionProperties.next();
                actionBuilder.putString(property, String.valueOf(mStory.getStoryAction().getParams().get(property)));
            }

            actionBuilder.setActionType(storyPath);
            builder.setAction(actionBuilder.build());

        } else {

            // object
            ShareOpenGraphObject.Builder objectBuilder = new ShareOpenGraphObject.Builder();

            // set the option 2
            Bundle objectProperties = mStory.getStoryObject().getObjectProperties();
            Iterator<String> iterator = objectProperties.keySet().iterator();
            String OG = "og:";
            while (iterator.hasNext()) {
                String property = iterator.next();
                objectBuilder.putString(OG + property, String.valueOf(objectProperties.get(property)));
            }

            // set type
            objectBuilder.putString(OG + "type", objectType);

            // set custom object properties
            JSONObject data = mStory.getStoryObject().getData();
            String OG_CUSTOM = configuration.getNamespace() + ":";
            if (data != null) {
                Iterator<String> keys = data.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    objectBuilder.putString(OG_CUSTOM + key, String.valueOf(data.opt(key)));
                }
            }

            ShareOpenGraphObject object = objectBuilder.build();

            // action
            ShareOpenGraphAction.Builder actionBuilder = new ShareOpenGraphAction.Builder();
            actionBuilder.putObject(objectType, object);

            // set custom action properties
            Iterator<String> actionProperties = mStory.getStoryAction().getParams().keySet().iterator();
            while (actionProperties.hasNext()) {
                String property = actionProperties.next();
                actionBuilder.putString(property, String.valueOf(mStory.getStoryAction().getParams().get(property)));
            }

            actionBuilder.setActionType(storyPath);
            builder.setAction(actionBuilder.build());

        }

        // build sharable content
        builder.setPreviewPropertyName(objectType);
        ShareOpenGraphContent content = builder.build();

        if (shareDialog.canShow(content)) {
            shareDialog.registerCallback(sessionManager.getCallbackManager(), new FacebookCallback<Sharer.Result>() {
                @Override
                public void onSuccess(Sharer.Result result) {
                    String postId = result.getPostId();
                    if (mOnPublishListener != null) {
                        mOnPublishListener.onComplete(postId);
                    }
                }

                @Override
                public void onCancel() {
                    if (mOnPublishListener != null) {
                        mOnPublishListener.onFail("Canceled by user");
                    }
                }

                @Override
                public void onError(FacebookException e) {
                    if (mOnPublishListener != null) {
                        mOnPublishListener.onFail(e.getMessage());
                    }
                }
            });
            shareDialog.show(content);
        } else {
            mOnPublishListener.onFail("Open graph sharing dialog isn't supported");
        }

    }

}
