package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

/**
 * Object that describes 'Application' on facebook side.
 * 
 * @author sromku
 */
public class Application {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String NAMESPACE = "namespace";

	private String mAppId = null;
	private String mAppName = null;
	private String mAppNamespace = null;

	private Application(GraphObject graphObject) {
		// application name
		mAppName = Utils.getPropertyString(graphObject, NAME);

		// application namespace
		mAppNamespace = Utils.getPropertyString(graphObject, NAMESPACE);

		// application id
		mAppId = Utils.getPropertyString(graphObject, ID);
	}

	public static Application create(GraphObject graphObject) {
		return new Application(graphObject);
	}

	/**
	 * @return Application id
	 */
	public String getAppId() {
		return mAppId;
	}

	/**
	 * @return Application name
	 */
	public String getAppName() {
		return mAppName;
	}

	/**
	 * @return Application namespace
	 */
	public String getAppNamespace() {
		return mAppNamespace;
	}
}
