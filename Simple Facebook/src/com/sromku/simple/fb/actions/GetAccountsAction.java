package com.sromku.simple.fb.actions;

import java.util.ArrayList;
import java.util.List;

import com.facebook.Response;
import com.facebook.model.GraphObject;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Account;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

public class GetAccountsAction extends GetAction<List<Account>> {

	public GetAccountsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.ACCOUNTS;
	}

	@Override
	protected List<Account> processResponse(Response response) {
		List<GraphObject> graphObjects = Utils.typedListFromResponse(response, GraphObject.class);
		List<Account> groups = new ArrayList<Account>(graphObjects.size());
		for (GraphObject graphObject : graphObjects) {
			Account group = Account.create(graphObject);
			groups.add(group);
		}
		return groups;
	}

}
