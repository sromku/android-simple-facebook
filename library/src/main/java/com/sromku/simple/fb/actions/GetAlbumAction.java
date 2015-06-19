package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.entities.Page.Properties;
import com.sromku.simple.fb.utils.JsonUtils;

public class GetAlbumAction extends GetAction<Album> {

    private Properties mProperties;

    public GetAlbumAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    public void setProperties(Properties properties) {
        mProperties = properties;
    }

    @Override
    protected String getGraphPath() {
        return getTarget();
    }

    @Override
    protected Bundle getBundle() {
        if (mProperties != null) {
            return mProperties.getBundle();
        }
        return null;
    }

    @Override
    protected Album processResponse(GraphResponse response) {
        return JsonUtils.fromJson(response.getRawResponse(), Album.class);
    }

}
