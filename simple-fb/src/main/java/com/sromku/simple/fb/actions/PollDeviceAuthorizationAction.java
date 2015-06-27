package com.sromku.simple.fb.actions;

import android.text.TextUtils;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnAuthorizationDeviceListener;
import com.sromku.simple.fb.utils.Logger;

import org.json.JSONException;

import java.lang.reflect.Field;
import java.util.Locale;

public class PollDeviceAuthorizationAction {

    private OnAuthorizationDeviceListener mOnActionListener;
    private final String mCode;

    public PollDeviceAuthorizationAction(String code) {
        mCode = code;
    }

    public void setActionListener(OnAuthorizationDeviceListener actionListener) {
        mOnActionListener = actionListener;
    }

    public void execute() {
        try {
            if (TextUtils.isEmpty(mCode)) {
                mOnActionListener.onFail("Must set authorization code, that was retrieved from connecting device action");
                return;
            }

            // set the link
            String appId = SimpleFacebook.getConfiguration().getAppId();

            String url = "https://graph.facebook.com/oauth/device?" +
                    "type=device_token" +
                    "&client_id=%s" +
                    "&code=%s";

            url = String.format(Locale.US, url, appId, mCode);

            // create POST request
            GraphRequest request = new GraphRequest(null, "");
            request.setHttpMethod(HttpMethod.POST);
            Field field = GraphRequest.class.getDeclaredField("overriddenURL");
            field.setAccessible(true);
            field.set(request, url);
            request.setCallback(new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    FacebookRequestError error = response.getError();
                    if (error != null) {
                        Logger.logError(GetAction.class, "Failed to get what you have requested", error.getException());
                        mOnActionListener.onException(error.getException());
                    } else {
                        String rawResponse = response.getRawResponse();
                        if (rawResponse == null) {
                            Logger.logError(GetAction.class, "The response GraphObject has null value. Response=" + response.toString(), null);
                        } else {
                            try {
                                String accessToken = response.getJSONObject().getString("access_token");
                                mOnActionListener.onComplete(accessToken);
                            } catch (JSONException e) {
                                mOnActionListener.onFail("Failed to parse access token result");
                            }

                        }
                    }

                }
            });
            GraphRequestAsyncTask task = new GraphRequestAsyncTask(request);
            task.execute();
        } catch (Exception e) {
            mOnActionListener.onException(e);
        }

    }


}
