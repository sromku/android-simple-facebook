package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Like;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetLikesAction extends GetAction<List<Like>> {

    public GetLikesAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget() + "/" + GraphPath.LIKES;
    }

    @Override
    protected List<Like> processResponse(GraphResponse response) {
        Utils.DataResult<Like> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Like>>() {
        }.getType());
        return dataResult.data;
    }
}
