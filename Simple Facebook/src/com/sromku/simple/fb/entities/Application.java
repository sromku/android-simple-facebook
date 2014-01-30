package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class Application {

    private static final String ID = "id";
    private static final String NAME = "name";
    
    private String mAppId = null;
    private String mAppName = null;
    private String mAppNamespace = null;

    private Application(GraphObject graphObject) {
	// application name
	mAppName = Utils.getPropertyInsideProperty(graphObject, "application", NAME);

	// application namespace
	mAppNamespace = Utils.getPropertyInsideProperty(graphObject, "application", "namespace");

	// application id
	mAppId = Utils.getPropertyInsideProperty(graphObject, "application", ID);
    }

    public static Application create(GraphObject graphObject) {
	return new Application(graphObject);
    }

    public String getAppId() {
	return mAppId;
    }

    public String getAppName() {
	return mAppName;
    }

    public String getAppNamespace() {
	return mAppNamespace;
    }
}
