package com.sromku.simple.fb.actions;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

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

        ShareDialog shareDialog = new ShareDialog(sessionManager.getActivity());
        SharePhotoContent content = new SharePhotoContent.Builder()
                .addPhotos(Utils.extractBitmaps(mPhotos))
                .build();

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
        }
    }

}
