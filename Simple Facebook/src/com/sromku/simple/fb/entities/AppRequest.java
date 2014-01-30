package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class AppRequest {

    private static final String ID = "id";

    private final GraphObject mGraphObject;
    private String mRequestId;
    private Application mApplication;
    private User mTo;
    private User mFrom;
    private String mData;
    private String mMessage;
    private Long mCreatedTime;

    private AppRequest(GraphObject graphObject) {
	mGraphObject = graphObject;

	// request id
	mRequestId = String.valueOf(graphObject.getProperty(ID));
	
	// create application
	GraphObject applicationGraphObject = graphObject.getPropertyAs("application", GraphObject.class);
	mApplication = Application.create(applicationGraphObject);
	
	// to
	mTo = Utils.createUser(graphObject, "to");
	
	// from
	mFrom = Utils.createUser(graphObject, "from");
	
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

    public Application getApplication() {
	return mApplication;
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
}
