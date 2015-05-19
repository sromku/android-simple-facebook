package com.sromku.simple.fb.actions;

import com.facebook.GraphResponse;
import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.entities.Account;
import com.sromku.simple.fb.utils.GraphPath;
import com.sromku.simple.fb.utils.Utils;

import java.util.List;

public class GetAccountsAction extends GetAction<List<Account>> {

	public GetAccountsAction(SessionManager sessionManager) {
		super(sessionManager);
	}

	@Override
	protected String getGraphPath() {
		return getTarget() + "/" + GraphPath.ACCOUNTS;
	}

	@Override
	protected List<Account> processResponse(GraphResponse response) {
		List<Account> accounts = Utils.typedListFromResponse(response);
		return accounts;
	}

}
