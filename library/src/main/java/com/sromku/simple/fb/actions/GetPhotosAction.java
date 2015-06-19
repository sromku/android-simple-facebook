package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Photo;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetPhotosAction extends GetAction<List<Photo>> {

    public GetPhotosAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget() + "/" + GraphPath.PHOTOS;
    }

    @Override
    protected List<Photo> processResponse(GraphResponse response) {
        Utils.DataResult<Photo> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Photo>>() {
        }.getType());
        return dataResult.data;
    }

}
