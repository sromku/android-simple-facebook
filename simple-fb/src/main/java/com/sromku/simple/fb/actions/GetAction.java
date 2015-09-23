package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.listeners.OnActionListener;
import com.sromku.simple.fb.utils.Errors;
import com.sromku.simple.fb.utils.Errors.ErrorMsg;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

import org.json.JSONException;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GetAction<T> extends AbstractAction {

    private String mTarget = "me"; // default
    private String mEdge = null;
    private OnActionListener<T> mOnActionListener = null;
    private Cursor<T> mCursor = null;

    private GraphRequest.Callback mCallback = new GraphRequest.Callback() {
        @Override
        public void onCompleted(GraphResponse response) {
            final OnActionListener<T> actionListener = getActionListener();
            FacebookRequestError error = response.getError();
            if (error != null) {
                Logger.logError(GetAction.class, "Failed to get what you have requested", error.getException());
                if (actionListener != null) {
                    actionListener.onException(error.getException());
                }
            } else {
                if (response.getRawResponse() == null) {
                    Logger.logError(GetAction.class, "The response GraphObject has null value. Response=" + response.toString(), null);
                } else {
                    if (actionListener != null) {
                        T result = null;
                        try {
                            updateCursor(response);
                            result = processResponse(response);
                        } catch (Exception e) {
                            actionListener.onException(e);
                            return;
                        }
                        actionListener.onComplete(result);
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
        if (target != null) {
            mTarget = target;
        }
    }

    public void setActionListener(OnActionListener<T> actionListener) {
        mOnActionListener = actionListener;
    }

    @Override
    protected void executeImpl() {
        OnActionListener<T> actionListener = getActionListener();
        if (sessionManager.isLogin()) {
            AccessToken accessToken = sessionManager.getAccessToken();
            Bundle bundle = updateAppSecretProof(getBundle());
            GraphRequest request = new GraphRequest(accessToken, getGraphPath(), bundle, HttpMethod.GET);
            request.setVersion(configuration.getGraphVersion());
            runRequest(request);
        } else {
            String reason = Errors.getError(ErrorMsg.LOGIN);
            Logger.logError(getClass(), reason, null);
            if (actionListener != null) {
                actionListener.onFail(reason);
            }
        }
    }

    /**
     * Update the params to contain 'appsecret_proof' if it was asked in
     * SimpleFacebookConfiguration.
     *
     * @param bundle
     * @return Updated bundle
     * // @see https://developers.facebook.com/docs/graph-api/securing-requests
     */
    private Bundle updateAppSecretProof(Bundle bundle) {
        if (configuration.useAppsecretProof()) {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putString("appsecret_proof", Utils.encode(configuration.getAppSecret(), sessionManager.getAccessToken().getToken()));
        }
        return bundle;
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
        return null;
    }

    protected OnActionListener<T> getActionListener() {
        return mOnActionListener;
    }

    /**
     * It is better to override this method and implement your faster
     * conversion.
     */
    protected T processResponse(GraphResponse response) {
        Type type = mOnActionListener.getGenericType();
        if (type instanceof ParameterizedType) {
            Object arrayJson;
            try {
                arrayJson = response.getJSONObject().get("data");
            } catch (JSONException e) {
                return null;
            }
            T data = Utils.convert(String.valueOf(arrayJson), type);
            return data;
        }
        return Utils.convert(response, type);
    }

    void runRequest(GraphRequest request) {
        OnActionListener<T> actionListener = getActionListener();
        request.setCallback(mCallback);
        GraphRequestAsyncTask task = new GraphRequestAsyncTask(request);
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
    private void updateCursor(GraphResponse response) {
        if (mOnActionListener == null) {
            return;
        }

        if (mCursor == null) {
            mCursor = new Cursor<T>(GetAction.this);
        }

        GraphRequest requestNextPage = response.getRequestForPagedResults(GraphResponse.PagingDirection.NEXT);
        if (requestNextPage != null) {
            requestNextPage.setCallback(mCallback);
        }
        mCursor.setNextPage(requestNextPage);

        GraphRequest requestPrevPage = response.getRequestForPagedResults(GraphResponse.PagingDirection.PREVIOUS);
        if (requestPrevPage != null) {
            requestPrevPage.setCallback(mCallback);
        }
        mCursor.setPrevPage(requestPrevPage);
        mOnActionListener.setCursor(mCursor);
    }
}
