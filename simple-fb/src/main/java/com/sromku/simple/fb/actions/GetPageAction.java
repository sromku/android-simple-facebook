package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.entities.Page.Properties;
import com.sromku.simple.fb.utils.JsonUtils;

public class GetPageAction extends GetAction<Page> {

    private Properties mProperties;

    public GetPageAction(SessionManager sessionManager) {
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
    protected Page processResponse(GraphResponse response) {
        return JsonUtils.fromJson(response.getRawResponse(), Page.class);
    }

}
