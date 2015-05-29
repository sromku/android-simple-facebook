package com.sromku.simple.fb.actions;

import com.sromku.simple.fb.SessionManager;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;

public abstract class AbstractAction {

	protected SessionManager sessionManager;
	protected SimpleFacebookConfiguration configuration = SimpleFacebook.getConfiguration();

	public AbstractAction(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public void execute() {
		executeImpl();
	}

	protected abstract void executeImpl();
}
