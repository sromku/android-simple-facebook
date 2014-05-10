package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Notification;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetNotificationsAction extends GetAction<List<Notification>> {

	public GetNotificationsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.NOTIFICATIONS);
	}

	@Override
	protected List<Notification> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Notification> notifications = new ArrayList<Notification>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Notification notification = Notification.create(graphObject);
			notifications.add(notification);
		}
		return notifications;
	}

}
