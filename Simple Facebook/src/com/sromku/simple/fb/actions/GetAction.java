package com.sromku.simple.fb.actions;

import java.lang.reflect.Type;

import android.os.Bundle;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Response.PagingDirection;
import com.facebook.Session;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

public class GetAction<T> extends AbstractAction {

	private String mTarget = "me"; // default
	private String mEdge = null;
	private OnActionListener<T> mOnActionListener = null;
	private Cursor<T> mCursor = null;

	private Request.Callback mCallback = new Request.Callback() {
		@Override
		public void onCompleted(Response response) {
			final OnActionListener<T> actionListener = getActionListener();
			FacebookRequestError error = response.getError();
			if (error != null) {
				Logger.logError(GetAction.class, "Failed to get what you have requested", error.getException());
				if (actionListener != null) {
					actionListener.onException(error.getException());
				}
			}
			else {
				if (response.getGraphObject() == null) {
					Logger.logError(GetAction.class, "The response GraphObject has null value. Response=" + response.toString(), null);
				}
				else {
					if (actionListener != null) {
						try {
							updateCursor(response);
							T result = processResponse(response);
							actionListener.onComplete(result);
						}
						catch (Exception e) {
							actionListener.onException(e);
						}
					}
				}
			}
		}
	};

	public GetAction(SessionManager sessionManager) {
		super(sessionManager);
	}
	
	public void setEdge(String edge) {
		mEdge = edge;
	}

	public void setTarget(String target) {
		mTarget = target;
	}

	public void setActionListener(OnActionListener<T> actionListener) {
		mOnActionListener = actionListener;
	}

	@Override
	protected void executeImpl() {
		OnActionListener<T> actionListener = getActionListener();
		if (sessionManager.isLogin(true)) {
			Session session = sessionManager.getActiveSession();
			Request request = new Request(session, getGraphPath(), getBundle(), HttpMethod.GET);
			runRequest(request);
		}
		else {
			String reason = Errors.getError(ErrorMsg.LOGIN);
			Logger.logError(getClass(), reason, null);
			if (actionListener != null) {
				actionListener.onFail(reason);
			}
		}
	}

	protected String getTarget() {
		return mTarget;
	}
	
	protected String getGraphPath() {
		if (mEdge != null) {
			return mTarget + "/" + mEdge;
		}
		return mTarget;
	}

	protected Bundle getBundle() {
		Bundle bundle = new Bundle();
		bundle.putString("date_format", "U");
		return bundle;
	}

	protected OnActionListener<T> getActionListener() {
		return mOnActionListener;
	}

	/**
	 * It is better to override this method and implement your faster
	 * conversion.
	 */
	protected T processResponse(Response response) {
		Type type = mOnActionListener.getGenericType();
		return Utils.convert(response, type);
	}

	void runRequest(Request request) {
		OnActionListener<T> actionListener = getActionListener();
		request.setCallback(mCallback);
		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
		if (actionListener != null) {
			actionListener.onThinking();
		}
	}

	/**
	 * set next and prev pages requests
	 * 
	 * @param response
	 */
	private void updateCursor(Response response) {
		if (mOnActionListener == null) {
			return;
		}

		if (mCursor == null) {
			mCursor = new Cursor<T>(GetAction.this);
		}

		Request requestNextPage = response.getRequestForPagedResults(PagingDirection.NEXT);
		if (requestNextPage != null) {
			requestNextPage.setCallback(mCallback);
		}
		mCursor.setNextPage(requestNextPage);

		Request requestPrevPage = response.getRequestForPagedResults(PagingDirection.PREVIOUS);
		if (requestPrevPage != null) {
			requestPrevPage.setCallback(mCallback);
		}
		mCursor.setPrevPage(requestPrevPage);
		mOnActionListener.setCursor(mCursor);
	}
}
