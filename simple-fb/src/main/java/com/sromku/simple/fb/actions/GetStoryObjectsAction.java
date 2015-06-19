package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Story.StoryObject;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetStoryObjectsAction extends GetAction<List<StoryObject>> {

    private String mObjectName;

    public GetStoryObjectsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    public void setObjectName(String objectName) {
        mObjectName = objectName;
    }

    @Override
    protected String getGraphPath() {
        String namespace = configuration.getNamespace();
        return getTarget() + "/" + GraphPath.OBJECTS + "/" + namespace  + ":" + mObjectName;
    }

    @Override
    protected List<StoryObject> processResponse(GraphResponse response) {
        Utils.DataResult<StoryObject> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<StoryObject>>() {
        }.getType());
        return dataResult.data;
    }

}
