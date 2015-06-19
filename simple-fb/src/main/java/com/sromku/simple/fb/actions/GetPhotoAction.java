package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.utils.JsonUtils;

public class GetPhotoAction extends GetAction<Photo> {

    public GetPhotoAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget();
    }

    @Override
    protected Photo processResponse(GraphResponse response) {
        return JsonUtils.fromJson(response.getRawResponse(), Photo.class);
    }

}
