package com.sromku.simple.fb.actions;

import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestAsyncTask;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Device;
import com.sromku.simple.fb.listeners.OnConnectDeviceListener;
import com.sromku.simple.fb.utils.JsonUtils;
import com.sromku.simple.fb.utils.Logger;
import com.sromku.simple.fb.utils.Utils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Locale;

public class ConnectDeviceAction {

    private OnConnectDeviceListener mOnActionListener;
    private final Permission[] mPermissions;

    public ConnectDeviceAction(Permission[] permissions) {
        mPermissions = permissions;
    }

    public void setActionListener(OnConnectDeviceListener actionListener) {
        mOnActionListener = actionListener;
    }

    public void execute() {
        try {
            if (mPermissions == null || mPermissions.length == 0) {
                mOnActionListener.onFail("Must set needed permissions for this device");
                return;
            }

            // set the link
            String appId = SimpleFacebook.getConfiguration().getAppId();
            String scope = Utils.join(Permission.convert(Arrays.asList(mPermissions)).iterator(), ",");

            String url = "https://graph.facebook.com/oauth/device?" +
                    "type=device_code" +
                    "&client_id=%s" +
                    "&scope=%s";

            url = String.format(Locale.US, url, appId, scope);

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
                        if (response.getRawResponse() == null) {
                            Logger.logError(GetAction.class, "The response GraphObject has null value. Response=" + response.toString(), null);
                        } else {
                            Device device = JsonUtils.fromJson(response.getRawResponse(), Device.class);
                            mOnActionListener.onComplete(device);
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
