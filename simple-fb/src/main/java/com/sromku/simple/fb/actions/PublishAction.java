package com.sromku.simple.fb.actions;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Publishable;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnPublishListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Logger;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
        if (sessionManager.isLogin()) {
            if (!sessionManager.hasPendingRequest()) {
                // if we defined the publish permission

				/*
				 * We need also add one more check of next case: - if we gave
				 * extended permissions in runtime, but we don't have these
				 * permissions in the configuration
				 */
                final String neededPermission = mPublishable.getPermission().getValue();

                if (mOnPublishListener != null) {
                    mOnPublishListener.onThinking();
                }

                /*
                 * Check if session to facebook has needed publish
                 * permission. If not, we will ask user for this permission.
                 */
                if (!sessionManager.hasAccepted(neededPermission)) {
                    sessionManager.getLoginCallback().loginListener = new OnLoginListener() {

                        @Override
                        public void onException(Throwable throwable) {
                            returnFail(throwable != null ? String.valueOf(throwable.getMessage()) : "Got exception on asking for publish permissions");
                        }

                        @Override
                        public void onFail(String reason) {
                            returnFail(reason);
                        }

                        @Override
                        public void onLogin(String accessToken, List<Permission> acceptedPermissions, List<Permission> declinedPermissions) {
                            if (sessionManager.hasAccepted(neededPermission)) {
                                publishImpl(mPublishable, mOnPublishListener);
                            }
                        }

                        @Override
                        public void onCancel() {
                            returnFail("User canceled the publish dialog");
                        }

                        private void returnFail(String reason) {
                            Logger.logError(PublishAction.class, reason, null);
                            if (mOnPublishListener != null) {
                                mOnPublishListener.onFail(reason);
                            }
                        }

                    };
                    // build the needed permission for this action and request
                    Permission permission = mPublishable.getPermission();
                    List<String> permissions = new ArrayList<String>();
                    permissions.add(permission.getValue());
                    sessionManager.requestPublishPermissions(permissions);

                } else {
                    publishImpl(mPublishable, mOnPublishListener);
                }

            } else {
                return;
            }
        } else {
            if (mOnPublishListener != null) {
                String reason = Errors.getError(Errors.ErrorMsg.LOGIN);
                Logger.logError(PublishAction.class, reason, null);
                mOnPublishListener.onFail(reason);
            }
        }
    }

    private void publishImpl(Publishable publishable, final OnPublishListener onPublishListener) {

        AccessToken accessToken = sessionManager.getAccessToken();
        GraphRequest request = new GraphRequest(accessToken, mTarget + "/" + publishable.getPath(), publishable.getBundle(), HttpMethod.POST, new GraphRequest.Callback() {
            @Override
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error = response.getError();
                if (error != null) {
                    Logger.logError(GetAction.class, "Failed to publish", error.getException());
                    if (onPublishListener != null) {
                        onPublishListener.onException(error.getException());
                    }
                } else {
                    if (response.getRawResponse() == null) {
                        Logger.logError(GetAction.class, "The response GraphObject has null value. Response=" + response.toString(), null);
                    } else {
                        if (onPublishListener != null) {
                            JSONObject jsonObject = response.getJSONObject();
                            String id = jsonObject.optString("id");
                            onPublishListener.onComplete(id);
                        }
                    }
                }
            }
        });
        request.setVersion(configuration.getGraphVersion());
        GraphRequestAsyncTask task = new GraphRequestAsyncTask(request);
        task.execute();
    }

}
