package com.sromku.simple.fb.actions;

import com.sromku.simple.fb.SessionManager;

public class PublishStoryDialogAction extends AbstractAction {

	public PublishStoryDialogAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected void executeImpl() {
		
	}

	// private OnPublishListener mOnPublishListener;
	// private Story mStory;
	//
	// public void setStory(Story story) {
	// mStory = story;
	// }
	//
	// public void setOnPublishListener(OnPublishListener onPublishListener) {
	// mOnPublishListener = onPublishListener;
	// }
	//
	// @Override
	// protected void executeImpl() {
	// if (sessionManager.isLogin(true)) {
	//
	// } else {
	// if (mOnPublishListener != null) {
	// String reason = Errors.getError(ErrorMsg.LOGIN);
	// Logger.logError(PublishStoryDialogAction.class, reason, null);
	// mOnPublishListener.onFail(reason);
	// }
	// }
	// }

}
