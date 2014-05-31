package com.sromku.simple.fb.actions;

import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Feed;
import com.sromku.simple.fb.entities.Story;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class PublishStoryDialogAction extends AbstractAction {

	private OnPublishListener mOnPublishListener;
	private Story mStory;

	public PublishStoryDialogAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setStory(Story story) {
		mStory = story;
	}

	public void setOnPublishListener(OnPublishListener onPublishListener) {
		mOnPublishListener = onPublishListener;
	}

	@Override
	protected void executeImpl() {
		if (sessionManager.isLogin(true)) {

		} else {
			if (mOnPublishListener != null) {
				String reason = Errors.getError(ErrorMsg.LOGIN);
				Logger.logError(PublishStoryDialogAction.class, reason, null);
				mOnPublishListener.onFail(reason);
			}
		}
	}

}
