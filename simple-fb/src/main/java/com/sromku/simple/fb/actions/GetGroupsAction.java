package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Group;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetGroupsAction extends GetAction<List<Group>> {

    public GetGroupsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return getTarget() + "/" + GraphPath.GROUPS;
    }

    @Override
    protected List<Group> processResponse(GraphResponse response) {
        Utils.DataResult<Group> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Group>>() {
        }.getType());
        return dataResult.data;
    }

}
