package com.sromku.simple.fb.actions;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.listeners.OnPublishListener;

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

        // build link content
        ShareLinkContent.Builder shareLinkContent = new ShareLinkContent.Builder()
                .setContentTitle(mFeed.getBundle().getString(Feed.Builder.Parameters.NAME))
                .setContentDescription(mFeed.getBundle().getString(Feed.Builder.Parameters.DESCRIPTION));

        // set link if possible
        String link = mFeed.getBundle().getString(Feed.Builder.Parameters.LINK);
        if (!TextUtils.isEmpty(link)) {
            shareLinkContent.setContentUrl(Uri.parse(link));
        }
        ShareLinkContent linkContent = shareLinkContent.build();

        // check if we can share
        ShareDialog shareDialog = new ShareDialog(sessionManager.getActivity());
        if (shareDialog.canShow(linkContent)) {
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
            shareDialog.show(linkContent);
        }
    }

}
