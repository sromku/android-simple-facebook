package com.sromku.simple.fb.actions;

import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.utils.GraphPath;

public class GetInvitableFriendsAction  extends GetFriendsAction {

	public GetInvitableFriendsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return String.format("%s/%s", getTarget(), GraphPath.INVITABLE_FRIENDS);
	}

}
