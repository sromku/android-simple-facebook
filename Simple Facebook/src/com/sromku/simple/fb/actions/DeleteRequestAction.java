package com.sromku.simple.fb.actions;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnDeleteListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class DeleteRequestAction extends AbstractAction {

	private OnDeleteListener mOnDeleteListener;
	private String mRequestId;

	public DeleteRequestAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setRequestId(String requestId) {
		mRequestId = requestId;
	}

	public void setOnDeleteListener(OnDeleteListener onDeleteRequestListener) {
		mOnDeleteListener = onDeleteRequestListener;
	}

	@Override
	protected void executeImpl() {
		if (sessionManager.isLogin(true)) {
			Session session = sessionManager.getActiveSession();
			Request request = new Request(session, mRequestId, null, HttpMethod.DELETE, new Request.Callback() {
				@Override
				public void onCompleted(Response response) {
					FacebookRequestError error = response.getError();
					if (error != null) {
						Logger.logError(DeleteRequestAction.class, "failed to delete requests", error.getException());
						if (mOnDeleteListener != null) {
							mOnDeleteListener.onException(error.getException());
						}
					}
					else {
						if (mOnDeleteListener != null) {
							mOnDeleteListener.onComplete(null);
						}
					}
				}
			});
			Request.executeBatchAsync(request);
		}
		else {
			String reason = Errors.getError(ErrorMsg.LOGIN);
			Logger.logError(DeleteRequestAction.class, reason, null);
			if (mOnDeleteListener != null) {
				mOnDeleteListener.onFail(reason);
			}
		}
	}

}
