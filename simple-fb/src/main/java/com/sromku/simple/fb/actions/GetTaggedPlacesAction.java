package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.PlaceTag;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetTaggedPlacesAction extends GetAction<List<PlaceTag>> {

    public GetTaggedPlacesAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget() + "/" + GraphPath.TAGGED_PLACES;
    }

    @Override
    protected List<PlaceTag> processResponse(GraphResponse response) {
        Utils.DataResult<PlaceTag> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<PlaceTag>>() {
        }.getType());
        return dataResult.data;
    }
}
