package com.sromku.simple.fb.entities;

import org.json.JSONObject;

import com.facebook.model.GraphObject;

public class AppRequest {

    private static final String ID = "id";
    private static final String NAME = "name";

    private final GraphObject mGraphObject;
    private String mRequestId = null;
    private String mAppId = null;
    private String mAppName = null;
    private String mAppNamespace = null;
    private User mTo = null;
    private User mFrom = null;
    private String mData;
    private String mMessage;
    private Long mCreatedTime;

    private AppRequest(GraphObject graphObject) {
	mGraphObject = graphObject;

	// request id
	mRequestId = String.valueOf(graphObject.getProperty(ID));
	
	// application name
	mAppName = getPropertyInsideProperty(graphObject, "application", NAME);
	
	// application namespace
	mAppNamespace = getPropertyInsideProperty(graphObject, "application", "namespace");
	
	// application id
	mAppId = getPropertyInsideProperty(graphObject, "application", ID);
	
	// to
	String toId = getPropertyInsideProperty(graphObject, "to", ID);
	String toName = getPropertyInsideProperty(graphObject, "to", NAME);
	mTo = createUser(toId, toName);
	
	// from
	String fromId = getPropertyInsideProperty(graphObject, "from", ID);
	String fromName = getPropertyInsideProperty(graphObject, "from", NAME);
	mFrom = createUser(fromId, fromName);
	
	// data
	mData = String.valueOf(graphObject.getProperty("data"));
	
	// message
	mMessage = String.valueOf(graphObject.getProperty("message"));
	
	// create time
	mCreatedTime = Long.valueOf(String.valueOf(mGraphObject.getProperty("created_time"))); 
    }

    public static AppRequest create(GraphObject graphObject) {
	return new AppRequest(graphObject);
    }
    
    public GraphObject getGraphObject() {
	return mGraphObject;
    }

    public String getRequestId() {
	return mRequestId;
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

    public User getTo() {
	return mTo;
    }

    public User getFrom() {
	return mFrom;
    }

    public String getData() {
	return mData;
    }

    public String getMessage() {
	return mMessage;
    }

    public Long getCreatedTime() {
	return mCreatedTime;
    }

    private User createUser(final String id, final String name) {
	User user = new User() {

	    @Override
	    public String getName() {
		return name;
	    }

	    @Override
	    public String getId() {
		return id;
	    }
	};
	return user;
    }
    
    private String getPropertyInsideProperty(GraphObject graphObject, String parent, String child) {
	JSONObject jsonObject = (JSONObject) graphObject.getProperty(parent);
	String value = String.valueOf(jsonObject.opt(child));
	return value;
    }
}
