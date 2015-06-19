package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.google.gson.reflect.TypeToken;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Notification;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetNotificationsAction extends GetAction<List<Notification>> {

    public GetNotificationsAction(SessionManager sessionManager) {
        super(sessionManager);
    }

    @Override
    protected String getGraphPath() {
        return String.format("%s/%s", getTarget(), GraphPath.NOTIFICATIONS);
    }

    @Override
    protected List<Notification> processResponse(GraphResponse response) {
        Utils.DataResult<Notification> dataResult = Utils.convert(response, new TypeToken<Utils.DataResult<Notification>>() {
        }.getType());
        return dataResult.data;
    }

}
