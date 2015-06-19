package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Page;
import com.sromku.simple.fb.entities.Page.Properties;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetPagesAction extends GetAction<List<Page>> {

    private Properties mProperties;

    public GetPagesAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    public void setProperties(Properties properties) {
        mProperties = properties;
    }

    @Override
    protected Bundle getBundle() {
        if (mProperties != null) {
            return mProperties.getBundle();
        }
        return null;
    }

    @Override
    protected List<Page> processResponse(GraphResponse response) {
        Utils.DataResult<Page> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Page>>() {
        }.getType());
        return dataResult.data;
    }

}
