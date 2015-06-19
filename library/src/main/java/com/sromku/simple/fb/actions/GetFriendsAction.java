package com.sromku.simple.fb.actions;

import android.os.Bundle;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetFriendsAction extends GetAction<List<Profile>> {

    private Properties mProperties;

    public GetFriendsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    public void setProperties(Properties properties) {
        mProperties = properties;
    }

    @Override
    protected String getGraphPath() {
        return String.format("%s/%s", getTarget(), GraphPath.FRIENDS);
    }

    @Override
    protected Bundle getBundle() {
        if (mProperties != null) {
            return mProperties.getBundle();
        }
        return null;
    }

    @Override
    protected List<Profile> processResponse(GraphResponse response) {
        Utils.DataResult<Profile> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Profile>>() {
        }.getType());
        return dataResult.data;
    }

}
