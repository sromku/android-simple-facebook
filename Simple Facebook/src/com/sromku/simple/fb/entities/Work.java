package com.sromku.simple.fb.entities;

import com.facebook.model.GraphObject;
import com.sromku.simple.fb.utils.Utils;

public class Work {
    public static final String NAME = "name";
    public static final String EMPLOYER = "employer";
    public static final String LOCATION = "location";
    public static final String POSITION = "position";
    public static final String DESCRIPTION = "description";
    public static final String START_DATE = "start_date";
    public static final String END_DATE = "end_date";

    private User mEmployer;
    private Location mLocation;
    private String mPosition;
    private String mDescription;
    private String mStartDate;
    private String mEndDate;

    private Work(GraphObject graphObject) {
	
	 // employer
	mEmployer = Utils.createUser(graphObject, EMPLOYER);

	// location
	GraphObject location = Utils.getPropertyGraphObject(graphObject, LOCATION);
	mLocation = Location.create(location);

	// position
	mPosition = Utils.getPropertyInsideProperty(graphObject, POSITION, NAME);

	 // description
	mDescription = Utils.getPropertyString(graphObject, DESCRIPTION);

	 // start date
	mStartDate = Utils.getPropertyString(graphObject, START_DATE);

	 // end date
	mEndDate = Utils.getPropertyString(graphObject, END_DATE);
    }
    
    public static Work create(GraphObject graphObject) {
	return new Work(graphObject);
    }

    public User getEmployer() {
	return mEmployer;
    }

    public Location getLocation() {
	return mLocation;
    }

    public String getPosition() {
	return mPosition;
    }

    public String getDescription() {
	return mDescription;
    }

    public String getStartDate() {
	return mStartDate;
    }

    public String getEndDate() {
	return mEndDate;
    }
}
