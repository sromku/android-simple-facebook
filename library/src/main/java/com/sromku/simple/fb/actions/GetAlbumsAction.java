package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Album;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetAlbumsAction extends GetAction<List<Album>> {

    public GetAlbumsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return String.format("%s/%s", getTarget(), GraphPath.ALBUMS);
    }

    @Override
    protected List<Album> processResponse(GraphResponse response) {
        Utils.DataResult<Album> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Album>>() {
        }.getType());
        return dataResult.data;
    }

}
