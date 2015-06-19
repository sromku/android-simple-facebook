package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.AppRequest;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetAppRequestsAction extends GetAction<List<AppRequest>> {

    public GetAppRequestsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return String.format("%s/%s", getTarget(), GraphPath.APPREQUESTS);
    }

    @Override
    protected List<AppRequest> processResponse(GraphResponse response) {
        Utils.DataResult<AppRequest> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<AppRequest>>() {
        }.getType());
        return dataResult.data;
    }

}
