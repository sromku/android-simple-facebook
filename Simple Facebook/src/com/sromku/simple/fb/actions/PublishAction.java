package com.sromku.simple.fb.actions;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Publishable;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.listeners.OnReopenSessionListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;

public class PublishAction extends AbstractAction {

	private OnPublishListener mOnPublishListener;
	private Publishable mPublishable;
	private String mTarget = "me";

	public PublishAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	public void setPublishable(Publishable publishable) {
		mPublishable = publishable;
	}

	public void setTarget(String target) {
		mTarget = target;
	}

	public void setOnPublishListener(OnPublishListener onPublishListener) {
		mOnPublishListener = onPublishListener;
	}

	@Override
	protected void executeImpl() {
		if (sessionManager.isLogin(true)) {
			if (sessionManager.canMakeAdditionalRequest()) {
				// if we defined the publish permission

				/*
				 * We need also add one more check of next case: - if we gave
				 * extended permissions in runtime, but we don't have these
				 * permissions in the configuration
				 */
				if (configuration.getPublishPermissions().contains(mPublishable.getPermission().getValue())
						|| sessionManager.getActiveSessionPermissions().contains(mPublishable.getPermission().getValue())) {
					if (mOnPublishListener != null) {
						mOnPublishListener.onThinking();
					}

					/*
					 * Check if session to facebook has needed publish
					 * permission. If not, we will ask user for this permission.
					 */
					if (!sessionManager.getActiveSessionPermissions().contains(mPublishable.getPermission().getValue())) {
						sessionManager.getSessionStatusCallback().setOnReopenSessionListener(new OnReopenSessionListener() {
							@Override
							public void onSuccess() {
								publishImpl(mPublishable, mOnPublishListener);
							}

							@Override
							public void onNotAcceptingPermissions(Permission.Type type) {
								String reason = Errors.getError(ErrorMsg.CANCEL_PERMISSIONS_PUBLISH, String.valueOf(configuration.getPublishPermissions()));
								Logger.logError(PublishAction.class, reason, null);
								if (mOnPublishListener != null) {
									mOnPublishListener.onFail(reason);
								}
							}
						});
						sessionManager.extendPublishPermissions();
					} else {
						publishImpl(mPublishable, mOnPublishListener);
					}
				} else {
					String reason = Errors.getError(ErrorMsg.PERMISSIONS_PUBLISH, mPublishable.getPermission().getValue());
					Logger.logError(PublishAction.class, reason, null);
					if (mOnPublishListener != null) {
						mOnPublishListener.onFail(reason);
					}
				}
			} else {
				return;
			}
		} else {
			if (mOnPublishListener != null) {
				String reason = Errors.getError(ErrorMsg.LOGIN);
				Logger.logError(PublishAction.class, reason, null);
				mOnPublishListener.onFail(reason);
			}
		}
	}

	private void publishImpl(Publishable publishable, final OnPublishListener onPublishListener) {
		Session session = sessionManager.getActiveSession();
		Request request = new Request(session, mTarget + "/" + publishable.getPath(), publishable.getBundle(), HttpMethod.POST, new Request.Callback() {
			@Override
			public void onCompleted(Response response) {
				GraphObject graphObject = response.getGraphObject();
				if (graphObject != null) {
					JSONObject graphResponse = graphObject.getInnerJSONObject();
					String postId = null;
					try {
						postId = graphResponse.getString("id");
					} catch (JSONException e) {
						Logger.logError(PublishAction.class, "JSON error", e);
						postId = "no_id";
					}
					FacebookRequestError error = response.getError();
					if (error != null) {
						Logger.logError(PublishAction.class, "Failed to publish", error.getException());
						if (onPublishListener != null) {
							onPublishListener.onException(error.getException());
						}
					} else {
						if (onPublishListener != null) {
							onPublishListener.onComplete(postId);
						}
					}
				} else {
					Logger.logError(PublishAction.class, "The GraphObject in Response of publish action has null value. Response=" + response.toString(), null);
					FacebookRequestError error = response.getError();
					if (error != null) {
						Logger.logError(PublishAction.class, "Failed to publish", error.getException());
						if (onPublishListener != null) {
							onPublishListener.onException(error.getException());
						}
					} else if (onPublishListener != null) {
						onPublishListener.onFail("The returned value is null");
					}
				}
			}
		});
		RequestAsyncTask task = new RequestAsyncTask(request);
		task.execute();
	}

}
