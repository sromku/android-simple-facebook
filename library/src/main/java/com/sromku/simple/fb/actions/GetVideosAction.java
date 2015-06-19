package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Video;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetVideosAction extends GetAction<List<Video>> {

    public GetVideosAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget() + "/" + GraphPath.VIDEOS;
    }

    @Override
    protected List<Video> processResponse(GraphResponse response) {
        Utils.DataResult<Video> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Video>>() {
        }.getType());
        return dataResult.data;
    }

}
